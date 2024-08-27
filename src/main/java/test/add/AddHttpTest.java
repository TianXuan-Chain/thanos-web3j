package test.add;

import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.common.utils.ByteUtil;
import com.thanos.web3j.abi.datatypes.Address;
import com.thanos.web3j.abi.datatypes.DynamicArray;
import com.thanos.web3j.abi.datatypes.Utf8String;
import com.thanos.web3j.abi.datatypes.generated.Bytes32;
import com.thanos.web3j.abi.datatypes.generated.Uint256;
import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.response.EthGetNumber;
import com.thanos.web3j.protocol.manage.Web3Manager;
import com.thanos.web3j.utils.ConfigResourceUtil;
import com.thanos.web3j.utils.Numeric;
import com.thanos.web3j.utils.SystemConstant;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import test.ftbtoken.contractcode.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * 类NotarizationMailTest.java的实现描述：
 *
 * @author xuhao create on 2020/12/10 11:41
 */

public class AddHttpTest {
    private static final Logger logger = LoggerFactory.getLogger("test");
    private static Long DECIMALS_VALUE = 100000000L;

    private static Web3Manager web3Manager;
//    private static List<Web3j> web3jList = new ArrayList<Web3j>();

    //操作账户
    private static Credentials systemCred;
    private static Credentials distributorCred;
    private static Credentials user1Cred;
    private static Credentials user2Cred;

    private static SystemConfig systemConfig;
    //controller
    private String controllerAddress;
    //proxy
    private String c2cAddress;
    private String b2cAddress;
    //handler
    private String handlerAddress;
    //token
    private String tokenAddress;
    //award
    private String mineAddress;
    private String freezeAddress;

    static {
        systemConfig = ConfigResourceUtil.loadSystemConfig();
        ConfigResourceUtil.loadLogConfig(systemConfig.logConfigPath());
        web3Manager = new Web3Manager(systemConfig);

        try {
//            SecureKey secureKey = SecureKey.getInstance();
            SecureKey _extractor = SecureKey.fromPrivate(Hex.decode("010001689223ff8509cc684b161cdda03cfe8705f031ed198183f8d51ae250c620e31c"));
            systemCred = Credentials.create(_extractor);

//            List<String> nodeListStr = Arrays.asList(
//                    "http://127.0.0.1:8580/rpc",
//                    "http://127.0.0.1:8581/rpc");
//            Web3j web3j = null;
//            for (String str : nodeListStr) {
//                web3j = Web3j.build(new HttpService(str));
//                web3jList.add(web3j);
//            }
        } catch (Exception e) {
            logger.error("NotarizationMailTest create systemCred failed.", e);
        }
    }

//    Web3j getHttpWeb3jRandomly() {
//        int k = new Random().nextInt(web3jList.size());
//        return web3jList.get(k);
//    }

    public static void main(String[] args) {
//        AddHttpTest addTest = new AddHttpTest();
        //1. 部署合约
//        addTest.deployContract();
//        addTest.setAddress("ded72dd7c3966c79998d87df69eb7b39a432cef7");
//        addTest.add(1, 2);
//        ThanosTransactionReceipt receipt = new ThanosTransactionReceipt();
//        receipt.rlpDecoded(Hex.decode("f9011dc0f8f5b84401000104a5118d2047d529340ed11d90d22c028215631f45536f9df52e8bd494034104ca2dad6d969dee35944e9ef3791941298bbb0bb9b2614c5d25a13c2a2ec21b9cda8227663d008411e1a30094ded72dd7c3966c79998d87df69eb7b39a432cef700b844eb8ac92100000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002c0b8463044022010ff62916cc8e6bded227a546bd2485e44908687a640bdfde67bb2538967c8c8022015aa44b2560b988ac316eb1bc5e619a6578b524229313c2c0e86bcf31c18facd82012ba0000000000000000000000000000000000000000000000000000000000000000380"));
//        System.out.println("receipt:" + receipt);


//        for (int i = 0; i < 5; i++) {
//            Web3j web3 = web3Manager.getHttpWeb3jRandomly();
//            try {
//                EthGetNumber number = web3.thanosGetLatestBeExecutedNum().send();
//                System.out.println("i=" + i + " number=" + number.getNumber());
//
//
//                Thread.sleep(2000);
//                number = web3.thanosGetLatestBeExecutedNum().send();
//                number = web3.thanosGetLatestBeExecutedNum().send();
//                //System.out.println("i=" + i + " number=" + number.getNumber());
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                web3.close();
//            }
//        }
//        try {
//            Thread.sleep(600 * 1000);
//        } catch (InterruptedException e) {
//        }
    }

