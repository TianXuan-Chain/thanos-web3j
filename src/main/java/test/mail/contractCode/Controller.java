package test.mail.contractCode;

import com.thanos.web3j.channel.client.TransactionSucCallback;
import com.thanos.web3j.abi.EventEncoder;
import com.thanos.web3j.abi.EventValues;
import com.thanos.web3j.abi.TypeReference;
import com.thanos.web3j.abi.datatypes.*;
import com.thanos.web3j.abi.datatypes.generated.Bytes32;
import com.thanos.web3j.abi.datatypes.generated.Uint256;
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
public final class Controller extends Contract {
    private static String BINARY = "6060604052341561000c57fe5b5b6000805480600101828161002191906100bc565b916000526020600020900160005b32909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550506001600160003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505b61010d565b8154818355818115116100e3578183600052602060002091820191016100e291906100e8565b5b505050565b61010a91905b808211156101065760008160009055506001016100ee565b5090565b90565b610e048061011c6000396000f300606060405236156100b8576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806314bfd6d0146100ba5780631e59c5291461011a57806327e1f7df146101ab5780632cff89c9146101e157806370480275146102725780637ef50298146102a85780639026dee81461030c578063cfb519281461035a578063d9f774fc146103d0578063dfcd157814610445578063e16f5c5114610497578063f67187ac146104e9575bfe5b34156100c257fe5b6100d86004808035906020019091905050610583565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561012257fe5b610191600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506105c3565b604051808215151515815260200191505060405180910390f35b34156101b357fe5b6101df600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610718565b005b34156101e957fe5b610258600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506108c8565b604051808215151515815260200191505060405180910390f35b341561027a57fe5b6102a6600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506109bc565b005b34156102b057fe5b6102ca600480803560001916906020019091905050610b37565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561031457fe5b610340600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610b6a565b604051808215151515815260200191505060405180910390f35b341561036257fe5b6103b2600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610bb7565b60405180826000191660001916815260200191505060405180910390f35b34156103d857fe5b6103e0610be8565b6040518080602001828103825283818151815260200191508051906020019060200280838360008314610432575b8051825260208311156104325760208201915060208101905060208303925061040e565b5050509050019250505060405180910390f35b341561044d57fe5b610455610c7d565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561049f57fe5b6104a7610cc4565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156104f157fe5b610541600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610d0b565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b60008181548110151561059257fe5b906000526020600020900160005b915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600060006001600160003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541415156106165760006000fd5b61061f84610bb7565b9050600060026000836000191660001916815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415156106825760006000fd5b8260026000836000191660001916815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405180905060405180910390a1600191505b5b5092915050565b60006001600160003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541415156107695760006000fd5b60026000805490501015151561077f5760006000fd5b600090505b60008054905081101561084d578173ffffffffffffffffffffffffffffffffffffffff166000828154811015156107b757fe5b906000526020600020900160005b9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141561083f5760008181548110151561080f57fe5b906000526020600020900160005b6101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690555b5b8080600101915050610784565b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405180905060405180910390a15b5b5b5050565b600060006001600160003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205414151561091b5760006000fd5b61092484610bb7565b90508260026000836000191660001916815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405180905060405180910390a1600191505b5b5092915050565b6001600160003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054141515610a0b5760006000fd5b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054141515610a5a5760006000fd5b60008054806001018281610a6e9190610d5f565b916000526020600020900160005b83909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550506001600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405180905060405180910390a15b5b50565b60026020528060005260406000206000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60006001600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541490505b919050565b6000610bc1610d8b565b829050600081511415610bda5760006001029150610be2565b602083015191505b50919050565b610bf0610d9f565b6000805480602002602001604051908101604052809291908181526020018280548015610c7257602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019060010190808311610c28575b505050505090505b90565b6000610cbe604060405190810160405280601781526020017f4e6f746172697a6174696f6e4d61696c53746f72616765000000000000000000815250610d0b565b90505b90565b6000610d05604060405190810160405280601781526020017f4e6f746172697a6174696f6e4d61696c48616e646c6572000000000000000000815250610d0b565b90505b90565b60006000610d1883610bb7565b905060026000826000191660001916815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1691505b50919050565b815481835581811511610d8657818360005260206000209182019101610d859190610db3565b5b505050565b602060405190810160405280600081525090565b602060405190810160405280600081525090565b610dd591905b80821115610dd1576000816000905550600101610db9565b5090565b905600a165627a7a723058206a6438d1d45cf6252dbe56e23bbc3884af4dbe911a45ae867da7cb2b642ac56d0029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"admins\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_name\",\"type\":\"string\"},{\"name\":\"_address\",\"type\":\"address\"}],\"name\":\"register\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"deleteAdmin\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_name\",\"type\":\"string\"},{\"name\":\"_address\",\"type\":\"address\"}],\"name\":\"update\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"addAdmin\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"registry\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"checkAdmin\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"source\",\"type\":\"string\"}],\"name\":\"stringToBytes32\",\"outputs\":[{\"name\":\"result\",\"type\":\"bytes32\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getAdminList\",\"outputs\":[{\"name\":\"\",\"type\":\"address[]\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"getNotarizationMailStorage\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"getNotarizationMailHandler\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_name\",\"type\":\"string\"}],\"name\":\"lookup\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[],\"name\":\"successEvent\",\"type\":\"event\"}]";

