//package test.helloword;
//
//import com.thanos.web3j.channel.client.Service;
//import com.thanos.web3j.channel.handler.ChannelConnections;
//import com.thanos.web3j.protocol.Web3j;
//import com.thanos.web3j.protocol.channel.ChannelEthereumService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 类 HelloWorldTest 的实现描述: netease-chain2.0 链接测试
// * Created by blockchain.dev on 2020/08/18
// */
//public class HelloWorldTest {
//
//    static Logger logger = LoggerFactory.getLogger(HelloWorldTest.class);
//
//    private static String privateKey = "c2e26b7249199115f17c68e939a574bb8026210343a9be18e70b895724b80115";
//
//    private static Web3j web3;
//
//    public static void main(String[] args) {
//        // 1、初始化链接
//        initClient();
//        // 2、调用helloword合约
//        testHelloWorld();
//    }
//
//    /**
//     * 初始化链接
//     */
//    private static void initClient() {
//        //1、设置链接
//        ChannelConnections channelConnection = new ChannelConnections();
//        channelConnection.setCaCertPath("classpath:ca.crt");
//        channelConnection.setClientKeystorePath("classpath:client.keystore");
//        channelConnection.setKeystorePassWord("123456");
//
//        //链接地址
//        List<String> nodeArray = new ArrayList<>();
//        //节点0
//        nodeArray.add("node0@10.240.129.100:8821");
//        //节点1
//        nodeArray.add("node1@10.240.129.100:8822");
//        channelConnection.setConnectionsStr(nodeArray);
//
//        ConcurrentHashMap<String, ChannelConnections> allConnections = new ConcurrentHashMap<String, ChannelConnections>();
//        allConnections.put("netease", channelConnection);
//
//        Service service = new Service();
//        service.setOrgID("netease");
//        service.setConnectSeconds(100);
//        service.setConnectSleepPerMillis(10);
//        service.setAllChannelConnections(allConnections);
//
//        //初始化Service
//        try {
//            service.run();
//        } catch (Exception e) {
//            logger.error("ERROR while start", e);
//            System.exit(1);
//        }
//
//        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
//        channelEthereumService.setChannelService(service);
//
//        // init web3j
//        web3 = Web3j.build(channelEthereumService);
//
//        logger.info("web3 started ...");
//
//    }
//
//    /**
//     * 部署测试合约
//     */
//    private static void testHelloWorld() {
//        //部署合约
//        HelloWorldService helloWorldService = new HelloWorldService();
//        String address = helloWorldService.deployContract(privateKey, web3);
//        logger.info("HelloWorldService address:{}", address);
//
//        String msg = "hello";
//        boolean flag = helloWorldService.setHello(privateKey, web3, address, msg);
//        logger.info("setHello flag:{}", flag);
//
//        String result = helloWorldService.getHello(privateKey, web3, address);
//        logger.info("getHello, result:{}", result);
//    }
//}
