package com.thanos.web3j.protocol.manage;

import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.http.OkHttpService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 类Web3Manager.java的实现描述：
 *
 * @author xuhao create on 2021/1/7 16:02
 */

public class Web3Manager {
    private static final Logger logger = LoggerFactory.getLogger("rpc");


    private GatewayConnector[] allGatewayConnectors;
    private final static int RETRY = 3;
    //可供进行rpc连接的gateway数
    private int rpcMaxCount;
    private AtomicLong rpcCurrentUse = new AtomicLong();
    //可供进行http连接的gateway数
    private List<String> rpcHttpStrList;
    private int httpMaxCount;
    private AtomicLong httpCurrentUse = new AtomicLong();

    private SystemConfig systemConfig;
    private volatile boolean isClosed = false;

    public Web3Manager(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
        initGatewayConnectors();
        checkConnect();
    }


    public Web3j getWeb3jRandomly() {
        if (rpcMaxCount == 0) {
            logger.warn("get available Web3j failed, gateway.rpc.ip.list is null.");
            return null;
        }
        for (int j = 0; j < RETRY; j++) {
            int i = (int) (rpcCurrentUse.getAndIncrement() % rpcMaxCount);
            Web3j web3j = allGatewayConnectors[i].getWeb3jRandomly();
            if (web3j != null) {
                return web3j;
            }
        }
        logger.warn("get available Web3j failed, retry {} times.", RETRY);
        return null;
    }


    public Web3j getHttpWeb3jRandomly() {
        if (httpMaxCount == 0) {
            logger.warn("get available Web3j failed, gateway.http.ip.list is null.");
            return null;
        }
        int i = (int) (httpCurrentUse.getAndIncrement() % httpMaxCount);
        return Web3j.build(new OkHttpService(rpcHttpStrList.get(i)));
    }

    public Web3j getHttpWeb3jByNodeIndex(int index) {
        if (index >= rpcHttpStrList.size()) {
            logger.error("getHttpWeb3jByNodeIndex, index:{} out of bound:{}.", index, rpcHttpStrList.size());
            throw new IndexOutOfBoundsException();
        }
        return Web3j.build(new OkHttpService(rpcHttpStrList.get(index)));
    }

    public void shutdown() {
        isClosed = true;
        releaseAllConnects();
        allGatewayConnectors = null;
    }


    private void initGatewayConnectors() {
        this.rpcHttpStrList = getHttpUrlFromIpList(systemConfig.gatewayHttpIPList());
        this.httpMaxCount = rpcHttpStrList.size();

        List<String> rpcIpStrList = systemConfig.gatewayRpcIPList();
        this.rpcMaxCount = rpcIpStrList.size();

        if (rpcMaxCount > 0) {
            allGatewayConnectors = new GatewayConnector[rpcMaxCount];

            for (int i = 0; i < rpcMaxCount; i++) {
                try {
                    allGatewayConnectors[i] = new GatewayConnector(systemConfig, rpcIpStrList.get(i));
                } catch (Exception e) {
                    logger.error("gateway:[{}] init failed.", rpcIpStrList.get(i), e);
                }
            }
        }
        logger.info("Web3Manager init success.");
    }

    private void checkConnect() {
        //no rpc gateway, not need check connect
        if (rpcMaxCount == 0) {
            return;
        }

        int checkInterval = systemConfig.getCheckInterval();

        new Thread(() -> {
            while (!isClosed) {
                try {
                    Thread.sleep(checkInterval * 1000);
                    for (GatewayConnector gatewayConnector : allGatewayConnectors) {
                        gatewayConnector.checkConnect();
                    }
                    logger.info("checkConnect success!");
                } catch (Exception e) {
                    logger.error("checkConnect exception.", e);
                }
            }
        }).start();
    }

    private void releaseAllConnects() {
        for (GatewayConnector gatewayConnector : allGatewayConnectors) {
            gatewayConnector.release();
        }
    }

    private List<String> getHttpUrlFromIpList(List<String> ipList) {
        List<String> urlList = new ArrayList<>();
        if (CollectionUtils.isEmpty(ipList)) {
            return urlList;
        }
        for (String ip : ipList) {
            String url = "http://" + ip + "/rpc";
            urlList.add(url);
        }
        return urlList;
    }
}
