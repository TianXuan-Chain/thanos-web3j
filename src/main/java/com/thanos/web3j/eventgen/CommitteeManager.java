package com.thanos.web3j.eventgen;


import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.common.utils.ByteArrayWrapper;
import com.thanos.common.utils.ByteUtil;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.model.ThanosGlobalNodeEventReceipt;
import com.thanos.web3j.model.event.CandidateEventConstant;
import com.thanos.web3j.model.event.GlobalEventCommand;
import com.thanos.web3j.model.event.VoteCommitteeCandidateEvent;
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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CommitteeManager {
    protected static final Logger logger = LoggerFactory.getLogger(CommitteeManager.class);

    //新增委员
    public static byte[] addCommittee(Web3j web3j, SecureKey sender, byte[] candidateAddr) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            byte[] proposalId = randomid.toByteArray();

            VoteCommitteeCandidateEvent event = new VoteCommitteeCandidateEvent(
                    CandidateEventConstant.AGREE_VOTE,
                    CandidateEventConstant.REGISTER_PROCESS,
                    proposalId,
                    candidateAddr
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.VOTE_COMMITTEE_CANDIDATE.getCode(),
                    event.getEncoded(),
                    null
            );

            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.debug("addCommittee txHash:{}", str);

            //Thread.sleep(5000);

            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt == null || receipt.getExecutionResult() == null) {
                logger.warn("CommitteeManager addCommittee receipt is null.");
                return null;
            }

            int executeResult = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            if (CandidateEventConstant.VOTE_FAILED == executeResult) {
                logger.warn("CommitteeManager addCommittee failed. receipt:{}", receipt);
                return null;
            }
            return proposalId;
        } catch (Exception e) {
            logger.error("CommitteeManager addCommittee failed.", e);
            throw new RuntimeException(e);
        }
    }

    //新增委员-投票
    public static boolean voteForAddCommittee(Web3j web3j, SecureKey sender, byte[] candidateAddr, byte[] proposalId, int voteType) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);

            VoteCommitteeCandidateEvent event = new VoteCommitteeCandidateEvent(
                    voteType,
                    CandidateEventConstant.REGISTER_PROCESS,
                    proposalId,
                    candidateAddr
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.VOTE_COMMITTEE_CANDIDATE.getCode(),
                    event.getEncoded(),
                    null
            );

            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.debug("voteForAddCommittee txHash:{}", str);

            //Thread.sleep(5000);

            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt == null || receipt.getExecutionResult() == null) {
                logger.warn("CommitteeManager voteForAddCommittee receipt is null.");
                return false;
            }

            int executeResult = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            if (CandidateEventConstant.VOTE_FAILED == executeResult) {
                logger.warn("CommitteeManager voteForAddCommittee failed. receipt:{}", receipt);
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("CommitteeManager voteForAddCommittee failed.", e);
            throw new RuntimeException(e);
        }
    }

    //删除委员
    public static byte[] deleteCommittee(Web3j web3j, SecureKey sender, byte[] candidateAddr) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            byte[] proposalId = randomid.toByteArray();

            VoteCommitteeCandidateEvent event = new VoteCommitteeCandidateEvent(
                    CandidateEventConstant.AGREE_VOTE,
                    CandidateEventConstant.CANCEL_PROCESS,
                    proposalId,
                    candidateAddr
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.VOTE_COMMITTEE_CANDIDATE.getCode(),
                    event.getEncoded(),
                    null
            );

            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.debug("deleteCommittee txHash:{}", str);


            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt == null || receipt.getExecutionResult() == null) {
                logger.warn("CommitteeManager deleteCommittee receipt is null.");
                return null;
            }

            int executeResult = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            if (CandidateEventConstant.VOTE_FAILED == executeResult) {
                logger.warn("CommitteeManager deleteCommittee failed. receipt:{}", receipt);
                return null;
            }
            return proposalId;

        } catch (Exception e) {
            logger.error("CommitteeManager deleteCommittee failed.", e);
            throw new RuntimeException(e);
        }
    }

    //删除委员-投票
    public static boolean voteForDeleteCommittee(Web3j web3j, SecureKey sender, byte[] candidateAddr, byte[] proposalId, int voteType) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);

            VoteCommitteeCandidateEvent event = new VoteCommitteeCandidateEvent(
                    voteType,
                    CandidateEventConstant.CANCEL_PROCESS,
                    proposalId,
                    candidateAddr
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.VOTE_COMMITTEE_CANDIDATE.getCode(),
                    event.getEncoded(),
                    null
            );

            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.debug("voteForDeleteCommittee txHash:{}", str);

            //查询结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt == null || receipt.getExecutionResult() == null) {
                logger.warn("CommitteeManager voteForDeleteCommittee receipt is null.");
                return false;
            }

            int executeResult = ByteUtil.byteArrayToInt(receipt.getExecutionResult());
            if (CandidateEventConstant.VOTE_FAILED == executeResult) {
                logger.warn("CommitteeManager voteForDeleteCommittee failed. receipt:{}", receipt);
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("CommitteeManager voteForDeleteCommittee failed.", e);
            throw new RuntimeException(e);
        }
    }
}
