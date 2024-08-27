//package com.thanos.web3j.protocol.http;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ByteArrayEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
//import org.apache.http.message.BasicHeader;
//
//import com.thanos.web3j.protocol.Service;
//import com.thanos.web3j.protocol.core.Request;
//import com.thanos.web3j.protocol.core.Response;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * HTTP implementation of our services API.
// */
//public class HttpService extends Service {
//
//    private static Logger logger = LoggerFactory.getLogger("rpc");
//
//    static {
//        System.setProperty("http.keepAlive","false");
//    }
//
//    public static final String DEFAULT_URL = "http://localhost:8545/";
//
//
//    private CloseableHttpClient httpClient;
//
//    private final String url;
//
//    public HttpService(String url, CloseableHttpClient httpClient, boolean includeRawResponses) {
//        super(includeRawResponses);
//        this.url = url;
//        this.httpClient = httpClient;
//    }
//
//    public HttpService(String url, CloseableHttpClient httpClient) {
//        this(url, httpClient, false);
//    }
//
//    public HttpService(String url) {
////        this(url, HttpClients.custom().setConnectionManagerShared(true).build());
//        //this(url, HttpClients.custom().setConnectionManagerShared(false).setConnectionManager(new BasicHttpClientConnectionManager()).build());
//        this(url, HttpClients.custom().setConnectionManagerShared(false).setConnectionManager(new BasicHttpClientConnectionManager()).evictIdleConnections(60000, TimeUnit.MILLISECONDS).evictExpiredConnections().build());
//    }
//
//    public HttpService() {
//        this(DEFAULT_URL);
//    }
//
//    protected void setHttpClient(CloseableHttpClient httpClient) {
//        this.httpClient = httpClient;
//    }
//
//    @Override
//    public <T extends Response> T send(
//            Request request, Class<T> responseType) throws IOException {
//
//        byte[] payload = objectMapper.writeValueAsBytes(request);
//
//        HttpPost httpPost = new HttpPost(this.url);
//        httpPost.setEntity(new ByteArrayEntity(payload));
//        Header[] headers = buildHeaders();
//        httpPost.setHeaders(headers);
//
//        ResponseHandler<T> responseHandler = getResponseHandler(responseType);
//        try {
//            return httpClient.execute(httpPost, responseHandler);
//        } finally {
//
//        }
//    }
//
//    @Override
//    public void close() {
//        try {
//            httpClient.close();
//        } catch (IOException e) {
//            logger.warn("HttpService close failed. e:", e);
//        }
//    }
//
//    @Override
//    public boolean isActive() {
//        return true;
//    }
//
//    private Header[] buildHeaders() {
//        List<Header> headers = new ArrayList<>();
//        headers.add(new BasicHeader("Content-Type", "application/json; charset=UTF-8"));
//        addHeaders(headers);
//        return headers.toArray(new Header[0]);
//    }
//
//    protected void addHeaders(List<Header> headers) { }
//
//    public <T extends Response> ResponseHandler<T> getResponseHandler(Class<T> type) {
//        return response -> {
//            int status = response.getStatusLine().getStatusCode();
//            if (status >= 200 && status < 300) {
//                HttpEntity entity = response.getEntity();
//
//                if (entity != null) {
//                    return objectMapper.readValue(response.getEntity().getContent(), type);
//                } else {
//                    return null;
//                }
//            } else {
//                throw new ClientProtocolException("Unexpected response status: " + status);
//            }
//        };
//    }
//}
