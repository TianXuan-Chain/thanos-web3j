package test.camanager;


import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.eventgen.NodeInfo;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.model.ThanosGlobalNodeEventReceipt;
import com.thanos.web3j.model.event.CandidateEventConstant;
import com.thanos.web3j.model.event.GlobalEventCommand;
import com.thanos.web3j.model.event.VoteNodeBlackListCandidateEvent;
import com.thanos.web3j.model.event.VoteNodeCandidateEvent;
import com.thanos.web3j.model.state.EpochState;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.response.EthSendTransaction;
import com.thanos.web3j.tx.RawTransactionManager;
import com.thanos.web3j.tx.TransactionConstant;
import org.junit.Test;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class NodeBlackListManagerTest extends ManageTestBase {

    @Test
    public void testAddBlackListSuccess() {
        //网络通道
        Web3j web3j = web3Manager.getWeb3jRandomly();

        SecureKey candidate = SecureKey.fromPrivate(Hex.decode("010001d9694bc7257b6e11d5a6d3076b28fd9011b46fcc036fbfddf2d6f87866673480"));
        NodeInfo nodeInfo = new NodeInfo(Hex.toHexString(candidate.getPubKey()), "xiaoming02", "agency03", "7F393CBB7323A7A208A0794A479DA729D290C231", Hex.toHexString(candidate.getPrivKeyBytes()));

        //1. 新增委员
        //CommitteeManagerTest.add2Committees();

        //2. 新增黑名单
        //3票赞成，加入成功
        byte[] proposalId = addBlackListNode(web3j, committee0, candidate.getPubKey(), nodeInfo.getCaHash());
        EpochState epochState = getEpochState();
        logger.info("addBlackList 发起提案 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);

        voteForAddBlackListNode(web3j, committeeCandidate1, candidate.getPubKey(), nodeInfo.getCaHash(), proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("addBlackList 投票1完成 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("------------------------------------");

        voteForAddBlackListNode(web3j, committeeCandidate2, candidate.getPubKey(), nodeInfo.getCaHash(), proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("addBlackList 投票2完成 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("==============================================");
    }


    @Test
    public void testCancelNodeSuccess() {
        //网络通道
        Web3j web3j = web3Manager.getWeb3jRandomly();

        SecureKey candidate = SecureKey.fromPrivate(Hex.decode("010001d9694bc7257b6e11d5a6d3076b28fd9011b46fcc036fbfddf2d6f87866673480"));
        NodeInfo nodeInfo = new NodeInfo(Hex.toHexString(candidate.getPubKey()), "xiaoming02", "agency03", "7F393CBB7323A7A208A0794A479DA729D290C231", Hex.toHexString(candidate.getPrivKeyBytes()));

        logger.info("current state {}", getEpochState());

        //1. 删除黑名单
        //3票赞成，退出成功
        byte[] proposalId = deleteBlackListNode(web3j, committee0, candidate.getPubKey(), nodeInfo.getCaHash());
        EpochState epochState = getEpochState();
        logger.info("deleteBlackList 发起提案 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);

        voteForDeleteBlackListNode(web3j, committeeCandidate1, candidate.getPubKey(),  nodeInfo.getCaHash(), proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("deleteBlackList 投票1完成 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("------------------------------------");

        voteForDeleteBlackListNode(web3j, committeeCandidate2, candidate.getPubKey(), nodeInfo.getCaHash(), proposalId, CandidateEventConstant.AGREE_VOTE);
        epochState = getEpochState();
        logger.info("deleteBlackList 投票2完成 proposalId:{}, globalEvent:{}", Hex.toHexString(proposalId), epochState);
        logger.info("==============================================");

        //2. 删除委员
        //CommitteeManagerTest.delete2Committees();
    }


    public static void main(String[] args) {
        //网络通道
        Web3j web3j = web3Manager.getWeb3jRandomly();

        SecureKey candidate = SecureKey.fromPrivate(Hex.decode("010001d9694bc7257b6e11d5a6d3076b28fd9011b46fcc036fbfddf2d6f87866673480"));
        NodeInfo nodeInfo = new NodeInfo(Hex.toHexString(candidate.getPubKey()), "xiaoming02", "agency03", "7F393CBB7323A7A208A0794A479DA729D290C231", Hex.toHexString(candidate.getPrivKeyBytes()));

        addBlackListNode(web3j, committee0, candidate.getPubKey(), nodeInfo.getCaHash());
        EpochState epochState = getEpochState();
        logger.info("blackList after add is:{}", epochState.getGlobalEventState().getNodeBlackList());
        deleteBlackListNode(web3j, committee0, candidate.getPubKey(), nodeInfo.getCaHash());
        EpochState epochState2 = getEpochState();
        logger.info("blackList after delete is:{}", epochState2.getGlobalEventState().getNodeBlackList());
    }

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


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("NodeManagerTest addBlackListNode receipt:{}", receipt);
            }
            return proposalId;

        } catch (Exception e) {
            logger.error("NodeManagerTest addBlackListNode failed.", e);
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
            logger.info("voteForAddBlackListNode txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("NodeManagerTest voteForAddBlackListNode receipt:{}", receipt);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("NodeManagerTest voteForAddBlackListNode failed.", e);
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
            logger.info("deleteBlackListNode txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("NodeManagerTest deleteBlackListNode receipt:{}", receipt);
            }
            return proposalId;

        } catch (Exception e) {
            logger.error("NodeManagerTest deleteBlackListNode failed.", e);
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
            logger.info("voteForDeleteBlackListNode txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("NodeManagerTest voteForDeleteBlackListNode receipt:{}", receipt);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("NodeManagerTest voteForDeleteBlackListNode failed.", e);
            throw new RuntimeException(e);
        }
    }
}
