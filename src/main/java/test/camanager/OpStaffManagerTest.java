package test.camanager;


import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.model.ThanosGlobalNodeEventReceipt;
import com.thanos.web3j.model.event.CandidateEventConstant;
import com.thanos.web3j.model.event.GlobalEventCommand;
import com.thanos.web3j.model.event.ProcessOperationsStaffCandidateEvent;
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

public class OpStaffManagerTest extends ManageTestBase {

    @Test
    public void testDeleteOpstaff0Success(){
        Web3j web3j = web3Manager.getWeb3jRandomly();
        deleteOpStaff(web3j, committee0, opStaff0);
        EpochState epochState = getEpochState();
        logger.info("opStaffAddrs after delete is:{}", epochState.getGlobalEventState().getOperationsStaffAddrs());
    }

    @Test
    public void testAddOpstaff0Success(){
        Web3j web3j = web3Manager.getWeb3jRandomly();
        addOpStaff(web3j, committee0, opStaff0);
        EpochState epochState = getEpochState();
        logger.info("opStaffAddrs after add is:{}", epochState.getGlobalEventState().getOperationsStaffAddrs());
    }

    public static void main(String[] args) {
        //网络通道
        Web3j web3j = web3Manager.getWeb3jRandomly();




        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(getCurrentCommitRound());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        SecureKey candidate = SecureKey.fromPrivate(Hex.decode("010001d9694bc7257b6e11d5a6d3076b28fd9011b46fcc036fbfddf2d6f87866673480"));

        addOpStaff(web3j, committee0, candidate);
        EpochState epochState = getEpochState();
        logger.info("opStaffAddrs after add is:{}", epochState.getGlobalEventState().getOperationsStaffAddrs());
        deleteOpStaff(web3j, committee0, candidate);
        EpochState epochState2 = getEpochState();
        logger.info("opStaffAddrs after delete is:{}", epochState2.getGlobalEventState().getOperationsStaffAddrs());
    }

    //新增运维
    public static boolean addOpStaff(Web3j web3j, SecureKey sender, SecureKey candidate) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            byte[] proposalId = randomid.toByteArray();

            ProcessOperationsStaffCandidateEvent event = new ProcessOperationsStaffCandidateEvent(
                    CandidateEventConstant.REGISTER_PROCESS,
                    candidate.getAddress()
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.PROCESS_OPERATIONS_STAFF.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("addOpStaff txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("OpStaffManagerTest addOpStaff receipt:{}", receipt);
                return true;

            }
            return false;

        } catch (Exception e) {
            logger.error("OpStaffManagerTest addOpStaff failed.", e);
            throw new RuntimeException(e);
        }
    }


    //删除运维节点
    public static boolean deleteOpStaff(Web3j web3j, SecureKey sender, SecureKey candidate) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);

            ProcessOperationsStaffCandidateEvent event = new ProcessOperationsStaffCandidateEvent(
                    CandidateEventConstant.CANCEL_PROCESS,
                    candidate.getAddress()
            );
            //节点准入交易
            ThanosGlobalNodeEvent globalNodeEvent = new ThanosGlobalNodeEvent(
                    sender.getPubKey(),
                    randomid.toByteArray(),
                    consensusNumber + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM,
                    GlobalEventCommand.PROCESS_OPERATIONS_STAFF.getCode(),
                    event.getEncoded(),
                    null
            );


            //发送交易
            EthSendTransaction transaction = transactionManager.sendGlobalNodeEvent(globalNodeEvent);
            String str = transaction.getTransactionHash();
            logger.info("deleteOpStaff txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.info("NodeManagerTest deleteOpStaff receipt:{}", receipt);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("NodeManagerTest deleteOpStaff failed.", e);
            throw new RuntimeException(e);
        }
    }

}
