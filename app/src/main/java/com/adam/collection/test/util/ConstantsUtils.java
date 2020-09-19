package com.adam.collection.test.util;

import android.graphics.Color;


public class ConstantsUtils {
	public static final String DOMAIN_NAME = "http://www.qubaobei.com/ios/weekend/";

	public static final String QQ_APP_ID = "1104736508";
	public static final String QQ_APP_KEY = "eb3As0fqiv1HCaTz";
	public static final String WX_APP_ID = "wxfc9dd9b02984d61c";
	public static final String WX_APP_KEY = "9470342874190f9be9aaddc6cd6593a2";

	//微信支付
	public static final String WX_PAY_APP_ID = "wx9a5ac5e488377e01";
	public static final String WX_PAY_MCH_ID = "1443832502";
	public static final String WX_PAY_API_KEY = "6b66d5134c0d8e92619da4bd17db618a";
	
	/** 登录之后的resultCode */
	public static final int CHECK_LOGIN_CODE = 1001;

	/** 保存已经点击的帖子的id的文件名 */
	public static final String FILE_NAME = "yunqi_seleted_id.txt";
	/** 发布帖子存储图片的key */
	public static final String POST_PUBLISH_PIC1_KEY1 = "post_publish_selected_images";
	/**发布评论存储图片的key*/
	public static final String COMMENT_PUBLISH_PIC1_KEY1 = "comment_publish_selected_images";
	/** 上传帖子的receiver的action */
	public static final String UPLOAD_POST_RECEIVER_ACTION = "com.ci123.babyweekend.UploadPostReceiver";

	/** 首页高德地图定位返回的citycode */
	public static final String AMAP_HOME_CITY_CODE = "amap_home_city_code";
	/** 服务器端的citycode */
	public static final String HOME_CITY_CODE = "home_city_code";
	/** 城市圈id */
	public static final String HOME_CITY_QUAN_CODE = "home_city_quan_code";
	/** 城市名称 */
	public static final String HOME_CITY_NAME = "home_city_name";
	/** 城市天气ID */
	public static final String HOME_CITY_WEATHER_CODE = "home_city_weather_code";
	/**用户id*/
	public static final String USER_ID="user_id";
	/**用户昵称*/
	public static final String USER_NICKNAME="nickname";
	/**用户头像连接*/
	public static final String USER_HEAD_IMAGE_URL="head_img";
	/**宝宝生日*/
	public static final String BABY_BIRTHDAY="birthday";
	/**宝宝性别*/
	public static final String BABY_SEX="baby_sex";
	/**用户手机号*/
	public static final String USER_MOBILE="mobile";
	
	public static final String MIX_ID="mix_id";
	/**用户等级*/
	public static final String LEVEL="level";

	public static final String USER_LEVEL ="user_level";

	/**经度*/
	public static final String LOCATION_LONGITUDE="cur_lng";
	/**纬度*/
	public static final String LOCATION_LATITUDE="cur_lat";

	/**设备号*/
	public static final String PHONE_DEVICE_ID="phone_device_id";

	
	/**个人中心的消息中心的帖子提醒数目*/
	public static final String USER_CENTER_POST_MSG_NUM_INT="user_center_post_msg_num";
	/**个人中心的消息中心的站内信提醒数目*/
	public static final String USER_CENTER_LETTER_MSG_NUM_INT="user_center_letter_msg_num";

	public static final int REQUEST_CHOOSE_PHOTOS = 0xff10;

	public static final int REQUEST_CHOOSE_VIDEO = 0xff11;

	public static final int THRESHOLD_OF_VIDEO_SIZE = 2 * 1024 * 1024;

	public static final int DEFAULT_RECYCLER_VIEW_CACHE_SIZE = 20;

	public static final int PICTURE_SELECTOR_TYPE = 0x302;

	public static final int REQUEST_VOUCHER = 0x203;

    public static final int RESULT_VOUCHER = 0x303;

    public static final String LIKE_POST_URL = "http://www.qubaobei.com/ios/weekend/reply/love.php";

