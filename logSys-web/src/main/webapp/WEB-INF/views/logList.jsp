
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/requirejs.jsp" %>
<link rel="stylesheet" type="text/css" href="${ctxPath}/static/style/css/logList.css">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>日志查询</title>
</head>
<body>
<div>
    <div class="form-inline poll-left" style="background-color: #f5f5f5;padding:10px">
        <div class="input-daterange input-group" id="datetimepicker">
            <input class="input-sm form-control form_datetime" name="end" placeholder="开始时间" id="beginTime" type="text"
                   value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
            <span class="input-group-addon">到</span>
            <input class="input-sm form-control form_datetime" name="end" placeholder="结束时间" id="endTime" type="text"
                   value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
        </div>
        <div class="input-daterange input-group" >
            <span class="input-group-addon">&nbsp;&nbsp;&nbsp;&nbsp;userId:</span>
            <input name="value" type="text" class="form-control" id="userId"
                   placeholder="请输入userId的值" style="width: 250px"/>
        </div>
        <div class="input-daterange input-group" >
            <span class="input-group-addon">&nbsp;&nbsp;&nbsp;&nbsp;sid:</span>
            <input name="value" type="text" class="form-control" id="sid"
                   placeholder="请输入sid的值" style="width: 250px"/>

        </div>
        <div class="input-daterange form-group">
            <span class="input-group-addon">&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <input name="value" type="text" class="form-control" id="conditionValue"
            placeholder="请输入其他参数的值" style="width: 250px"/>
            <select class="form-control" id="conditionCommand">
                <option value="等于">包含</option>
                <option value="不等于">不包含</option>
            </select>
        </div>
        <button id="btn-search" class="btn btn-primary" type="button"> 搜索</button>
    </div>
</div>
<div class="box box-primary" style="padding: 10px">
    <textarea id="logData" style="width: 100%;height: 80%">

    </textarea>
</div>


<script>
    $newAndInit("${ctx}/static/js/log/logList.js?v=2.02", {
        url: '${ctx}/logQuery/query'
    });
</script>
</body>
</html>