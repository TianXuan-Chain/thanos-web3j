package test.camanager;


import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.common.utils.ByteArrayWrapper;
import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.model.ThanosGlobalNodeEventReceipt;
import com.thanos.web3j.model.event.CandidateEventConstant;
import com.thanos.web3j.model.event.GlobalEventCommand;
import com.thanos.web3j.model.event.VoteCommitteeCandidateEvent;
import com.thanos.web3j.model.event.VoteNodeCandidateEvent;
import com.thanos.web3j.model.state.EpochState;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.response.EthSendTransaction;
import com.thanos.web3j.protocol.manage.Web3Manager;
import com.thanos.web3j.tx.RawTransactionManager;
import com.thanos.web3j.tx.TransactionConstant;
import com.thanos.web3j.utils.ConfigResourceUtil;
import com.thanos.web3j.utils.Numeric;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CommitteeManagerTest extends ManageTestBase {

    @Test
    public void testCommitteeForever() {
        int count = 0;
        while (true) {
            add2Committees();
            delete2Committees();
            count++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("****************testCommittee success! count={}", count);
        }
    }

    @Test
    public void testAddCommitteeSuccess() {
        add2Committees();
//        add3Committees();
//        EpochState epochState = getEpochState();
//        logger.info("globalEventState is:{}", epochState.getGlobalEventState());
    }


    @Test
    public void testDeleteCommitteeSuccess() {
        delete2Committees();
    }


    public static void add2Committees() {
        Web3j web3j = web3Manager.getWeb3jRandomly();
        byte[] proposalId = addCommittee(web3j, committee0, committeeCandidate1);
        EpochState epochState = getEpochState();
        logger.info("add 1 committee proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("========================================");
        proposalId = addCommittee(web3j, committee0, committeeCandidate2);
        epochState = getEpochState();
        logger.info("add 2 committee 发起提案 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        voteForAddCommittee(web3j, committeeCandidate1, committeeCandidate2, proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("add 2 committee 投票完成 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("========================================");
        List aserList = Arrays.asList(new ByteArrayWrapper(committee0.getAddress()), new ByteArrayWrapper(committeeCandidate1.getAddress()), new ByteArrayWrapper(committeeCandidate2.getAddress()));
        boolean res = epochState.getGlobalEventState().getCommitteeAddrs().equals(aserList);
        Assert.assertTrue(res);
    }

    public static void add3Committees() {
        add2Committees();
        Web3j web3j = web3Manager.getWeb3jRandomly();
        byte[] proposalId = addCommittee(web3j, committee0, committeeCandidate3);
        EpochState epochState = getEpochState();
        logger.info("========================================");
        logger.info("add 3 committee 发起提案 proposalId:{}, committeeAddrs:{}", Hex.toHexString(proposalId), epochState.getGlobalEventState().getCommitteeAddrs());

        voteForAddCommittee(web3j, committeeCandidate1, committeeCandidate3, proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("add 3 committee 投票1 proposalId:{}, committeeAddrs:{}", Hex.toHexString(proposalId), epochState.getGlobalEventState().getCommitteeAddrs());

        voteForAddCommittee(web3j, committeeCandidate2, committeeCandidate3, proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("add 3 committee 投票2 proposalId:{}, committeeAddrs:{}", Hex.toHexString(proposalId), epochState.getGlobalEventState().getCommitteeAddrs());
    }

    public static void delete2Committees() {
        EpochState epochState = getEpochState();
        logger.info("globalEventState is:{}", epochState);
        Web3j web3j = web3Manager.getWeb3jRandomly();
        byte[] proposalId = deleteCommittee(web3j, committee0, committeeCandidate1);
        epochState = getEpochState();
        logger.info("delete 1 committee 发起投票 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("------------------------------------");

        voteForDeleteCommittee(web3j, committeeCandidate1, committeeCandidate1, proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("delete 1 committee 投票1完成 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("------------------------------------");

        voteForDeleteCommittee(web3j, committeeCandidate2, committeeCandidate1, proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("delete 1 committee 投票2完成 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("========================================");

        proposalId = deleteCommittee(web3j, committee0, committeeCandidate2);
        epochState = getEpochState();
        logger.info("delete 2 committee 发起投票 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("------------------------------------");
        voteForDeleteCommittee(web3j, committeeCandidate2, committeeCandidate2, proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("delete 2 committee 投票完成 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("========================================");
        List aserList = Arrays.asList(new ByteArrayWrapper(committee0.getAddress()));
        boolean res = epochState.getGlobalEventState().getCommitteeAddrs().equals(aserList);
        Assert.assertTrue(res);
    }


    //新增委员
    public static byte[] addCommittee(Web3j web3j, SecureKey sender, SecureKey candidate) {
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
                    candidate.getAddress()
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
            logger.info("[{}]-[{}]", Hex.toHexString(globalNodeEvent.getHash()), Hex.toHexString(proposalId));

            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("addCommittee txHash:{}", str);

            //Thread.sleep(5000);

            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            //ThanosGlobalNodeEvent event = transactionManager.getThanosGlobalEventReceiptByHash(str);
            if (receipt != null) {
                logger.info("CommitteeManagerTest addCommittee receipt:{}", receipt);
            }
            return proposalId;

        } catch (Exception e) {
            logger.error("CommitteeManagerTest addCommittee failed.", e);
            throw new RuntimeException(e);
        }
    }

    //新增委员-投票
    public static boolean voteForAddCommittee(Web3j web3j, SecureKey sender, SecureKey candidate, byte[] proposalId, int voteType) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);

            VoteCommitteeCandidateEvent event = new VoteCommitteeCandidateEvent(
                    CandidateEventConstant.AGREE_VOTE,
                    CandidateEventConstant.REGISTER_PROCESS,
                    proposalId,
                    candidate.getAddress()
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
            logger.info("[{}]-[{}]", Hex.toHexString(globalNodeEvent.getHash()), Hex.toHexString(proposalId));


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("voteForAddCommittee txHash:{}", str);

            //Thread.sleep(5000);

            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            //ThanosGlobalNodeEvent event = transactionManager.getThanosGlobalEventReceiptByHash(str);
            if (receipt != null) {
                logger.info("CommitteeManagerTest voteForAddCommittee receipt:{}", receipt);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("CommitteeManagerTest voteForAddCommittee failed.", e);
            throw new RuntimeException(e);
        }
    }

    //删除委员
    public static byte[] deleteCommittee(Web3j web3j, SecureKey sender, SecureKey candidate) {
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
                    candidate.getAddress()
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

            logger.info("[{}]-[{}]", Hex.toHexString(globalNodeEvent.getHash()), Hex.toHexString(proposalId));


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("deleteCommittee txHash:{}", str);

            //Thread.sleep(5000);

            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            //ThanosGlobalNodeEvent event = transactionManager.getThanosGlobalEventReceiptByHash(str);
            if (receipt != null) {
                logger.info("CommitteeManagerTest deleteCommittee receipt:{}", receipt);
            }
            return proposalId;

        } catch (Exception e) {
            logger.error("CommitteeManagerTest deleteCommittee failed.", e);
            throw new RuntimeException(e);
        }
    }

    //删除委员-投票
    public static boolean voteForDeleteCommittee(Web3j web3j, SecureKey sender, SecureKey candidate, byte[] proposalId, int voteType) {
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
                    candidate.getAddress()
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
            logger.info("[{}]-[{}]", Hex.toHexString(globalNodeEvent.getHash()), Hex.toHexString(proposalId));


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("voteForDeleteCommittee txHash:{}", str);

            //Thread.sleep(5000);

            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            //ThanosGlobalNodeEvent event = transactionManager.getThanosGlobalEventReceiptByHash(str);
            if (receipt != null) {
                logger.info("CommitteeManagerTest voteForDeleteCommittee receipt:{}", receipt);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("CommitteeManagerTest voteForDeleteCommittee failed.", e);
            throw new RuntimeException(e);
        }
    }
}
