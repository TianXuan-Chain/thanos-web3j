package com.thanos.web3j.eventgen;


import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.model.ThanosGlobalNodeEventReceipt;
import com.thanos.web3j.model.event.CandidateEventConstant;
import com.thanos.web3j.model.event.GlobalEventCommand;
import com.thanos.web3j.model.event.ProcessOperationsStaffCandidateEvent;
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

public class OpStaffManager {
    protected static final Logger logger = LoggerFactory.getLogger(OpStaffManager.class);

    //新增运维
    public static boolean addOpStaff(Web3j web3j, SecureKey sender, byte[] candidateAddr) {
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
                    candidateAddr
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
            logger.debug("addOpStaff txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.debug("OpStaffManager addOpStaff receipt:{}", receipt);
                return true;

            }
            return false;

        } catch (Exception e) {
            logger.error("OpStaffManager addOpStaff failed.", e);
            throw new RuntimeException(e);
        }
    }


    //删除运维节点
    public static boolean deleteOpStaff(Web3j web3j, SecureKey sender,byte[] candidateAddr) {
        try {
            Credentials credentials = Credentials.create(sender);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            Long consensusNumber = transactionManager.getThanosLatestConsensusNumber();
            //随机nonce
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);

            ProcessOperationsStaffCandidateEvent event = new ProcessOperationsStaffCandidateEvent(
                    CandidateEventConstant.CANCEL_PROCESS,
                    candidateAddr
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
            logger.debug("deleteOpStaff txHash:{}", str);


            //查询节点准入结果
            ThanosGlobalNodeEventReceipt receipt = transactionManager.waitForGlobalNodeEventReceipt(str);
            if (receipt != null) {
                logger.debug("NodeManager deleteOpStaff receipt:{}", receipt);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("NodeManager deleteOpStaff failed.", e);
            throw new RuntimeException(e);
        }
    }

}
