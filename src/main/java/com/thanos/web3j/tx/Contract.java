package com.thanos.web3j.tx;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.thanos.web3j.abi.datatypes.*;
import com.thanos.web3j.channel.client.TransactionSucCallback;
import com.thanos.web3j.abi.EventEncoder;
import com.thanos.web3j.abi.EventValues;
import com.thanos.web3j.abi.FunctionEncoder;
import com.thanos.web3j.abi.FunctionReturnDecoder;
import com.thanos.web3j.abi.TypeReference;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.crypto.sm2.util.encoders.Hex;
import com.thanos.web3j.model.ThanosLogInfo;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.DefaultBlockParameterName;
import com.thanos.web3j.protocol.core.methods.response.EthGetCode;
import com.thanos.web3j.protocol.core.methods.response.Log;
import com.thanos.web3j.protocol.core.methods.response.TransactionReceipt;
import com.thanos.web3j.protocol.exceptions.TransactionTimeoutException;
import com.thanos.web3j.utils.Async;
import com.thanos.web3j.utils.Numeric;


/**
 * Solidity contract type abstraction for interacting with smart contracts via native Java types.
 */
public abstract class Contract extends ManagedTransaction {

    // https://www.reddit.com/r/ethereum/comments/5g8ia6/attention_miners_we_recommend_raising_gas_limit/
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

    private String contractBinary;
    private String contractAddress;
    private String contractName;
    private boolean isInitByName = false;
    private final BigInteger gasPrice;
    private final BigInteger gasLimit;
    private ThanosTransactionReceipt transactionReceipt;

    public static Set<String> executeStatesByDeploy;

    public Set<String> executeStates;


    //update contract binary according to crypto type
    public void setContractBinary(String contractBinary) {
        this.contractBinary = contractBinary;
    }

    protected Contract(String contractBinary, String contractAddress,
                       Web3j web3j, TransactionManager transactionManager,
                       BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(web3j, transactionManager);

        this.contractBinary = contractBinary;
        if (isInitByName) {
            this.contractName = contractAddress;
        } else {
            this.contractAddress = contractAddress;
        }
        this.isInitByName = isInitByName;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
    }

    protected Contract(String contractBinary, String contractAddress,
                       Web3j web3j, TransactionManager transactionManager,
                       BigInteger gasPrice, BigInteger gasLimit) {
        super(web3j, transactionManager);
        this.isInitByName = false;
        this.contractBinary = contractBinary;
        /*if (isInitByName)
        {
            this.contractName = contractAddress;
        }
        else
        {
            this.contractAddress = contractAddress;
        }
        */
        this.contractAddress = contractAddress;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
    }

    protected Contract(String contractBinary, String contractAddress,
                       Web3j web3j, Credentials credentials,
                       BigInteger gasPrice, BigInteger gasLimit, boolean isInitByName) {
        this(contractBinary, contractAddress, web3j, new RawTransactionManager(web3j, credentials),
                gasPrice, gasLimit, isInitByName);
    }

    protected Contract(String contractBinary, String contractAddress,
                       Web3j web3j, Credentials credentials,
                       BigInteger gasPrice, BigInteger gasLimit) {
        this(contractBinary, contractAddress, web3j, new RawTransactionManager(web3j, credentials),
                gasPrice, gasLimit, false);
    }

