package com.tripsters.sample.util;

public class Constants {

    public static final String DOWNLOAD_URL_WEIXIN =
            "http://a.app.qq.com/o/simple.jsp?pkgname=com.tripsters.android&g_f=991653";

    public static final String DOWNLOAD_URL_OTHER = "http://t.cn/RAEQDkm";

    public static final String APP_ICON_URL = "http://www.tripsters.cn/download/icon/80.png";

    public static final String WEIBO_URL = "http://weibo.com/u/5584400415";

    public static final String URL_DAREN = "http://www.tripsters.cn/wx/tripsters/apply.php";
    public static final String URL_POINTS = "http://www.tripsters.cn/wx/tripsters/points.php";
    public static final String URL_GOLD = "http://www.tripsters.cn/wx/tripsters/gold.php";

    public static final String URL_GOOGLE_MAP_IMAGE =
            "http://maps.google.cn/maps/api/staticmap?center=%1$s,%2$s&zoom=12&size=%3$sx%4$s&maptype=roadmap&markers=%5$s&sensor=true";
    public static final String URL_GOOGLE_MAP_IMAGE_MARKERS = "markerStyles|color:red|%1$s,%2$s";
    public static final String URL_GOOGLE_MAP = "http://www.google.cn/maps/place/%1$s,%2$s";
    public static final String URL_GOOGLE_MAP_NAVIGATION =
            "http://maps.google.com/maps?daddr=%s,%s";
    // public static final String URL_GOOGLE_PLAY_MAPS =
    // "https://play.google.com/store/apps/details?id=com.google.android.apps.maps";

    // 附近的人每页的条目数字
    public static final int PAGE_COUNT = 20;

    // 多图最多显示个数
    public static int PIC_MAX_COUNT = 15;

    // 问题城市选择最多个数
    public static int QUESTION_CITY_MAX_COUNT = 2;

    // 推荐城市选择最多个数
    public static int BLOG_CITY_MAX_COUNT = 3;

    // 行程城市选择最多个数
    public static int TRIP_CITY_MAX_COUNT = 10;

    // POI选择最多个数
    public static int BLOG_MAX_COUNT = 3;

    // POI选择最多个数
    public static int POI_MAX_COUNT = 3;

    // 每个提问花费积分
    public static final int POINTS_PER_QUESTION_CONSUME = 20;
    // 每个翻译花费积分
    public static final int POINTS_PER_TRANSLATE_CONSUME = 200;
    // 每个顾问花费积分
    public static final int POINTS_PER_ASSISTANT_CONSUME = 500;

    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";

    public static final String PRE_HUANXIN_USER_NAME = "lvshizhen";
    public static final String PRE_HUANXIN_PASSWD_NAME = "L123456";

    public static class Action {
        public static final String LOGIN_SUCCESS = "login_success";
        public static final String LOGOUT_SUCCESS = "logout_success";
        public static final String CHANGE_LOCATION = "change_location";
        public static final String SHARE_SUCCESS = "share_success";
        public static final String SHARE_WITH_SEND_QUESTION = "share_with_send_question";
        public static final String QUESTION_SUCCESS = "question_success";
        public static final String ANSWER_SUCCESS = "answer_success";
        public static final String FAVORITE_SUCCESS = "favorite_success";
        public static final String UNFAVORITE_SUCCESS = "unfavorite_success";
        public static final String GOLD_POINTS_CHANGED = "gold_points_changed";
        public static final String FOLLOW_SUCCESS = "follow_success";
        public static final String UPDATE_USERINFO = "update_userinfo";
        public static final String EDIT_USERAVATA = "edit_useravata";
        public static final String SEND_BLOG = "send_blog";
        public static final String SEND_BLOG_SUCCESS = "send_blog_success";
        public static final String SEND_BLOG_FAILED = "send_blog_failed";
        public static final String SEND_DRAFT_SAVE = "send_draft_save";
        public static final String SEND_DRAFT_DELETE = "send_draft_delete";
        public static final String SAVE_BLOG_SUCCESS = "save_blog_success";
        public static final String UNSAVE_BLOG_SUCCESS = "unsave_blog_success";
        public static final String FAVORITE_BLOG_SUCCESS = "favorite_blog_success";
        public static final String UNFAVORITE_BLOG_SUCCESS = "unfavorite_blog_success";
        public static final String COMMENT_BLOG_SUCCESS = "comment_blog_success";
        public static final String MESSAGE_UNREAD_CHANGED = "message_unread_changed";
        public static final String HOME_NOTIFY = "home_notify";
        public static final String GROUP_ADD = "group_add";
        public static final String GROUP_DEL = "group_del";
    }

    public static class Extra {
        public static final String CONFLICT = "conflict";
        public static final String FINISH = "finish";
        public static final String USER = "user";
        public static final String QUESTION = "question";
        public static final String QUESTION_ID = "question_id";
        public static final String USERINFO = "userinfo";
        public static final String USER_ID = "user_id";
        public static final String USER_NAME = "user_name";
        public static final String POINTS = "points";
        public static final String GOLD = "gold";
        public static final String INCREASE = "increase";
        public static final String FOLLOW_UID = "follow_uid";
        public static final String BITMAP = "bitmap";
        public static final String COUNTRY = "country";
        public static final String CITIES = "cities";
        public static final String CITY = "city";
        public static final String TAGS = "tags";
        public static final String BLOGS = "blogs";
        public static final String POIS = "pois";
        public static final String POI = "poi";
        public static final String COMPOSER = "composer";
        public static final String COMPOSER_TYPE = "composer_type";
        public static final String ANSWER = "answer";
        public static final String MESSAGE = "message";
        public static final String BLOG = "blog";
        public static final String TYPE = "type";
        public static final String MEDIA_INFOS = "media_infos";
        public static final String MAX_COUNT = "max_count";
        public static final String CUT_IMAGE = "cut_image";
        public static final String SINGLE_TYPE = "single_type";
        public static final String IMAGES_TYPE = "images_type";
        public static final String PIC_INFOS = "pic_infos";
        public static final String VISIBLE_ITEM = "visible_item";
        public static final String BLOG_ID = "blog_id";
        public static final String CIYT_OPENED_HIDDEN = "ciyt_opened_hidden";
        public static final String BLOG_COMMENT = "blog_comment";
        public static final String TEXT = "text";
        public final static String TITLE = "title";
        public final static String URL = "url";
        public final static String PATH = "path";
        public static final String NOTICE_TYPE = "notice_type";
        public static final String GROUP = "group";
        public static final String GROUP_ID = "group_id";
        public static final String GROUP_GROUPID = "group_groupid";
        public static final String CHANGE_COUNTRY = "change_country";
        public static final String TRIPS = "trips";
        public static final String TRIP = "trip";
    }

    public static class Edit {
        public static final int MAX_QUESTION_TITLE_SIZE = 60;

        public static final int MAX_PROFILE_NAME_SIZE = 20;
        public static final int MAX_PROFILE_ADDRESS_SIZE = 32;
        public static final int MAX_PROFILE_NATION_SIZE = 25;
        public static final int MAX_PROFILE_OCCUPATION_SIZE = 20;
        public static final int MAX_PROFILE_DETAIL_SIZE = 70;
        public static final int MAX_BLOG_TITLE_SIZE = 40;
    }

    public static final String CHAT_ROOM = "item_chatroom";
    public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";
    public static final String ACCOUNT_REMOVED = "account_removed";
}
