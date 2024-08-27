package com.thanos.web3j.eventgen;

import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.common.utils.Numeric;
import com.thanos.common.utils.rlp.RLP;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.model.ThanosGlobalNodeEventReceipt;
import com.thanos.web3j.model.event.GlobalEventCommand;
import com.thanos.web3j.model.event.InvokeFilterEvent;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.response.EthSendTransaction;
import com.thanos.web3j.tx.RawTransactionManager;
import com.thanos.web3j.tx.TransactionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 类InvokeFilterManager.java的实现描述：
 *
 * @author xuhao create on 2021/6/17 14:53
 */

public class InvokeFilterManager {
    protected static final Logger logger = LoggerFactory.getLogger(InvokeFilterManager.class);

    public static final byte[] INVOKE_ETH_CONTRACT_AUTH_FILTER_ADDR = Numeric.hexStringToByteArray("0x0000000000000000000000000000000000000001");

    public static class MethodIdConstant {
        //设置是否 激活 【调用合约】 过滤检测功能，默认为否。 setEnableInvoke(boolean flag)
        public static final byte[] SET_ENABLE_INVOKE = Hex.decode("fc1c4e803364cf828b8b7c3581c5143e682e5adee2934b74cd1a3691a83fc784");
        //设置 合约调用【黑白名单】模式.默认为黑名单。setContractInvokeBlack(byte[] addr, boolean flag)。 flag=true：黑名单 flag=false：白名单。
        public static final byte[] SET_CONTRACT_INVOKE_BLACK_MODE = Hex.decode("180d069d019c2064dfcb36f19fe1a61f8db5104b1d5c4a341d6a9c22edbaf661");
        //设置 【调用合约】 黑名单 账户地址，仅在黑名单模式下生效。setInvokeBlack(byte[] contractAddress, byte[] userAddress, int opcode) opcode=0新增，=1删除
        public static final byte[] SET_INVOKE_BLACK = Hex.decode("41786c5997c35f984ef9132e9a040b1eb232d7c8b9b12f26d381d5aff7c520ed");
        //设置 【调用合约】 白名单 账户地址，仅在白名单模式下生效。setInvokeWhite(byte[] contractAddress, byte[] userAddress, int opcode)
        public static final byte[] SET_INVOKE_WHITE = Hex.decode("64f795e1d95af40755a127951c4d5ff9a509ec9f3f1214f84218f209f2e3e8b1");
    }

    //启用【调用合约】的过滤检测功能，这是设置【调用合约】黑/白名单的前提
    public static boolean setEnableInvokeCheck(Web3j web3j, SecureKey sender) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            //设置过滤检测功能 生效
            byte[] methodInput = RLP.encodeList(RLP.encodeInt(1));

