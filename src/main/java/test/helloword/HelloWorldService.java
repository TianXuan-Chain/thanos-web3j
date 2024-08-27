//package test.helloword;
//
//import com.thanos.web3j.abi.datatypes.Utf8String;
//import com.thanos.web3j.crypto.Credentials;
//import com.thanos.web3j.crypto.GenCredential;
//import com.thanos.web3j.model.ThanosTransactionReceipt;
//import com.thanos.web3j.protocol.Web3j;
//import com.thanos.web3j.utils.SystemConstant;
//import org.apache.commons.collections.CollectionUtils;
//
//import java.math.BigInteger;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//
///**
// * 类 HelloWorldService 的实现描述: helloworld服务
// * Created by blockchain.dev on 2020/08/19
// */
//public class HelloWorldService {
//
//    /**
//     * 部署HelloWorld合约
//     *
//     * @param privateKey
//     * @param web3
//     * @return
//     */
//    public String deployContract(String privateKey, Web3j web3) {
//        Credentials sysCredentials = GenCredential.create(privateKey);
//        try {
//            HelloWorld helloWorld = HelloWorld.deploy(web3, sysCredentials, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO).get();
//            return helloWorld.getContractAddress();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 设置hello值
//     *
//     * @param privateKey
//     * @param web3
//     * @param address
//     * @param msg
//     * @return
//     */
//    public boolean setHello(String privateKey, Web3j web3, String address, String msg) {
//        Credentials sysCredentials = GenCredential.create(privateKey);
//
//        try {
//            HelloWorld helloWorld = HelloWorld.load(address, web3, sysCredentials, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT);
//            ThanosTransactionReceipt transactionReceipt = helloWorld.set(new Utf8String(msg)).get();
//            List<HelloWorld.SuccessEventEventResponse> responses = null;//helloWorld.getSuccessEventEvents(transactionReceipt);
//
//            return CollectionUtils.isNotEmpty(responses) ? true : false;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * 获取值
//     *
//     * @param privateKey
//     * @param web3
//     * @param address
//     */
//    public String getHello(String privateKey, Web3j web3, String address) {
//
//        Credentials sysCredentials = GenCredential.create(privateKey);
//
//        try {
//            HelloWorld helloWorld = HelloWorld.load(address, web3, sysCredentials, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT);
//            Utf8String str = helloWorld.get().get();
//
//            return str.getValue();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
