package com.thanos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import com.thanos.web3j.channel.client.TransactionSucCallback;
import com.thanos.web3j.crypto.sm2.util.encoders.Hex;
import com.thanos.web3j.model.ThanosBlock;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.model.ThanosGlobalNodeEventReceipt;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.model.state.EpochState;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.response.EthGetString;
import com.thanos.web3j.protocol.core.methods.response.EthSendTransaction;
import com.thanos.web3j.protocol.exceptions.TransactionTimeoutException;
import com.thanos.web3j.utils.AttemptsConf;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Transaction manager abstraction for executing transactions with Ethereum client via
 * various mechanisms.
 */
public abstract class TransactionManager {
    private static final Logger logger = LoggerFactory.getLogger("test");

    private static final int SLEEP_DURATION = AttemptsConf.sleepDuration;
    private static final int ATTEMPTS = AttemptsConf.attempts;

    private final int sleepDuration;
    private final int attempts;

    private final Web3j web3j;

    protected TransactionManager(Web3j web3j) {
        this.web3j = web3j;
        this.attempts = ATTEMPTS;
        this.sleepDuration = SLEEP_DURATION;
    }

    protected TransactionManager(Web3j web3j, int attempts, int sleepDuration) {
        this.web3j = web3j;
        this.attempts = attempts;
        this.sleepDuration = sleepDuration;
    }

