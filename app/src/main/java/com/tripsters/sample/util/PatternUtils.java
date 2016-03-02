package com.tripsters.sample.util;

import java.util.Stack;

import android.content.Context;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;

import com.tripsters.sample.R;

public class PatternUtils {

    static class WebSpannable extends ClickableSpan {

        private Context mContext;
        private String mUrl;

        WebSpannable(Context context, String url) {
            this.mContext = context;
            this.mUrl = url;
        }

        @Override
        public void onClick(View widget) {
            IntentUtils.openUrlByBrowser(mContext, mUrl);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(mContext.getResources().getColor(R.color.tb_blue));
            ds.setUnderlineText(true);
        }
    }

    public static final String HTTP_PREFIX = "http://";
    public static final String HTTPS_PREFIX = "https://";

    // private static final String PUNCTUATION = "`~!@#\\$%^&*()=+[]{}/?<>,.:\u00D7\u00B7";
    private static final String LEGAL_URL_PUNCTUATION = "/?:-._~!$&'()*+,;=%#";

    // private static enum CharType {
    // EMOJI_UCS32, EMOJI_UCS16, NORMAL;
    //
    // /**
    // * 16字节emoji
    // */
    // private static final char[] UCS_2_EMOJI = {0x2600, 0x2601, 0x2614, 0x2615, 0x26A1, 0x26BD,
    // 0x270A, 0x270C, 0x2764};
    //
    // public static CharType check(char codePoint) {
    // /**
    // * 我们支持的大部分emoji表情，都是UCS32，编码位于unicode5的代理区，既D800到DFFF
    // */
    // if ((codePoint >= 0xD800) && (codePoint <= 0xDFFF)) {
    // return EMOJI_UCS32;
    // }
    //
    // /**
    // * 剩下UCS16的emoji表情很少，放到一个数组中二分查找
    // */
    // if (Arrays.binarySearch(UCS_2_EMOJI, codePoint) > -1) {
    // return EMOJI_UCS16;
    // }
    //
    // return NORMAL;
    // }
    // }

    private static class StateData {

        /**
         * 状态机的各个状态。
         */
        static final int NORMAL = 1;
        // static final int AT = 2;
        // static final int TOPIC = 3;
        // static final int PHONE = 4;
        static final int WEB = 5;
        static final int WEB_HTTP = 6;
        static final int WEB_HASDOT = 7;
        static final int EMOTION = 8;

        int state = NORMAL;
        int spanStart = -1;
        int spanEnd = -1;
    }

    private static final Stack<StateData> sPrefixStack = new Stack<StateData>();

    public static void matcher(Context context, Spannable text) {
        if (sPrefixStack.size() != 0) {
            sPrefixStack.clear();
        }

        StateData data = new StateData();
        int len = text.length();

        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);