    public void setAddress(String address) {
        controllerAddress = address;
    }

    private void deployContract() {
//            Web3j web3 = web3Manager.getWeb3jRandomly();
        Web3j web3 = web3Manager.getHttpWeb3jRandomly();
        try {
            logger.info("AddHttpTest begin deployContract...");
            Add contract = Add.deploy(web3, systemCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO).get();
            this.controllerAddress = contract.getContractAddress();
            logger.info("AddHttpTest contract address:{}", controllerAddress);
        } catch (Exception e) {
            logger.error("AddHttpTest deployContract error.", e);
            throw new RuntimeException(e);
        } finally {
            web3.close();
        }
    }

    private void add(int a, int b) {
        Web3j web3 = web3Manager.getHttpWeb3jRandomly();
        try {
            Add proxy = Add.load(controllerAddress, web3, systemCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT);

            ThanosTransactionReceipt receipt = proxy.test(new Uint256(a), new Uint256(b)).get();
            int result = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            logger.info("FTBTokenTest add result:{}.", result);
        } catch (Exception e) {
            logger.error("mineToken error.", e);
        } finally {
            web3.close();
        }
    }

    private void userMine() {
        Web3j web3 = web3Manager.getHttpWeb3jRandomly();
        try {
            OperatingTokenProxy proxy = OperatingTokenProxy.load(b2cAddress, web3, distributorCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT);
            //user1挖矿 1 coin
            BigDecimal tokens = BigDecimal.ONE;
            Uint256 count = new Uint256(tokens.multiply(BigDecimal.valueOf(DECIMALS_VALUE)).longValue());
            ThanosTransactionReceipt receipt = proxy.transfer(new Address(user1Cred.getAddress()), count, new Utf8String(getRandomString(10)), new Utf8String("20201216")).get();
            List<OperatingTokenProxy.SuccessEventEventResponse> responses = OperatingTokenProxy.getSuccessEventEvents(receipt);
            boolean transferRes = CollectionUtils.isNotEmpty(responses) ? true : false;
            logger.info("user1 mine 1 coin, res:{}", transferRes);
        } catch (Exception e) {
            logger.error("userMine error.", e);
        } finally {
            web3.close();
        }
    }