    private Controller(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private Controller(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private Controller(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private Controller(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
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

    public Future<Address> admins(Uint256 param0) {
        Function function = new Function("admins",
                Arrays.<Type>asList(param0),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<ThanosTransactionReceipt> register(Utf8String _name, Address _address) {
        Function function = new Function("register", Arrays.<Type>asList(_name, _address), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void register(Utf8String _name, Address _address, TransactionSucCallback callback) {
        Function function = new Function("register", Arrays.<Type>asList(_name, _address), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<ThanosTransactionReceipt> deleteAdmin(Address account) {
        Function function = new Function("deleteAdmin", Arrays.<Type>asList(account), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void deleteAdmin(Address account, TransactionSucCallback callback) {
        Function function = new Function("deleteAdmin", Arrays.<Type>asList(account), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<ThanosTransactionReceipt> update(Utf8String _name, Address _address) {
        Function function = new Function("update", Arrays.<Type>asList(_name, _address), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void update(Utf8String _name, Address _address, TransactionSucCallback callback) {
        Function function = new Function("update", Arrays.<Type>asList(_name, _address), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<ThanosTransactionReceipt> addAdmin(Address account) {
        Function function = new Function("addAdmin", Arrays.<Type>asList(account), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void addAdmin(Address account, TransactionSucCallback callback) {
        Function function = new Function("addAdmin", Arrays.<Type>asList(account), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Address> registry(Bytes32 param0) {
        Function function = new Function("registry",
                Arrays.<Type>asList(param0),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Bool> checkAdmin(Address account) {
        Function function = new Function("checkAdmin",
                Arrays.<Type>asList(account),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<ThanosTransactionReceipt> stringToBytes32(Utf8String source) {
        Function function = new Function("stringToBytes32", Arrays.<Type>asList(source), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void stringToBytes32(Utf8String source, TransactionSucCallback callback) {
        Function function = new Function("stringToBytes32", Arrays.<Type>asList(source), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<DynamicArray<Address>> getAdminList() {
        Function function = new Function("getAdminList",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<ThanosTransactionReceipt> getNotarizationMailStorage() {
        Function function = new Function("getNotarizationMailStorage", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void getNotarizationMailStorage(TransactionSucCallback callback) {
        Function function = new Function("getNotarizationMailStorage", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<ThanosTransactionReceipt> getNotarizationMailHandler() {
        Function function = new Function("getNotarizationMailHandler", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void getNotarizationMailHandler(TransactionSucCallback callback) {
        Function function = new Function("getNotarizationMailHandler", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Address> lookup(Utf8String _name) {
        Function function = new Function("lookup",
                Arrays.<Type>asList(_name),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<Controller> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Controller.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<Controller> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Controller.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Controller load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Controller(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static Controller load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Controller(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static Controller loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Controller(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static Controller loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Controller(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static class SuccessEventEventResponse {
    }
}
