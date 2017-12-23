<%--
  Created by IntelliJ IDEA.
  User: jiangwei
  Date: 2017/9/18
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/requirejs.jsp" %>
<link rel="stylesheet" type="text/css" href="${ctxPath}/static/style/css/event.css">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>风险概览</title>
</head>
<body>
<div>
    <div class="form-inline poll-left" style="background-color: #f5f5f5;padding:10px">
        <div class="input-daterange input-group" id="datetimepicker">
            <input class="input-sm form-control form_datetime" name="start" placeholder="开始时间" id="starttime"
                   type="text" value="" type="text"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endtime\')}'})">
            <span class="input-group-addon">到</span>
            <input class="input-sm form-control form_datetime" name="end" placeholder="结束时间" id="endtime" type="text"
                   value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
        </div>

        <div class="input-daterange form-group">
            <select class="form-control" id="eventType" placeholder="事件类型">
                <option></option>
                <option value=" ">全部事件</option>
                <option value="REGISTER">注册事件</option>
                <option value="LOGIN">登录事件</option>
                <option value="ID_OCR_UPDATE">身份信息提交</option>
                <option value="ADD_BANK_CARD">绑卡事件</option>
                <option value="APPLY_COMMIT">联系人事件</option>
                <option value="FACE_DETECT_UPDATE">活体检测</option>
                <option value="PIN_DRAW_COMMIT">借款</option>
                <option value="FIND_TRADE_PASSWORD">找回密码</option>
                <option value="ADD_NEW_BANK_CARD">绑新卡</option>
                <option value="UPDATE_TRADE_PASSWORD">修改密码</option>
                <option value="DELETE_BANK_CARD">解绑银行卡</option>
                <option value="SHOPPING_BUY">分期提交</option>
                <option value="SHOPPING_STAGES">商品分期</option>
                <option value="OPERATOR_AUTHENTICATION">运营商认证</option>
            </select>

            <select class="form-control" id="riskDecision">
                <option></option>
                <option value=" ">全部结果</option>
                <option value="Accept">低风险</option>
                <option value="Review">中风险</option>
                <option value="Reject">高风险</option>
            </select>
        </div>
    </div>
</div>
<div id="toolbar" style="border-bottom: 4px solid #f5f5f5;height: 45px;padding-left:10px">
    <form class="form-inline poll-left" id="paramsForm">
        <div class="form-group pull-left">
            <select class="form-control" name="field" id="searchField">
                <option value="mobileNo">手机号</option>
                <option value="userNo">用户号</option>
                <option value="deviceFp">设备ID</option>
                <option value="idNo">身份证号</option>
                <option value="custNo">客户号</option>
                <option value="ip">IP地址</option>
                <option value="bssid">BSSID</option>
            </select>
            <input name="value" type="text" class="form-control" id="searchValue"
                   placeholder="在当前条件下进行搜索" style="width: 250px"/>
            <button id="btn-search" class="btn btn-primary" type="button"> 搜索</button>
            <button class="btn btn-primary" type="button"> 高级搜索</button>
        </div>
    </form>
</div>
<div class="box box-primary" style="padding: 10px">
    <table id="eventDataTable">
    </table>
</div>

<script id="baseInfoTemplate" type="text/html">
    <div class="activity-title">
        <h2>风险决策</h2>
    </div>
    <div class="activity-content">
        <div class="activity-base">
            <div class="canvas-score-wp">
                <div class="canvas-score">
                    <canvas id="canvasScore" width="100" height="100"></canvas>
                </div>
            </div>
            <ul class="activity-list">
                <li>
                    <label>sequenceID</label>
                    <span id="sequence_id">{businessInfo.sequenceId}</span></li>
                <%--<%=data.sequence_idsequence_id%>--%>
                <li>
                    <label>事件标识</label>
                    <%--prefilter_professional_android--%>
                    <span id="eventId">{businessInfo.eventType}</span></li>
                <li class="">
                    <label>策略集名称</label>
                    <%--预筛事件_安卓--%>
                    <span id="policyName_group">{businessInfo.policySetName}</span></li>
                <li class="">
                    <label>风险级别</label>
                    <%--Reject--%>
                    <span id="riskStatus" class="{businessInfo.riskDecision}">{businessInfo.riskDecision}</span></li>
                <li class="">
                    <label>风险系数</label>
                    <%--80--%>
                    <span id="riskScore">{businessInfo.riskScore}</span></li>
            </ul>
        </div>
        <div class="divider"></div>
        <div class="activity-hit" style="display: {hitPolicyList.length>0?'block':'none'};">
            <div class="activity-hit-title">
                <h5>风险详情</h5>
                <a class="view-more" style="display: none;">显示更多</a>
            </div>
            <div class="activity-hit-content hit-list">
                { each hitPolicyList as policy i }
                <ul class="activity-list activity-hit-policy"
                    style="overflow: hidden; margin-top: 10px; margin-bottom: 10px;">
                    <%--非准入客户_安卓--%>
                    <li><label>策略名称</label><span>{policy.policyName}</span></li>
                    <%--Reject--%>
                    <li><label>风险状态</label><span class="{policy.policyDecision}">{policy.policyDecision}</span></li>
                    <%--80--%>
                    <li><label>风险系数</label><span>{policy.riskScore}</span></li>
                    <%--非准入客户--%>
                    <li><label>风险类型</label><span>{policy.riskType}</span></li>
                    <li><label>命中规则：</label>
                        <ul class="hit-policy-rule-list has-detail">
                            { each policy.hitRules as rule i }
                            <%--2a72d139afc3458f88a8938738fd66bf--%>
                            <li><input value="" type="hidden">
                                <%--手机号命中欠款公司法人代表名单--%>
                                <a>{rule.ruleName}</a>
                            </li>
                            { /each }
                        </ul>
                    </li>
                </ul>
                { /each }
            </div>
        </div>
    </div>
