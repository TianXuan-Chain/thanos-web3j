package com.thanos.web3j.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

import com.google.gson.Gson;
import com.thanos.web3j.channel.client.TransactionSucCallback;
import com.thanos.web3j.crypto.sm2.util.encoders.Hex;
import com.thanos.web3j.model.ThanosBlock;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.model.state.EpochState;
import com.thanos.web3j.protocol.core.Request;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.crypto.TransactionEncoder;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.DefaultBlockParameterName;
import com.thanos.web3j.protocol.core.methods.request.RawTransaction;
import com.thanos.web3j.protocol.core.methods.response.*;
import com.thanos.web3j.utils.Numeric;
import com.thanos.web3j.utils.TypeConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TransactionManager implementation using Ethereum wallet file to create and sign transactions
 * locally.
 *
 * <p>This transaction manager provides support for specifying the chain id for transactions as per
 * <a href="https://github.com/ethereum/EIPs/issues/155">EIP155</a>.
 */
public class RawTransactionManager extends TransactionManager {
    static Logger logger = LoggerFactory.getLogger(RawTransactionManager.class);
    private final Web3j web3j;
    final Credentials credentials;
    //default event number for thanosEthCall
    private static Long DEFAULT_EVENT_NUMBER = 10L;
    private final byte chainId;

    public RawTransactionManager(Web3j web3j, Credentials credentials, byte chainId) {
        super(web3j);
        this.web3j = web3j;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(
            Web3j web3j, Credentials credentials, byte chainId, int attempts, int sleepDuration) {
        super(web3j, attempts, sleepDuration);
        this.web3j = web3j;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(Web3j web3j, Credentials credentials) {
        this(web3j, credentials, ChainId.NONE);
    }

    public RawTransactionManager(
            Web3j web3j, Credentials credentials, int attempts, int sleepDuration) {
        this(web3j, credentials, ChainId.NONE, attempts, sleepDuration);
    }

    BigInteger getNonce() throws IOException {
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.PENDING).send();

        return ethGetTransactionCount.getTransactionCount();
    }

    @Override
    public EthSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value, Set<String> executeStates, Long futureEventNumber) throws IOException {

//        Random r = new SecureRandom();
//        BigInteger randomid = new BigInteger(250, r);
        UUID uuid = UUID.randomUUID();
        BigInteger randomid = new BigInteger(uuid.toString().replaceAll("-", ""), 16);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                randomid,
                gasPrice,
                gasLimit,
                to,
                value,
                data,
                executeStates, futureEventNumber);