    public static final String LIKE_SPU_PREFIX = "LIKE_";

    public static final int REQUEST_PAY_RESULT = 0x315;

    public static final String CURRENT_ADDRESS = "cur_address";

    public static final int REQUEST_SEARCH = 0x123;

    public static final int APP_TOOL_BAR_COLOR = Color.parseColor("#f8f8f8");

    public static final String INTENT_KEY_FOR_PAYING_AND_RETURNING = "payReturn";

    public static final int COLOR_SECONDARY = Color.parseColor("#8a8a8a");

    public static final String UMENG_PAGE_TAG_HOME = "首页-首页";

    public static final String UMENG_PAGE_TAG_COMMUNITY = "首页-社区";

    public static final String UMENG_PAGE_TAG_ORDER = "首页-订单";

    public static final String UMENG_PAGE_TAG_MY = "首页-我的";

	public static final String UMENG_PAGE_TAG_AMUSEMENT_PARK = "游乐场";

	public static final String UMENG_PAGE_TAG_QQT = "亲亲淘";

	public static final String UMENG_PAGE_TAG_EDIT_POST_POST = "发布社区帖子";

	public static final String UMENG_PAGE_TAG_EDIT_POST_COMMENT = "发表评价";

	public static final String UMENG_PAGE_TAG_HANDWORK = "手工";

	public static final String UMENG_PAGE_TAG_PHOTOGRAPHING = "亲子摄影";

	public static final String KEY_KEFU_DRAFT = "key_kefu_draft";

	public static final String KEY_TAB_BAR_SOUND = "key_tab_bar_sound";

	public static final int REQUEST_GENERAL_ACTIVITY = 0x124;

	public static final String KEY_ORDER_FROM = "order_from";

	public static final String KEY_APPOINTMENT_SOURCE = "appointment_source";

	public static final String COMMUNITY_ENTRY_PREFIX = "https://www.qubaobei.com/weekend/view/guide/user.php?uid=";

	public static final String COMMUNITY_FRAGMENT_ENTRY_PREFIX = "https://www.qubaobei.com/weekend/view/guide/user.php?";

	public static final String COMMUNITY_HOME_PAGE = "https://www.qubaobei.com/weekend/view/guide/theme1.php?";

	public static final String HOME_ACTIVITY_OPENED_TIMES = "app_opened_times";

	public static final String HOME_ACTIVITY_OPENED_TIMES_V2 = "app_opened_times_v2";

	public static final int REQUEST_VISIT_CMA = 0xca;

	public static final String SIGN_URL_PREFIX = "http://www.qubaobei.com/weekend/view/fishball/signin_v2.php?";

	public static final String ORDER_DETAIL_RECOMMEND_URL = "http://www.qubaobei.com/ios/weekend/order_recommend_v46.php";

	public static final String MESSAGE_CENTER_URL = "http://www.qubaobei.com/ios/weekend/message_center.php";
	public static final String SNS_LIST_URL = "http://www.qubaobei.com/ios/weekend/message_sns_list.php";

	public static final String SNS_POST_URL = "http://www.qubaobei.com/ios/weekend/message_sns_reply.php";

	public static final String MESSAGE_NUM_URL = "http://www.qubaobei.com/ios/weekend/message_num_v41.php";

	public static final int MSG_GET_SNS_LIST_JSON_ERROR = -2;
	public static final int MSG_GET_SNS_LIST_FAILURE = -1;
	public static final int MSG_GET_SNS_LIST_EMPTY = 0;
	public static final int MSG_GET_SNS_LIST_SUCCESS = 1;

	public static final String KEY_OPEN_CHECK = "open_check";

	public static final String ACTION_CHECK_2 = "com.ci123.babyweekend.check2";
	public static final String KEY_IS_SIGN = "is_sign";

