package test.mail;

import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.abi.datatypes.Address;
import com.thanos.web3j.abi.datatypes.Bool;
import com.thanos.web3j.abi.datatypes.Type;
import com.thanos.web3j.abi.datatypes.Utf8String;
import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.manage.Web3Manager;
import com.thanos.web3j.utils.ConfigResourceUtil;
import com.thanos.web3j.utils.SystemConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import test.mail.contractCode.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 类NotarizationMailTest.java的实现描述：
 *
 * @author xuhao create on 2020/12/10 11:41
 */

public class NotarizationMailTest {
    private static final Logger logger = LoggerFactory.getLogger("test");

    private static Web3Manager web3Manager;
    private static Credentials credentials;
    private static SystemConfig systemConfig;

    private String controllerAddress;
    private String mailStorageAddress;
    private String mailHandlerAddress;
    private String notarizationMailProxyAddress;

    static {
        systemConfig = ConfigResourceUtil.loadSystemConfig();
        ConfigResourceUtil.loadLogConfig(systemConfig.logConfigPath());
        web3Manager=new Web3Manager(systemConfig);
        try {
//            SecureKey secureKey = SecureKey.getInstance();
//            credentials = Credentials.create(secureKey);
            SecureKey sender = SecureKey.fromPrivate(Hex.decode("0100013ec771c31cac8c0dba77a69e503765701d3c2bb62435888d4ffa38fed60c445c"));
            credentials = Credentials.create(sender);
        } catch (Exception e) {
            logger.error("NotarizationMailTest create credentials failed.", e);
        }
    }

    public static void main(String[] args) {
        NotarizationMailTest mailTest = new NotarizationMailTest();
        //1. 部署合约
        mailTest.deployContract();
        mailTest.upgradeContract();
        int count = 10;
        for (int i = 0; i < count; i++) {
            NotarizationMailUpChainBo mailBo = mockMail();
            //2. 存储邮件
            boolean res = mailTest.storeMail(mailBo);
            assert res;
            //3. 查询邮件
            NotarizationMailUpChainBo queryMail = mailTest.queryMail(mailBo.getEmailNumber());
            if (mailBo.equals(queryMail)) {
                logger.info("test[{}] queryMail is equal to mailBo", i);
            } else {
                logger.error("test[{}] queryMail isn't equal to mailBo. mailBo:{}. queryMail:{}.", i, mailBo, queryMail);
            }
            assert mailBo.equals(queryMail);
        }

    }

    private void deployContract() {
        try {
            Web3j web3 = web3Manager.getWeb3jRandomly();
            logger.info("NotarizationMailTest begin deployContract...");


            // Controller
            Controller nbdControllerFactory = Controller.deploy(web3, credentials, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO).get();
            this.controllerAddress = nbdControllerFactory.getContractAddress();
            logger.info("NotarizationMailTest controllerAddress:{}", controllerAddress);

            //NotarizationMailStorageFactory
            NotarizationMailStorageFactory notarizationMailStorageFactory = NotarizationMailStorageFactory.deploy(web3, credentials, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO, new Address(controllerAddress)).get();
            logger.info("NotarizationMailStorageFactory notarizationMailStorageFactory:{}", notarizationMailStorageFactory.getContractAddress());
            ThanosTransactionReceipt receipt = notarizationMailStorageFactory.createMailStorage().get();

            List<NotarizationMailStorageFactory.CreateSuccessEventEventResponse> responses = NotarizationMailStorageFactory.getCreateSuccessEventEvents(receipt);
            this.mailStorageAddress = CollectionUtils.isEmpty(responses) ? null : responses.get(0).addr.toString();
            logger.info("NotarizationMailTest mailStorageAddress:{}", mailStorageAddress);


            //NotarizationMailHandlerFactory
            NotarizationMailHandlerFactory notarizationMailHandlerFactory = NotarizationMailHandlerFactory.deploy(web3, credentials, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO, new Address(controllerAddress)).get();
            ThanosTransactionReceipt receipt2 = notarizationMailHandlerFactory.createHandler().get();

            List<NotarizationMailHandlerFactory.CreateSuccessEventEventResponse> responses2 = NotarizationMailHandlerFactory.getCreateSuccessEventEvents(receipt2);
            this.mailHandlerAddress = CollectionUtils.isEmpty(responses2) ? null : responses2.get(0).addr.toString();
            logger.info("NotarizationMailTest mailHandlerAddress:{}", mailHandlerAddress);


            //NotarizationMailProxyFactory
            NotarizationMailProxyFactory notarizationMailProxyFactory = NotarizationMailProxyFactory.deploy(web3, credentials, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO, new Address(controllerAddress)).get();
            ThanosTransactionReceipt receipt3 = notarizationMailProxyFactory.createNotarizationMailProxy().get();
            List<NotarizationMailProxyFactory.CreateSuccessEventEventResponse> responses3 = NotarizationMailProxyFactory.getCreateSuccessEventEvents(receipt3);
            this.notarizationMailProxyAddress = CollectionUtils.isEmpty(responses3) ? null : responses3.get(0).addr.toString();
            logger.info("NotarizationMailTest notarizationMailProxyAddress:{}", notarizationMailProxyAddress);

            logger.info("NotarizationMailTest deployContract finished.");
        } catch (Exception e) {
            logger.error("NotarizationMailTest deployContract error.", e);
            throw new RuntimeException(e);
        }
    }