        return signAndSend(rawTransaction);
    }

    @Override
    public EthSendTransaction sendTransaction(BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value, Set<String> executeStates, Long futureEventNumber, TransactionSucCallback callback) throws IOException {
//        Random r = new SecureRandom();
//        BigInteger randomid = new BigInteger(250, r);
        UUID uuid = UUID.randomUUID();
        BigInteger randomid = new BigInteger(uuid.toString().replaceAll("-", ""), 16);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                randomid,
                gasPrice,
                gasLimit,
                to,
                value,
                data,
                executeStates, futureEventNumber);

        return signAndSend(rawTransaction, callback);
    }

    public EthSendTransaction signAndSend(RawTransaction rawTransaction)
            throws IOException {

        rawTransaction.setPublicKey(credentials.getSecureKey().getPubKey());

        byte[] signedMessage;

        if (chainId > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        }

        String hexValue = Numeric.toHexString(signedMessage);

        return web3j.thanosSendEthRawTransaction(hexValue).send();
    }

    public EthSendTransaction sendGlobalNodeEvent(ThanosGlobalNodeEvent thanosGlobalNodeEvent)
            throws IOException {

        byte[] signedMessage = TransactionEncoder.signGloabalNodeEvent(thanosGlobalNodeEvent, credentials);

        String hexValue = Numeric.toHexString(signedMessage);

        return web3j.thanosSendGlobalNodeEvent(hexValue).send();
    }

    public EthSendTransaction signAndSend(RawTransaction rawTransaction, TransactionSucCallback callback)
            throws IOException {

        byte[] signedMessage;

        if (chainId > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        }

        String hexValue = Numeric.toHexString(signedMessage);
        Request<?, EthSendTransaction> request = web3j.thanosSendEthRawTransaction(hexValue);
        request.setNeedTransCallback(true);
        request.setTransactionSucCallback(callback);
        return request.send();
    }

    @Override
    public String getFromAddress() {
        return credentials.getAddress();
    }


    private final static Gson GSON = new Gson();

    public EthSendTransactionList SendTransactionList(List<RawTransaction> rawTransactions) throws IOException {
        List<byte[]> rawStringList = new ArrayList<>();
        for (RawTransaction rt : rawTransactions) {
            if (rt == null) {
                continue;
            }
            byte[] signedMessage = TransactionEncoder.signMessage(rt, rt.getCredentials());
//            String hexValue = Numeric.toHexString(signedMessage);
            rawStringList.add(signedMessage);
        }
//        String s = GSON.toJson(rawStringList);
        Request<?, EthSendTransactionList> request = web3j.thanosSendEthRawTransactionList(rawStringList);
        request.setNeedTransCallback(true);
        return request.send();
    }

    public EthSendTransactionList SendTransactionListWithoutSign(List<byte[]> rawTransactions) throws IOException {
//        String s = GSON.toJson(rawTransactions);
        Request<?, EthSendTransactionList> request = web3j.thanosSendEthRawTransactionList(rawTransactions);
        request.setNeedTransCallback(true);
        return request.send();
    }

    public EthSendTransaction SendTransactionAfterSign(String rawTransaction) throws IOException {
        Request<?, EthSendTransaction> request = web3j.thanosSendEthRawTransaction(rawTransaction);
        request.setNeedTransCallback(true);
        return request.send();
    }

    // ================================= 3.0 接口 ==================================

    public ThanosTransactionReceipt thanosCall(BigInteger gasPrice, BigInteger gasLimit, String to,
                                               String data, BigInteger value, Set<String> executeStates) throws IOException {
//        Random r = new SecureRandom();
//        BigInteger randomid = new BigInteger(250, r);
        UUID uuid = UUID.randomUUID();
        BigInteger randomid = new BigInteger(uuid.toString().replaceAll("-", ""), 16);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                randomid,
                gasPrice,
                gasLimit,
                to,
                value,
                data, executeStates, DEFAULT_EVENT_NUMBER);

        rawTransaction.setPublicKey(credentials.getSecureKey().getPubKey());

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);

        String hexValue = Numeric.toHexString(signedMessage);

        EthGetString result = web3j.thanosEthCall(hexValue).send();

        String valueStr = result.getString();

        byte[] receipt = Hex.decode(valueStr);
        ThanosTransactionReceipt transactionReceipt = new ThanosTransactionReceipt();
        transactionReceipt.rlpDecoded(receipt);
        return transactionReceipt;
    }

    public Long getThanosLatestBlockNumber() throws IOException {
        EthGetNumber number = web3j.thanosGetLatestBeExecutedNum().send();
        return number.getNumber();
    }

    public Long getThanosLatestConsensusNumber() throws IOException {
        EthGetNumber number = web3j.thanosGetLatestConsensusNumber().send();
        return number.getNumber();
    }

    public Long getThanosCurrentCommitRound() throws IOException {
        EthGetNumber number = web3j.thanosGetCurrentCommitRound().send();
        return number.getNumber();
    }

    public ThanosBlock getThanosBlockByNumber(String blockNumber) throws IOException {
        EthGetString result = web3j.thanosGetBlockByNumber(blockNumber).send();
        String blockStr = result.getString();
        if (StringUtils.isBlank(blockStr)) {
            return null;
        }
        byte[] data = Hex.decode(blockStr);
        ThanosBlock block = new ThanosBlock(data);
        return block;
    }

    public ThanosTransactionReceipt getThanosTransactionByHash(String transactionHash) throws IOException {
        EthGetString result = web3j.thanosGetEthTransactionByHash(transactionHash).send();
        String value = result.getString();
        ThanosTransactionReceipt receipt = new ThanosTransactionReceipt();
        receipt.rlpDecoded(TypeConverter.hexToByteArray(value));
        return receipt;
    }

    public ThanosTransactionReceipt getThanosTransactionByHashByChain(String transactionHash) throws IOException {
        EthGetString result = web3j.thanosGetEthTransactionByHashByChain(transactionHash).send();
        String value = result.getString();
        ThanosTransactionReceipt receipt = new ThanosTransactionReceipt();
        receipt.rlpDecoded(TypeConverter.hexToByteArray(value));
        return receipt;
    }

    public List<ThanosTransactionReceipt> getThanosTransactionsByHashes(List<String> transactionHashs) throws IOException {
        String str = GSON.toJson(transactionHashs);
        EthGetStringList result = web3j.thanosGetEthTransactionsByHashes(str).send();
        List<String> values = result.getString();
        List<ThanosTransactionReceipt> receipts = new ArrayList<>();
        for (String s : values) {
            ThanosTransactionReceipt receipt = null;
            if (StringUtils.isNotBlank(s)) {
                receipt = new ThanosTransactionReceipt();
                receipt.rlpDecoded(org.spongycastle.util.encoders.Hex.decode(s));
            }
            receipts.add(receipt);
        }
        return receipts;
    }

    public ThanosGlobalNodeEvent getThanosGlobalEventReceiptByHash(String transactionHash) throws IOException {
        if (StringUtils.isNotBlank(transactionHash) && transactionHash.startsWith("0x")) {
            transactionHash = transactionHash.substring(2);
        }
        EthGetString result = web3j.thanosGetGlobalNodeEventByHash(transactionHash).send();
        String value = result.getString();
        if (StringUtils.isBlank(value)) {
            return null;
        }
        ThanosGlobalNodeEvent event = new ThanosGlobalNodeEvent(Hex.decode(value));
        return event;
    }

    public EpochState getThanosEpochState() throws IOException {
        EthGetString result = web3j.thanosGetEpochState().send();
        String value = result.getString();
        if (StringUtils.isBlank(value)) {
            return null;
        }
        EpochState event = new EpochState(Hex.decode(value));
        return event;
    }
}