	public static final int FLAG_OPEN_FISH_BALL_V2_FROM_NOTIFICATION = 0x01;
	public static final int FLAG_GO_TO_COMMENT_IN_FISH_BALL_V2 = 0x10;
	public static final int FLAG_TRIGGER_GO_TO_COMMENT = 0x11;
	public static final String URL_FUN_VIP_ENTRY = "http://www.qubaobei.com/weekend/view/fun/index.php";
	public static final String URL_SNAPSHOT = "http://www.qubaobei.com/ios/weekend/act_snapshot_v46.php";

	public static int FROM_LOCAL_SIGN_PUSH;
	public static boolean GO_TO_COMMENT_FROM_WEB;
	public static final int MIN_CLICK_INTERVAL = 1000;

	public static final String TEST_AVATAR = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=317775618,547075562&fm=58&bpow=480&bpoh=360";
	public static final String TEST_PIC = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1564545531&di=1ec76154e7b312c94144da851f7db540&src=http://pic.rmb.bdstatic.com/cd2476300bbad8dfcfff1d277b79401a.jpeg";

	public static final String KEY_COMMENT_FROM = "comment_from";

	public static final String DETAIL_STORE_URL = "";
	public static final String WELFARE_URL = "http://www.qubaobei.com/weekend/view/fuli/index.php";
	public static final String KEY_PAGE = "key_page";
	public static final String KEY_SHOW_ORDER_LIST_FRAGMENT_TOP = "key_show_order_list_fragment_top";
	public static final String KEY_STORE_ID = "store_id";
	public static final String KEY_FROM = "from";

	public static final String EDIT_POST_V2_TAGS_AND_BOTTOM_URL = "http://www.qubaobei.com/ios/weekend/comment_info.php";
	public static final String KEY_STORE_NUM = "key_store_num";
	public static final String KEY_ACT_ID = "key_act_id";
	public static final String PREFIX_DETAIL_STORE = "detail_store_act_";
	public static final String TENANT_HOME_URL = "http://www.qubaobei.com/ios/weekend/store_list_v42.php";
	public static final String DIARY_LIST_URL = "http://www.qubaobei.com/ios/weekend/store_diary_list.php";
	public static final String KEY_TENANT_RECENT = "recent";
	public static final String KEY_THEME_ID = "theme_id";
	public static final String USER_CENTRE_URL = "http://www.qubaobei.com/ios/weekend/user_centre_v42.php";
	public static final String KEY_EDIT_POST_V2_CLICK_ME = "KEY_EDIT_POST_V2_CLICK_ME".toLowerCase();
	public static final String TEXT_FUN_VIP_PROMOTE_PREFIX = "FUN卡会员预计最高可省";

	/**
	 * JSON转换错误
	 */
	public static final int MSG_GET_DATA_JSON_ERROR = -3;
	/**
	 * 网络错误
	 */
	public static final int MSG_GET_DATA_ERROR = -2;
	/**
	 * ret != 1
	 */
	public static final int MSG_GET_DATA_NO_RET = -1;
	/**
	 * 网络返回值为空
	 */
	public static final int MSG_GET_DATA_EMPTY = 0;
	/**
	 * 请求成功
	 */
	public static final int MSG_GET_DATA_SUCCESS = 1;

	public static final String PRIVACY_POLICY =
			"欢迎使用亲子周末App！\n\n" +
					"我们非常重视您的个人信息和隐私保护。"
					+ "为了更好的保护您的个人权益，在您使用我们的产品前，请您认真阅读我们的用户协议和隐私政策，以了解我们的服务内容和我们在收集、使用您的相关信息时的处理规则。"
					+ "我们将严格按照用户协议和隐私政策为您提供服务，保护您的个人信息。\n\n"
					+ "阅读《用户协议》和《隐私政策》后，您继续使用我们的产品/服务时，即意味着您同意我们按照本隐私政策处理。";

	public static final String TEXT_QZZM_USER_AGREEMENT = "《用户协议》";
	public static final String TEXT_QZZM_PRIVACY_POLICY = "《隐私政策》";
	public static final String URL_FULL_PRIVACY_POLICY = "https://www.qubaobei.com/ios/weekend/view/privacy.php";
	public static final String KEY_PRIVACY_POLICY_OPEN_TIMES = "key_privacy_policy_open_times";