    private void batchMine() {
        Web3j web3 = web3Manager.getHttpWeb3jRandomly();
        try {
            OperatingTokenProxy proxy = OperatingTokenProxy.load(b2cAddress, web3, distributorCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT);
            Uint256 oneToken = new Uint256(BigDecimal.ONE.multiply(BigDecimal.valueOf(DECIMALS_VALUE)).longValue());
            Uint256 threeTokens = new Uint256(new BigDecimal(3).multiply(BigDecimal.valueOf(DECIMALS_VALUE)).longValue());
            //user1挖矿2笔1coin，user2挖矿1笔3coin
            List<Address> toList = new ArrayList<>();
            List<Uint256> tnList = new ArrayList<>();
            List<Bytes32> traceIdList = new ArrayList<>();
            List<Bytes32> realtimeList = new ArrayList<>();
            //user1 1coin
            toList.add(new Address(user1Cred.getAddress()));
            tnList.add(oneToken);
            traceIdList.add(stringToBytes32(getRandomString(10)));
            realtimeList.add(stringToBytes32("20201216"));
            //user1 1coin
            toList.add(new Address(user1Cred.getAddress()));
            tnList.add(oneToken);
            traceIdList.add(stringToBytes32(getRandomString(10)));
            realtimeList.add(stringToBytes32("20201216"));
            //user2 3coin
            toList.add(new Address(user2Cred.getAddress()));
            tnList.add(threeTokens);
            traceIdList.add(stringToBytes32(getRandomString(10)));
            realtimeList.add(stringToBytes32("20201216"));

            ThanosTransactionReceipt receipt = proxy.batchTransfer(new DynamicArray<Address>(toList), new DynamicArray<Uint256>(tnList), new DynamicArray<Bytes32>(traceIdList), new DynamicArray<Bytes32>(realtimeList)).get();
            List<OperatingTokenProxy.SuccessEventEventResponse> responses = OperatingTokenProxy.getSuccessEventEvents(receipt);
            boolean transferRes = CollectionUtils.isNotEmpty(responses) ? true : false;
            logger.info("user1 batchMine, res:{}", transferRes);
        } catch (Exception e) {
            logger.error("batchMine error.", e);
        } finally {
            web3.close();
        }
    }

    private void batchTransfer() {
        Web3j web3 = web3Manager.getHttpWeb3jRandomly();
        try {
            //转账前 user1:6coin user2:3coin
            OperatingTokenProxy proxy = OperatingTokenProxy.load(b2cAddress, web3, user1Cred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT);
            Uint256 oneToken = new Uint256(BigDecimal.ONE.multiply(BigDecimal.valueOf(DECIMALS_VALUE)).longValue());
            Uint256 twoTokens = new Uint256(new BigDecimal(2).multiply(BigDecimal.valueOf(DECIMALS_VALUE)).longValue());
            //user1转账2笔2coin
            List<Address> toList = new ArrayList<>();
            List<Uint256> tnList = new ArrayList<>();
            List<Bytes32> traceIdList = new ArrayList<>();
            List<Bytes32> realtimeList = new ArrayList<>();
            //user1 2coin
            toList.add(new Address(user2Cred.getAddress()));
            tnList.add(twoTokens);
            traceIdList.add(stringToBytes32(getRandomString(10)));
            realtimeList.add(stringToBytes32("20201216"));
            //user1 2coin
            toList.add(new Address(user2Cred.getAddress()));
            tnList.add(twoTokens);
            traceIdList.add(stringToBytes32(getRandomString(10)));
            realtimeList.add(stringToBytes32("20201216"));


            ThanosTransactionReceipt receipt = proxy.batchTransfer(new DynamicArray<Address>(toList), new DynamicArray<Uint256>(tnList), new DynamicArray<Bytes32>(traceIdList), new DynamicArray<Bytes32>(realtimeList)).get();
            List<OperatingTokenProxy.SuccessEventEventResponse> responses = OperatingTokenProxy.getSuccessEventEvents(receipt);
            boolean transferRes = CollectionUtils.isNotEmpty(responses) ? true : false;
            logger.info("user1 batchTransfer, res:{}", transferRes);
        } catch (Exception e) {
            logger.error("batchTransfer error.", e);
        } finally {
            web3.close();
        }
    }

    private BigDecimal balanceOf(String account) {
        Web3j web3 = web3Manager.getHttpWeb3jRandomly();
        try {
            OperatingTokenProxy proxy = OperatingTokenProxy.load(b2cAddress, web3, systemCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT);
            return BigDecimal.valueOf(proxy.balanceOf(new Address(account)).get().getValue().longValue()).divide(BigDecimal.valueOf(DECIMALS_VALUE));
        } catch (Exception e) {
            logger.error("FTBTokenTest.balanceOf exception. account: {}", account, e);
        } finally {
            web3.close();
        }
        return null;
    }


    //length用户要求产生字符串的长度
    private static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static Bytes32 stringToBytes32(String string) {
        byte[] byteValue = string.getBytes();
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return new Bytes32(byteValueLen32);
    }
}
