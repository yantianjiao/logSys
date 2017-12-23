<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${ctxPath}/static/framework/require/require-min.js"></script>
<script src="${ctxPath}/static/framework/plugins/jquery.min.js"></script>
<script type="text/javascript" src="${ctxPath}/static/js/util/date.js?v=2.02"></script>
<script>
    requirejs.config({
        baseUrl: '${ctxPath}/static/',
        paths: {
            'echarts': 'framework/echarts/js/echarts.min',
            'echarts-china': 'framework/echarts/js/china',
            'bootstrap': 'framework/bootstrap/js/bootstrap.min',
            'bootstrap-validator': 'framework/bootstrap/js/bootstrapValidator.min',
            'bootstrap-table': 'plugins/bootstrap-table/bootstrap-table.min',
            'bootstrap-table-zh-CN': 'plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min',
            'bootstrap-datetimepicker': 'plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min',
            'bootstrap-datetimepicker-zh-CN': 'plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN',
            'jquery': 'framework/plugins/jquery.min',
            'bluebird': 'framework/bluebird/bluebird.min',
            'select2': 'framework/plugins/select2/select2.min',
            'template': 'framework/plugins/template',
            'datepicker': 'framework/plugins/My97DatePicker/WdatePicker',
            'wheel': 'js/util/wheel',
            'views': '/views/',
            'easyui': 'framework/plugins/easyui/jquery.easyui.min',
            'easyui-lang-zh_CN': 'framework/plugins/easyui/locale/easyui-lang-zh_CN',
            'extJs': '/js/util/extJs',
            'appjs': 'framework/dist/js/app'
        },
        map: {
            '*': {
                'css': 'framework/require/css'
            }
        },
        shim: {
            'datepicker': {
                deps: ['jquery']
            },
            'template': {
                deps: ['jquery']
            },
            'select2': {
                deps: [
                    'css!framework/plugins/select2/select2.min'
                ],
                exports: 'select2'
            },
            'bootstrap': {
                deps: [
                    'jquery',
                    'css!framework/bootstrap/css/bootstrap.min'
                ],
                exports: 'bootstrap'
            },
            'bootstrap-validator': {
                deps: [
                    'jquery',
                    'css!framework/bootstrap/css/bootstrapValidator.min'
                ],
                exports: 'bootstrap'
            },
            'bootstrap-table': {
                deps: [
                    'jquery',
                    'css!plugins/bootstrap-table/bootstrap-table.min'
                ],
                exports: 'bootstrap-table'
            },
            'bootstrap-table-zh-CN': {
                deps: [
                    'bootstrap-table'
                ],
                exports: 'bootstrap-table-zh-CN'
            },
            'bootstrap-datetimepicker': {
                deps: [
                    'jquery',
                    'css!plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min'
                ],
                exports: 'bootstrap-datetimepicker'
            },
            'bootstrap-datetimepicker-zh-CN': {
                deps: [
                    'bootstrap-datetimepicker',
                ],
                exports: 'bootstrap-datetimepicker-zh-CN'
            },
            'easyui': {
                deps: [
                    'jquery',
                    'css!framework/plugins/easyui/themes/default/easyui',
                    'css!framework/plugins/easyui/themes/icon'
                ],
                exports: 'easyui'
            },
            'easyui-lang-zh_CN': {
                deps: [
                    'easyui'
                ],
                exports: 'easyui-lang-zh_CN'
            },
            'extJs': {
                deps: [
                    'jquery',
                    'css!style/css/dreamlu',
                    'css!foundation-icons/foundation-icons'
                ],
                export: 'extJs'
            }
        }
    });

    function newAndInit(url, options) {
        require([url], function (_module) {
            var $module = new _module();
            $module.init(options);
        });
    }

    //等待dom加载完成后初始化
    function $newAndInit(url, options) {
        $(function () {
            newAndInit(url, options);
        });
    }

    function init(url, options) {
        require([url], function (_module) {
            _module.init(options);
        });
    }

    //等待dom加载完成后初始化
    function $init(url, options) {
        $(function () {
            init(url, options);
        });
    }

</script>
