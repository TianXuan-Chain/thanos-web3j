package com.thanos.web3j.protocol.http;

import com.thanos.common.utils.ThanosThreadFactory;
import com.thanos.web3j.protocol.Service;
import com.thanos.web3j.protocol.core.Request;
import com.thanos.web3j.protocol.core.Response;
import okhttp3.*;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.RealConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okio.Buffer;
import okio.BufferedSource;

/**
 * OkHttpService.java description：
 *
 * @Author laiyiyu create on 2021-12-16 15:36:19
 */
public class OkHttpService extends Service {

    static {
        System.setProperty("http.keepAlive", "false");
    }

    public static final MediaType JSON_MEDIA_TYPE
            = MediaType.parse("application/json; charset=utf-8");

    private static Logger logger = LoggerFactory.getLogger("rpc");


    private static OkHttpClient httpClient = createOkHttpClient();

    private final String url;

    private boolean includeRawResponse;

    private static HashMap<String, String> headers = new HashMap() {{
        // put("Connection", "close");
    }};

    public OkHttpService(String url, boolean includeRawResponses) {
        super(includeRawResponses);
        this.url = url;
        //this.httpClient = httpClient;
    }

//    public OkHttpService(String url, OkHttpClient httpClient) {
//        this(url, httpClient, false);
//    }

    public OkHttpService(String url) {
//        this(url, HttpClients.custom().setConnectionManagerShared(true).build());
        //this(url, HttpClients.custom().setConnectionManagerShared(false).setConnectionManager(new BasicHttpClientConnectionManager()).build());
        this(url, false);
    }

//    private static OkHttpClient createOkHttpClient() {
//        return new OkHttpClient
//                .Builder()
//                .connectionPool(new ConnectionPool(0, 3, TimeUnit.SECONDS))
//                .retryOnConnectionFailure(true)
//                .connectTimeout(3, TimeUnit.SECONDS)
//                .readTimeout(3, TimeUnit.SECONDS)
//                .build();
//    }

    private static OkHttpClient createOkHttpClient() {
        return new OkHttpClient
                .Builder()
                .connectionPool(new ConnectionPool(50, 60, TimeUnit.SECONDS))
                .retryOnConnectionFailure(true)
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                //.readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public <T extends Response> T send(Request request, Class<T> responseType) throws IOException {
        String payload = objectMapper.writeValueAsString(request);

        try (InputStream result = performIO(payload)) {
            if (result != null) {
                return objectMapper.readValue(result, responseType);
            } else {
                return null;
            }
        }
    }


    protected InputStream performIO(String request) throws IOException {

        RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, request);
        Headers headers = buildHeaders();

        okhttp3.Request httpRequest = new okhttp3.Request.Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build();

        okhttp3.Response response = httpClient.newCall(httpRequest).execute();
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return buildInputStream(responseBody);
            } else {
                return null;
            }
        } else {
            throw new RuntimeException(
                    "Invalid response received: " + response.body());
        }
    }

    private InputStream buildInputStream(ResponseBody responseBody) throws IOException {
        InputStream inputStream = responseBody.byteStream();

        if (includeRawResponse) {
            // we have to buffer the entire input payload, so that after processing
            // it can be re-read and used to populate the rawResponse field.

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body
            Buffer buffer = source.buffer();

            long size = buffer.size();
            if (size > Integer.MAX_VALUE) {
                throw new UnsupportedOperationException(
                        "Non-integer input buffer size specified: " + size);
            }

            int bufferSize = (int) size;
            BufferedInputStream bufferedinputStream =
                    new BufferedInputStream(inputStream, bufferSize);

            bufferedinputStream.mark(inputStream.available());
            return bufferedinputStream;

        } else {
            return inputStream;
        }
    }

    @Override
    public void close() {
        //no need to close for ok http client!
    }

    @Override
    public boolean isActive() {
        return true;
    }

    private Headers buildHeaders() {
        return Headers.of(headers);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addHeaders(Map<String, String> headersToAdd) {
        headers.putAll(headersToAdd);
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }
}
