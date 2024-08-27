package test.ftbtoken.contractcode;

import com.thanos.web3j.channel.client.TransactionSucCallback;
import com.thanos.web3j.abi.EventEncoder;
import com.thanos.web3j.abi.EventValues;
import com.thanos.web3j.abi.TypeReference;
import com.thanos.web3j.abi.datatypes.Address;
import com.thanos.web3j.abi.datatypes.Event;
import com.thanos.web3j.abi.datatypes.Function;
import com.thanos.web3j.abi.datatypes.Type;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.DefaultBlockParameter;
import com.thanos.web3j.protocol.core.methods.request.EthFilter;
import com.thanos.web3j.protocol.core.methods.response.Log;
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
 *
 * <p>Generated with web3j version none.
 */
public final class FTBControllerFactory extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b61131b8061001e6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680633018205f14610053578063adb69560146100a8578063f77c4791146100fd57600080fd5b341561005e57600080fd5b610066610152565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156100b357600080fd5b6100bb61017b565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561010857600080fd5b610110610269565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b60008061018661028e565b604051809103906000f080151561019c57600080fd5b9050806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507fd48a3367e9d4b68bf56c2f3a09c4f33ac41b10a851d74b6290cba55aa4e9ecc681604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390a16000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1691505090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6040516110518061029f8339019056006060604052341561000f57600080fd5b6000805480600101828161002391906100bb565b9160005260206000209001600032909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505060018060003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555061010c565b8154818355818115116100e2578183600052602060002091820191016100e191906100e7565b5b505050565b61010991905b808211156101055760008160009055506001016100ed565b5090565b90565b610f368061011b6000396000f300606060405236156100ce576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806314bfd6d0146100d35780631ae2f037146101365780631e59c5291461018b57806327e1f7df1461021f5780632cff89c9146102585780633e9e5b30146102ec57806348bb53b51461034157806370480275146103965780637ef50298146103cf5780639026dee814610436578063ce553b5c14610487578063cfb51928146104dc578063d9f774fc14610555578063f67187ac146105bf575b600080fd5b34156100de57600080fd5b6100f4600480803590602001909190505061065c565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561014157600080fd5b61014961069b565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561019657600080fd5b610205600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506106e0565b604051808215151515815260200191505060405180910390f35b341561022a57600080fd5b610256600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061082c565b005b341561026357600080fd5b6102d2600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506109d0565b604051808215151515815260200191505060405180910390f35b34156102f757600080fd5b6102ff610abc565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561034c57600080fd5b610354610b01565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156103a157600080fd5b6103cd600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610b46565b005b34156103da57600080fd5b6103f4600480803560001916906020019091905050610cb7565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561044157600080fd5b61046d600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610cea565b604051808215151515815260200191505060405180910390f35b341561049257600080fd5b61049a610d35565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156104e757600080fd5b610537600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610d7a565b60405180826000191660001916815260200191505060405180910390f35b341561056057600080fd5b610568610dab565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156105ab578082015181840152602081019050610590565b505050509050019250505060405180910390f35b34156105ca57600080fd5b61061a600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610e3f565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b60008181548110151561066b57fe5b90600052602060002090016000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60006106db6040805190810160405280600681526020017f467265657a650000000000000000000000000000000000000000000000000000815250610e3f565b905090565b60008060018060003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205414151561073057600080fd5b61073984610d7a565b9050600060026000836000191660001916815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151561079b57600080fd5b8260026000836000191660001916815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405160405180910390a1600191505092915050565b600060018060003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205414151561087b57600080fd5b60026000805490501015151561089057600080fd5b600090505b60008054905081101561095b578173ffffffffffffffffffffffffffffffffffffffff166000828154811015156108c857fe5b906000526020600020900160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141561094e5760008181548110151561091f57fe5b906000526020600020900160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690555b8080600101915050610895565b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405160405180910390a15050565b60008060018060003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054141515610a2057600080fd5b610a2984610d7a565b90508260026000836000191660001916815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405160405180910390a1600191505092915050565b6000610afc6040805190810160405280600881526020017f465442546f6b656e000000000000000000000000000000000000000000000000815250610e3f565b905090565b6000610b416040805190810160405280600981526020017f4177617264506f6f6c0000000000000000000000000000000000000000000000815250610e3f565b905090565b60018060003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054141515610b9357600080fd5b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054141515610be157600080fd5b60008054806001018281610bf59190610e91565b9160005260206000209001600083909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505060018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405160405180910390a150565b60026020528060005260406000206000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600060018060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054149050919050565b6000610d756040805190810160405280600f81526020017f465442546f6b656e48616e646c65720000000000000000000000000000000000815250610e3f565b905090565b6000610d84610ebd565b829050600081511415610d9d5760006001029150610da5565b602083015191505b50919050565b610db3610ed1565b6000805480602002602001604051908101604052809291908181526020018280548015610e3557602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019060010190808311610deb575b5050505050905090565b600080610e4b83610d7a565b905060026000826000191660001916815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16915050919050565b815481835581811511610eb857818360005260206000209182019101610eb79190610ee5565b5b505050565b602060405190810160405280600081525090565b602060405190810160405280600081525090565b610f0791905b80821115610f03576000816000905550600101610eeb565b5090565b905600a165627a7a7230582067f79d6ae9a0fc017f7ad2363b846beea6a8484ce0a165f105e24095aae72ae90029a165627a7a723058207bfb2d13f04d753d1ef1b9758ead9bbd2bbb73a5273a53474102a87e236b78270029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[],\"name\":\"getController\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"createController\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"controller\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"createSuccessEvent\",\"type\":\"event\"}]";

    private FTBControllerFactory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private FTBControllerFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private FTBControllerFactory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private FTBControllerFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static List<CreateSuccessEventEventResponse> getCreateSuccessEventEvents(ThanosTransactionReceipt transactionReceipt) {
        final Event event = new Event("createSuccessEvent",
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<CreateSuccessEventEventResponse> responses = new ArrayList<CreateSuccessEventEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            CreateSuccessEventEventResponse typedResponse = new CreateSuccessEventEventResponse();
            typedResponse.addr = (Address) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<CreateSuccessEventEventResponse> createSuccessEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("createSuccessEvent",
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, CreateSuccessEventEventResponse>() {
            @Override
            public CreateSuccessEventEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                CreateSuccessEventEventResponse typedResponse = new CreateSuccessEventEventResponse();
                typedResponse.addr = (Address) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Future<ThanosTransactionReceipt> getController() {
        Function function = new Function("getController", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void getController(TransactionSucCallback callback) {
        Function function = new Function("getController", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<ThanosTransactionReceipt> createController() {
        Function function = new Function("createController", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void createController(TransactionSucCallback callback) {
        Function function = new Function("createController", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Address> controller() {
        Function function = new Function("controller",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<FTBControllerFactory> deployAddBin(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String bin) {
        return deployAsync(FTBControllerFactory.class, web3j, credentials, gasPrice, gasLimit, bin, "", initialWeiValue);
    }

    public static Future<FTBControllerFactory> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(FTBControllerFactory.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<FTBControllerFactory> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(FTBControllerFactory.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static FTBControllerFactory load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FTBControllerFactory(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static FTBControllerFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FTBControllerFactory(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static FTBControllerFactory loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FTBControllerFactory(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static FTBControllerFactory loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FTBControllerFactory(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static class CreateSuccessEventEventResponse {
        public Address addr;
    }
}