            InvokeFilterEvent event = new InvokeFilterEvent(
                    INVOKE_ETH_CONTRACT_AUTH_FILTER_ADDR,
                    MethodIdConstant.SET_ENABLE_INVOKE,
                    methodInput
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.INVOKE_FILTER.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("setEnableInvokeCheck txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("setEnableInvokeCheck receipt:{}", receipt);
                return true;

            }
            return false;
        } catch (Exception e) {
            logger.error("setEnableInvokeCheck failed.", e);
            throw new RuntimeException(e);
        }
    }

    //禁用【调用合约】的过滤检测功能
    public static boolean setDisableInvokeCheck(Web3j web3j, SecureKey sender) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            //设置过滤检测功能 生效
            byte[] methodInput = RLP.encodeList(RLP.encodeInt(0));

            InvokeFilterEvent event = new InvokeFilterEvent(
                    INVOKE_ETH_CONTRACT_AUTH_FILTER_ADDR,
                    MethodIdConstant.SET_ENABLE_INVOKE,
                    methodInput
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.INVOKE_FILTER.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("setDisableInvokeCheck txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("setDisableInvokeCheck receipt:{}", receipt);
                return true;

            }
            return false;
        } catch (Exception e) {
            logger.error("setDisableInvokeCheck failed.", e);
            throw new RuntimeException(e);
        }
    }

    //设置 合约调用【黑名单】模式
    public static boolean setContractInvokeBlackMode(Web3j web3j, SecureKey sender, byte[] contractAddress) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            //设置指定合约为调用黑名单检测模式，默认即为黑名单检测模式
            byte[] methodInput = RLP.encodeList(RLP.encodeElement(contractAddress), RLP.encodeInt(1));

            InvokeFilterEvent event = new InvokeFilterEvent(
                    INVOKE_ETH_CONTRACT_AUTH_FILTER_ADDR,
                    MethodIdConstant.SET_CONTRACT_INVOKE_BLACK_MODE,
                    methodInput
            );

            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.INVOKE_FILTER.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("setContractInvokeBlackMode txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("setContractInvokeBlackMode receipt:{}", receipt);
                return true;

            }
            return false;
        } catch (Exception e) {
            logger.error("setContractInvokeBlackMode failed.", e);
            throw new RuntimeException(e);
        }
    }

    //设置 合约调用【白名单】模式
    public static boolean setContractInvokeWhiteMode(Web3j web3j, SecureKey sender, byte[] contractAddress) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            //设置指定合约为调用白名单检测模式
            byte[] methodInput = RLP.encodeList(RLP.encodeElement(contractAddress), RLP.encodeInt(0));

            InvokeFilterEvent event = new InvokeFilterEvent(
                    INVOKE_ETH_CONTRACT_AUTH_FILTER_ADDR,
                    MethodIdConstant.SET_CONTRACT_INVOKE_BLACK_MODE,
                    methodInput
            );

            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.INVOKE_FILTER.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("setContractInvokeWhiteMode txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("setContractInvokeWhiteMode receipt:{}", receipt);
                return true;

            }
            return false;
        } catch (Exception e) {
            logger.error("setContractInvokeWhiteMode failed.", e);
            throw new RuntimeException(e);
        }
    }

    //新增 合约调用 黑名单 账户地址，仅在合约采用黑名单模式时生效
    public static boolean addContractInvokeBlackUser(Web3j web3j, SecureKey sender, byte[] contractAddress, byte[] userAddress) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            //新增用户地址 到 指定合约的调用黑名单
            byte[] methodInput = RLP.encodeList(RLP.encodeElement(contractAddress), RLP.encodeElement(userAddress), RLP.encodeInt(0));

            InvokeFilterEvent event = new InvokeFilterEvent(
                    INVOKE_ETH_CONTRACT_AUTH_FILTER_ADDR,
                    MethodIdConstant.SET_INVOKE_BLACK,
                    methodInput
            );

            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.INVOKE_FILTER.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("addContractInvokeBlackUser txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("addContractInvokeBlackUser receipt:{}", receipt);
                return true;

            }
            return false;
        } catch (Exception e) {
            logger.error("addContractInvokeBlackUser failed.", e);
            throw new RuntimeException(e);
        }
    }

    //删除 合约调用 黑名单 账户地址，仅在合约采用黑名单模式时生效
    public static boolean removeContractInvokeBlackUser(Web3j web3j, SecureKey sender, byte[] contractAddress, byte[] userAddress) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            //将用户地址 从 指定合约的调用黑名单 中删除
            byte[] methodInput = RLP.encodeList(RLP.encodeElement(contractAddress), RLP.encodeElement(userAddress), RLP.encodeInt(1));

            InvokeFilterEvent event = new InvokeFilterEvent(
                    INVOKE_ETH_CONTRACT_AUTH_FILTER_ADDR,
                    MethodIdConstant.SET_INVOKE_BLACK,
                    methodInput
            );

            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.INVOKE_FILTER.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("removeContractInvokeBlackUser txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("removeContractInvokeBlackUser receipt:{}", receipt);
                return true;

            }
            return false;
        } catch (Exception e) {
            logger.error("removeContractInvokeBlackUser failed.", e);
            throw new RuntimeException(e);
        }
    }

    //新增 合约调用 白名单 账户地址，仅在合约采用白名单模式时生效
    public static boolean addContractInvokeWhiteUser(Web3j web3j, SecureKey sender, byte[] contractAddress, byte[] userAddress) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            //新增用户地址 到 指定合约的调用白名单
            byte[] methodInput = RLP.encodeList(RLP.encodeElement(contractAddress), RLP.encodeElement(userAddress), RLP.encodeInt(0));

            InvokeFilterEvent event = new InvokeFilterEvent(
                    INVOKE_ETH_CONTRACT_AUTH_FILTER_ADDR,
                    MethodIdConstant.SET_INVOKE_WHITE,
                    methodInput
            );

            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.INVOKE_FILTER.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("addContractInvokeWhiteUser txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("addContractInvokeWhiteUser receipt:{}", receipt);
                return true;

            }
            return false;
        } catch (Exception e) {
            logger.error("addContractInvokeWhiteUser failed.", e);
            throw new RuntimeException(e);
        }
    }

    //删除 合约调用 黑名单 账户地址，仅在合约采用黑名单模式时生效
    public static boolean removeContractInvokeWhiteUser(Web3j web3j, SecureKey sender, byte[] contractAddress, byte[] userAddress) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            //将用户地址 从 指定合约的调用白名单 中删除
            byte[] methodInput = RLP.encodeList(RLP.encodeElement(contractAddress), RLP.encodeElement(userAddress), RLP.encodeInt(1));

            InvokeFilterEvent event = new InvokeFilterEvent(
                    INVOKE_ETH_CONTRACT_AUTH_FILTER_ADDR,
                    MethodIdConstant.SET_INVOKE_WHITE,
                    methodInput
            );

            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.INVOKE_FILTER.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("removeContractInvokeWhiteUser txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("removeContractInvokeWhiteUser receipt:{}", receipt);
                return true;

            }
            return false;
        } catch (Exception e) {
            logger.error("removeContractInvokeWhiteUser failed.", e);
            throw new RuntimeException(e);
        }
    }
}