</script>

<script id="loanMonitorInfoTemplate" type="text/html">
    <div class="activity-title">
        <h2>贷后监控</h2>
    </div>
    <div class="activity-content">
    </div>
</script>

<script id="ipInfoTemplate" type="text/html">
    <div class="activity-title">
        <h2>地理位置信息</h2>
    </div>
    <div class="activity-content">
        <ul class="activity-list ip-list">
            <li>
                <label>来源IP</label>
                <span id="ipAddress" class="font-color-blue">{businessInfo.ip}</span></li>
            <li>
                <label>IP所在省份</label>
                <span id="ipGeoLocation">{businessInfo.ipAddressProvince}</span></li>
            <li>
                <label>IP所在城市</label>
                <span id="ipLongitude">{businessInfo.ipAddressCity}</span></li>
            <li>
                <label>IP类型</label>
                <span id="ipLatitude"></span></li>
            <li>
                <label>代理检测</label>
                <span id="ipIsp"></span></li>
            <li>
                <label>网络类型</label>
                <span id="ipType">{businessInfo.ipIsWifi}</span></li>
        </ul>
        <ul class="activity-list ip-list ipJudge">
            <li>
                <label>LBS地址</label>
                <span id="trueGeoLocation">{deviceInfo.addrInfo}</span></li>
            <li>
                <label>经度</label>
                <span id="trueIpLongitude">{deviceInfo.longitude}</span></li>
            <li>
                <label>纬度</label>
                <span id="trueIpLatitude">{deviceInfo.latitude}</span></li>
            <li>
                <label>LBS国家</label>
                <span id="trueIpIsp">{deviceInfo.country}</span></li>
            <li>
                <label>LBS省份</label>
                <span id="trueIpType">{deviceInfo.province}</span></li>
            <li>
                <label>LBS城市</label>
                <span id="trueIpRiskScore">{deviceInfo.city}</span></li>
        </ul>
    </div>
</script>

