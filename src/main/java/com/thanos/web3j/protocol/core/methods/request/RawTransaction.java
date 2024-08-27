package com.thanos.web3j.protocol.core.methods.request;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.utils.Numeric;


/**
 * Transaction class used for signing transactions locally.<br>
 * For the specification, refer to p4 of the <a href="http://gavwood.com/paper.pdf">
 * yellow paper</a>.
 */
public class RawTransaction {

    private BigInteger nonce;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private String to;
    private BigInteger value;
    private String data;
    protected Set<String> executeStates;

    protected Long futureEventNumber;

    private byte[] publicKey;

    private Credentials credentials;

    private RawTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
                           BigInteger value, String data, Set<String> executeStates, Long futureEventNumber) {
        this.nonce = nonce;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.to = to;
        this.value = value;

        if (data != null) {
            this.data = Numeric.cleanHexPrefix(data);
        }
        if (executeStates == null) {
            executeStates = new HashSet<>();
        }
        this.executeStates = executeStates;
        this.futureEventNumber = futureEventNumber;
    }

    public static RawTransaction createContractTransaction(
            BigInteger randomid, BigInteger gasPrice, BigInteger gasLimit, BigInteger value, Set<String> executeStates, Long futureEventNumber) {

        return new RawTransaction(randomid, gasPrice, gasLimit, "", value, "", executeStates, futureEventNumber);
    }

    public static RawTransaction createEtherTransaction(
            BigInteger randomid, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value, Set<String> executeStates, Long futureEventNumber) {

        return new RawTransaction(randomid, gasPrice, gasLimit, to, value, "", executeStates, futureEventNumber);

    }

    public static RawTransaction createTransaction(
            BigInteger randomid, BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, Set<String> executeStates, Long futureEventNumber) {
        return createTransaction(randomid, gasPrice, gasLimit, to, BigInteger.ZERO, data, executeStates, futureEventNumber);
    }

    public static RawTransaction createTransaction(
            BigInteger randomid, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value, String data, Set<String> executeStates, Long futureEventNumber) {

        return new RawTransaction(randomid, gasPrice, gasLimit, to, value, data, executeStates, futureEventNumber);
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public String getTo() {
        return to;
    }

    public BigInteger getValue() {
        return value;
    }

    public String getData() {
        return data;
    }

    public Set<String> getExecuteStates() {
        return executeStates;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Long getFutureEventNumber() {
        return futureEventNumber;
    }

    public void setFutureEventNumber(Long futureEventNumber) {
        this.futureEventNumber = futureEventNumber;
    }

}
