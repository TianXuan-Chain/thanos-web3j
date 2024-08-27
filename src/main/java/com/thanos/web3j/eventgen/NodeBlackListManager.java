package com.thanos.web3j.eventgen;


import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.common.utils.ByteUtil;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.model.ThanosGlobalNodeEventReceipt;
import com.thanos.web3j.model.event.CandidateEventConstant;
import com.thanos.web3j.model.event.GlobalEventCommand;
import com.thanos.web3j.model.event.VoteNodeBlackListCandidateEvent;
import com.thanos.web3j.model.state.EpochState;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.response.EthSendTransaction;
import com.thanos.web3j.tx.RawTransactionManager;
import com.thanos.web3j.tx.TransactionConstant;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import test.camanager.ManageTestBase;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class NodeBlackListManager {
    protected static final Logger logger = LoggerFactory.getLogger(NodeBlackListManager.class);

    //将节点加入黑名单
    public static byte[] addBlackListNode(Web3j web3j, SecureKey sender, byte[] publicKey, String caHash) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            byte[] proposalId = randomid.toByteArray();

            VoteNodeBlackListCandidateEvent event = new VoteNodeBlackListCandidateEvent(
                    CandidateEventConstant.AGREE_VOTE,
                    CandidateEventConstant.REGISTER_PROCESS,
                    proposalId,
                    publicKey,
                    caHash.getBytes()
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.VOTE_NODE_BLACKLIST_CANDIDATE.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("addBlackListNode txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt == null || receipt.getExecutionResult() == null) {
                logger.warn("CommitteeManager addBlackListNode receipt is null.");
                return null;
            }

            int executeResult = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            if (CandidateEventConstant.VOTE_FAILED == executeResult) {
                logger.warn("CommitteeManager addBlackListNode failed. receipt:{}", receipt);
                return null;
            }
            return proposalId;

        } catch (Exception e) {
            logger.error("NodeManager addBlackListNode failed.", e);
            throw new RuntimeException(e);
        }
    }

    //节点移出黑名单-投票
    public static boolean voteForAddBlackListNode(Web3j web3j, SecureKey sender, byte[] publicKey, String caHash, byte[] proposalId, int voteType) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);

            VoteNodeBlackListCandidateEvent event = new VoteNodeBlackListCandidateEvent(
                    voteType,
                    CandidateEventConstant.REGISTER_PROCESS,
                    proposalId,
                    publicKey,
                    caHash.getBytes()
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.VOTE_NODE_BLACKLIST_CANDIDATE.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.debug("voteForAddBlackListNode txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt == null || receipt.getExecutionResult() == null) {
                logger.warn("CommitteeManager voteForAddBlackListNode receipt is null.");
                return false;
            }

            int executeResult = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            if (CandidateEventConstant.VOTE_FAILED == executeResult) {
                logger.warn("CommitteeManager voteForAddBlackListNode failed. receipt:{}", receipt);
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("NodeManager voteForAddBlackListNode failed.", e);
            throw new RuntimeException(e);
        }
    }

    //将节点移出黑名单
    public static byte[] deleteBlackListNode(Web3j web3j, SecureKey sender, byte[] publicKey, String caHash) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            byte[] proposalId = randomid.toByteArray();

            VoteNodeBlackListCandidateEvent event = new VoteNodeBlackListCandidateEvent(
                    CandidateEventConstant.AGREE_VOTE,
                    CandidateEventConstant.CANCEL_PROCESS,
                    proposalId,
                    publicKey,
                    caHash.getBytes()
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.VOTE_NODE_BLACKLIST_CANDIDATE.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.debug("deleteBlackListNode txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt == null || receipt.getExecutionResult() == null) {
                logger.warn("CommitteeManager deleteBlackListNode receipt is null.");
                return null;
            }

            int executeResult = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            if (CandidateEventConstant.VOTE_FAILED == executeResult) {
                logger.warn("CommitteeManager deleteBlackListNode failed. receipt:{}", receipt);
                return null;
            }
            return proposalId;

        } catch (Exception e) {
            logger.error("NodeManager deleteBlackListNode failed.", e);
            throw new RuntimeException(e);
        }
    }

    //节点移出黑名单-投票
    public static boolean voteForDeleteBlackListNode(Web3j web3j, SecureKey sender, byte[] publicKey, String caHash, byte[] proposalId, int voteType) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);

            VoteNodeBlackListCandidateEvent event = new VoteNodeBlackListCandidateEvent(
                    voteType,
                    CandidateEventConstant.CANCEL_PROCESS,
                    proposalId,
                    publicKey,
                    caHash.getBytes()
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.VOTE_NODE_BLACKLIST_CANDIDATE.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.debug("voteForDeleteBlackListNode txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt == null || receipt.getExecutionResult() == null) {
                logger.warn("CommitteeManager voteForDeleteBlackListNode receipt is null.");
                return false;
            }

            int executeResult = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            if (CandidateEventConstant.VOTE_FAILED == executeResult) {
                logger.warn("CommitteeManager voteForDeleteBlackListNode failed. receipt:{}", receipt);
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("NodeManager voteForDeleteBlackListNode failed.", e);
            throw new RuntimeException(e);
        }
    }
}
