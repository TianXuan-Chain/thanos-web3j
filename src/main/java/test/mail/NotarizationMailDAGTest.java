package test.mail;

import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.abi.datatypes.Address;
import com.thanos.web3j.abi.datatypes.Type;
import com.thanos.web3j.abi.datatypes.Utf8String;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.request.RawTransaction;
import com.thanos.web3j.protocol.core.methods.response.EthSendTransactionList;
import com.thanos.web3j.tx.RawTransactionManager;
import com.thanos.web3j.utils.SystemConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import test.TestBase;
import test.mail.contractCode.*;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Future;

/**
 * 类NotarizationMailTest.java的实现描述：
 *
 * @author xuhao create on 2020/12/10 11:41
 */

public class NotarizationMailDAGTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger("test");

    private static Credentials credentials;

    private String controllerAddress;
    private String mailStorageAddress;
    private String mailHandlerAddress;
    private String notarizationMailProxyAddress;

    static {

        try {
            SecureKey sender = SecureKey.fromPrivate(Hex.decode("0100013ec771c31cac8c0dba77a69e503765701d3c2bb62435888d4ffa38fed60c445c"));
            credentials = Credentials.create(sender);
        } catch (Exception e) {
            logger.error("NotarizationMailDAGTest create credentials failed.", e);
        }
    }

    public static void main(String[] args) {
        try {
            NotarizationMailDAGTest mailTest = new NotarizationMailDAGTest();
            //1. 部署合约
            mailTest.deployContract();
//            mailTest.upgradeContract();
            int batchNumber = 1;
            int mailCount = 100;
            while (true) {
                logger.info("--------NotarizationMail test with {} mail start. batchNumber={}.------------", mailCount, batchNumber);
                //生成邮件
                List<NotarizationMailUpChainBo> mailList = mockMailList(mailCount);

                //2. 存储邮件
                mailTest.storeMail(mailList);
                //3. 查询邮件
                for (NotarizationMailUpChainBo mailBo : mailList) {
                    NotarizationMailUpChainBo queryMail = mailTest.queryMail(mailBo.getEmailNumber());
                    if (!mailBo.equals(queryMail)) {
                        logger.error("queryMail isn't equal to mailBo. mailBo:{}. queryMail:{}.", mailBo, queryMail);
                    }
                    assert mailBo.equals(queryMail);
                }
                logger.info("NotarizationMail test with {} mail success. batchNumber={}.", mailCount, batchNumber);

                batchNumber++;
            }
        } catch (Exception e) {
            logger.error("NotarizationMailDAGTest error.", e);
            throw new RuntimeException(e);
        }
    }

    private void deployContract() {
        try {
            Web3j web3 = getWeb3jRandomly();
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
            Web3j web3 = getWeb3jRandomly();
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

    private void storeMail(List<NotarizationMailUpChainBo> mailList) {
        if (mailList == null) {
            return;
        }
        try {
            List<RawTransaction> rawTnxList = new ArrayList<>();

            for (NotarizationMailUpChainBo mail : mailList) {
//                Random r = new SecureRandom();
//                BigInteger randomid = new BigInteger(250, r);
                UUID uuid = UUID.randomUUID();
                BigInteger randomid = new BigInteger(uuid.toString().replaceAll("-", ""), 16);
                String extendInfo = mail.getEmailLength() + "," + mail.getUserNumber();
                String storeData = NotarizationMailProxy.storeData(new Utf8String(mail.getEmailNumber()), new Utf8String(mail.getEmailFingerprint()), new Utf8String(mail.getEmailHash()), new Utf8String(extendInfo));
                Set<String> exeSta = new HashSet<>();
                exeSta.add(mail.getEmailNumber());

                Long consensusNumber = 100l;//transactionManager.getThanosLatestConsensusNumber();
                RawTransaction rawTransaction = RawTransaction.createTransaction(
                        randomid,
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(3000000),
                        notarizationMailProxyAddress,
                        storeData,
                        exeSta, consensusNumber + 5);
                rawTransaction.setPublicKey(credentials.getSecureKey().getPubKey());
                rawTransaction.setCredentials(credentials);
                rawTnxList.add(rawTransaction);
            }
            Web3j web3 = getWeb3jRandomly();
            RawTransactionManager transactionManager = new RawTransactionManager(web3, credentials);
            EthSendTransactionList sendTransactionList = transactionManager.SendTransactionList(rawTnxList);
            if (sendTransactionList == null || sendTransactionList.hasError()) {
                String error = "storeMail send transaction with error:" + (sendTransactionList == null ? "" : sendTransactionList.getError().toString());
                throw new RuntimeException(error);
            }
            List<ThanosTransactionReceipt> receiptList = waitForTnxReceiptList(transactionManager, sendTransactionList.getTransactionHash());
            for (ThanosTransactionReceipt receipt : receiptList) {
                if (StringUtils.isNotBlank(receipt.getError())) {
                    throw new RuntimeException("storeMail tnxReceipt has error:" + receipt.getError());
                }
            }
        } catch (Exception e) {
            logger.error("NotarizationMailDAGTest storeMail error.", e);
            throw new RuntimeException(e);
        }
    }

    private NotarizationMailUpChainBo queryMail(String mailNumber) {
        Web3j web3 = getWeb3jRandomly();
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
//                logger.debug("queryMail, emailNumber:{}, ", emailNumber2);

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

    private static List<NotarizationMailUpChainBo> mockMailList(int count) {
        List<NotarizationMailUpChainBo> mailUpChainBoList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            NotarizationMailUpChainBo mailUpChainBo = mockMail();
            mailUpChainBoList.add(mailUpChainBo);
        }
        return mailUpChainBoList;
    }


}