            switch (data.state) {
                case StateData.NORMAL:
                    i = dealNormalState(context, data, text, i, c);
                    break;
                // case StateData.AT:
                // i = dealAtState(context, data, text, i, c);
                // break;
                // case StateData.TOPIC:
                // i = dealTopicState(context, data, text, i, c);
                // break;
                case StateData.EMOTION:
                    i = dealEmotionState(context, data, text, i, c);
                    break;
                case StateData.WEB:
                    i = dealWebState(context, text, data, i, c);
                    break;
                case StateData.WEB_HTTP:
                    i = dealWebHttpState(context, text, data, i, c);
                    break;
                case StateData.WEB_HASDOT:
                    i = dealWebDotState(context, text, data, i, c);
                    break;
                default:
                    break;
            }
        }

        // 处理结束遗留的状态，
        /*
         * if (data.state == StateData.AT && data.spanStart != text.length() - 1) { data.spanEnd =
         * text.length(); setSpan(context, text, data); } else
         */if (data.state == StateData.WEB_HASDOT) {
            data.spanEnd = text.length();
            setSpan(context, text, data);
        }
    }

    private static int dealNormalState(Context context, StateData data, Spannable text, int index,
            char c) {
        /*
         * if (c == '@') { data.state = StateData.AT; data.spanStart = index; } else
         */if (c == '[') {
            data.state = StateData.EMOTION;
            data.spanStart = index;
        }/*
          * else if (c == '#') { data.state = StateData.TOPIC; data.spanStart = index; }
          */
        else if (isChar(c)) {
            int httpPreLen = checkHttp(text, index, c);

            if (httpPreLen > -1) {
                data.state = StateData.WEB_HTTP;
                data.spanStart = index;
                index += httpPreLen;
            } else {
                data.state = StateData.WEB;
                data.spanStart = index;
            }
        } else {
            index = dealEmoji(context, text, index, c);
        }

        return index;
    }

    // private static int dealAtState(Context context, StateData data, Spannable text, int index,
    // char c) {
    // if (c == '@') {
    // if (data.spanStart + 1 != index) {
    // data.spanEnd = index;
    // setSpan(context, text, data);
    // }
    // data.state = StateData.AT;
    // data.spanStart = index;
    // } else if (c == '[') {
    // if (data.spanStart + 1 != index) {
    // data.spanEnd = index;
    // setSpan(context, text, data);
    // }
    // data.state = StateData.EMOTION;
    // data.spanStart = index;
    // } else if (c == '#') {
    // if (data.spanStart + 1 != index) {
    // data.spanEnd = index;
    // setSpan(context, text, data);
    // }
    // data.state = StateData.TOPIC;
    // data.spanStart = index;
    // } else if (c == ' ' || isPunctuation(c)) {
    // if (data.spanStart + 1 != index) {
    // data.spanEnd = index;
    // setSpan(context, text, data);
    // }
    // data.state = StateData.NORMAL;
    // data.spanStart = index;
    // } else {
    // index = dealEmoji(context, text, index, c);
    // }
    //
    // return index;
    // }
    //
    // private static int dealTopicState(Context context, StateData data, Spannable text, int index,
    // char c) {
    // if (c == '@') {
    // data.state = StateData.AT;
    // data.spanStart = index;
    // } else if (c == '#') {
    // if (data.spanStart + 1 != index) {
    // data.spanEnd = index + 1;
    // setSpan(context, text, data);
    // data.state = StateData.NORMAL;
    // data.spanStart = index;
    // } else {
    // data.state = StateData.TOPIC;
    // data.spanStart = index;
    // }
    // } else if (c == '[') {
    // StateData preData = new StateData();
    // preData.state = data.state;
    // preData.spanStart = data.spanStart;
    // sPrefixStack.push(preData);
    // data.state = StateData.EMOTION;
    // data.spanStart = index;
    // } else {
    // index = dealEmoji(context, text, index, c);
    // }
    //
    // return index;
    // }

    private static int dealEmotionState(Context context, StateData data, Spannable text, int index,
            char c) {
        /*
         * if (c == '@') { data.state = StateData.AT; data.spanStart = index; } else
         */if (c == '[') {
            data.state = StateData.EMOTION;
            data.spanStart = index;
        }/*
          * else if (c == '#') { if (!sPrefixStack.isEmpty()) { StateData savedData =
          * sPrefixStack.pop(); data.state = savedData.state; data.spanStart = savedData.spanStart;
          * if (data.state == StateData.TOPIC) { data.spanEnd = index + 1; setSpan(context, text,
          * data); data.state = StateData.NORMAL; data.spanStart = index; } } else { data.state =
          * StateData.TOPIC; data.spanStart = index; } }
          */else if (c == ']') {
            if (data.spanStart + 1 != index) {
                data.spanEnd = index + 1;
                setSpan(context, text, data);
            }
            if (!sPrefixStack.isEmpty()) {
                StateData preData = sPrefixStack.pop();
                data.state = preData.state;
                data.spanStart = preData.spanStart;
            } else {
                data.state = StateData.NORMAL;
                data.spanStart = index;
            }
        } else {
            index = dealEmoji(context, text, index, c);
        }

        return index;
    }

    private static int dealWebState(Context context, Spannable text, StateData data, int index,
            char c) {
        if (isLegalUrlChar(c)) {
            int httpPreLen = checkHttp(text, index, c);

            if (httpPreLen > -1) {
                data.state = StateData.WEB_HTTP;
                data.spanStart = index;
                index += httpPreLen;
            } else {
                int dotPreLen = checkHttpDot(text, index, c);

                if (dotPreLen > -1) {
                    data.state = StateData.WEB_HASDOT;
                    index += dotPreLen;
                }
            }
        } else {
            /*
             * if (c == '@') { data.state = StateData.AT; data.spanStart = index; } else
             */if (c == '[') {
                data.state = StateData.EMOTION;
                data.spanStart = index;
            }/*
              * else if (c == '#') { data.state = StateData.TOPIC; data.spanStart = index; }
              */
            else if (isChar(c)) {
                int httpPreLen = checkHttp(text, index, c);

                if (httpPreLen > -1) {
                    data.state = StateData.WEB_HTTP;
                    data.spanStart = index;
                    index += httpPreLen;
                } else {
                    data.state = StateData.WEB;
                    data.spanStart = index;
                }
            } else {
                data.state = StateData.NORMAL;
                data.spanStart = index;

                index = dealEmoji(context, text, index, c);
            }
        }

        return index;
    }

    private static int dealWebHttpState(Context context, Spannable text, StateData data, int index,
            char c) {
        if (isLegalUrlChar(c)) {
            int httpPreLen = checkHttp(text, index, c);

            if (httpPreLen > -1) {
                data.state = StateData.WEB_HTTP;
                data.spanStart = index;
                index += httpPreLen;
            } else {
                int dotPreLen = checkHttpDot(text, index, c);

                if (dotPreLen > -1) {
                    data.state = StateData.WEB_HASDOT;
                    index += dotPreLen;
                }
            }
        } else {
            /*
             * if (c == '@') { data.state = StateData.AT; data.spanStart = index; } else
             */if (c == '[') {
                data.state = StateData.EMOTION;
                data.spanStart = index;
            }/*
              * else if (c == '#') { data.state = StateData.TOPIC; data.spanStart = index; }
              */
            else if (isChar(c)) {
                int httpPreLen = checkHttp(text, index, c);

                if (httpPreLen > -1) {
                    data.state = StateData.WEB_HTTP;
                    data.spanStart = index;
                    index += httpPreLen;
                } else {
                    data.state = StateData.WEB;
                    data.spanStart = index;
                }
            } else {
                data.state = StateData.NORMAL;
                data.spanStart = index;

                index = dealEmoji(context, text, index, c);
            }
        }

        return index;
    }

    private static int dealWebDotState(Context context, Spannable text, StateData data, int index,
            char c) {
        if (isLegalUrlChar(c)) {
            int httpPreLen = checkHttp(text, index, c);

            if (httpPreLen > -1) {
                data.spanEnd = index;
                setSpan(context, text, data);

                data.state = StateData.WEB_HTTP;
                data.spanStart = index;
                index += httpPreLen;
            } else {
                int dotPreLen = checkHttpDot(text, index, c);

                if (dotPreLen > -1) {
                    data.state = StateData.WEB_HASDOT;
                    index += dotPreLen;
                }
            }
        } else {
            data.spanEnd = index;
            setSpan(context, text, data);

            data.state = StateData.NORMAL;
            data.spanStart = index;

            index = dealEmoji(context, text, index, c);
        }

        return index;
    }

    private static int dealEmoji(Context context, Spannable text, int index, char c) {
        // CharType type = CharType.check(c);
        //
        // if (type == CharType.EMOJI_UCS32) {
        // checkAndInsertEmojiUCS32(context, text, index);
        //
        // index++;
        // } else if (type == CharType.EMOJI_UCS16) {
        // checkAndInsertEmojiUCS16(context, text, index, c);
        // }

        return index;
    }

    private static int checkHttp(Spannable text, int index, char c) {
        if (c == 'H' || c == 'h') {
            if (text.length() > index + HTTP_PREFIX.length()) {
                String sub = text.subSequence(index, index + HTTP_PREFIX.length()).toString();

                if (sub.equalsIgnoreCase(HTTP_PREFIX)) {
                    if (isChar(text.charAt(index + HTTP_PREFIX.length()))) {
                        return HTTP_PREFIX.length();
                    }
                }
            } else {
                if (text.length() > index + HTTP_PREFIX.length()) {
                    String sub = text.subSequence(index, index + HTTPS_PREFIX.length()).toString();

                    if (sub.equalsIgnoreCase(HTTPS_PREFIX)) {
                        if (isChar(text.charAt(index + HTTPS_PREFIX.length()))) {
                            return HTTPS_PREFIX.length();
                        }
                    }
                }
            }
        }

        return -1;
    }

    private static int checkHttpDot(Spannable text, int index, char c) {
        if (c == '.') {
            if (text.length() > index + 1 && isChar(text.charAt(index + 1))) {
                return 1;
            }
        }

        return -1;
    }

    // 4字节emoji表情,
    // private static void checkAndInsertEmojiUCS32(Context context, Spannable text, int index) {
    // int codePoint = Character.codePointAt(text, index);
    // Integer drawableSrc = EmotionHelper.EMOJI_CODEPOINT_TO_DRAWBLE.get(codePoint);
    //
    // if (drawableSrc != null && drawableSrc > 0) {
    // ImageSpan imageSpan = getImageSpan(context, drawableSrc, emotionHeight);
    // text.setSpan(imageSpan, index, index + Character.charCount(codePoint),
    // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    // }
    // }

    // 2字节emoji表情,
    // private static void checkAndInsertEmojiUCS16(Context Context, Spannable text, int index, char
    // c) {
    // int codePoint = c;
    // Integer drawableSrc = EmotionHelper.EMOJI_CODEPOINT_TO_DRAWBLE.get(codePoint);
    //
    // if (drawableSrc != null && drawableSrc > 0) {
    // ImageSpan imageSpan = getImageSpan(Context, drawableSrc, emotionHeight);
    // text.setSpan(imageSpan, index, index + Character.charCount(codePoint),
    // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    // }
    // }

    // private static boolean isPunctuation(char c) {
    // int index = PUNCTUATION.indexOf(c);
    //
    // if (index != -1) {
    // if ((c > '\u2014' && c < '\u2026') || (c > '\u3001' && c < '\u3011')
    // || (c > '\uFE30' && c < '\uFFE5')) {
    // return true;
    // }
    // }
    //
    // return false;
    // }

    private static boolean isChar(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
            return true;
        }

        return false;
    }

    private static boolean isLegalUrlChar(char c) {
        if (isChar(c)) {
            return true;
        } else if (LEGAL_URL_PUNCTUATION.indexOf(c) > -1) {
            return true;
        }

        return false;
    }

    private static void setSpan(Context context, Spannable text, StateData data) {
        if (data.state == StateData.EMOTION) {
            ImageSpan span =
                    SmileUtils.getExpressionFromText(context,
                            text.subSequence(data.spanStart, data.spanEnd).toString());

            if (span != null) {
                text.setSpan(span, data.spanStart, data.spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else if (data.state == StateData.WEB_HASDOT) {
            String url = text.subSequence(data.spanStart, data.spanEnd).toString();

            text.setSpan(new WebSpannable(context, url), data.spanStart, data.spanEnd,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            text.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tb_blue)),
                    data.spanStart, data.spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
