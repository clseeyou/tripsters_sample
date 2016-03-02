package com.tripsters.sample.view;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.tripsters.sample.R;
import com.tripsters.sample.util.PatternUtils;

public class TTextView extends TextView {

    public TTextView(Context context) {
        super(context);

        init();
    }

    public TTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public TTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        setOnTouchListener(new OnTouchListener() {

            private final long mClickDelay = ViewConfiguration.getLongPressTimeout();
            private long mLastClickTime;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView widget = ((TextView) v);
                Spannable buffer = Spannable.Factory.getInstance().newSpannable(widget.getText());

                int action = event.getAction();

                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();

                    x += widget.getScrollX();
                    y += widget.getScrollY();

                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

                    if (link.length != 0) {
                        if (action == MotionEvent.ACTION_UP) {
                            if (System.currentTimeMillis() - mLastClickTime < mClickDelay) {
                                link[0].onClick(widget);
                            }
                        } else if (action == MotionEvent.ACTION_DOWN) {
                            mLastClickTime = System.currentTimeMillis();
                        }

                        return true;
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text)) {
            Spannable spannable = new SpannableStringBuilder(text);
            PatternUtils.matcher(getContext(), spannable);

            super.setText(spannable, type);
        } else {
            super.setText(text, type);
        }
    }

    @Override
    public void setLongClickable(boolean longClickable) {
        if (longClickable) {
            setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    String[] items = new String[] {getContext().getString(R.string.text_copy)};
                    new android.app.AlertDialog.Builder(getContext())
                            .setItems(items, new Dialog.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        ClipboardManager clip =
                                                (ClipboardManager) getContext().getSystemService(
                                                        Context.CLIPBOARD_SERVICE);
                                        clip.setPrimaryClip(ClipData.newPlainText(null, getText()));
                                    }
                                }
                            }).create().show();
                    return true;
                }
            });
        } else {
            super.setLongClickable(longClickable);
        }
    }
}
