package test.tokensDemo;

import com.thanos.web3j.channel.client.TransactionSucCallback;
import com.thanos.web3j.abi.FunctionEncoder;
import com.thanos.web3j.abi.TypeReference;
import com.thanos.web3j.abi.datatypes.Address;
import com.thanos.web3j.abi.datatypes.Function;
import com.thanos.web3j.abi.datatypes.Type;
import com.thanos.web3j.abi.datatypes.generated.Int32;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.tx.Contract;
import com.thanos.web3j.tx.TransactionManager;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link com.thanos.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 */
public final class TokensDemo extends Contract {
    private static String BINARY = "608060405234801561001057600080fd5b50610158806100206000396000f3006080604052600436106100565763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166337f023b0811461005b578063b7335b7e14610091578063f8b2cb4f146100cb575b600080fd5b34801561006757600080fd5b5061008f73ffffffffffffffffffffffffffffffffffffffff6004351660243560030b610112565b005b34801561009d57600080fd5b5061008f73ffffffffffffffffffffffffffffffffffffffff6004358116906024351660443560030b610116565b3480156100d757600080fd5b506100f973ffffffffffffffffffffffffffffffffffffffff60043516610128565b60408051600392830b90920b8252519081900360200190f35b9055565b82548254908290039093559091019055565b54905600a165627a7a723058201557eb66aa12eae90518a0c29f2cde69a3163064d5370c77bfd932a2a5de153e0029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"source\",\"type\":\"address\"},{\"name\":\"tokens\",\"type\":\"int32\"}],\"name\":\"setBalance\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"source\",\"type\":\"address\"},{\"name\":\"to\",\"type\":\"address\"},{\"name\":\"tokens\",\"type\":\"int32\"}],\"name\":\"transfer\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"source\",\"type\":\"address\"}],\"name\":\"getBalance\",\"outputs\":[{\"name\":\"r\",\"type\":\"int32\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]";

    private TokensDemo(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private TokensDemo(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private TokensDemo(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private TokensDemo(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public Future<ThanosTransactionReceipt> setBalance(Address source, Int32 tokens) {
        Function function = new Function("setBalance", Arrays.<Type>asList(source, tokens), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public static String setBalanceData(Address source, Int32 tokens) {
        Function function = new Function("setBalance", Arrays.<Type>asList(source, tokens), Collections.<TypeReference<?>>emptyList());
        return FunctionEncoder.encode(function);
    }

    public static String transferData(Address source, Address to, Int32 tokens) {
        Function function = new Function("transfer", Arrays.<Type>asList(source, to, tokens), Collections.<TypeReference<?>>emptyList());
        return FunctionEncoder.encode(function);
    }

    public static Function getBalanceData(Address source) {
        return new Function("getBalance",
                Arrays.<Type>asList(source),
                Arrays.<TypeReference<?>>asList(new TypeReference<Int32>() {
                }));
    }

    public void setBalance(Address source, Int32 tokens, TransactionSucCallback callback) {
        Function function = new Function("setBalance", Arrays.<Type>asList(source, tokens), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<ThanosTransactionReceipt> transfer(Address source, Address to, Int32 tokens) {
        Function function = new Function("transfer", Arrays.<Type>asList(source, to, tokens), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }


    public void transfer(Address source, Address to, Int32 tokens, TransactionSucCallback callback) {
        Function function = new Function("transfer", Arrays.<Type>asList(source, to, tokens), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Int32> getBalance(Address source) {
        Function function = new Function("getBalance", 
                Arrays.<Type>asList(source), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int32>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<TokensDemo> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(TokensDemo.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<TokensDemo> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(TokensDemo.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static TokensDemo load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TokensDemo(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static TokensDemo load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TokensDemo(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static TokensDemo loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TokensDemo(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static TokensDemo loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TokensDemo(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }
}
