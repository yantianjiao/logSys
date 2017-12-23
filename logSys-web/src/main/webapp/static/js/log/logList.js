/**
 * Created by wangxiangyu on 2017/9/21.
 */
define(['jquery', 'bluebird', 'wheel', 'template', 'bootstrap', 'bootstrap-table', 'bootstrap-table-zh-CN',
    'datepicker', 'select2'], function ($, Promise, Wheel, template) {

    var exports = function () {
    };
    $.extend(exports.prototype, {
        bind: function () {
            $('#btn-search').click(function () {
                $('#logData').val("");
                query();
            });
            function query() {
                var beginTime = $("#beginTime").val();
                var endtime = $("#endTime").val();
                var userId = $("#userId").val();
                var sid = $("#sid").val();
                var conditionValue = $("#conditionValue").val();
                var conditionCommand = $("#conditionCommand").val();
                // var data = {beginTime: beginTime, endtime: endtime, userId: userId, sid: sid};
                var data = {
                    "beginTime": beginTime,
                    "endtime": endtime,
                    "userId": userId,
                    "sid": sid,
                    "conditionValue":conditionValue,
                    "conditionCommand":conditionCommand
                };
                $.ajax({
                    type: "POST",
                    url: "/logQuery/query",
                    dataType: "json",
                    async: false,
                    contentType: "application/x-www-form-urlencoded",
                    data: data,
                    success: function (data) {
                        parseData(data);
                    }
                });
            }
            function parseData(data) {
                $('#logData').val(JSON.stringify(data,null,"\t"));
               //  var content ="";
               //  for(var i=0;i<data.length;i++){
               //      content += JSON.stringify(data[i]) +"<br />";
               //  }
               //  $("#logData").val(content);
               // alert(tables);
            }
        },

        init: function (options) {

            this.bind();
        }
    });
    return exports;
});