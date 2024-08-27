package com.thanos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Set;

import com.thanos.web3j.channel.client.TransactionSucCallback;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.Request;
import com.thanos.web3j.protocol.core.methods.request.Transaction;
import com.thanos.web3j.protocol.core.methods.response.EthSendTransaction;

/**
 * TransactionManager implementation for using an Ethereum node to transact.
 *
 * <p><b>Note</b>: accounts must be unlocked on the node for transactions to be successful.
 */
public class ClientTransactionManager extends TransactionManager {

    private final Web3j web3j;
    private final String fromAddress;

    public ClientTransactionManager(
            Web3j web3j, String fromAddress) {
        super(web3j);
        this.web3j = web3j;
        this.fromAddress = fromAddress;
    }

    public ClientTransactionManager(
            Web3j web3j, String fromAddress, int attempts, int sleepDuration) {
        super(web3j, attempts, sleepDuration);
        this.web3j = web3j;
        this.fromAddress = fromAddress;
    }

    @Override
    public EthSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value,  Set<String> executeStates, Long futureEventNumber)
            throws IOException {

        Transaction transaction = new Transaction(
                fromAddress, null, gasPrice, gasLimit, to, value, data, null, false);

        return web3j.ethSendTransaction(transaction)
                .send();
    }

    @Override
    public EthSendTransaction sendTransaction(BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value,  Set<String> executeStates, Long futureEventNumber, TransactionSucCallback callback) throws IOException {
        Transaction transaction = new Transaction(
                fromAddress, null, gasPrice, gasLimit, to, value, data, null, false);

        Request<?, EthSendTransaction> request = web3j.ethSendTransaction(transaction);
        request.setNeedTransCallback(true);
        request.setTransactionSucCallback(callback);
        return request.send();
    }

    @Override
    public String getFromAddress() {
        return fromAddress;
    }
}
