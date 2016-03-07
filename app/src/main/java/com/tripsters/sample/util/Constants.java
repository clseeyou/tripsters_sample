package com.tripsters.sample.util;

public class Constants {

    // 附近的人每页的条目数字
    public static final int PAGE_COUNT = 20;

    // 多图最多显示个数
    public static int PIC_MAX_COUNT = 15;

    // 问题城市选择最多个数
    public static int QUESTION_CITY_MAX_COUNT = 2;

    public static class Action {
        public static final String LOGIN_SUCCESS = "login_success";
        public static final String LOGOUT_SUCCESS = "logout_success";
        public static final String CHANGE_LOCATION = "change_location";
        public static final String QUESTION_SUCCESS = "question_success";
        public static final String ANSWER_SUCCESS = "answer_success";
        public static final String MESSAGE_UNREAD_CHANGED = "message_unread_changed";
    }

    public static class Extra {
        public static final String USER = "user";
        public static final String QUESTION = "question";
        public static final String QUESTION_ID = "question_id";
        public static final String USERINFO = "userinfo";
        public static final String USER_ID = "user_id";
        public static final String COUNTRY = "country";
        public static final String CITIES = "cities";
        public static final String COMPOSER = "composer";
        public static final String COMPOSER_TYPE = "composer_type";
        public static final String ANSWER = "answer";
        public static final String TYPE = "type";
        public static final String MEDIA_INFOS = "media_infos";
        public static final String MAX_COUNT = "max_count";
        public static final String CUT_IMAGE = "cut_image";
        public static final String SINGLE_TYPE = "single_type";
        public static final String CIYT_OPENED_HIDDEN = "ciyt_opened_hidden";
        public static final String TEXT = "text";
        public static final String NOTICE_TYPE = "notice_type";
        public static final String CHANGE_COUNTRY = "change_country";
    }

    public static class Edit {
        public static final int MAX_QUESTION_TITLE_SIZE = 60;
    }
}