	public static final String URL_USER_AGREEMENT = "http://www.qubaobei.com/ios/weekend/view/protocol.php";

	public static final String URL_DELETE_ACCOUNT = "http://www.qubaobei.com/ios/weekend/view/account_delete.php";

	public static final String KEY_ORDER_ID = "key_order_id";
	public static final String KEY_ORDER_DETAIL_PIC = "key_order_detail_pic";
	public static final String KEY_ORDER_DETAIL_NAME = "key_order_detail_name";

	public static boolean HAS_LOGGED_OUT;

	public static final String KEY_IS_FUN = "key_is_fun";

	public static boolean IS_TOP_BOX_SHOW;

	public static final String KEY_LAST_POPUP_TIME = "key_last_popup_time";

	public static final long ONE_DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

	public static final String C_CITY_CODE = "c_city_code";

	public static final String DEFAULT_USER_PIC = "http://www.qubaobei.com/files/head/300/20170719/38/15004576311769.jpg";

	public static final String KEY_VIDEO_SOURCE = "key_video_source";
	public static final String KEY_VIDEO_THUMB = "key_video_thumb";
	public static final String KEY_VIDEO_TITLE = "key_video_title";
	public static final String KEY_VIDEO_WIDTH = "key_video_width";
	public static final String KEY_VIDEO_HEIGHT = "key_video_height";

	public static final int TYPE_PIC = 0;
	public static final int TYPE_VIDEO = 1;

	public static final int TYPE_DISCOVER_SITE = 0;
	public static final int TYPE_PIC_AND_VIDEO = 1;

	public static final String KEY_TENANT_GALLERY_MEDIA_TYPE = "key_tenant_gallery_media_type";
	public static final String KEY_TENANT_ALBUM_ID_PIC = "key_tenant_album_id_pic";
	public static final String KEY_TENANT_ALBUM_ID_VIDEO = "key_tenant_album_id_video";

	public static final int ERROR_CODE = -1;

	public static final String KEY_TENANT_GALLERY_TAB_POS = "key_tenant_gallery_tab_pos";
	public static final String URL_CUSTOMER_SERVICE = "http://www.qubaobei.com/ios/weekend/view/service_v2.php";
	public static final String KEY_IMAGE_URL = "key_image_url";

	public static final int EMPTY_ALBUM_ID = -1;
	public static final String WEBP_SUFFIX = "webp";
	public static final String URL_QUICK_SITES_LIST = "http://www.qubaobei.com/ios/weekend/store_diaries.php";

	public static final int DEFAULT_APPRAISE_ITEM_ROUNDED_CORNER_IN_DP = 10;

	public static final String URL_APP_NOTIFY = "http://www.qubaobei.com/ios/weekend/app_notify.php";
	public static final String URL_APP_NOTIFY_UPDATE = "http://www.qubaobei.com/ios/weekend/app_notify_update.php";

	/**
	 * 儿童剧
	 */
	public static final int CATE_CHILDREN_OPERA = 1;
	/**
	 * 亲子活动
	 */
	public static final int CATE_PARENT_CHILDREN_ACTIVITY = 2;
	/**
	 * 游乐场
	 */
	public static final int CATE_AMUSEMENT_PARK = 3;
	/**
	 * 户外
	 */
	public static final int CATE_OUTDOOR_ACTIVITIES = 4;
	/**
	 * 景点
	 */
	public static final int CATE_SIGHTSEEING = 5;
	/**
	 * 酒店
	 */
	public static final int CATE_HOTEL = 6;
	/**
	 * 早教
	 */
	public static final int CATE_EARLY_EDUCATION = 7;
	/**
	 * 电商
	 */
	public static final int CATE_ONLINE_SHOPPING = 8;
	/**
	 * 商场
	 */
	public static final int CATE_MALL = 9;
	/**
	 * 摄影
	 */
	public static final int CATE_PHOTOGRAPHING = 10;
	/**
	 * 生活休闲
	 */
	public static final int CATE_LEISURE_LIFE = 11;
	/**
	 * 月子中心
	 */
	public static final int CATE_MATERNITY_HOTEL = 12;
	/**
	 * 美食
	 */
	public static final int CATE_DELIOUS_FOOD = 13;
	/**
	 * 在线教育
	 */
	public static final int CATE_ONLINE_EDU = 14;

