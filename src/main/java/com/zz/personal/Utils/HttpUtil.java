//package com.zz.personal.Utils;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.net.ssl.*;
//import java.io.IOException;
//import java.security.SecureRandom;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by zz on 2017.07.10.
// */
//public class HttpUtil {
//    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
//
//    /**
//     * 向指定URL发送GET方法的请求
//     *
//     * @param url   发送请求的URL
//     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
//     * @return URL 所代表远程资源的响应结果
//     */
//    public static String get(String url, Map<String, Object> param) throws IOException {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(2, TimeUnit.SECONDS)
//                .readTimeout(2, TimeUnit.SECONDS)
//                .writeTimeout(2, TimeUnit.SECONDS)
//                .build();
//
//        StringBuilder getParams = new StringBuilder();
//        if (null != param || !param.isEmpty()) {
//            boolean flag = false;
//            for (Map.Entry<String, Object> entry : param.entrySet()) {
//                if (flag) {
//                    getParams.append("&");
//                }
//                getParams.append(entry.getKey() + "=" + entry.getValue());
//                flag = true;
//            }
//            url = url + "?" + getParams;
//        }
//        log.info("http request is {}", url);
//        Request request = new Request.Builder()
//                .get()
//                .url(url)
//                .build();
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            log.error("服务器端错误: {}", response.body());
//        }
//        return response.body().string();
//    }
//
//
//    /**
//     * 向指定URL发送GET方法的请求
//     *
//     * @param url   发送请求的URL
//     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
//     * @return URL 所代表远程资源的响应结果
//     */
//    public static String getHttps(String url, Map<String, Object> param) throws IOException {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(2, TimeUnit.SECONDS)
//                .readTimeout(2, TimeUnit.SECONDS)
//                .writeTimeout(2, TimeUnit.SECONDS)
//                .hostnameVerifier(new TrustAllHostnameVerifier())
//                .sslSocketFactory(createSSLSocketFactory(), new X509TrustManager() {
//                    @Override
//                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//                    }
//
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//                    }
//
//                    @Override
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[0];
//                    }
//                })
//                .build();
//
//        StringBuilder getParams = new StringBuilder();
//        if (null != param || !param.isEmpty()) {
//            boolean flag = false;
//            for (Map.Entry<String, Object> entry : param.entrySet()) {
//                if (flag) {
//                    getParams.append("&");
//                }
//                getParams.append(entry.getKey() + "=" + entry.getValue());
//                flag = true;
//            }
//            url = url + "?" + getParams;
//        }
//        Request request = new Request.Builder()
//                .get()
//                .url(url)
//                .build();
//        log.info("request url is {}", url);
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            log.error("服务器端错误: {}", response.body());
//        }
//        return response.body().string();
//    }
//
//
//    /**
//     * 向指定 URL 发送POST方法的请求
//     *
//     * @param url      发送请求的 URL
//     * @param postBody 请求参数
//     * @return 所代表远程资源的响应结果
//     */
//    public static String post(String url, String postBody) throws IOException {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(2, TimeUnit.SECONDS)
//                .readTimeout(2, TimeUnit.SECONDS)
//                .writeTimeout(2, TimeUnit.SECONDS)
//                .build();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody))
//                .build();
//
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            log.error("服务器端错误: {}", response.body());
//        }
//        return response.body().string();
//    }
//
//
//    public static Map<String, Object> OaApiCall(String catalog, String apiName, String username) throws Exception {
//        String oaUrl = "http://oa.zhubajie.la:888" + "/general/zbj-new/api/" + catalog + "/" + apiName + ".php";
//        String oaInfo = null;
//        try {
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .connectTimeout(2, TimeUnit.SECONDS)
//                    .readTimeout(2, TimeUnit.SECONDS)
//                    .writeTimeout(2, TimeUnit.SECONDS)
//                    .build();
//
//            RequestBody formBody = new FormBody.Builder()
//                    .add("user", username)
//                    .build();
//
//            okhttp3.Request request = new okhttp3.Request.Builder()
//                    .url(oaUrl)
//                    .post(formBody)
//                    .build();
//
//            Response response = client.newCall(request).execute();
//            if (!response.isSuccessful()) {
//                log.error("服务器端错误:{} ", response.body());
//            }
//            oaInfo = response.body().string();
//            Map<String, Object> result = JsonUtil.toMap(oaInfo);
//            if (result.containsKey("data")) {
//                Map<String, Object> dataMap = JsonUtil.toMap(result.get("data").toString());
//                return dataMap;
//            }
//        } catch (IOException e) {
//            log.error("服务器端错误: {}", e);
//        }
//        return null;
//    }
//
//
//    private static class TrustAllCerts implements X509TrustManager {
//        @Override
//        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//        }
//
//        @Override
//        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//        }
//
//        @Override
//        public X509Certificate[] getAcceptedIssuers() {
//            return new X509Certificate[0];
//        }
//    }
//
//    private static class TrustAllHostnameVerifier implements HostnameVerifier {
//        @Override
//        public boolean verify(String hostname, SSLSession session) {
//            return true;
//        }
//    }
//
//    private static SSLSocketFactory createSSLSocketFactory() {
//        SSLSocketFactory ssfFactory = null;
//
//        try {
//            SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
//
//            ssfFactory = sc.getSocketFactory();
//        } catch (Exception e) {
//        }
//
//        return ssfFactory;
//    }
//}
