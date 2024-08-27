package test.mail.contractCode;

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
public final class NotarizationMailStorage extends Contract {
    private static String BINARY = "60606040525b32600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b611cdb806100576000396000f30060606040523615610097576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063057af1c41461009957806338278cda1461010b5780637c26192914610246578063893d20e8146104c85780638da5cb5b1461051a57806392eefe9b1461056c578063b99c4079146105ba578063e4224d38146106f5578063f77c4791146108ca575bfe5b34156100a157fe5b6100f1600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190505061091c565b604051808215151515815260200191505060405180910390f35b341561011357fe5b61022c600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506109e3565b604051808215151515815260200191505060405180910390f35b341561024e57fe5b61029e600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610de5565b604051808060200180602001806020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018060200185810385528a81815181526020019150805190602001908083836000831461032b575b80518252602083111561032b57602082019150602081019050602083039250610307565b505050905090810190601f1680156103575780820380516001836020036101000a031916815260200191505b5085810384528981815181526020019150805190602001908083836000831461039f575b80518252602083111561039f5760208201915060208101905060208303925061037b565b505050905090810190601f1680156103cb5780820380516001836020036101000a031916815260200191505b50858103835288818151815260200191508051906020019080838360008314610413575b805182526020831115610413576020820191506020810190506020830392506103ef565b505050905090810190601f16801561043f5780820380516001836020036101000a031916815260200191505b50858103825286818151815260200191508051906020019080838360008314610487575b80518252602083111561048757602082019150602081019050602083039250610463565b505050905090810190601f1680156104b35780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390f35b34156104d057fe5b6104d86112c8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561052257fe5b61052a6112f3565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561057457fe5b6105a0600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050611319565b604051808215151515815260200191505060405180910390f35b34156105c257fe5b6106db600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506113f3565b604051808215151515815260200191505060405180910390f35b34156106fd57fe5b61074d600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050611812565b604051808060200180602001806020018481038452878181518152602001915080519060200190808383600083146107a4575b8051825260208311156107a457602082019150602081019050602083039250610780565b505050905090810190601f1680156107d05780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360008314610818575b805182526020831115610818576020820191506020810190506020830392506107f4565b505050905090810190601f1680156108445780820380516001836020036101000a031916815260200191505b5084810382528581815181526020019150805190602001908083836000831461088c575b80518252602083111561088c57602082019150602081019050602083039250610868565b505050905090810190601f1680156108b85780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b34156108d257fe5b6108da611b50565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000600073ffffffffffffffffffffffffffffffffffffffff166002836040518082805190602001908083835b6020831061096c5780518252602082019150602081019050602083039250610949565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141590505b919050565b6000604060405190810160405280601781526020017f4e6f746172697a6174696f6e4d61696c48616e646c6572000000000000000000815250600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825283818151815260200191508051906020019080838360008314610ade575b805182526020831115610ade57602082019150602081019050602083039250610aba565b505050905090810190601f168015610b0a5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b1515610b2557fe5b6102c65a03f11515610b3357fe5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610b775760006000fd5b600073ffffffffffffffffffffffffffffffffffffffff166002876040518082805190602001908083835b60208310610bc55780518252602082019150602081019050602083039250610ba2565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610c425760009150610ddb565b60a0604051908101604052808781526020018681526020018581526020013273ffffffffffffffffffffffffffffffffffffffff168152602001848152506002876040518082805190602001908083835b60208310610cb65780518252602082019150602081019050602083039250610c93565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000820151816000019080519060200190610d05929190611b76565b506020820151816001019080519060200190610d22929190611b76565b506040820151816002019080519060200190610d3f929190611b76565b5060608201518160030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506080820151816004019080519060200190610da3929190611b76565b509050507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405180905060405180910390a1600191505b5b50949350505050565b610ded611bf6565b610df5611bf6565b610dfd611bf6565b6000610e07611bf6565b6002866040518082805190602001908083835b60208310610e3d5780518252602082019150602081019050602083039250610e1a565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000016002876040518082805190602001908083835b60208310610ea95780518252602082019150602081019050602083039250610e86565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206001016002886040518082805190602001908083835b60208310610f155780518252602082019150602081019050602083039250610ef2565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206002016002896040518082805190602001908083835b60208310610f815780518252602082019150602081019050602083039250610f5e565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1660028a6040518082805190602001908083835b6020831061100e5780518252602082019150602081019050602083039250610feb565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600401848054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156110d95780601f106110ae576101008083540402835291602001916110d9565b820191906000526020600020905b8154815290600101906020018083116110bc57829003601f168201915b50505050509450838054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156111755780601f1061114a57610100808354040283529160200191611175565b820191906000526020600020905b81548152906001019060200180831161115857829003601f168201915b50505050509350828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156112115780601f106111e657610100808354040283529160200191611211565b820191906000526020600020905b8154815290600101906020018083116111f457829003601f168201915b50505050509250808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156112ad5780601f10611282576101008083540402835291602001916112ad565b820191906000526020600020905b81548152906001019060200180831161129057829003601f168201915b50505050509050945094509450945094505b91939590929450565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690505b90565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff161415156113785760006000fd5b81600060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405180905060405180910390a1600190505b5b919050565b6000604060405190810160405280601781526020017f4e6f746172697a6174696f6e4d61696c48616e646c6572000000000000000000815250600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252838181518152602001915080519060200190808383600083146114ee575b8051825260208311156114ee576020820191506020810190506020830392506114ca565b505050905090810190601f16801561151a5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b151561153557fe5b6102c65a03f1151561154357fe5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156115875760006000fd5b600073ffffffffffffffffffffffffffffffffffffffff166002876040518082805190602001908083835b602083106115d557805182526020820191506020810190506020830392506115b2565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156116515760009150611808565b846002876040518082805190602001908083835b602083106116885780518252602082019150602081019050602083039250611665565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010190805190602001906116d1929190611c0a565b50836002876040518082805190602001908083835b6020831061170957805182526020820191506020810190506020830392506116e6565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206002019080519060200190611752929190611c0a565b50826002876040518082805190602001908083835b6020831061178a5780518252602082019150602081019050602083039250611767565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060040190805190602001906117d3929190611c0a565b507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405180905060405180910390a1600191505b5b50949350505050565b61181a611bf6565b611822611bf6565b61182a611bf6565b6002846040518082805190602001908083835b60208310611860578051825260208201915060208101905060208303925061183d565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000016002856040518082805190602001908083835b602083106118cc57805182526020820191506020810190506020830392506118a9565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206001016002866040518082805190602001908083835b602083106119385780518252602082019150602081019050602083039250611915565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600201828054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611a035780601f106119d857610100808354040283529160200191611a03565b820191906000526020600020905b8154815290600101906020018083116119e657829003601f168201915b50505050509250818054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611a9f5780601f10611a7457610100808354040283529160200191611a9f565b820191906000526020600020905b815481529060010190602001808311611a8257829003601f168201915b50505050509150808054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611b3b5780601f10611b1057610100808354040283529160200191611b3b565b820191906000526020600020905b815481529060010190602001808311611b1e57829003601f168201915b505050505090509250925092505b9193909250565b600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611bb757805160ff1916838001178555611be5565b82800160010185558215611be5579182015b82811115611be4578251825591602001919060010190611bc9565b5b509050611bf29190611c8a565b5090565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611c4b57805160ff1916838001178555611c79565b82800160010185558215611c79579182015b82811115611c78578251825591602001919060010190611c5d565b5b509050611c869190611c8a565b5090565b611cac91905b80821115611ca8576000816000905550600101611c90565b5090565b905600a165627a7a723058206d24de7047a81dfe770ef098dfc51f730678c25726a21b296f8e2db4d52e9a5e0029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"_emailNumber\",\"type\":\"string\"}],\"name\":\"exist\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_emailNumber\",\"type\":\"string\"},{\"name\":\"_emailFingerprint\",\"type\":\"string\"},{\"name\":\"_emailHash\",\"type\":\"string\"},{\"name\":\"_extendInfo\",\"type\":\"string\"}],\"name\":\"store\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_emailNumber\",\"type\":\"string\"}],\"name\":\"query\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getOwner\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_controller\",\"type\":\"address\"}],\"name\":\"setController\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_emailNumber\",\"type\":\"string\"},{\"name\":\"_emailFingerprint\",\"type\":\"string\"},{\"name\":\"_emailHash\",\"type\":\"string\"},{\"name\":\"_extendInfo\",\"type\":\"string\"}],\"name\":\"update\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_emailNumber\",\"type\":\"string\"}],\"name\":\"querySample\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"controller\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[],\"name\":\"successEvent\",\"type\":\"event\"}]";

