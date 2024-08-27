package com.thanos.web3j.eventgen;


import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.common.utils.ByteArrayWrapper;
import com.thanos.common.utils.ByteUtil;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.model.ThanosGlobalNodeEventReceipt;
import com.thanos.web3j.model.event.CandidateEventConstant;
import com.thanos.web3j.model.event.GlobalEventCommand;
import com.thanos.web3j.model.event.VoteNodeCandidateEvent;
import com.thanos.web3j.model.state.EpochState;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.response.EthSendTransaction;
import com.thanos.web3j.tx.RawTransactionManager;
import com.thanos.web3j.tx.TransactionConstant;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import test.camanager.ManageTestBase;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class NodeManager {

    protected static final Logger logger = LoggerFactory.getLogger(NodeManager.class);

    //新增共识节点
    public static byte[] registerNode(Web3j web3j, SecureKey sender, NodeInfo nodeInfo) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            byte[] proposalId = randomid.toByteArray();

            VoteNodeCandidateEvent event = new VoteNodeCandidateEvent(
                    CandidateEventConstant.AGREE_VOTE,
                    CandidateEventConstant.REGISTER_PROCESS,
                    proposalId,
                    Hex.decode(nodeInfo.getPublicKey()),
                    nodeInfo.getName(),
                    nodeInfo.getAgency(),
                    nodeInfo.getCaHash(),
                    1,
                    1
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.VOTE_NODE_CANDIDATE.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.debug("registerNode txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt == null || receipt.getExecutionResult() == null) {
                logger.warn("CommitteeManager registerNode receipt is null.");
                return null;
            }

            int executeResult = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            if (CandidateEventConstant.VOTE_FAILED == executeResult) {
                logger.warn("CommitteeManager registerNode failed. receipt:{}", receipt);
                return null;
            }
            return proposalId;

        } catch (Exception e) {
            logger.error("NodeManager registerNode failed.", e);
            throw new RuntimeException(e);
        }
    }

    //注册节点-投票
    public static boolean voteForRegisterNode(Web3j web3j, SecureKey sender, NodeInfo nodeInfo, byte[] proposalId, int voteType) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);

            VoteNodeCandidateEvent event = new VoteNodeCandidateEvent(
                    voteType,
                    CandidateEventConstant.REGISTER_PROCESS,
                    proposalId,
                    Hex.decode(nodeInfo.getPublicKey()),
                    nodeInfo.getName(),
                    nodeInfo.getAgency(),
                    nodeInfo.getCaHash(),
                    1,
                    1
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.VOTE_NODE_CANDIDATE.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.debug("voteForRegisterNode txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt == null || receipt.getExecutionResult() == null) {
                logger.warn("CommitteeManager voteForRegisterNode receipt is null.");
                return false;
            }

            int executeResult = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            if (CandidateEventConstant.VOTE_FAILED == executeResult) {
                logger.warn("CommitteeManager voteForRegisterNode failed. receipt:{}", receipt);
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("NodeManager voteForRegisterNode failed.", e);
            throw new RuntimeException(e);
        }
    }

    //删除共识节点
    public static byte[] cancelNode(Web3j web3j, SecureKey sender, NodeInfo nodeInfo) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            byte[] proposalId = randomid.toByteArray();

            VoteNodeCandidateEvent event = new VoteNodeCandidateEvent(
                    CandidateEventConstant.AGREE_VOTE,
                    CandidateEventConstant.CANCEL_PROCESS,
                    proposalId,
                    Hex.decode(nodeInfo.getPublicKey()),
                    nodeInfo.getName(),
                    nodeInfo.getAgency(),
                    nodeInfo.getCaHash(),
                    1,
                    1
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.VOTE_NODE_CANDIDATE.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.debug("cancelNode txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt == null || receipt.getExecutionResult() == null) {
                logger.warn("CommitteeManager cancelNode receipt is null.");
                return null;
            }

            int executeResult = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            if (CandidateEventConstant.VOTE_FAILED == executeResult) {
                logger.warn("CommitteeManager cancelNode failed. receipt:{}", receipt);
                return null;
            }
            return proposalId;

        } catch (Exception e) {
            logger.error("NodeManager cancelNode failed.", e);
            throw new RuntimeException(e);
        }
    }

    //删除节点-投票
    public static boolean voteForCancelNode(Web3j web3j, SecureKey sender, NodeInfo nodeInfo, byte[] proposalId, int voteType) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);

            VoteNodeCandidateEvent event = new VoteNodeCandidateEvent(
                    voteType,
                    CandidateEventConstant.CANCEL_PROCESS,
                    proposalId,
                    Hex.decode(nodeInfo.getPublicKey()),
                    nodeInfo.getName(),
                    nodeInfo.getAgency(),
                    nodeInfo.getCaHash(),
                    1,
                    1
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.VOTE_NODE_CANDIDATE.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.debug("voteForCancelNode txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt == null || receipt.getExecutionResult() == null) {
                logger.warn("CommitteeManager voteForCancelNode receipt is null.");
                return false;
            }

            int executeResult = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            if (CandidateEventConstant.VOTE_FAILED == executeResult) {
                logger.warn("CommitteeManager voteForCancelNode failed. receipt:{}", receipt);
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("NodeManager voteForCancelNode failed.", e);
            throw new RuntimeException(e);
        }
    }
}
