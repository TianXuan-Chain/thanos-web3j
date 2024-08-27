package test.mail.contractCode;

import com.thanos.web3j.abi.FunctionEncoder;
import com.thanos.web3j.channel.client.TransactionSucCallback;
import com.thanos.web3j.abi.EventEncoder;
import com.thanos.web3j.abi.EventValues;
import com.thanos.web3j.abi.TypeReference;
import com.thanos.web3j.abi.datatypes.*;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.DefaultBlockParameter;
import com.thanos.web3j.protocol.core.methods.request.EthFilter;
import com.thanos.web3j.protocol.core.methods.response.Log;
import com.thanos.web3j.protocol.core.methods.response.TransactionReceipt;
import com.thanos.web3j.tx.Contract;
import com.thanos.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link com.thanos.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 */
public final class NotarizationMailProxy extends Contract {
    private static String BINARY = "60606040525b32600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b6109b6806100576000396000f30060606040523615610076576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063057af1c41461007857806338278cda146100ea578063893d20e8146102255780638da5cb5b1461027757806392eefe9b146102c9578063f77c479114610317575bfe5b341561008057fe5b6100d0600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610369565b604051808215151515815260200191505060405180910390f35b34156100f257fe5b61020b600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610517565b604051808215151515815260200191505060405180910390f35b341561022d57fe5b610235610839565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561027f57fe5b610287610864565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156102d157fe5b6102fd600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061088a565b604051808215151515815260200191505060405180910390f35b341561031f57fe5b610327610964565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663dfcd15786000604051602001526040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401809050602060405180830381600087803b15156103f957fe5b6102c65a03f1151561040757fe5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff1663057af1c4836000604051602001526040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252838181518152602001915080519060200190808383600083146104b0575b8051825260208311156104b05760208201915060208101905060208303925061048c565b505050905090810190601f1680156104dc5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b15156104f757fe5b6102c65a03f1151561050557fe5b5050506040518051905090505b919050565b6000600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663e16f5c516000604051602001526040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401809050602060405180830381600087803b15156105a757fe5b6102c65a03f115156105b557fe5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff166338278cda868686866000604051602001526040518563ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001806020018060200185810385528981815181526020019150805190602001908083836000831461066d575b80518252602083111561066d57602082019150602081019050602083039250610649565b505050905090810190601f1680156106995780820380516001836020036101000a031916815260200191505b508581038452888181518152602001915080519060200190808383600083146106e1575b8051825260208311156106e1576020820191506020810190506020830392506106bd565b505050905090810190601f16801561070d5780820380516001836020036101000a031916815260200191505b50858103835287818151815260200191508051906020019080838360008314610755575b80518252602083111561075557602082019150602081019050602083039250610731565b505050905090810190601f1680156107815780820380516001836020036101000a031916815260200191505b508581038252868181518152602001915080519060200190808383600083146107c9575b8051825260208311156107c9576020820191506020810190506020830392506107a5565b505050905090810190601f1680156107f55780820380516001836020036101000a031916815260200191505b5098505050505050505050602060405180830381600087803b151561081657fe5b6102c65a03f1151561082457fe5b5050506040518051905090505b949350505050565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690505b90565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff161415156108e95760006000fd5b81600060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405180905060405180910390a1600190505b5b919050565b600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16815600a165627a7a72305820d411a7041ecf242e379f36babd548422e571e8fa6a3ddb1a58f0cb73bbf1046f0029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"_emailNumber\",\"type\":\"string\"}],\"name\":\"exist\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_emailNumber\",\"type\":\"string\"},{\"name\":\"_emailFingerprint\",\"type\":\"string\"},{\"name\":\"_emailHash\",\"type\":\"string\"},{\"name\":\"_extendInfo\",\"type\":\"string\"}],\"name\":\"store\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getOwner\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_controller\",\"type\":\"address\"}],\"name\":\"setController\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"controller\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[],\"name\":\"successEvent\",\"type\":\"event\"}]";

    private NotarizationMailProxy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private NotarizationMailProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private NotarizationMailProxy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private NotarizationMailProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static List<SuccessEventEventResponse> getSuccessEventEvents(ThanosTransactionReceipt transactionReceipt) {
        final Event event = new Event("successEvent",
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<SuccessEventEventResponse> responses = new ArrayList<SuccessEventEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            SuccessEventEventResponse typedResponse = new SuccessEventEventResponse();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SuccessEventEventResponse> successEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("successEvent",
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, SuccessEventEventResponse>() {
            @Override
            public SuccessEventEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                SuccessEventEventResponse typedResponse = new SuccessEventEventResponse();
                return typedResponse;
            }
        });
    }

    public Future<Bool> exist(Utf8String _emailNumber) {
        Function function = new Function("exist",
                Arrays.<Type>asList(_emailNumber),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<ThanosTransactionReceipt> store(Utf8String _emailNumber, Utf8String _emailFingerprint, Utf8String _emailHash, Utf8String _extendInfo) {
        Function function = new Function("store", Arrays.<Type>asList(_emailNumber, _emailFingerprint, _emailHash, _extendInfo), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public static String storeData(Utf8String _emailNumber, Utf8String _emailFingerprint, Utf8String _emailHash, Utf8String _extendInfo) {
        Function function = new Function("store", Arrays.<Type>asList(_emailNumber, _emailFingerprint, _emailHash, _extendInfo), Collections.<TypeReference<?>>emptyList());
        return FunctionEncoder.encode(function);
    }

    public void store(Utf8String _emailNumber, Utf8String _emailFingerprint, Utf8String _emailHash, Utf8String _extendInfo, TransactionSucCallback callback) {
        Function function = new Function("store", Arrays.<Type>asList(_emailNumber, _emailFingerprint, _emailHash, _extendInfo), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Address> getOwner() {
        Function function = new Function("getOwner",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Address> owner() {
        Function function = new Function("owner",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<ThanosTransactionReceipt> setController(Address _controller) {
        Function function = new Function("setController", Arrays.<Type>asList(_controller), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setController(Address _controller, TransactionSucCallback callback) {
        Function function = new Function("setController", Arrays.<Type>asList(_controller), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Address> controller() {
        Function function = new Function("controller",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<NotarizationMailProxy> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(NotarizationMailProxy.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<NotarizationMailProxy> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(NotarizationMailProxy.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static NotarizationMailProxy load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotarizationMailProxy(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static NotarizationMailProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotarizationMailProxy(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static NotarizationMailProxy loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotarizationMailProxy(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static NotarizationMailProxy loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotarizationMailProxy(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static class SuccessEventEventResponse {
    }
}