    private NotarizationMailStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private NotarizationMailStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private NotarizationMailStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private NotarizationMailStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
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

    public void store(Utf8String _emailNumber, Utf8String _emailFingerprint, Utf8String _emailHash, Utf8String _extendInfo, TransactionSucCallback callback) {
        Function function = new Function("store", Arrays.<Type>asList(_emailNumber, _emailFingerprint, _emailHash, _extendInfo), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<List<Type>> query(Utf8String _emailNumber) {
        Function function = new Function("query",
                Arrays.<Type>asList(_emailNumber),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
        return executeCallMultipleValueReturnAsync(function);
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

    public Future<ThanosTransactionReceipt> update(Utf8String _emailNumber, Utf8String _emailFingerprint, Utf8String _emailHash, Utf8String _extendInfo) {
        Function function = new Function("update", Arrays.<Type>asList(_emailNumber, _emailFingerprint, _emailHash, _extendInfo), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void update(Utf8String _emailNumber, Utf8String _emailFingerprint, Utf8String _emailHash, Utf8String _extendInfo, TransactionSucCallback callback) {
        Function function = new Function("update", Arrays.<Type>asList(_emailNumber, _emailFingerprint, _emailHash, _extendInfo), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<List<Type>> querySample(Utf8String _emailNumber) {
        Function function = new Function("querySample",
                Arrays.<Type>asList(_emailNumber),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<Address> controller() {
        Function function = new Function("controller",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<NotarizationMailStorage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(NotarizationMailStorage.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<NotarizationMailStorage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(NotarizationMailStorage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static NotarizationMailStorage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotarizationMailStorage(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static NotarizationMailStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotarizationMailStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static NotarizationMailStorage loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotarizationMailStorage(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static NotarizationMailStorage loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotarizationMailStorage(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static class SuccessEventEventResponse {
    }
}