    @Deprecated
    protected Contract(String contractAddress,
                       Web3j web3j, TransactionManager transactionManager,
                       BigInteger gasPrice, BigInteger gasLimit, boolean isInitByName) {
        this("", contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    @Deprecated
    protected Contract(String contractAddress,
                       Web3j web3j, TransactionManager transactionManager,
                       BigInteger gasPrice, BigInteger gasLimit) {
        this("", contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    @Deprecated
    protected Contract(String contractAddress,
                       Web3j web3j, Credentials credentials,
                       BigInteger gasPrice, BigInteger gasLimit, boolean isInitByName) {
        this("", contractAddress, web3j, new RawTransactionManager(web3j, credentials),
                gasPrice, gasLimit, isInitByName);
    }

    @Deprecated
    protected Contract(String contractAddress,
                       Web3j web3j, Credentials credentials,
                       BigInteger gasPrice, BigInteger gasLimit) {
        this("", contractAddress, web3j, new RawTransactionManager(web3j, credentials),
                gasPrice, gasLimit, false);
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setTransactionReceipt(ThanosTransactionReceipt transactionReceipt) {
        this.transactionReceipt = transactionReceipt;
    }

    public String getContractBinary() {
        return contractBinary;
    }

    /**
     * Check that the contract deployed at the address associated with this smart contract wrapper
     * is in fact the contract you believe it is.
     *
     * <p>This method uses the
     * <a href="https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_getcode">eth_getCode</a> method
     * to get the contract byte code and validates it against the byte code stored in this smart
     * contract wrapper.
     *
     * @return true if the contract is valid
     * @throws IOException if unable to connect to web3j node
     */
    public boolean isValid() throws IOException {
        if (isInitByName) {
            if (contractName.equals("")) {
                throw new UnsupportedOperationException(
                        "Contract binary not present, you will need to regenerate your smart "
                                + "contract wrapper with web3j v2.2.0+");
            }
        } else {
            if (contractAddress.equals("")) {
                throw new UnsupportedOperationException(
                        "Contract binary not present, you will need to regenerate your smart "
                                + "contract wrapper with web3j v2.2.0+");
            }
        }

        EthGetCode ethGetCode;
        if (isInitByName) {
            ethGetCode = web3j
                    .ethGetCodeCNS(contractName, DefaultBlockParameterName.LATEST)
                    .send();
        } else {
            ethGetCode = web3j
                    .ethGetCode(contractAddress, DefaultBlockParameterName.LATEST)
                    .send();
        }

        if (ethGetCode.hasError()) {
            return false;
        }

        String code = Numeric.cleanHexPrefix(ethGetCode.getCode());
        // There may be multiple contracts in the Solidity bytecode, hence we only check for a
        // match with a subset
        return !code.isEmpty() && contractBinary.contains(code);
    }

    /**
     * If this Contract instance was created at deployment, the TransactionReceipt associated
     * with the initial creation will be provided, e.g. via a <em>deploy</em> method. This will
     * not persist for Contracts instances constructed via a <em>load</em> method.
     *
     * @return the TransactionReceipt generated at contract deployment
     */
    public Optional<ThanosTransactionReceipt> getTransactionReceipt() {
        return Optional.ofNullable(transactionReceipt);
    }

    /**
     * Execute constant function call - i.e. a call that does not change state of the contract
     *
     * @param function to call
     * @return {@link List} of values returned by function call
     * @throws InterruptedException if async call is interrupted
     * @throws ExecutionException   if async call throws an exception
     */
    private List<Type> executeCall(
            Function function) throws InterruptedException, ExecutionException {
//        String encodedFunction = FunctionEncoder.encode(function);

//        com.thanos.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
//                    Transaction.createEthCallTransaction(
//                            transactionManager.getFromAddress(), isInitByName?contractName:contractAddress, encodedFunction, callType,isInitByName),
//                    DefaultBlockParameterName.LATEST)
//                    .sendAsync().get();
//
//        String value = ethCall.getValue();

        String value = null;
        try {
//            String test = "test";
//            Set<String> testSet = new HashSet<>();
//            testSet.add(test);
            ThanosTransactionReceipt receipt = transactionManager.thanosCall(BigInteger.valueOf(30000000), BigInteger.valueOf(30000000), contractAddress, FunctionEncoder.encode(function), gasPrice, null);
            value = Hex.toHexString(receipt.getExecutionResult());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FunctionReturnDecoder.decode(value, function.getOutputParameters());
    }

    protected <T extends Type> CompletableFuture<T> executeCallSingleValueReturnAsync(
            Function function) {
        return Async.run(() -> executeCallSingleValueReturn(function));
    }

    protected CompletableFuture<List<Type>> executeCallMultipleValueReturnAsync(
            Function function) {
        return Async.run(() -> executeCallMultipleValueReturn(function));
    }

    protected <T extends Type> T executeCallSingleValueReturn(
            Function function) throws InterruptedException, ExecutionException {
        List<Type> values = executeCall(function);
        if (!values.isEmpty()) {
            return (T) values.get(0);
        } else {
            return null;
        }
    }

    protected List<Type> executeCallMultipleValueReturn(
            Function function) throws InterruptedException, ExecutionException {
        return executeCall(function);
    }

    public ThanosTransactionReceipt executeTransaction(Function function, Set<String> executeStates) throws InterruptedException,
            IOException, TransactionTimeoutException {
        return executeTransaction(FunctionEncoder.encode(function), executeStates, BigInteger.ZERO);
    }

    public String executeTransactionOnly(
            String data, BigInteger weiValue)
            throws InterruptedException, IOException, TransactionTimeoutException {

        return sendOnly(contractAddress, data, weiValue, gasPrice, gasLimit);
    }

    /**
     * Given the duration required to execute a transaction, asyncronous execution is strongly
     * recommended via {@link Contract#executeTransactionAsync}.
     *
     * @param data     to send in transaction
     * @param weiValue in Wei to send in transaction
     * @return {@link Optional} containing our transaction receipt
     * @throws InterruptedException        if the current thread was interrupted
     *                                     while waiting
     * @throws IOException                 if the call to the node fails
     * @throws TransactionTimeoutException if the transaction was not mined while waiting
     */
    protected ThanosTransactionReceipt executeTransaction(
            String data, Set<String> executeStates, BigInteger weiValue)
            throws InterruptedException, IOException, TransactionTimeoutException {
        Long futureEventNumber = transactionManager.getThanosLatestConsensusNumber() + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM;
        if (isInitByName) {
            return send(contractName, data, weiValue, gasPrice, gasLimit, executeStates, futureEventNumber);
        } else {
            return send(contractAddress, data, weiValue, gasPrice, gasLimit, executeStates, futureEventNumber);
        }
    }

    /**
     * Execute the provided function as a transaction asynchronously.
     *
     * @param function to transact with
     * @return {@link Future} containing executing transaction
     */
    protected CompletableFuture<ThanosTransactionReceipt> executeTransactionAsync(Function function) {
        return executeTransactionAsync(function, new HashSet<>());
    }

    /**
     * Execute the provided function as a transaction asynchronously.
     *
     * @param function to transact with
     * @return {@link Future} containing executing transaction
     */
    protected CompletableFuture<ThanosTransactionReceipt> executeTransactionAsync(Function function, Set<String> executeStates) {
        return Async.run(() -> executeTransaction(function, executeStates));
    }

    /**
     * @param function
     * @param callback to get transaction receipt message.
     * @return void
     * @author tabsu
     */
    protected void executeTransactionAsync(Function function, TransactionSucCallback callback) {
        try {
            Long futureEventNumber = transactionManager.getThanosLatestConsensusNumber() + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM;
            if (isInitByName) {
                send(contractName, FunctionEncoder.encode(function), BigInteger.ZERO, gasPrice, gasLimit, executeStates, futureEventNumber, callback);
            } else {
                send(contractAddress, FunctionEncoder.encode(function), BigInteger.ZERO, gasPrice, gasLimit, executeStates, futureEventNumber, callback);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransactionTimeoutException e) {
            e.printStackTrace();
        }
    }


    /**
     * Execute the provided payable function as a transaction asynchronously.
     *
     * @param function to transact with
     * @param weiValue in Wei to send in transaction
     * @return {@link Future} containing executing transaction
     */
    protected CompletableFuture<ThanosTransactionReceipt> executeTransactionAsync(
            Function function, BigInteger weiValue) {
        return Async.run(() -> executeTransaction(FunctionEncoder.encode(function), new HashSet(), weiValue));
    }

    /**
     * @desc 为了能在sol实例化出来的java类里面用到static的方法，而不需要实例化这个java类(load方法，用了name service，屏蔽了自身address)
     */
    protected static EventValues extractEventParameters(
            Event event, ThanosLogInfo log) {

        List<byte[]> topics = log.getTopics();
        String encodedEventSignature = EventEncoder.encode(event);
        if (!Numeric.toHexString(topics.get(0)).equals(encodedEventSignature)) {
            return null;
        }

        List<Type> indexedValues = new ArrayList<>();
        List<Type> nonIndexedValues = FunctionReturnDecoder.decode(Numeric.toHexString(log.getData()), event.getNonIndexedParameters());

        List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();
        for (int i = 0; i < indexedParameters.size(); i++) {
            Type value = FunctionReturnDecoder.decodeIndexedValue(
                    Numeric.toHexString(topics.get(i + 1)), indexedParameters.get(i));
            indexedValues.add(value);
        }
        return new EventValues(indexedValues, nonIndexedValues);
    }

    protected static EventValues extractEventParameters(
            Event event, Log log) {

        List<String> topics = log.getTopics();
        String encodedEventSignature = EventEncoder.encode(event);
        if (!topics.get(0).equals(encodedEventSignature)) {
            return null;
        }

        List<Type> indexedValues = new ArrayList<>();
        List<Type> nonIndexedValues = FunctionReturnDecoder.decode(
                log.getData(), event.getNonIndexedParameters());

        List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();
        for (int i = 0; i < indexedParameters.size(); i++) {
            Type value = FunctionReturnDecoder.decodeIndexedValue(
                    topics.get(i + 1), indexedParameters.get(i));
            indexedValues.add(value);
        }
        return new EventValues(indexedValues, nonIndexedValues);
    }

    protected static List<EventValues> extractEventParameters(
            Event event, ThanosTransactionReceipt transactionReceipt) {

        List<ThanosLogInfo> logs = transactionReceipt.getLogInfoList();
        List<EventValues> values = new ArrayList<>();
        for (ThanosLogInfo log : logs) {
            EventValues eventValues = extractEventParameters(event, log);
            if (eventValues != null) {
                values.add(eventValues);
            }
        }

        return values;
    }


    protected static <T extends Contract> T deploy(
            Class<T> type,
            Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit,
            String binary, String encodedConstructor, BigInteger value) throws Exception {

        Constructor<T> constructor = type.getDeclaredConstructor(
                String.class,
                Web3j.class, Credentials.class,
                BigInteger.class, BigInteger.class);
        constructor.setAccessible(true);

        // we want to use null here to ensure that "to" parameter on message is not populated
        T contract = constructor.newInstance(null, web3j, credentials, gasPrice, gasLimit);

        return create(contract, binary, encodedConstructor, value);
    }

    protected static <T extends Contract> T deploy(
            Class<T> type,
            Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit,
            String binary, String encodedConstructor, BigInteger value) throws Exception {

        Constructor<T> constructor = type.getDeclaredConstructor(
                String.class,
                Web3j.class, TransactionManager.class,
                BigInteger.class, BigInteger.class);
        constructor.setAccessible(true);

        // we want to use null here to ensure that "to" parameter on message is not populated
        T contract = constructor.newInstance(null, web3j, transactionManager, gasPrice, gasLimit);

        return create(contract, binary, encodedConstructor, value);
    }

    private static <T extends Contract> T create(
            T contract, String binary, String encodedConstructor, BigInteger value)
            throws InterruptedException, IOException, TransactionTimeoutException {

//        if(contract.getExecuteStates()==null){
//            contract.setExecuteStates(new HashSet<>());
//        }

        ThanosTransactionReceipt transactionReceipt =
                contract.executeTransaction(binary + encodedConstructor, new HashSet(), value);

//        String contractAddress = transactionReceipt.getContractAddress();
//        if (contractAddress == null) {
//            throw new RuntimeException("Empty contract address returned");
//        }
        if (transactionReceipt == null) {
            return contract;
        }
        String contractAddress = Hex.toHexString(transactionReceipt.getExecutionResult());
        if (contractAddress == null) {
            throw new RuntimeException("Empty contract address returned");
        }

        contract.setContractAddress(contractAddress);
        contract.setTransactionReceipt(transactionReceipt);

        return contract;
    }

    private static String bytesToHex(byte[] bytes) {
        return new BigInteger(1, bytes).toString(16);
    }

    protected static <T extends Contract> CompletableFuture<T> deployAsync(
            Class<T> type, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit,
            String binary, String encodedConstructor, BigInteger initialWeiValue) {

        return Async.run(() -> deploy(type, web3j, credentials, gasPrice, gasLimit,
                binary, encodedConstructor, initialWeiValue));
    }

    protected static <T extends Contract> CompletableFuture<T> deployAsync(
            Class<T> type, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit,
            String binary, String encodedConstructor, BigInteger initialWeiValue) {

        return Async.run(() -> deploy(type, web3j, transactionManager, gasPrice, gasLimit,
                binary, encodedConstructor, initialWeiValue));
    }

    protected ThanosTransactionReceipt thanosCall(BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value)
            throws InterruptedException, IOException, TransactionTimeoutException {
        return transactionManager.thanosCall(gasPrice, gasLimit, to, data, value, executeStates);
    }

    public Set<String> getExecuteStates() {
        return executeStates;
    }

    public void setExecuteStates(Set<String> executeStates) {
        this.executeStates = executeStates;
    }


}