<script id="deviceInfoTemplate" type="text/html">
    <div class="activity-title">
        <h2>设备信息</h2>
    </div>
    <div class="activity-content">
        <ul class="activity-list" id="device_list" style="display: block;">
            {if deviceInfo.deviceOs=='IOS'}
            <li><label>设备名称</label><span>{deviceInfo.deviceName}</span></li>
            <li><label>终端类型</label><span>{deviceInfo.terminalType}</span></li>
            <li><label>是否越狱</label><span>{deviceInfo.isJailbreaking}</span></li>
            <li><label>是否模拟器</label><span>{deviceInfo.isEmulator}</span></li>
            <li><label>自研设备ID</label><span>{deviceInfo.deviceFingerprint}</span></li>
            <li><label>M2</label><span>{deviceInfo.deviceFingerPrintM2}</span></li>
            <li><label>同盾设备id</label><span>{deviceInfo.deviceFingerPrintTd}</span></li>
            <li><label>网络类型</label><span>{deviceInfo.networkType}</span></li>
            <li><label>WIFI名称</label><span>{deviceInfo.wifiName}</span></li>
            <li><label>WIFI标识</label><span>{deviceInfo.bssid}</span></li>
            <li><label>操作系统</label><span>{deviceInfo.deviceOs}</span></li>
            <li><label>操作系统版本</label><span>{deviceInfo.deviceOsVersion}</span></li>
            <li><label>手机品牌</label><span>{deviceInfo.brand}</span></li>
            <li><label>手机型号</label><span>{deviceInfo.model}</span></li>
            <li><label>手机序列号</label><span>{deviceInfo.deviceSn}</span></li>
            <li><label>IDFA</label><span>{deviceInfo.imsi}</span></li>
            <li><label>IDFV</label><span>{deviceInfo.wifiMac}</span></li>
            <li><label>deviceUUID</label>{deviceInfo.uuid}<span></span></li>
            <li><label>存储空间</label><span>{deviceInfo.totalStorage}</span></li>
            <li><label>已用存储空间</label><span>{deviceInfo.usedStorage}</span></li>
            {/if}
            {if deviceInfo.deviceOs=='ANDROID'}
            <li><label>设备名称</label><span>{deviceInfo.deviceName}</span></li>
            <li><label>终端类型</label>{deviceInfo.terminalType}<span></span></li>
            <li><label>是否越狱</label>{deviceInfo.isJailbreaking}<span></span></li>
            <li><label>是否root</label>{deviceInfo.isRoot}<span></span></li>
            <li><label>是否模拟器</label><span>{deviceInfo.isEmulator}</span></li>
            <li><label>是否云OS</label><span>{deviceInfo.isYUNOS}</span></li>
            <li><label>自研设备ID</label><span>{deviceInfo.deviceFingerprint}</span></li>
            <li><label>M2</label><span>{deviceInfo.deviceFingerPrintM2}</span></li>
            <li><label>同盾设备id</label><span>{deviceInfo.deviceFingerPrintTd}</span></li>
            <li><label>网络类型</label><span>{deviceInfo.networkType}</span></li>
            <li><label>WIFI名称</label><span>{deviceInfo.wifiName}</span></li>
            <li><label>WIFI标识</label><span>{deviceInfo.bssid}</span></li>
            <li><label>操作系统</label><span>{deviceInfo.deviceOs}</span></li>
            <li><label>操作系统版本</label><span>{deviceInfo.deviceOsVersion}</span></li>
            <li><label>手机品牌</label><span>{deviceInfo.brand}</span></li>
            <li><label>手机型号</label><span>r{deviceInfo.model}</span></li>
            <li><label>出厂时间</label><span>{deviceInfo.factoryTime}</span></li>
            <li><label>手机序列号</label><span>{deviceInfo.deviceSn}</span></li>
            <li><label>IMEI</label><span>{deviceInfo.imei}</span></li>
            <li><label>WIFI MAC</label><span>{deviceInfo.wifiMac}</span></li>
            <li><label>UUID</label><span>{deviceInfo.uuid}</span></li>
            <li><label>IMSI</label><span>{deviceInfo.imsi}</span></li>
            <li><label>存储空间</label><span>{deviceInfo.totalStorage}</span></li>
            <li><label>已用存储空间</label><span>{deviceInfo.usedStorage}</span></li>
            {/if}
            {if deviceInfo.deviceOs=='PC'}
            <li><label>浏览器名</label><span>{deviceInfo.browserName}</span></li>
            <li><label>浏览器版本</label><span>{deviceInfo.browserVersion}</span></li>
            <li><label>是否启用cookies</label><span>{deviceInfo.cookiesEnable}</span></li>
            <li><label>cookies</label><span>{deviceInfo.cookiescookies}</span></li>
            <li><label>操作系统</label><span>{deviceInfo.operatingSystem}</span></li>
            <li><label>userAgent</label><span>{deviceInfo.userAgent}</span></li>
            <li><label>浏览器渲染引擎</label><span>{deviceInfo.browserRenderingEngine}</span></li>
            <li><label>屏幕分辨率</label><span>{deviceInfo.screenResolution}</span></li>
            <li><label>是否安装摄像头</label><span>{deviceInfo.installCamera}</span></li>
            {/if}
        </ul>


    </div>
</script>

<script id="fieldInfoTemplate" type="text/html">
    <div class="activity-title">
        <h2>业务数据</h2>
    </div>
    <div class="activity-content">
        <ul class="activity-list field-other-info">
            { each fieldMap as field i}
            <li><label>{field}</label><span id="idCardCounty">{businessInfo[i]}</span></li>
            { /each }
        </ul>
    </div>
</script>

<section class="content" id="eventDetail">
    <div class="event-detail-slider" id="detail-slider" title="关闭事件详情" ></div>
    <div class="slideInfoContent">
        <!-- column-left start -->
        <div class="column-left">
            <!-- 事件评估信息tab start -->
            <section id="baseInfo" class="box activity">
            </section>
            <!-- column-right start -->
            <div class="column-right">
                <!-- 代理IP start -->
                <section id="ipInfo" class="box activity">
                </section>
                <!-- 代理IP End -->
                <!-- 字段信息tab End -->
            </div>
            <!-- column-right end -->
        </div>
        <!-- column-left end -->
        <!-- 设备环境 start -->
        <div id="deviceInfo" class="box activity">
        </div>
        <!-- 设备环境信息tab End -->

        <!-- 业务数据 start -->
        <section id="fieldInfo" class="box activity">
        </section>
    </div>
</section>

<script>
    $newAndInit("${ctx}/static/js/event/eventList.js?v=2.02", {
        url: '${ctx}/event/listData'
    });
</script>
</body>
</html>