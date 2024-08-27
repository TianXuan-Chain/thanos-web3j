package com.thanos.web3j.protocol.rpc;

import com.thanos.web3j.protocol.Service;
import com.thanos.web3j.protocol.core.Request;
import com.thanos.web3j.protocol.core.Response;
import com.thanos.web3j.protocol.core.methods.response.EthGetString;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * HTTP implementation of our services API.
 */
public class RpcSocketService extends Service {
    private static Logger logger = LoggerFactory.getLogger("rpc");

    private final static int RETRY_TIMES = 3;

    private volatile Socket socket;
    private JsonRpcClient jsonRpcClient;

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    private String ip;
    private int port;
    private SocketFactory socketFactory;


    public RpcSocketService(SocketFactory socketFactory, String ip, int port) {
        super(false);
        this.jsonRpcClient = new JsonRpcClient();
        this.ip = ip;
        this.port = port;
        this.socketFactory = socketFactory;
        doConnect();
    }

    @Override
    public <T extends Response> T send(
            Request request, Class<T> responseType) throws IOException {

        if (socket == null) {
            logger.warn("send msg failed. remote: [{}:{}], socket create failed.", ip, port);
            throw new RuntimeException("RpcSocketService send msg error, socket create failed.");
        }

        try {
            T res = null;
            for (int i = 1; i <= RETRY_TIMES; i++) {
                try {
                    res = jsonRpcClient.invokeAndReadResponse(request.getMethod(), request.getParams(), responseType, socket.getOutputStream(), socket.getInputStream());
                    break;
                } catch (Throwable e) {
                    if (i == RETRY_TIMES) {
                        throw e;
                    }
                }
            }
            return res;
        } catch (Throwable throwable) {
            logger.warn("RpcSocketService send msg error, request:{}. exception:{}.", request, ExceptionUtils.getStackTrace(throwable));
            throw new RuntimeException("RpcSocketService send msg error", throwable);
        }
    }

    public void close() {
        try {
            if (this.socket == null) {
                return;
            }

            try {
                this.socket.close();
            } catch (Exception e) {
                logger.warn("RpcSocketService socket close with exception:", e);
            }

            try {
                this.socket.getOutputStream().close();
            } catch (Exception e) {
                logger.warn("RpcSocketService shutdown output failed. e:", e);

            }


            try {
                this.socket.getInputStream().close();
            } catch (Exception e) {
                logger.warn("RpcSocketService shutdown input failed. e:", e);

            }

        } catch (Exception e) {
            logger.warn("RpcSocketService close failed. e:", e);
        }
    }

    @Override
    public boolean isActive() {
        boolean isActive = true;
        try {
            if (socket == null) {
                logger.warn("send heartBeat package failed. remote: [{}:{}], socket create failed.", ip, port);
                return false;
            }
            jsonRpcClient.invokeAndReadResponse("thanos_protocolVersion", new ArrayList<>(), EthGetString.class, socket.getOutputStream(), socket.getInputStream());
        } catch (Throwable throwable) {
            logger.warn("send heartBeat package failed. remote: [{}:{}], e:{}.", ip, port, throwable.toString());
            isActive = false;
        }
        return isActive;
    }


    private void checkConnect() {
        if (socket.isOutputShutdown()) {
            synchronized (this) {
                if (socket.isOutputShutdown()) {
                    doConnect();
                }
            }
        }
    }

    private void doConnect() {
        try {
//            this.socket = this.socketFactory.createSocket(this.ip, this.port);
            socket = this.socketFactory.createSocket();
            socket.connect(new InetSocketAddress(this.ip, this.port), 5000);//socket连接建立超时时间5s
            socket.setSoTimeout(1200000);//socket读写阻塞时间最大值：20min
            socket.setReuseAddress(false);
//            logger.info("connect [{}]:[{}] success", this.ip, this.port);
        } catch (Exception e) {
            logger.warn("socket to [{}:{}] connect error. e:{}", ip, port, e.getMessage());
        }
    }


}
