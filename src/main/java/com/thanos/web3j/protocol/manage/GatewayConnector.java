package com.thanos.web3j.protocol.manage;

import com.thanos.common.utils.SSLUtil;
import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.rpc.RpcSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 类GatewayConnectManager.java的实现描述：
 *
 * @author xuhao create on 2021/1/8 11:11
 */

public class GatewayConnector {
    private static final Logger logger = LoggerFactory.getLogger("rpc");

    private Web3j[] web3jList;
    private String ipPortStr;
    private String ip;
    private int port;
    private int maxCount;
    private AtomicLong currentUse = new AtomicLong();
    private int checkIndex = 0;
    private SocketFactory socketFactory;
    private final static int RETRY = 3;

    public GatewayConnector(SystemConfig systemConfig, String ipPortStr) {
        this(ipPortStr, systemConfig.getWeb3Size(), systemConfig.needTLS(), systemConfig.getKeyPath(), systemConfig.getCertsPath());
    }

    public GatewayConnector(String ipPortStr, int maxCount, boolean needTls, String keyPath, String certsPath) {
        this.ipPortStr = ipPortStr;
        String[] ipAndPortStr = ipPortStr.split(":");
        this.ip = ipAndPortStr[0];
        this.port = Integer.valueOf(ipAndPortStr[1]);
        this.maxCount = maxCount;
        loadSocketFactory(needTls, keyPath, certsPath);
        initWeb3jList();
    }

    private void loadSocketFactory(boolean needTls, String keyPath, String certsPath) {
        if (needTls) {
            SSLContext sslContext = SSLUtil.loadSSLContext(keyPath, certsPath);
            this.socketFactory = sslContext.getSocketFactory();
        } else {
            this.socketFactory = SocketFactory.getDefault();
        }
    }

    private void initWeb3jList() {
        this.web3jList = new Web3j[maxCount];
        for (int i = 0; i < maxCount; i++) {
            Web3j web3j = Web3j.build(new RpcSocketService(socketFactory, ip, port));
            web3jList[i] = web3j;
        }
        logger.info("init web3jList down. gateway:[{}], connectCount:{}.", ipPortStr, maxCount);
    }

    public Web3j getWeb3jRandomly() {
        for (int j = 0; j < RETRY; j++) {
            int i = (int) (currentUse.getAndIncrement() % maxCount);
            if (web3jList[i].isActive()) {
                return web3jList[i];
            }
        }
        return null;
    }

    public String getIpPortStr() {
        return ipPortStr;
    }


    public void checkConnect() {
        try {
            Web3j web3j = web3jList[checkIndex];
            if (!web3j.isActive()) {
                web3j.close();
                web3jList[checkIndex] = Web3j.build(new RpcSocketService(socketFactory, ip, port));
            }
        } catch (Exception e) {
            logger.error("checkConnect failed. remote:[{}], checkIndex:{}, e:", ipPortStr, checkIndex, e);
        } finally {
            checkIndex = (checkIndex + 1) % maxCount;
        }
    }

    public void release() {
        for (Web3j web3j : web3jList) {
            web3j.close();
        }
    }

}
