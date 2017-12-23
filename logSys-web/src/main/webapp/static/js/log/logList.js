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
                $('#eventDataTable').bootstrapTable('refresh');
            });
            $('#detail-slider').on('click',function(){
                $('#eventDetail').hide();
            })
        },

        init: function (options) {
            initParam();
            var eventMap = {
                'REGISTER': '注册事件',
                'LOGIN': '登录事件',
                'ID_OCR_UPDATE': '身份信息提交',
                'ADD_BANK_CARD': '绑卡事件',
                'APPLY_COMMIT': '联系人事件',
                'FACE_DETECT_UPDATE': '活体检测',
                'PIN_DRAW_COMMIT': '借款',
                'FIND_TRADE_PASSWORD': '找回密码',
                'ADD_NEW_BANK_CARD': '绑新卡',
                'UPDATE_TRADE_PASSWORD': '修改密码',
                'DELETE_BANK_CARD': '解绑银行卡',
                'SHOPPING_BUY': '分期提交',
                'SHOPPING_STAGES': '商品分期',
                'OPERATOR_AUTHENTICATION': '运营商认证'
            };
            var decisionMap = {
                "Accept": "<span style='color:#a7db65'>低风险</span>",
                "Review": "<span style='color:#f8d436'>中风险</span>",
                "Reject": "<span style='color:#ff6c5c'>高风险</span>"
            };
            var riskTypeMap = {
                'other_risk': '其他',
                'embezzle_risk': '伪冒'
            };
            //$('.form_datetime').datetimepicker({format: 'yyyy-MM-dd hh:mm:ss', language: 'zh-CN'});
            $('#eventType').select2({minimumResultsForSearch: Infinity, placeholder: '事件类型'});
            $('#field').select2({minimumResultsForSearch: Infinity});
            $('#riskDecision').select2({minimumResultsForSearch: Infinity, placeholder: '评估结果'});
            $('#searchField').select2({minimumResultsForSearch: Infinity});
            // bootstrap table初始化
            $('#eventDataTable').bootstrapTable({
                url: options.url,
                method: 'post',
                dataType: 'json',
                height: '600',
                minimumCountColumns: 2,
                clickToSelect: true,
                pagination: true,
                paginationLoop: false,
                queryParams: buildParams,
                pageSize: 20,
                sidePagination: 'server',
                classes: 'table table-hover table-no-bordered',
                maintainSelected: true,
                contentType: 'application/x-www-form-urlencoded',
                columns: [
                    {
                        field: 'eventTime',
                        title: '事件发生时间',
                        sortable: true,
                        align: 'left',
                        formatter: function (value, row, i) {
                            return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                        }
                    },
                    {
                        field: 'eventType', title: '事件类型', align: 'left', formatter: function (value) {
                        return eventMap[value];
                    }
                    },
                    {
                        field: 'riskDecision', title: '评估结果', align: 'left', formatter: function (value) {
                        return decisionMap[value];
                    }
                    },
                    {
                        field: 'riskTypes', title: '风险类型', align: 'left', formatter: function (value) {
                        var result ='';
                        if (value === null || value === undefined || value === '') {
                            return '';
                        }
                        for(var i=0;i<value.length;i++){
                            result += ","+riskTypeMap[value[i]];
                        }
                        return result.substring(1);
                    }
                    },
                    {
                        field: 'eventResult', title: '事件结果', align: 'left', formatter: function (value) {
                        if (value == 'S') return '<span style="color:#a7db65">成功</span>'; else return '<span style="color:red">失败</span>';
                    }
                    },
                    {field: 'mobileNo', title: '手机号', align: 'left'},
                    {field: 'deviceFp', title: '设备id', align: 'left'},
                    {field: 'ip', title: '来源ip', align: 'left'},
                    {field: 'ipAddressCity', title: '地理位置', align: 'left'},
                    {field: 'channelSource', title: '事件来源', align: 'left'},
                    {field: 'appVersion', title: 'APP版本', align: 'left'}
                ],
                onClickRow: function (row, element) {
                    Wheel.AjaxUtil.get('eventDetail.json?sequenceId=' + row.sequenceId)
                        .then(function (data) {
                            var hitPolicyList = data.hitPolicyInfo;
                            var hitRuleList = data.hitRuleInfo;
                            for (var i in hitPolicyList) {
                                hitPolicyList[i]['hitRules'] = hitRuleList.filter(function (element, index, array) {
                                    return element.policyId == hitPolicyList[i].policyId;
                                });
                            }
                            $('#baseInfo').html(template('baseInfoTemplate', {'hitPolicyList': data.hitPolicyInfo,'businessInfo':data.businessInfo}));
                            // $('#loanMonitorInfo').html(template('loanMonitorInfoTemplate', data));
                            $('#ipInfo').html(template('ipInfoTemplate', {'deviceInfo': data.deviceInfo,'businessInfo': data.businessInfo}));
                            $('#deviceInfo').html(template('deviceInfoTemplate', {'deviceInfo': data.deviceInfo}));
                            $('#fieldInfo').html(template('fieldInfoTemplate', {'fieldMap': data.fieldMap, 'businessInfo': data.businessInfo}));
                            $('#eventDetail').slideDown(200);

                            // 事件得分canvas
                            var canvas = document.getElementById("canvasScore");
                            var ctx = canvas.getContext("2d");
                            var W = canvas.width;
                            var H = canvas.height;
                            var deg = 0, new_deg = 0, dif = 0;
                            var loop, re_loop;
                            var text, text_w;
                            draw(row.riskScore);

                            function init() {
                                ctx.clearRect(0, 0, W, H);
                                ctx.beginPath();
                                ctx.strokeStyle = "#dcdcdc";
                                ctx.lineWidth = 5;
                                ctx.arc(W / 2, H / 2, 46, 0, Math.PI * 2, false);
                                ctx.stroke();

                                var r = deg * Math.PI / 180;
                                ctx.beginPath();
                                var cr = Math.min(255, (deg * 1.42) | 0);
                                var cg = deg < 180 ? 200 : 200 - Math.max((deg * 0.7) | 0);
                                var cb = 90;
                                ctx.strokeStyle = "rgb(" + cr + "," + cg + "," + cb + ")";
                                ctx.lineWidth = 5;
                                ctx.arc(W / 2, H / 2, 46, 0 - 90 * Math.PI / 180, r - 90 * Math.PI / 180, false);
                                ctx.stroke();

                                // 设置数值字体样式
                                ctx.fillStyle = "#666";
                                ctx.font = "40px arial";
                                text = Math.ceil((deg * 100) / 360);
                                text_w = ctx.measureText(text).width;
                                ctx.fillText(text, W / 2 - text_w / 2, H / 2 + 15);
                                console.log('init');
                            }
                            function draw(_mark) {
                                // 重新绘制前进行清除，避免操作过快
                                clearInterval(loop);
                                new_deg = (_mark / 100 * 360) | 0;
                                dif = new_deg - deg;
                                loop = setInterval(to, 1000 / dif);
                            }

                            function to() {
                                if (deg < new_deg) {
                                    deg++;
                                } else {
                                    deg--;
                                }
                                if (deg == new_deg) {
                                    clearInterval(loop);
                                }
                                init();
                            }
                        }).catch(function (error) {
                        console.log('error: ', error)
                        alert("服务繁忙，请稍候重试!");
                    });
                }
            });


            function buildParams(params) {
                var paramsArr = $("#paramsForm").serializeArray();
                var map = {};
                $.each(paramsArr, function () {
                    map[this.name] = this.value;
                });
                var queryMap = {};
                if (map && map['field'] && map['value']) {
                    queryMap[map['field']] = map['value'];
                }
                var starttime = $("#starttime").val();
                var endtime = $("#endtime").val();
                if (starttime || endtime) {
                    var startTimestamp = Date.parse(starttime ? new Date(starttime) : new Date('1970-01-01 08:00:00'));
                    var endTimestamp = Date.parse(endtime ? new Date(endtime) : Wheel.DateUtil.getDate(1));
                    queryMap['eventTime'] = JSON.stringify([startTimestamp, endTimestamp]);
                }
                var eventType = $("#eventType").val();
                if ($.trim(eventType)) {
                    queryMap['eventType'] = eventType;
                }
                if ($.trim($('#riskDecision').val())) {
                    queryMap["riskDecision"] = $.trim($('#riskDecision').val());
                }
                var pageMap = {};
                if (!params || !params.offset || !params.limit) {
                    pageMap['page'] = 1;
                    pageMap['rows'] = 20;
                } else {
                    pageMap['page'] = (params.offset / params.limit) + 1;
                    pageMap['rows'] = 20;
                }
                return $.extend({}, pageMap, queryMap);
            }
            function initParam() {
                var riskDecision = getQueryString("rd");
                if (riskDecision !== null || riskDecision !== undefined || riskDecision !== '') {
                    $('#riskDecision').val(riskDecision);
                }
                var starttime = decodeURI(getQueryString("st"));
                if (starttime !== null || starttime !== undefined || starttime !== '') {
                    $('#starttime').val(starttime);
                }
                var endtime = decodeURI(getQueryString("et"));
                if (endtime !== null || endtime !== undefined || endtime !== '') {
                    $('#endtime').val(endtime);
                }

                var keyword = getQueryString("keyword");
                if (keyword !== null || keyword !== undefined || keyword !== '') {
                    $('#searchValue').val(keyword);
                }

                var fieldName = getQueryString("fieldName");
                if (fieldName !== null || fieldName !== undefined || fieldName !== '') {
                    $('#searchField').val(fieldName);
                }


            }
            function getQueryString(name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
                var r = window.location.search.substr(1).match(reg);
                var context = "";
                if (r != null)
                    context = r[2];
                reg = null;
                r = null;
                return context == null || context == "" || context == "undefined" ? "" : context;
            }

            this.bind();
        }
    });
    return exports;
});