    public ThanosTransactionReceipt executeTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value, Set<String> executeStates, Long futureEventNumber)
            throws InterruptedException, IOException, TransactionTimeoutException {

        EthSendTransaction ethSendTransaction = sendTransaction(
                gasPrice, gasLimit, to, data, value, executeStates, futureEventNumber);
        return processResponse(ethSendTransaction);
    }

    void executeTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value, Set<String> executeStates, Long futureEventNumber, TransactionSucCallback callback)
            throws InterruptedException, IOException, TransactionTimeoutException {

        sendTransaction(gasPrice, gasLimit, to, data, value, executeStates, futureEventNumber, callback);
    }

    String executeTransactionOnly(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value)
            throws InterruptedException, IOException, TransactionTimeoutException {

        //TODO 待修改
        EthSendTransaction ethSendTransaction = sendTransaction(
                gasPrice, gasLimit, to, data, value, null, null);
        return processResponseOnly(ethSendTransaction);
    }

    public abstract EthSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value, Set<String> executeStates, Long futureEventNumber)
            throws IOException;

    public abstract EthSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value, Set<String> executeStates, Long futureEventNumber, TransactionSucCallback callback)
            throws IOException;

    public abstract String getFromAddress();

    public ThanosTransactionReceipt processResponse(EthSendTransaction transactionResponse)
            throws InterruptedException, IOException, TransactionTimeoutException {
        if (transactionResponse.hasError()) {
            throw new RuntimeException("Error processing transaction request: "
                    + transactionResponse.getError().getMessage());
        }

        String transactionHash = transactionResponse.getTransactionHash();

        return waitForTransactionReceipt(transactionHash);
    }

    private String processResponseOnly(EthSendTransaction transactionResponse)
            throws InterruptedException, IOException, TransactionTimeoutException {
        if (transactionResponse.hasError()) {
            throw new RuntimeException("Error processing transaction request: "
                    + transactionResponse.getError().getMessage());
        }

        return transactionResponse.getTransactionHash();
    }

    public ThanosTransactionReceipt waitForTransactionReceipt(
            String transactionHash)
            throws InterruptedException, IOException, TransactionTimeoutException {

        int sumTime = 0;

        Thread.sleep((long) sleepDuration * 2);
        sumTime += sleepDuration * 2;
        ThanosTransactionReceipt receiptOptional = sendTransactionReceiptRequest(transactionHash, false);
        // read gateway cache
        for (int i = 0; i < attempts; i++) {

            if (receiptOptional == null) {
                Thread.sleep((long) sleepDuration);
                sumTime += sleepDuration;
                receiptOptional = sendTransactionReceiptRequest(transactionHash, false);
            } else {
                return receiptOptional;
            }
        }

        // read chain db
        for (int i = 0; i < attempts; i++) {

            if (receiptOptional == null) {
                Thread.sleep((long) sleepDuration * 4);
                sumTime += sleepDuration * 4;
                logger.debug("getTransactionReceipt from chain db. txHash:{}, attempts:{}.", transactionHash, attempts);
                receiptOptional = sendTransactionReceiptRequest(transactionHash, true);
            } else {
                return receiptOptional;
            }
        }

        throw new TransactionTimeoutException("Transaction receipt was not generated after "
                + ((sumTime) / 1000
                + " seconds for transaction: " + transactionHash));
    }

    private ThanosTransactionReceipt sendTransactionReceiptRequest(
            String transactionHash, boolean fromChain) throws IOException {
        EthGetString transactionReceipt;
        try {
            if (fromChain) {
                transactionReceipt = web3j.thanosGetEthTransactionByHashByChain(transactionHash).send();
            } else {
                transactionReceipt = web3j.thanosGetEthTransactionByHash(transactionHash).send();
            }
            if (transactionReceipt != null && StringUtils.isNotBlank(transactionReceipt.getString())) {
                ThanosTransactionReceipt receipt = new ThanosTransactionReceipt();
                receipt.rlpDecoded(Hex.decode(transactionReceipt.getString()));
                return receipt;
            }
        } catch (Throwable t) {
            logger.warn("sendTransactionReceiptRequest failed. transactionHash:{}. t:{}.", transactionHash, t.getMessage());
            throw new IOException("sendTransactionReceiptRequest failed.", t);
        }
        return null;
    }


    public ThanosGlobalNodeEventReceipt waitForGlobalNodeEventReceipt(
            String transactionHash)
            throws InterruptedException, IOException, TransactionTimeoutException {

        ThanosGlobalNodeEventReceipt receiptOptional = null;
        int sumTime = 0;
        // read chain db
        for (int i = 0; i < attempts; i++) {
            //sleep 1 s
            Thread.sleep((long) sleepDuration * 4);
            sumTime += sleepDuration * 4;
            if (receiptOptional == null) {
                receiptOptional = sendGlobalNodeEventReceiptRequest(transactionHash);
                logger.debug("getGlobalNodeEventReceipt from chain db. txHash:{}, attempts:{}.", transactionHash, attempts);
                Thread.sleep((long) sleepDuration * 8);
                sumTime += sleepDuration * 8;
            } else {
                return receiptOptional;
            }
        }

        throw new TransactionTimeoutException("Transaction receipt was not generated after "
                + ((sumTime) / 1000
                + " seconds for globalNodeEvent: " + transactionHash));
    }

    private ThanosGlobalNodeEventReceipt sendGlobalNodeEventReceiptRequest(
            String transactionHash) throws IOException {
        EthGetString globalNodeEventReceipt;
        try {
//            if (fromChain) {
//                globalNodeEventReceipt = web3j.thanosGetGlobalNodeEventByHashByChain(transactionHash).send();
//            } else {
//                globalNodeEventReceipt = web3j.thanosGetGlobalNodeEventByHash(transactionHash).send();
//            }

            globalNodeEventReceipt = web3j.thanosGetGlobalNodeEventReceiptByHash(transactionHash).send();

            if (globalNodeEventReceipt != null && StringUtils.isNotBlank(globalNodeEventReceipt.getString())) {
                ThanosGlobalNodeEventReceipt receipt = new ThanosGlobalNodeEventReceipt(Hex.decode(globalNodeEventReceipt.getString()));
                return receipt;
            }
        } catch (Throwable t) {
            logger.warn("sendGlobalNodeEventReceiptRequest failed. transactionHash:{}. t:{}.", transactionHash, t.getMessage());
            throw new IOException("sendGlobalNodeEventReceiptRequest failed.", t);
        }
        return null;
    }


    public ThanosTransactionReceipt thanosCall(BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value, Set<String> executeStates) throws IOException {
        return null;
    }

    public Long getThanosLatestBlockNumber() throws IOException {
        return null;
    }

    public Long getThanosLatestConsensusNumber() throws IOException {
        return null;
    }

    public ThanosBlock getThanosBlockByNumber(String blockNumber) throws IOException {
        return null;
    }

    public ThanosTransactionReceipt getThanosTransactionByHash(String transactionHash) throws IOException {
        return null;
    }

    public List<ThanosTransactionReceipt> getThanosTransactionsByHashes(List<String> transactionHashs) throws IOException {
        return null;
    }

    public ThanosGlobalNodeEvent getThanosGlobalEventReceiptByHash(String transactionHash) throws IOException {
        return null;
    }

    public EpochState getThanosEpochState() throws IOException {
        return null;
    }
}
