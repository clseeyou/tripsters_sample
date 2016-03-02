package com.tripsters.sample;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

import com.tripsters.android.model.MediaInfo;
import com.tripsters.sample.adapter.GalleryAdapter;
import com.tripsters.sample.adapter.GalleryAdapter.GalleryListener;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.util.FileUtils;
import com.tripsters.sample.util.GalleryHelper;
import com.tripsters.sample.view.TitleBar;
import com.tripsters.sample.view.TitleBar.LeftType;
import com.tripsters.sample.view.TitleBar.RightType;

import java.util.ArrayList;
import java.util.List;

public class TGalleryActivity extends BaseActivity {

    public static final int TYPE_FROM_CAMERA = 0;
    public static final int TYPE_FROM_ACTIVITY = 1;

    // Sub activity request code
    private static final int REQUEST_CODE_CAMERA = 0;

    private TitleBar mTitleBar;
    private GridView mGridView;
    private GalleryAdapter mGalleryAdapter;

    private int mType;
    private int mMaxCount;
    private boolean mSingleType;
    private ArrayList<MediaInfo> mMediaInfos;
    private ArrayList<MediaInfo> mSelectedMediaInfos;

    private Uri mOriginPicUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);

        mType = getIntent().getIntExtra(Constants.Extra.TYPE, TYPE_FROM_CAMERA);
        mSelectedMediaInfos = getIntent().getParcelableArrayListExtra(Constants.Extra.MEDIA_INFOS);
        mMaxCount = getIntent().getIntExtra(Constants.Extra.MAX_COUNT, Constants.PIC_MAX_COUNT);
        mSingleType = getIntent().getBooleanExtra(Constants.Extra.SINGLE_TYPE, false);

        if (mMediaInfos == null) {
            mMediaInfos = new ArrayList<MediaInfo>();
        }
        if (mSelectedMediaInfos == null) {
            mSelectedMediaInfos = new ArrayList<MediaInfo>();
        }

        if (!FileUtils.hasSDCardMounted()) {
            ErrorToast.getInstance().showErrorMessage(R.string.pls_insert_sdcard);

            finish();
            return;
        }

        if (!FileUtils.haveFreeSpace()) {
            ErrorToast.getInstance().showErrorMessage(R.string.have_no_enough_external_space);

            finish();
            return;
        }

        if (mType == TYPE_FROM_CAMERA) {
            startCameraActivity();

            return;
        }

        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        mTitleBar.initTitleBar(LeftType.ICON_BACK, R.string.titlebar_gallery, RightType.TEXT_DONE);
        mTitleBar.setLeftClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                galleryCancel();
            }
        });
        updateDoneText();
        mGridView = (GridView) findViewById(R.id.gv_gallery);
        mGalleryAdapter = new GalleryAdapter(this, mSingleType, new GalleryListener() {

            @Override
            public void onPicSelected(MediaInfo mediaInfo, boolean selected, int position) {
                if (selected) {
                    mSelectedMediaInfos.remove(mediaInfo);

                    updateDoneText();
                    mGalleryAdapter.notifyData(mMediaInfos, mSelectedMediaInfos);
                } else {
                    if (mSelectedMediaInfos.size() < mMaxCount) {
                        mSelectedMediaInfos.add(mediaInfo);

                        updateDoneText();
                        mGalleryAdapter.notifyData(mMediaInfos, mSelectedMediaInfos);
                    } else {
                        ErrorToast.getInstance().showErrorMessage(
                                getString(R.string.gallery_max_selected, mMaxCount));
                    }
                }
            }

            @Override
            public void onPicClicked(MediaInfo mediaInfo, int position) {
                if (mSingleType) {
                    selectOnlyOne(mediaInfo.getPath());

                    galleryDone();
                } else {
//                    Utils.startMediaImagesActivity(TGalleryActivity.this, mMediaInfos, position);
                }
            }

            @Override
            public void onCameraClicked() {
                startCameraActivity();
            }
        });
        mGridView.setAdapter(mGalleryAdapter);

        loadData();
    }

    private void loadData() {
        new AsyncTask<Void, Void, List<MediaInfo>>() {

            @Override
            protected List<MediaInfo> doInBackground(Void... params) {
                return GalleryHelper.getAllMedia(TripstersApplication.mContext);
            }

            @Override
            protected void onPostExecute(List<MediaInfo> result) {
                mMediaInfos.clear();
                mMediaInfos.addAll(result);
                mGalleryAdapter.notifyData(mMediaInfos, mSelectedMediaInfos);
            }
        }.execute();
    }

    private void updateDoneText() {
        if (mSingleType || mSelectedMediaInfos.isEmpty()) {
            mTitleBar.setClickable(false);
            mTitleBar.setRightText(getString(R.string.done));
        } else {
            mTitleBar.setRightClick(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    galleryDone();
                }
            });
            mTitleBar.setRightText(getString(R.string.gallery_done, mSelectedMediaInfos.size(),
                    mMaxCount));
        }
    }

    private void galleryDone() {
        switch (mType) {
            case TYPE_FROM_CAMERA:
            case TYPE_FROM_ACTIVITY:
                Intent data = new Intent();
                data.putParcelableArrayListExtra(Constants.Extra.MEDIA_INFOS, mSelectedMediaInfos);
                setResult(RESULT_OK, data);
                break;

            default:
                break;
        }

        finish();
    }

    private void galleryCancel() {
        switch (mType) {
            case TYPE_FROM_CAMERA:
            case TYPE_FROM_ACTIVITY:
                setResult(RESULT_CANCELED);
                ;
                break;

            default:
                break;
        }

        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    // some device do not put the picture to input Uri
                    String orgPicPath =
                            FileUtils.getUriFile(mOriginPicUri.toString(), this).getAbsolutePath();

                    if ((!FileUtils.doesExisted(orgPicPath) || FileUtils.getFileSize(orgPicPath) == 0)) {
                        getContentResolver().delete(mOriginPicUri, null, null);

                        if (data != null && data.getData() != null) {
                            mOriginPicUri = data.getData();
                        }
                    }

                    selectOnlyOne(orgPicPath);

                    galleryDone();
                    break;
            }
        } else { // 取消
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    if (mType == TYPE_FROM_CAMERA) {
                        galleryCancel();
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startCameraActivity() {
        ContentValues values = new ContentValues();
        mOriginPicUri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);

        if (mOriginPicUri != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mOriginPicUri);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        }
    }

    private void selectOnlyOne(String path) {
        MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.setType(MediaInfo.PIC);
        mediaInfo.setPath(path);
        mSelectedMediaInfos.clear();
        mSelectedMediaInfos.add(mediaInfo);
    }
}
