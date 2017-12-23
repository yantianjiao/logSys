// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.format = function(fmt)
{ //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

/**
 * 获取时间戳, 根据时间格式忽略掉时分秒
 */
function getTimeStamp(date, fmt) {
    return Date.parse(new Date(date.format(fmt)));
}
function getTimeString(date, fmt) {
    return new Date().format(fmt)+"";
}


/**
 * LBS drawRing
 * Date: 2015-04-24
 * ==================================
 * opts.parent 插入到哪里 一个JS元素对象
 * opts.width 宽度 = 2* (半径+弧宽)
 * opts.radius 半径
 * opts.arc 弧宽
 * opts.perent 百分比
 * opts.color 弧渲染颜色 [底色,进度色]
 * opts.text 文字,默认显示百分比
 * opts.textColor 文字渲染颜色
 * opts.textSize 文字渲染大小
 * opts.animated 是否以动画的方式绘制 默认false
 * opts.after 绘制完成时执行函数
 * ==================================
 **/
function drawRing(opts) {
    var _opts = {
        parent: document.body,
        width: 100,
        radius: 45,
        arc: 5,
        perent: 100,
        color: ['#ccc', '#042b61'],
        textColor: '#000',
        textSize: '14px',
        text: '',
        animated: false,
        after: function () {
        }
    }, k;
    for (k in opts) _opts[k] = opts[k];

    var parent = _opts.parent,
        width = _opts.width,
        radius = _opts.radius,
        arc = _opts.arc,
        perent = parseFloat(_opts.perent),
        color = _opts.color,
        text = _opts.text,
        textSize = _opts.textSize,
        textColor = _opts.textColor,
        c = document.createElement('canvas'),
        ctx = null,
        x = 0,
        animated = _opts.animated,
        after = _opts.after;

    parent.innerHTML = "";
    parent.appendChild(c);
    ctx = c.getContext("2d");
    ctx.canvas.width = width;
    ctx.canvas.height = width;

    function clearFill() {
        ctx.clearRect(0, 0, width, width);
    }

    function fillBG() {
        ctx.beginPath();
        ctx.lineWidth = arc;
        ctx.strokeStyle = color[0];
        ctx.arc(width / 2, width / 2, radius, 0, 2 * Math.PI);
        ctx.stroke();
    }

    function fillArc(x) {
        ctx.beginPath();
        ctx.lineWidth = arc;
        ctx.strokeStyle = color[1];
        ctx.arc(width / 2, width / 2, radius, -90 * Math.PI / 180, (x * 3.6 - 90) * Math.PI / 180);
        ctx.stroke();
    }

    function fillText(x) {
        ctx.font = textSize + ' Arial';
        ctx.fillStyle = textColor;
        ctx.textBaseline = "middle";
        ctx.textAlign = 'center';
        if (!text) {
            text = x.toFixed(2) + '%';
        }
        ctx.fillText(text, width / 2, width / 2);
    }

    function fill(x) {
        fillBG();
        fillArc(x);
        fillText(x);
    }

    if (!animated) return fill(perent);

    fill(x);
    !function animate() {
        if (++x > perent) return after && after();
        setTimeout(animate, 10);
        clearFill();
        fill(x);
    }();
}