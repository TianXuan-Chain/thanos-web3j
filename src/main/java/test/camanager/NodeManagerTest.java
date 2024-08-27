package test.camanager;


import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.common.crypto.key.asymmetric.SecurePublicKey;
import com.thanos.common.utils.ByteArrayWrapper;
import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.eventgen.NodeInfo;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.model.ThanosGlobalNodeEventReceipt;
import com.thanos.web3j.model.event.CandidateEventConstant;
import com.thanos.web3j.model.event.GlobalEventCommand;
import com.thanos.web3j.model.event.VoteNodeCandidateEvent;
import com.thanos.web3j.model.state.EpochState;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.response.EthSendTransaction;
import com.thanos.web3j.protocol.manage.Web3Manager;
import com.thanos.web3j.tx.RawTransactionManager;
import com.thanos.web3j.tx.TransactionConstant;
import com.thanos.web3j.utils.ConfigResourceUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class NodeManagerTest extends ManageTestBase {

    @Test
    public void testNodeForever() {
        int count = 0;
        int work_interval = 120; //注册后节点运行时间(s)
        int idle_interval = 30; //退出后节点闲置时间(s)
        while (true) {
            try {
                addNodeSuccess();
                Thread.sleep(work_interval * 1000);
                removeNodeSuccess();
                count++;
                logger.info("****************testNodeManage success! count={}", count);
                Thread.sleep(idle_interval * 1000);
            } catch (InterruptedException e) {
                logger.error("InterruptedException:{}", e);
                break;
            }
        }
    }

    @Test
    public void testRegisterNodeSuccess() {
        //网络通道
        Web3j web3j = web3Manager.getWeb3jRandomly();

        SecureKey candidate = SecureKey.fromPrivate(Hex.decode("010001d9694bc7257b6e11d5a6d3076b28fd9011b46fcc036fbfddf2d6f87866673480"));
        NodeInfo nodeInfo = new NodeInfo(Hex.toHexString(candidate.getPubKey()), "xiaoming02", "agency03", "7F393CBB7323A7A208A0794A479DA729D290C231", Hex.toHexString(candidate.getPrivKeyBytes()));
        //1. 新增委员
        CommitteeManagerTest.add2Committees();

        //2. 新增节点
        //3票赞成，加入成功
        byte[] proposalId = registerNode(web3j, committee0, nodeInfo);
        EpochState epochState = getEpochState();
        logger.info("registerNode 发起提案 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);

        voteForRegisterNode(web3j, committeeCandidate1, nodeInfo, proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("registerNode 投票1完成 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("------------------------------------");

        voteForRegisterNode(web3j, committeeCandidate2, nodeInfo, proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("registerNode 投票2完成 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("==============================================");
    }


    @Test
    public void testCancelNodeSuccess() {
        //网络通道
        Web3j web3j = web3Manager.getWeb3jRandomly();

        SecureKey candidate = SecureKey.fromPrivate(Hex.decode("010001d9694bc7257b6e11d5a6d3076b28fd9011b46fcc036fbfddf2d6f87866673480"));
        NodeInfo nodeInfo = new NodeInfo(Hex.toHexString(candidate.getPubKey()), "xiaoming02", "agency03", "7F393CBB7323A7A208A0794A479DA729D290C231", Hex.toHexString(candidate.getPrivKeyBytes()));

        //1. 删除节点
        //3票赞成，退出成功
        byte[] proposalId = cancelNode(web3j, committee0, nodeInfo);
        EpochState epochState = getEpochState();
        logger.info("cancelNode 发起提案 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);

        voteForCancelNode(web3j, committeeCandidate1, nodeInfo, proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("cancelNode 投票1完成 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("------------------------------------");

        voteForCancelNode(web3j, committeeCandidate2, nodeInfo, proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("cancelNode 投票2完成 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("==============================================");

        //2. 删除委员
        CommitteeManagerTest.delete2Committees();
    }

    public static void main(String[] args) {
        //网络通道
        Web3j web3j = web3Manager.getWeb3jRandomly();

        SecureKey candidate = SecureKey.fromPrivate(Hex.decode("010001d9694bc7257b6e11d5a6d3076b28fd9011b46fcc036fbfddf2d6f87866673480"));
        NodeInfo nodeInfo = new NodeInfo(Hex.toHexString(candidate.getPubKey()), "xiaoming02", "agency03", "7F393CBB7323A7A208A0794A479DA729D290C231", Hex.toHexString(candidate.getPrivKeyBytes()));

        EpochState epochState = getEpochState();
        logger.info("initial epochState is:{}", epochState.getGlobalEventState());
        //加入成功
        registerNode(web3j, committee0, nodeInfo);
        epochState = getEpochState();
        logger.info("validatorVerifier after add is:{}", epochState.getOrderedPublishKeys());
        //退出成功
        cancelNode(web3j, committee0, nodeInfo);
        epochState = getEpochState();
        logger.info("validatorVerifier after delete is:{}", epochState.getOrderedPublishKeys());
    }

    public static void addNodeSuccess() {
        Web3j web3j = web3Manager.getWeb3jRandomly();
        SecureKey candidate = SecureKey.fromPrivate(Hex.decode("010001d9694bc7257b6e11d5a6d3076b28fd9011b46fcc036fbfddf2d6f87866673480"));
        NodeInfo nodeInfo = new NodeInfo(Hex.toHexString(candidate.getPubKey()), "xiaoming02", "agency03", "7F393CBB7323A7A208A0794A479DA729D290C231", Hex.toHexString(candidate.getPrivKeyBytes()));

        //加入成功
        byte[] proposalId = registerNode(web3j, committee0, nodeInfo);
        EpochState epochState = getEpochState();
        logger.info("add node proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        boolean res = epochState.getGlobalEventState().getValidatorVerifier().containPublicKey(new ByteArrayWrapper(candidate.getPubKey()));
        Assert.assertTrue(res);
        logger.info("========================================");
    }

    public static void removeNodeSuccess() {
        Web3j web3j = web3Manager.getWeb3jRandomly();
        SecureKey candidate = SecureKey.fromPrivate(Hex.decode("010001d9694bc7257b6e11d5a6d3076b28fd9011b46fcc036fbfddf2d6f87866673480"));
        NodeInfo nodeInfo = new NodeInfo(Hex.toHexString(candidate.getPubKey()), "xiaoming02", "agency03", "7F393CBB7323A7A208A0794A479DA729D290C231", Hex.toHexString(candidate.getPrivKeyBytes()));

        //退出成功
        byte[] proposalId = cancelNode(web3j, committee0, nodeInfo);
        EpochState epochState = getEpochState();
        logger.info("remove node proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        boolean res = epochState.getGlobalEventState().getValidatorVerifier().containPublicKey(new ByteArrayWrapper(candidate.getPubKey()));
        Assert.assertFalse(res);
        logger.info("========================================");
    }

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
            logger.info("registerNode txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("NodeManagerTest registerNode receipt:{}", receipt);
            }
            return proposalId;

        } catch (Exception e) {
            logger.error("NodeManagerTest registerNode failed.", e);
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
            logger.info("voteForRegisterNode txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("NodeManagerTest voteForRegisterNode receipt:{}", receipt);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("NodeManagerTest voteForRegisterNode failed.", e);
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
            logger.info("cancelNode txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("NodeManagerTest cancelNode receipt:{}", receipt);
            }
            return proposalId;

        } catch (Exception e) {
            logger.error("NodeManagerTest cancelNode failed.", e);
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
            logger.info("voteForCancelNode txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("NodeManagerTest voteForCancelNode receipt:{}", receipt);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("NodeManagerTest voteForCancelNode failed.", e);
            throw new RuntimeException(e);
        }
    }
}
