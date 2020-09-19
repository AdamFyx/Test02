package com.adam.collection.test.ui;

import android.Manifest;
import android.graphics.Color;

/**
 * Created by ci on 2015/11/26.
 */
public class Constants {

    /**商家的id String*/
    public static final String BID="bid";
    public static final String SIGN = "sign";
    /**商家的name String*/
    public static final String NAME="name";
    /**商家的账户名 String*/
    public static final String USERNAME="username";
    /**商家的账户加密密码 String*/
    public static final String PWD="password";
    /**用户是否登录 boolean*/
    public static final String ISLOGIN="isLogin";

    public static final int TOOLBAR_COLOR = Color.parseColor("#ffffff");

    public static final String PRIVACY_POLICY =
            "欢迎使用狼部落App！\n\n" +
                    "我们非常重视您的个人信息和隐私保护。"
                    + "为了更好的保护您的个人权益，在您使用我们的产品前，请您认真阅读我们的用户协议和隐私政策，以了解我们的服务内容和我们在收集、使用您的相关信息时的处理规则。"
                    + "我们将严格按照用户协议和隐私政策为您提供服务，保护您的个人信息。\n\n"
                    + "阅读《用户协议》和《隐私政策》后，您继续使用我们的产品/服务时，即意味着您同意我们按照本隐私政策处理。";

    public static final String TEXT_QZZM_USER_AGREEMENT = "《用户协议》";
    public static final String TEXT_QZZM_PRIVACY_POLICY = "《隐私政策》";
    public static final String URL_FULL_PRIVACY_POLICY = "http://b.qianyuqinzi.com/weekend/business/privacy.php";
    public static final String KEY_PRIVACY_POLICY_OPEN_TIMES = "key_privacy_policy_open_times";

    public static final String URL_USER_AGREEMENT = "http://b.qianyuqinzi.com/weekend/business/protocol.php";
    public static final String FROM = "from";
    public static final String NEED_REFRESH = "need_refresh";

    public static final int REQUEST_PERMISSIONS = 200;
    public static final String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

}
