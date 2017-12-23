/**
 * Created by wangxiangyu on 2017/9/21.
 */
define(['jquery', 'bluebird'], function ($, Promise) {

    return {

        DateUtil: {
            //获取制定日期0点的时间  getDate(1)表示明天0点， getDate(-1)表示昨天0点
            getDate: function (days) {
                var today = new Date(new Date().toLocaleDateString());
                var date = new Date(today);
                date.setDate(today.getDate() + days);
                return date;
            }
        },

        AjaxUtil: {
            errorHandler: function (res) {
                function trim(str) {
                    return str.replace(/(^\s*)|(\s*$)/g, "");
                }

                if (!res.status || res.status != 'OK') {
                    return new Promise(function (resolve, reject) {
                        var errorMsg = (res.msg != null && trim(res.msg) != '') ? res.msg : "未知错误";
                        reject(errorMsg);
                    });
                }
                return res.data.OMS_RESPONSE_DATA_KEY;
            },

            //封装调用ajax的函数
            getByJson: function (url) {
                return Promise.resolve($.ajax({
                    url: url,
                    dataType: "json",
                    contentType: "application/json",
                    type: 'GET'
                })).then(this.errorHandler);
            },

            get: function (url) {
                return Promise.resolve($.get(url))
                    .then(this.errorHandler);
            },

            post: function (url, params) {
                return Promise.resolve($.ajax({
                    url: url,
                    dataType: "json",
                    contentType: "application/json",
                    type: 'POST',
                    data: JSON.stringify(params)
                })).then(this.errorHandler);
            }
        }
    };
});