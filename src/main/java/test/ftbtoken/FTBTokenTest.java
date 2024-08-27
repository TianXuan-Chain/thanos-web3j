package test.ftbtoken;

import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.abi.datatypes.Address;
import com.thanos.web3j.abi.datatypes.DynamicArray;
import com.thanos.web3j.abi.datatypes.Utf8String;
import com.thanos.web3j.abi.datatypes.generated.Bytes32;
import com.thanos.web3j.abi.datatypes.generated.Uint256;
import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.manage.Web3Manager;
import com.thanos.web3j.utils.ConfigResourceUtil;
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

public class FTBTokenTest {
    private static final Logger logger = LoggerFactory.getLogger("test");
    private static Long DECIMALS_VALUE = 100000000L;

    private static Web3Manager web3Manager;
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
//            systemCred = Credentials.create(secureKey);
            SecureKey _extractor = SecureKey.fromPrivate(Hex.decode("010001308f761b30da0baa33457550420bb8938d040a0c6f0582d9351fd5cead86ff11"));
            systemCred = Credentials.create(_extractor);
            SecureKey _distributor = SecureKey.fromPrivate(Hex.decode("010001c833689dd53a006191ec84a1764f0359c80f5f714c88ac9b235de8eab3fc1133"));
            distributorCred = Credentials.create(_distributor);
            SecureKey user1 = SecureKey.fromPrivate(Hex.decode("0100015acf4da74bcd28e7a99460e997a5410e926939ed639f5088c00d0151963459a5"));
            user1Cred = Credentials.create(user1);
            SecureKey user2 = SecureKey.fromPrivate(Hex.decode("0100017e91470c2049b31ce5675acd714c3545424a390e96c39a4e2c4771fc223f17c6"));
            user2Cred = Credentials.create(user2);
        } catch (Exception e) {
            logger.error("NotarizationMailTest create systemCred failed.", e);
        }
    }

    public static void main(String[] args) {
        FTBTokenTest mailTest = new FTBTokenTest();
        //1. 部署合约
        mailTest.deployContract();
        //2. 发矿50000
        mailTest.mineToken("20201215");
        //3. 用户挖矿（矿池账号给用户发币）
        BigDecimal distributorBalance;
        BigDecimal user1Balance;
        BigDecimal user2Balance;

        mailTest.userMine();
        user1Balance = mailTest.balanceOf(user1Cred.getAddress());
        distributorBalance = mailTest.balanceOf(distributorCred.getAddress());
        logger.info("user1 balance:{}", user1Balance == null ? "" : user1Balance);
        logger.info("distributor balance:{}", distributorBalance == null ? "" : distributorBalance);
        mailTest.userMine();
        mailTest.userMine();
        user1Balance = mailTest.balanceOf(user1Cred.getAddress());
        distributorBalance = mailTest.balanceOf(distributorCred.getAddress());
        logger.info("user1 balance:{}", user1Balance == null ? "" : user1Balance);
        logger.info("distributor balance:{}", distributorBalance == null ? "" : distributorBalance);

        mailTest.mineToken("20201216");
        mailTest.userMine();
        user1Balance = mailTest.balanceOf(user1Cred.getAddress());
        distributorBalance = mailTest.balanceOf(distributorCred.getAddress());
        logger.info("user1 balance:{}", user1Balance == null ? "" : user1Balance);
        logger.info("distributor balance:{}", distributorBalance == null ? "" : distributorBalance);
        //4.批量挖矿
        mailTest.batchMine();
        user1Balance = mailTest.balanceOf(user1Cred.getAddress());
        user2Balance = mailTest.balanceOf(user2Cred.getAddress());
        distributorBalance = mailTest.balanceOf(distributorCred.getAddress());
        logger.info("user1 balance:{}", user1Balance == null ? "" : user1Balance);
        logger.info("user2 balance:{}", user2Balance == null ? "" : user2Balance);
        logger.info("distributor balance:{}", distributorBalance == null ? "" : distributorBalance);
        //4. 用户之间转账
        mailTest.batchTransfer();
        user1Balance = mailTest.balanceOf(user1Cred.getAddress());
        user2Balance = mailTest.balanceOf(user2Cred.getAddress());
        logger.info("user1 balance:{}", user1Balance == null ? "" : user1Balance);
        logger.info("user2 balance:{}", user2Balance == null ? "" : user2Balance);
        //二次转账，此时user1因余额不够而失败。
        mailTest.batchTransfer();
        user1Balance = mailTest.balanceOf(user1Cred.getAddress());
        user2Balance = mailTest.balanceOf(user2Cred.getAddress());
        logger.info("user1 balance:{}", user1Balance == null ? "" : user1Balance);
        logger.info("user2 balance:{}", user2Balance == null ? "" : user2Balance);

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        web3Manager.shutdown();
//        logger.info("shutdown.");
    }

    private void deployContract() {
        try {
            Web3j web3 = web3Manager.getWeb3jRandomly();
            logger.info("FTBTokenTest begin deployContract...");


            // Controller
            FTBControllerFactory nbdControllerFactory = FTBControllerFactory.deploy(web3, systemCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO).get();
            ThanosTransactionReceipt controllerReceipt = nbdControllerFactory.createController().get();
            List<FTBControllerFactory.CreateSuccessEventEventResponse> controllerResponse = FTBControllerFactory.getCreateSuccessEventEvents(controllerReceipt);
            this.controllerAddress = CollectionUtils.isEmpty(controllerResponse) ? null : controllerResponse.get(0).addr.toString();
            logger.info("FTBTokenTest controllerAddress:{}", controllerAddress);

            //proxy
            FTBProxyFactory nbdProxyFactory = FTBProxyFactory.deploy(web3, systemCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO, new Address(controllerAddress)).get();
            ThanosTransactionReceipt c2creceipt = nbdProxyFactory.createC2CProxy().get();
            List<FTBProxyFactory.CreateSuccessEventEventResponse> c2cresponses = FTBProxyFactory.getCreateSuccessEventEvents(c2creceipt);
            this.c2cAddress = CollectionUtils.isEmpty(c2cresponses) ? null : c2cresponses.get(0).addr.toString();
            logger.info("FTBTokenTest c2cAddress:{}", c2cAddress);

            ThanosTransactionReceipt b2creceipt = nbdProxyFactory.createB2CProxy().get();
            List<FTBProxyFactory.CreateSuccessEventEventResponse> b2cresponses = FTBProxyFactory.getCreateSuccessEventEvents(b2creceipt);
            this.b2cAddress = CollectionUtils.isEmpty(b2cresponses) ? null : b2cresponses.get(0).addr.toString();
            logger.info("FTBTokenTest b2cAddress:{}", b2cAddress);


            //handler
            FTBHandlerFactory nbdHandlerFactory = FTBHandlerFactory.deploy(web3, systemCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO, new Address(controllerAddress)).get();
            ThanosTransactionReceipt handlerreceipt = nbdHandlerFactory.createHandler().get();
            List<FTBHandlerFactory.CreateSuccessEventEventResponse> handlerresponses = FTBHandlerFactory.getCreateSuccessEventEvents(handlerreceipt);
            this.handlerAddress = CollectionUtils.isEmpty(handlerresponses) ? null : handlerresponses.get(0).addr.toString();
            logger.info("FTBTokenTest handlerAddress:{}", handlerAddress);


            //token
            FTBTokenFactory nbdTokenFactory = FTBTokenFactory.deploy(web3, systemCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO, new Address(controllerAddress)).get();
            ThanosTransactionReceipt tokenreceipt = nbdTokenFactory.createToken().get();
            List<FTBTokenFactory.CreateSuccessEventEventResponse> tokenresponses = FTBTokenFactory.getCreateSuccessEventEvents(tokenreceipt);
            this.tokenAddress = CollectionUtils.isEmpty(tokenresponses) ? null : tokenresponses.get(0).addr.toString();
            logger.info("FTBTokenTest tokenAddress:{}", tokenAddress);

            //award
            FTBAwardPoolFactory nbdAwardPoolFactory = FTBAwardPoolFactory.deploy(web3, systemCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO, new Address(controllerAddress)).get();

            ThanosTransactionReceipt minereceipt = nbdAwardPoolFactory.createMine().get();
            List<FTBAwardPoolFactory.CreateSuccessEventEventResponse> mineresponses = FTBAwardPoolFactory.getCreateSuccessEventEvents(minereceipt);
            this.mineAddress = CollectionUtils.isEmpty(mineresponses) ? null : mineresponses.get(0).addr.toString();
            logger.info("FTBTokenTest mineAddress:{}", mineAddress);

            ThanosTransactionReceipt freezereceipt = nbdAwardPoolFactory.createFreeze().get();
            List<FTBAwardPoolFactory.CreateSuccessEventEventResponse> freezeresponses = FTBAwardPoolFactory.getCreateSuccessEventEvents(freezereceipt);
            this.freezeAddress = CollectionUtils.isEmpty(freezeresponses) ? null : freezeresponses.get(0).addr.toString();
            logger.info("FTBTokenTest freezeAddress:{}", freezeAddress);

            logger.info("FTBTokenTest deployContract finished.");
        } catch (Exception e) {
            logger.error("FTBTokenTest deployContract error.", e);
            throw new RuntimeException(e);
        }
    }

    private void mineToken(String mineDate) {

        try {
            Web3j web3 = web3Manager.getWeb3jRandomly();
            OperatingTokenProxy proxy = OperatingTokenProxy.load(b2cAddress, web3, systemCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT);

            ThanosTransactionReceipt receipt = proxy.minerTokens(new Uint256(Long.valueOf(mineDate))).get();
            List<OperatingTokenProxy.MineSuccessEventEventResponse> responses = OperatingTokenProxy.getMineSuccessEventEvents(receipt);
            Boolean success = CollectionUtils.isNotEmpty(responses);
            BigDecimal amount = CollectionUtils.isEmpty(responses) ? BigDecimal.valueOf(0) : BigDecimal.valueOf(responses.get(0).amount.getValue().longValue()).divide(BigDecimal.valueOf(100000000L));
            logger.info("FTBTokenTest mineToken ifSuccess:{}, amount:{}.", success, amount);
        } catch (Exception e) {
            logger.error("mineToken error.", e);
        }
    }

    private void userMine() {
        try {
            Web3j web3 = web3Manager.getWeb3jRandomly();
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
        }
    }

    private void batchMine() {
        try {
            Web3j web3 = web3Manager.getWeb3jRandomly();
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
        }
    }

    private void batchTransfer() {
        try {
            //转账前 user1:6coin user2:3coin
            Web3j web3 = web3Manager.getWeb3jRandomly();
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
        }
    }

    private BigDecimal balanceOf(String account) {
        Web3j web3 = web3Manager.getWeb3jRandomly();
        try {
            OperatingTokenProxy proxy = OperatingTokenProxy.load(b2cAddress, web3, systemCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT);
            return BigDecimal.valueOf(proxy.balanceOf(new Address(account)).get().getValue().longValue()).divide(BigDecimal.valueOf(DECIMALS_VALUE));
        } catch (Exception e) {
            logger.error("FTBTokenTest.balanceOf exception. account: {}", account, e);
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
