package com.tripsters.sample.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tripsters.sample.R;

public class LetterIndexBar extends View {

    /**
     * 索引条目总数
     */
    public static final int INDEX_COUNT_DEFAULT = 27;


    /**
     * 搜索图标的标识
     */
    public static final String SEARCH_ICON_LETTER = "";
    /**
     * 索引条目高度
     */
    private int mItemHeight;

    private Paint mPaint = new Paint();

    private String[] mIndexLetter;

    private boolean[] mNeedIndex;

    private int count = INDEX_COUNT_DEFAULT;
    /**
     * 当前选中索引
     */
    private int mIndex;
    /**
     * 索引变化监听器
     */
    private OnIndexChangeListener mListener;
    private int mItemPadding;
    private boolean mTouching;

    private RectF mRect = new RectF();
    private int mOrgTextSzie;


    private Drawable mSeatchIcon;

    public LetterIndexBar(Context context) {
        super(context);
        init();
    }

    public LetterIndexBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LetterIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.tb_light_grey));
        mOrgTextSzie = getResources().getDimensionPixelSize(R.dimen.index_bar_size);
    }

    /**
     * 设置哪些索引位置是有效的
     * 
     * @param mark
     */
    public void setIndexMark(boolean[] mark) {
        if (mark == null) {
            return;
        }
        mNeedIndex = mark;
        invalidate();
    }

    public void setIndexLetter(String[] letter) {
        if (letter == null) {
            return;
        }
        mIndexLetter = letter;
        count = mIndexLetter.length;
        mIndex = -1;

        invalidate();
    }

    public void setIndexChangeListener(OnIndexChangeListener listener) {
        mListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTouching) {
            int color = mPaint.getColor();
            mPaint.setColor(0x88777788);
            canvas.drawRoundRect(mRect, getMeasuredWidth() / 2, getMeasuredWidth() / 2, mPaint);
            mPaint.setColor(color);
        }
        int top = 0;
        int textSize = mOrgTextSzie;
        if (textSize > mItemHeight) {
            textSize = mItemHeight;
        } else {
            textSize = mOrgTextSzie;
        }
        mPaint.setTextSize(textSize);
        int textWidth;
        int left;
        String title;
        if (mIndexLetter == null) {
            char letter = 'A';
            for (int i = 0; i < count; i++) {
                top = (mItemHeight * i) + getPaddingTop() + textSize + mItemPadding;
                if (mNeedIndex == null || mNeedIndex[i]) {
                    if (i == count - 1) {
                        title = "#";
                    } else {
                        title = String.valueOf((char) (letter++));
                    }
                    textWidth = (int) mPaint.measureText(title);
                    left = (getMeasuredWidth() - textWidth) / 2;
                    canvas.drawText(title, left, top, mPaint);
                }
            }
        } else {
            for (int i = 0; i < count; i++) {
                top = (mItemHeight * i) + getPaddingTop() + textSize + mItemPadding;
                if (mNeedIndex == null || mNeedIndex[i]) {
                    title = mIndexLetter[i];
                    if (title.equals(SEARCH_ICON_LETTER)) {
                        textWidth = (int) mPaint.measureText("M");// m最宽，搜索图标宽度与M同
                        left = (getMeasuredWidth() - textWidth) / 2;
                        if (mSeatchIcon == null) {
                            mSeatchIcon = getResources().getDrawable(R.drawable.icon_letter_search);
                        }
                        mSeatchIcon.setBounds(left, top - left, textWidth + left, textWidth + top
                                - left);
                        mSeatchIcon.draw(canvas);
                    } else {
                        textWidth = (int) mPaint.measureText(title);
                        left = (getMeasuredWidth() - textWidth) / 2;
                        canvas.drawText(title, left, top, mPaint);
                    }
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mItemHeight = (height - getPaddingTop() - getPaddingBottom()) / count;
        mItemPadding = (int) ((mItemHeight - mPaint.getTextSize()) / 2);
        int width = mOrgTextSzie + getPaddingLeft() + getPaddingRight();
        setMeasuredDimension(width, heightMeasureSpec);
        mRect.set(0, getPaddingTop(), getMeasuredWidth(), height - getPaddingBottom());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mTouching = true;
                int y = (int) event.getY();
                int index = (y - getPaddingTop()) / mItemHeight;
                if (index != mIndex && (mNeedIndex == null || mNeedIndex[index]) && index < count
                        && index >= 0) {
                    mIndex = index;
                    if (mListener != null) {
                        mListener.onIndexChange(mIndex);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_UP:
                mTouching = false;
                break;
            default:
                // 不处理其他事件
                break;
        }
        invalidate();
        return true;
    }

    public interface OnIndexChangeListener {
        void onIndexChange(int index);
    }
}
