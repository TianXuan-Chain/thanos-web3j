//package com.thanos.web3j.tx;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.Optional;
//import java.util.Set;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//
//import com.thanos.web3j.crypto.Credentials;
//import com.thanos.web3j.model.ThanosTransactionReceipt;
//import com.thanos.web3j.protocol.Web3j;
//import com.thanos.web3j.protocol.exceptions.TransactionTimeoutException;
//import com.thanos.web3j.utils.Async;
//import com.thanos.web3j.utils.Convert;
//import com.thanos.web3j.utils.Numeric;
//
///**
// * Class for performing Ether transactions on the Ethereum blockchain.
// */
//public class Transfer extends ManagedTransaction {
//
//    // This is the cost to send Ether between parties
//    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(21000);
//
//    public Transfer(Web3j web3j, TransactionManager transactionManager) {
//        super(web3j, transactionManager);
//    }
//
//    /**
//     * Given the duration required to execute a transaction, asyncronous execution is strongly
//     *
//     * @param toAddress destination address
//     * @param value amount to send
//     * @param unit of specified send
//     *
//     * @return {@link Optional} containing our transaction receipt
//     * @throws ExecutionException if the computation threw an
//     *                            exception
//     * @throws InterruptedException if the current thread was interrupted
//     *                              while waiting
//     * @throws TransactionTimeoutException if the transaction was not mined while waiting
//     */
//    private ThanosTransactionReceipt send(String toAddress, BigDecimal value, Convert.Unit unit, Set<String> executeStates)
//            throws IOException, InterruptedException,
//            TransactionTimeoutException {
//
//        BigInteger gasPrice = getGasPrice();
//        return send(toAddress, value, unit, gasPrice, GAS_LIMIT, executeStates);
//    }
//
//    private ThanosTransactionReceipt send(
//            String toAddress, BigDecimal value, Convert.Unit unit, BigInteger gasPrice,
//            BigInteger gasLimit,  Set<String> executeStates) throws IOException, InterruptedException,
//            TransactionTimeoutException {
//
//        BigDecimal weiValue = Convert.toWei(value, unit);
//        if (!Numeric.isIntegerValue(weiValue)) {
//            throw new UnsupportedOperationException(
//                    "Non decimal Wei value provided: " + value + " " + unit.toString()
//                            + " = " + weiValue + " Wei");
//        }
//
//        return send(toAddress, "", weiValue.toBigIntegerExact(), gasPrice, gasLimit, executeStates, );
//    }
//
//    public static ThanosTransactionReceipt sendFunds(
//            Web3j web3j, Credentials credentials,
//            String toAddress, BigDecimal value, Convert.Unit unit,  Set<String> executeStates) throws InterruptedException,
//            IOException, TransactionTimeoutException {
//
//        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
//
//        return new Transfer(web3j, transactionManager).send(toAddress, value, unit, executeStates);
//    }
//
//    /**
//     * Execute the provided function as a transaction asynchronously. This is intended for one-off
//     * fund transfers. For multiple, create an instance.
//     *
//     * @param toAddress destination address
//     * @param value amount to send
//     * @param unit of specified send
//     *
//     * @return {@link Future} containing executing transaction
//     */
//    public Future<ThanosTransactionReceipt> sendFundsAsync(
//            String toAddress, BigDecimal value, Convert.Unit unit,  Set<String> executeStates) {
//        return Async.run(() -> send(toAddress, value, unit, executeStates));
//    }
//
//    public Future<ThanosTransactionReceipt> sendFundsAsync(
//            String toAddress, BigDecimal value, Convert.Unit unit, BigInteger gasPrice,
//            BigInteger gasLimit,  Set<String> executeStates) {
//        return Async.run(() -> send(toAddress, value, unit, gasPrice, gasLimit, executeStates));
//    }
//
//    public static Future<ThanosTransactionReceipt> sendFundsAsync(
//            Web3j web3j, Credentials credentials,
//            String toAddress, BigDecimal value, Convert.Unit unit,  Set<String> executeStates) throws InterruptedException,
//            ExecutionException, TransactionTimeoutException {
//
//        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
//
//        return new Transfer(web3j, transactionManager)
//                .sendFundsAsync(toAddress, value, unit, executeStates);
//    }
//}
