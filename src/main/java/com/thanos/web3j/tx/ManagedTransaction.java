package com.thanos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Set;

import com.thanos.web3j.channel.client.TransactionSucCallback;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.response.EthGasPrice;
import com.thanos.web3j.protocol.exceptions.TransactionTimeoutException;


/**
 * Generic transaction manager.
 */
public abstract class ManagedTransaction {

    // https://www.reddit.com/r/ethereum/comments/5g8ia6/attention_miners_we_recommend_raising_gas_limit/
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);

    protected Web3j web3j;

    protected TransactionManager transactionManager;

    protected ManagedTransaction(Web3j web3j, TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.web3j = web3j;
    }

    public BigInteger getGasPrice() throws IOException {
        EthGasPrice ethGasPrice = web3j.ethGasPrice().send();

        return ethGasPrice.getGasPrice();
    }

    protected ThanosTransactionReceipt send(
            String to, String data, BigInteger value, BigInteger gasPrice, BigInteger gasLimit, Set<String> executeStates, Long futureEventNumber)
            throws InterruptedException, IOException, TransactionTimeoutException {
        return transactionManager.executeTransaction(gasPrice, gasLimit, to, data, value, executeStates,futureEventNumber);
    }

    protected void send(
            String to, String data, BigInteger value, BigInteger gasPrice, BigInteger gasLimit, Set<String> executeStates, Long futureEventNumber, TransactionSucCallback callback)
            throws InterruptedException, IOException, TransactionTimeoutException {
        transactionManager.executeTransaction(gasPrice, gasLimit, to, data, value, executeStates, futureEventNumber, callback);
    }

    protected String sendOnly(
            String to, String data, BigInteger value, BigInteger gasPrice, BigInteger gasLimit)
            throws InterruptedException, IOException, TransactionTimeoutException {

        return transactionManager.executeTransactionOnly(
                gasPrice, gasLimit, to, data, value);
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }
}
