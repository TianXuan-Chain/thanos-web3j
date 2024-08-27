package com.thanos.web3j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 类Main.java的实现描述：
 *
 * @author xuhao create on 2021/3/15 15:07
 */

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
//        Web3j web3j = null;
//        try {
//            if (args.length != 2) {
//                throw new RuntimeException("invalid args count. please input like '[method-name] [configIniPath]' ");
//            }
//            String method = args[0];
//            String configIniPath = args[1];
//            NodeInfo nodeInfo = NodeInfo.convertFromIniFile(configIniPath);
//            TLSConnInfo rpcConnInfo = TLSConnInfo.convertFromIniFile(configIniPath);
//            web3j = NodeAction.getWeb3j(rpcConnInfo);
//            if (!web3j.isActive()) {
//                logger.error("web3j with [{}] not available.", rpcConnInfo.getIpPort());
//                return;
//            }
//            if ("registerNode".equals(method)) {
//                NodeAction.registerNode(nodeInfo, web3j);
//            } else if ("cancelNode".equals(method)) {
//                NodeAction.cancelNode(nodeInfo, web3j);
//            } else {
//                throw new RuntimeException("invalid method name . please input like 'registerNode' or  'cancelNode' ");
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("thanos-web3j manage node failed. e:", e);
//        } finally {
//            if (web3j != null) {
//                web3j.close();
//            }
//        }
    }
}
