package com.logSys.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

/**
 * 功能描述：http工具类
 * Created by ytj on 2017/12/21.
 */
public class HttpUtil {
    private final static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private final static int CONNECT_TIMEOUT = 5000;

    private final static int READ_TIMEOUT = 20000;

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String httpGet(String url, String param) {
        return httpGet(url, param, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url            发送请求的URL
     * @param param          请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param connectTimeout 连接超时时间 单位毫秒
     * @param readTimeout    读超时时间 单位毫秒
     * @return URL 所代表远程资源的响应结果
     */
    public static String httpGet(String url, String param, int connectTimeout, int readTimeout) {
        return send(false, "GET", url, null, param, null, connectTimeout, readTimeout);
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param Map<String,String> param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String httpGet(String url, Map<String, String> param) {
        return httpGet(url, param, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url            发送请求的 URL
     * @param param          Map<String,String> param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。 也可以是json字符串
     * @param connectTimeout 连接超时时间 单位毫秒
     * @param readTimeout    读超时时间 单位毫秒
     * @return 所代表远程资源的响应结果
     */
    public static String httpGet(String url, Map<String, String> param, int connectTimeout, int readTimeout) {
        if (!CollectionUtil.isEmpty(param)) {
            String paramStr = getParamStr(null, param);

            return send(false, "GET", url, null, paramStr, null, connectTimeout, readTimeout);
        } else {
            return send(false, "GET", url, null, null, null, connectTimeout, readTimeout);
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 默认将参数转换为如下形式提交 如果指定contentType（目前支持 from json xml） 则根据contentType来做相应转换
     *              name1=value1&name2=value2
     * @return 所代表远程资源的响应结果
     */
    public static String httpPost(String url, Map<String, String> param) {
        if (!CollectionUtil.isEmpty(param)) {
            String paramStr = getParamStr(ContentTypeEnum.FORM, param);

            return send(false, "POST", url, null, paramStr, null, CONNECT_TIMEOUT, READ_TIMEOUT);
        } else {
            return send(false, "POST", url, null, null, null, CONNECT_TIMEOUT, READ_TIMEOUT);
        }

    }

    /***
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 操作，post请求报文体
     * @return 所代表远程资源的响应结果
     */
    public static String httpPost(String url, String param) {
        return httpPost(url, param, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url            发送请求的 URL
     * @param param          操作，post请求报文体
     * @param connectTimeout 连接超时时间 单位毫秒
     * @param readTimeout    读超时时间 单位毫秒
     * @return 所代表远程资源的响应结果
     */
    public static String httpPost(String url, String param, int connectTimeout, int readTimeout) {
        return send(false, "POST", url, null, param, null, connectTimeout, readTimeout);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 参数，键值对。工具类会将键值对处理为，key1=value1&key2=value2&key3=value3发送给对方。
     * @return 所代表远程资源的响应结果
     */
    public static String httpPost(String url, Map<String, String> param, Map<String, String> head) {
        return httpPost(url, param, head, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url            发送请求的 URL
     * @param param          参数，键值对。工具类会将键值对处理为，key1=value1&key2=value2&key3=value3发送给对方。
     * @param connectTimeout 连接超时时间 单位毫秒
     * @param readTimeout    读超时时间 单位毫秒
     * @return 所代表远程资源的响应结果
     */

    public static String httpPost(String url, Map<String, String> param, Map<String, String> head, int connectTimeout,
                                  int readTimeout) {
        if (!CollectionUtil.isEmpty(param)) {
            String paramStr = getParamStr(ContentTypeEnum.FORM, param);

            return send(false, "POST", url, ContentTypeEnum.FORM, paramStr, head, connectTimeout, readTimeout);
        } else {
            return send(false, "POST", url, ContentTypeEnum.FORM, null, head, connectTimeout, readTimeout);
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     * @param body 报文字符串
     * @return 所代表远程资源的响应结果
     */
    public static String httpPost(String url, ContentTypeEnum contentType, String body, Map<String, String> head) {
        return httpPost(url, contentType, body, head, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     * @param body           报文字符串
     * @param connectTimeout 连接超时时间 单位毫秒
     * @param readTimeout    读超时时间 单位毫秒
     * @return 所代表远程资源的响应结果
     */
    public static String httpPost(String url, ContentTypeEnum contentType, String body, Map<String, String> head,
                                  int connectTimeout, int readTimeout) {
        if (body != null && !"".equals(body)) {
            return send(false, "POST", url, contentType, body, head, connectTimeout, readTimeout);
        } else {
            return send(false, "POST", url, contentType, null, head, connectTimeout, readTimeout);
        }

    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param contentType（目前支持 from json xml）
     * @param url              发送请求的 URL
     * @param param            默认将参数转换为如下形式提交 如果指定contentType（目前支持 from json xml） 则根据contentType来做相应转换
     *                         name1=value1&name2=value2
     * @return 所代表远程资源的响应结果
     */
    public static String httpPost(String url, ContentTypeEnum contentType, Map<String, String> param) {
        return httpPost(url, contentType, param, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param contentType（目前支持 from json xml）
     * @param url              发送请求的 URL
     * @param param            默认将参数转换为如下形式提交 如果指定contentType（目前支持 from json xml） 则根据contentType来做相应转换
     *                         name1=value1&name2=value2
     * @return 所代表远程资源的响应结果
     */
    public static String httpPost(String url, ContentTypeEnum contentType, Map<String, String> param,
                                  int connectTimeout, int readTimeout) {
        if (!CollectionUtil.isEmpty(param)) {
            String paramStr = getParamStr(contentType, param);

            return send(false, "POST", url, contentType, paramStr, null, connectTimeout, readTimeout);
        } else {
            return send(false, "POST", url, contentType, null, null, connectTimeout, readTimeout);
        }

    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url         请求地址
     * @param contentType 请求类型
     * @param param       发送内容, 自己将参数装换为相应格式的请求报文 并指定contentType
     * @return String
     */
    public static String httpPost(String url, ContentTypeEnum contentType, String param) {
        return httpPost(url, contentType, param, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url            请求地址
     * @param contentType    请求类型
     * @param param          发送内容, 自己将参数装换为相应格式的请求报文 并指定contentType
     * @param connectTimeout 连接超时时间 单位毫秒
     * @param readTimeout    读超时时间 单位毫秒
     * @return String
     */
    public static String httpPost(String url, ContentTypeEnum contentType, String param, int connectTimeout,
                                  int readTimeout) {
        if (param != null && !"".equals(param)) {
            return send(false, "POST", url, contentType, param, null, connectTimeout, readTimeout);
        } else {
            return send(false, "POST", url, contentType, null, null, connectTimeout, readTimeout);
        }

    }

    /**
     * 发送http请求
     *
     * @param isSSL  是否https
     * @param method HTTP方法。POST、 GET
     * @param url    请求URL
     * @param head   http头信息
     */
    private static String send(boolean isSSL, String method, String url, ContentTypeEnum contentType, String param,
                               Map<String, String> head, int connectTimeout, int readTimeout) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = null;
            if ("GET".equals(method)) {
                String urlNameString = url + "?" + param;
                realUrl = new URL(urlNameString);
            } else {
                realUrl = new URL(url);
            }

            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            if (null != contentType) {
                conn.setRequestProperty("Content-Type", contentType.getValue());
            }

            if (head != null && !head.isEmpty()) {
                Set<String> keys = head.keySet();
                for (String key : keys) {
                    conn.setRequestProperty(key, head.get(key));
                }
            }

            // 获取URLConnection对象对应的输出流
            if ("GET".equals(method)) {
                // 建立实际的连接
                conn.connect();
            } else {
                // 发送POST请求必须设置如下两行

                conn.setDoOutput(true);
                conn.setDoInput(true);
                if (param != null && !"".equals(param)) {
                    out = new PrintWriter(conn.getOutputStream());
                    // 发送请求参数
                    out.print(param);
                    // flush输出流的缓冲
                    out.flush();
                }
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("http send failed,url=" + url + "|param=" + param, e);
            // System.out.println("发送 POST 请求出现异常！" + e);
            throw new RuntimeException(e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;

    }

    /**
     * map转换为 key=1&key2=ee 这种格式
     *
     * @param param
     * @return String
     */
    private static String getParamStr(ContentTypeEnum contentType, Map<String, String> param) {
        if (param == null || param.isEmpty()) {
            return "";
        }
        if (null != contentType) {
            if (contentType == ContentTypeEnum.JSON) {
                return GsonUtil.toJson(param);
            }
        }
        StringBuilder sb = new StringBuilder();
        Set<String> keys = param.keySet();
        for (String key : keys) {
            sb.append("&").append(key).append("=").append(param.get(key));
        }

        return sb.toString().substring(1);
    }


    // public static void main(String[] args) {
    // Map<String, String> param = new HashMap<String, String>();
    // param.put("a", "111111");
    // param.put("b", "eeeee");
    // System.out.println(getParamStr(null, param));
    // }
}
