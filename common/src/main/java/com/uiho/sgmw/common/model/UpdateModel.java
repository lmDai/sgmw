package com.uiho.sgmw.common.model;

/**
 * 作者：uiho_mac
 * 时间：2018/8/7
 * 描述：版本更新
 * 版本：1.0
 * 修订历史：
 */
public class UpdateModel {

    private IosBean ios;
    private AndroidBean android;

    public IosBean getIos() {
        return ios;
    }

    public void setIos(IosBean ios) {
        this.ios = ios;
    }

    public AndroidBean getAndroid() {
        return android;
    }

    public void setAndroid(AndroidBean android) {
        this.android = android;
    }

    public static class IosBean {
        /**
         * minVersion : 1.0.0
         * latestVersion : 1.0.0
         * content : 更新123
         * url :
         */

        private String minVersion;
        private String latestVersion;
        private String content;
        private String url;

        public String getMinVersion() {
            return minVersion;
        }

        public void setMinVersion(String minVersion) {
            this.minVersion = minVersion;
        }

        public String getLatestVersion() {
            return latestVersion;
        }

        public void setLatestVersion(String latestVersion) {
            this.latestVersion = latestVersion;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class AndroidBean {
        /**
         * minVersion : “1.0.2”
         * latestVersion : “1.0.4”
         * content : 1、大部分的iPhone用户其实不会打开手机设置iTunes Store与App Store的自动下载更新2、 虽然App Store 会提示更新， 但是很多用户是选择忽略的， 不会点击更新3、 目前苹果也不允许通过检测App Store内的版本来进行软件更新提示更新版本目的： 修复一致的bug， 增加新的功能模块， 如果用户不更新App， 我们的努力用户就看不到， 体验不到
         * url : http://app.uiho.com/sgmwapp/app.apk
         */

        private String minVersion;
        private String latestVersion;
        private String content;
        private String url;

        public String getMinVersion() {
            return minVersion;
        }

        public void setMinVersion(String minVersion) {
            this.minVersion = minVersion;
        }

        public String getLatestVersion() {
            return latestVersion;
        }

        public void setLatestVersion(String latestVersion) {
            this.latestVersion = latestVersion;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