	public static final String KEY_CATE_ID = "cate_id";

	public static final String KEY_CATE_TITLE = "cate_title";

	public static final String GIF_SUFFIX = "gif";

	public static final String URL_QQ_LOGIN = "http://www.qubaobei.com/ios/weekend/qq_login_v17.php";

	public static final int RESULT_LOGIN_SUCCESSFULLY = 200;

	public static final int REQ_READ_PHONE_STATE_CODE = 100;

	// since 2.5.3
	public static final String ACT_URL = "http://www.qubaobei.com/weekend/view/daily/index.php?type=1";

	public static final String HOME_NEARBY = "http://www.qubaobei.com/ios/weekend/nearby.php";

	public static final String KEY_DEBUG = "key_debug";
	public static boolean DEBUG = true;

	public static final String KEY_CUSTOM_LONGITUDE_FOR_DEBUGGING = "key_custom_longitude_for_debugging";
	public static final String KEY_CUSTOM_LATITUDE_FOR_DEBUGGING = "key_custom_latitude_for_debugging";
	public static final String KEY_ENABLE_CUSTOMIZATION_OF_LON_LAT = "key_enable_customization_of_lon_lat";

	public static final String DEFAULT_LONGITUDE_FOR_DEBUGGING = "118.78525";
	public static final String DEFAULT_LATITUDE_FOR_DEBUGGING = "32.0414";

	public static Object TEST_VAL;

	public static final String KEY_CHECK_TRAVELER = "key_check_traveler";

	public static final String URL_TEST_ALI_BC = "http://www.qubaobei.com/weekend/view/guidingbuy/index.php?bwfull=1";

	public static final String URL_CITY_LIST = "http://www.qubaobei.com/ios/weekend/city_list_v35.php";
	public static final String KEY_CITY_NAME = "key_city_name";
	public static final String KEY_CITY_ID = "key_city_id";
	public static final int REQ_STORE_SEARCH = 300;
	public static final String KEY_STORE_SEARCH_RET = "key_store_search_ret";
	public static final String URL_APP_ACTIVE = "http://www.qubaobei.com/ios/weekend/app_active.php";
	// data field in app_active.php
	public static final String KEY_IN_JSON = "key_in_json";
	public static final int REQ_ADD_STUDENT_INFO = 0x800;
	public static final String URL_STUDENT_INFO = "http://www.qubaobei.com/weekend/view/studentinfo/cert_list.php";
	public static final String KEY_CLEAR_PASTEBOARD = "key_clear_pasteboard";
	static final String ACTION_CHECK_3 = "com.ci123.babyweekend.check3";
	public static final String KEY_CATE = "key_cate";
	public static final String KEY_CITY = "key_city";
	public static final String URL_HOME_PAGE_COUNTRY_SCOPE = "http://www.qubaobei.com/weekend/view/guidingbuy/main.php";
	public static final String GOODS_URL = "https://www.qubaobei.com/weekend/view/guidingbuy/index.php?theme=1&via=apptab&";
	public static final String IS_RET_CITY_NAME_VALID = "key_is_return_city_name_valid";
	public static final String APPOINTMENT_SOURCE_ORDER_LIST = "source_order_list";
	public static final String APPOINTMENT_SOURCE_ORDER_DETAIL = "source_order_detail";
	public static final String APPOINTMENT_SOURCE_ACTIVITY_SNAPSHOT = "source_snapshot";
	public static final String APPOINTMENT_SOURCE_TENANT_DETAIL = "source_td";
}