    private void upgradeContract() {
        try {
            Web3j web3 = web3Manager.getWeb3jRandomly();
            logger.info("NotarizationMailTest begin upgradeContract...");



            //NotarizationMailHandlerFactory
            NotarizationMailHandlerFactory notarizationMailHandlerFactory = NotarizationMailHandlerFactory.deploy(web3, credentials, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO, new Address(controllerAddress)).get();
            ThanosTransactionReceipt receipt2 = notarizationMailHandlerFactory.updateHandler().get();

            List<NotarizationMailHandlerFactory.CreateSuccessEventEventResponse> responses2 = NotarizationMailHandlerFactory.getCreateSuccessEventEvents(receipt2);
            this.mailHandlerAddress = CollectionUtils.isEmpty(responses2) ? null : responses2.get(0).addr.toString();
            logger.info("NotarizationMailTest mailHandlerAddress:{}", mailHandlerAddress);


            //NotarizationMailProxyFactory
            NotarizationMailProxyFactory notarizationMailProxyFactory = NotarizationMailProxyFactory.deploy(web3, credentials, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO, new Address(controllerAddress)).get();
            ThanosTransactionReceipt receipt3 = notarizationMailProxyFactory.updateNotarizationMailProxy().get();
            List<NotarizationMailProxyFactory.CreateSuccessEventEventResponse> responses3 = NotarizationMailProxyFactory.getCreateSuccessEventEvents(receipt3);
            this.notarizationMailProxyAddress = CollectionUtils.isEmpty(responses3) ? null : responses3.get(0).addr.toString();
            logger.info("NotarizationMailTest notarizationMailProxyAddress:{}", notarizationMailProxyAddress);

            logger.info("NotarizationMailTest upgradeContract finished.");
        } catch (Exception e) {
            logger.error("NotarizationMailTest upgradeContract error.", e);
            throw new RuntimeException(e);
        }
    }

    private boolean storeMail(NotarizationMailUpChainBo mailBo) {
        Web3j web3 = web3Manager.getWeb3jRandomly();
        NotarizationMailProxy mailProxy = NotarizationMailProxy.load(notarizationMailProxyAddress, web3, credentials, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT);
        try {
            Bool isExist = mailProxy.exist(new Utf8String(mailBo.getEmailNumber())).get();
            if (isExist.getValue()) {
                logger.warn("storeMail failed, mail with emailNumber{} already exist.", mailBo.getEmailNumber());
                return true;
            } else {
                String extendInfo = mailBo.getEmailLength() + "," + mailBo.getUserNumber();

                mailProxy.store(new Utf8String(mailBo.getEmailNumber()), new Utf8String(mailBo.getEmailFingerprint()), new Utf8String(mailBo.getEmailHash()), new Utf8String(extendInfo)).get();
                isExist = mailProxy.exist(new Utf8String(mailBo.getEmailNumber())).get();
                if (isExist.getValue()) {
                    logger.info("storeMail success, mail:{}.", mailBo);
                    return true;
                }

            }
        } catch (Exception e) {
            logger.error("storeMail error.", e);
        }
        return false;
    }

    private NotarizationMailUpChainBo queryMail(String mailNumber) {
        Web3j web3 = web3Manager.getWeb3jRandomly();
        NotarizationMailStorage proxy = NotarizationMailStorage.load(mailStorageAddress, web3, credentials, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT);
        try {
//            List<Type> list = proxy.query(new Utf8String(mailNumber)).get();
            Future<List<Type>> future = proxy.query(new Utf8String(mailNumber));
            List<Type> list = future.get();
            NotarizationMailUpChainBo bo = new NotarizationMailUpChainBo();
            if (!CollectionUtils.isEmpty(list)) {
                String emailNumber2 = ((Utf8String) list.get(0)).getValue();
                if (StringUtils.isBlank(emailNumber2)) {
                    logger.warn("queryMail mail with mailNumber{} not exist.", mailNumber);
                    return null;
                }
                String emailFingerprint = ((Utf8String) list.get(1)).getValue();
                String emailHash = ((Utf8String) list.get(2)).getValue();
                String address = ((Address) list.get(3)).toString();
                String extendInfo = ((Utf8String) list.get(4)).getValue();
                logger.info("queryMail, emailNumber:{}, ", emailNumber2);

                String[] extendInfos = extendInfo.split(",");

                bo.setEmailNumber(emailNumber2);
                bo.setEmailFingerprint(emailFingerprint);
                bo.setEmailHash(emailHash);
                bo.setEmailLength(Long.parseLong(extendInfos[0]));
                bo.setUserNumber(extendInfos[1]);
            }
            return bo;
        } catch (Exception e) {
            logger.error("queryMail error.", e);
        }
        return null;
    }

    private static NotarizationMailUpChainBo mockMail() {
        NotarizationMailUpChainBo bo = new NotarizationMailUpChainBo();
        String randomStr = getRandomString(32);

        bo.setEmailNumber(randomStr);
        bo.setEmailFingerprint("Fingerprint" + randomStr);
        bo.setEmailHash("Hash" + randomStr);
        bo.setEmailLength(64L);
        bo.setUserNumber("user" + randomStr);
        return bo;
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
}
