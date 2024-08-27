package test.ftbtoken.contractcode;

import com.thanos.web3j.channel.client.TransactionSucCallback;
import com.thanos.web3j.abi.EventEncoder;
import com.thanos.web3j.abi.EventValues;
import com.thanos.web3j.abi.FunctionEncoder;
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
public final class FTBTokenFactory extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b604051602080612e4683398101604052808051906020019091905050806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050612dcb8061007b6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680639cbf9e3614610048578063f77c47911461009d57600080fd5b341561005357600080fd5b61005b6100f2565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156100a857600080fd5b6100b0610378565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000806100fd61039d565b604051809103906000f080151561011357600080fd5b90508073ffffffffffffffffffffffffffffffffffffffff166392eefe9b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff166000604051602001526040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050602060405180830381600087803b15156101d957600080fd5b6102c65a03f115156101ea57600080fd5b50505060405180519050506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16631e59c529826000604051602001526040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825260088152602001807f465442546f6b656e00000000000000000000000000000000000000000000000081525060200192505050602060405180830381600087803b15156102f257600080fd5b6102c65a03f1151561030357600080fd5b50505060405180519050507fd48a3367e9d4b68bf56c2f3a09c4f33ac41b10a851d74b6290cba55aa4e9ecc681604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390a18091505090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6040516129f2806103ae83390190560060606040526000600860006101000a81548160ff02191690831515021790555034156200002b57600080fd5b32600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506040805190810160405280600381526020017f465442000000000000000000000000000000000000000000000000000000000081525060029080519060200190620000b9929190620002ad565b506040805190810160405280600a81526020017f46544220746f6b656e73000000000000000000000000000000000000000000008152506003908051906020019062000107929190620002ad565b506008600460006101000a81548160ff021916908360ff1602179055506639e8c37dd6c0006005819055506619ef4fb2dc4000600660003073ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055507314b70566d9825b8849fb013bd2eb7e0d74503025600860016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550661ff973cafa800060066000600860019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055503273ffffffffffffffffffffffffffffffffffffffff16600073ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef6005546040518082815260200191505060405180910390a36200035c565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620002f057805160ff191683800117855562000321565b8280016001018555821562000321579182015b828111156200032057825182559160200191906001019062000303565b5b50905062000330919062000334565b5090565b6200035991905b80821115620003555760008160009055506001016200033b565b5090565b90565b612686806200036c6000396000f300606060405236156100ef576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063095ea7b3146100f457806318160ddd1461014e57806323b872dd14610177578063623454cf146101f057806363a599a41461027257806370a082311461029f57806386d1a69f146102ec578063893d20e81461031957806389afcb441461036e5780638da5cb5b146103bf57806392250bdb1461041457806392eefe9b14610441578063a9059cbb14610492578063adc1f486146104ec578063dd62ed3e14610546578063e6a03b17146105b2578063f77c47911461060c575b600080fd5b34156100ff57600080fd5b610134600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610661565b604051808215151515815260200191505060405180910390f35b341561015957600080fd5b610161610959565b6040518082815260200191505060405180910390f35b341561018257600080fd5b6101d6600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610963565b604051808215151515815260200191505060405180910390f35b34156101fb57600080fd5b610258600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803590602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610db7565b604051808215151515815260200191505060405180910390f35b341561027d57600080fd5b610285611338565b604051808215151515815260200191505060405180910390f35b34156102aa57600080fd5b6102d6600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506114e4565b6040518082815260200191505060405180910390f35b34156102f757600080fd5b6102ff61152d565b604051808215151515815260200191505060405180910390f35b341561032457600080fd5b61032c6116d9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561037957600080fd5b6103a5600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050611703565b604051808215151515815260200191505060405180910390f35b34156103ca57600080fd5b6103d26119d7565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561041f57600080fd5b6104276119fd565b604051808215151515815260200191505060405180910390f35b341561044c57600080fd5b610478600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050611a14565b604051808215151515815260200191505060405180910390f35b341561049d57600080fd5b6104d2600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050611ae7565b604051808215151515815260200191505060405180910390f35b34156104f757600080fd5b61052c600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050611e88565b604051808215151515815260200191505060405180910390f35b341561055157600080fd5b61059c600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506121cc565b6040518082815260200191505060405180910390f35b34156105bd57600080fd5b6105f2600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050612253565b604051808215151515815260200191505060405180910390f35b341561061757600080fd5b61061f6125fd565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000801515600860009054906101000a900460ff16151514151561068457600080fd5b600860019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff16141515156106e157600080fd5b6040805190810160405280600f81526020017f465442546f6b656e48616e646c657200000000000000000000000000000000008152506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825283818151815260200191508051906020019080838360005b838110156107c95780820151818401526020810190506107ae565b50505050905090810190601f1680156107f65780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b151561081457600080fd5b6102c65a03f1151561082557600080fd5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561086857600080fd5b82600760003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508373ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925856040518082815260200191505060405180910390a3600191505092915050565b6000600554905090565b6000801515600860009054906101000a900460ff16151514151561098657600080fd5b6040805190810160405280600f81526020017f465442546f6b656e48616e646c657200000000000000000000000000000000008152506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825283818151815260200191508051906020019080838360005b83811015610a6e578082015181840152602081019050610a53565b50505050905090810190601f168015610a9b5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b1515610ab957600080fd5b6102c65a03f11515610aca57600080fd5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610b0d57600080fd5b610b5f83600660008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461262290919063ffffffff16565b600660008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550610c3183600760008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461262290919063ffffffff16565b600760008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550610d0383600660008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461263e90919063ffffffff16565b600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508373ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040518082815260200191505060405180910390a360019150509392505050565b6000801515600860009054906101000a900460ff161515141515610dda57600080fd5b600860019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff1614151515610e3757600080fd5b6040805190810160405280600f81526020017f465442546f6b656e48616e646c657200000000000000000000000000000000008152506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825283818151815260200191508051906020019080838360005b83811015610f1f578082015181840152602081019050610f04565b50505050905090810190601f168015610f4c5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b1515610f6a57600080fd5b6102c65a03f11515610f7b57600080fd5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610fbe57600080fd5b828501600660003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205411151561100d57600080fd5b61105f85600660003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461262290919063ffffffff16565b600660003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055506110f485600660008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461263e90919063ffffffff16565b600660008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555061118983600660003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461262290919063ffffffff16565b600660003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555061121e83600660008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461263e90919063ffffffff16565b600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508573ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef876040518082815260200191505060405180910390a38373ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040518082815260200191505060405180910390a36001915050949350505050565b60006040805190810160405280600f81526020017f465442546f6b656e48616e646c657200000000000000000000000000000000008152506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825283818151815260200191508051906020019080838360005b83811015611422578082015181840152602081019050611407565b50505050905090810190601f16801561144f5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b151561146d57600080fd5b6102c65a03f1151561147e57600080fd5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156114c157600080fd5b6001600860006101000a81548160ff021916908315150217905550600191505090565b6000600660008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b60006040805190810160405280600f81526020017f465442546f6b656e48616e646c657200000000000000000000000000000000008152506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825283818151815260200191508051906020019080838360005b838110156116175780820151818401526020810190506115fc565b50505050905090810190601f1680156116445780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b151561166257600080fd5b6102c65a03f1151561167357600080fd5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156116b657600080fd5b6000600860006101000a81548160ff021916908315150217905550600191505090565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b6000801515600860009054906101000a900460ff16151514151561172657600080fd5b6040805190810160405280600f81526020017f465442546f6b656e48616e646c657200000000000000000000000000000000008152506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825283818151815260200191508051906020019080838360005b8381101561180e5780820151818401526020810190506117f3565b50505050905090810190601f16801561183b5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b151561185957600080fd5b6102c65a03f1151561186a57600080fd5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156118ad57600080fd5b600660008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054600660008073ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540192505081905550600660008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546005600082825403925050819055506000600660008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055506001915050919050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600860009054906101000a900460ff16905090565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff16141515611a7257600080fd5b816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405160405180910390a160019050919050565b6000801515600860009054906101000a900460ff161515141515611b0a57600080fd5b600860019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff1614151515611b6757600080fd5b6040805190810160405280600f81526020017f465442546f6b656e48616e646c657200000000000000000000000000000000008152506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825283818151815260200191508051906020019080838360005b83811015611c4f578082015181840152602081019050611c34565b50505050905090810190601f168015611c7c5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b1515611c9a57600080fd5b6102c65a03f11515611cab57600080fd5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515611cee57600080fd5b611d4083600660003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461262290919063ffffffff16565b600660003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550611dd583600660008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461263e90919063ffffffff16565b600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508373ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040518082815260200191505060405180910390a3600191505092915050565b6000801515600860009054906101000a900460ff161515141515611eab57600080fd5b6040805190810160405280600f81526020017f465442546f6b656e48616e646c657200000000000000000000000000000000008152506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825283818151815260200191508051906020019080838360005b83811015611f93578082015181840152602081019050611f78565b50505050905090810190601f168015611fc05780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b1515611fde57600080fd5b6102c65a03f11515611fef57600080fd5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561203257600080fd5b61208483600660003073ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461262290919063ffffffff16565b600660003073ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555061211983600660008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461263e90919063ffffffff16565b600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508373ffffffffffffffffffffffffffffffffffffffff163073ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040518082815260200191505060405180910390a3600191505092915050565b6000600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905092915050565b6000801515600860009054906101000a900460ff16151514151561227657600080fd5b6040805190810160405280600f81526020017f465442546f6b656e48616e646c657200000000000000000000000000000000008152506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825283818151815260200191508051906020019080838360005b8381101561235e578082015181840152602081019050612343565b50505050905090810190601f16801561238b5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b15156123a957600080fd5b6102c65a03f115156123ba57600080fd5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156123fd57600080fd5b6124718360066000600860019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461262290919063ffffffff16565b60066000600860019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555061252883600660008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461263e90919063ffffffff16565b600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508373ffffffffffffffffffffffffffffffffffffffff16600860019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040518082815260200191505060405180910390a3600191505092915050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600082821115151561263357600080fd5b818303905092915050565b6000818301905082811015151561265457600080fd5b929150505600a165627a7a72305820db02a4653ab42798c93c2a656fe0d6a0fab5ed5d3aa773c66cf2b4d60ead10070029a165627a7a72305820116398f136b855f560d5f268fa5e79bb323927787535c5b3a9d681cd6efae6980029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[],\"name\":\"createToken\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"controller\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"name\":\"_controller\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"createSuccessEvent\",\"type\":\"event\"}]";

    private FTBTokenFactory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private FTBTokenFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private FTBTokenFactory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private FTBTokenFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
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

    public Future<ThanosTransactionReceipt> createToken() {
        Function function = new Function("createToken", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void createToken(TransactionSucCallback callback) {
        Function function = new Function("createToken", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Address> controller() {
        Function function = new Function("controller",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<FTBTokenFactory> deployAddBin(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Address _controller, String bin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_controller));
        return deployAsync(FTBTokenFactory.class, web3j, credentials, gasPrice, gasLimit, bin, encodedConstructor, initialWeiValue);
    }

    public static Future<FTBTokenFactory> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Address _controller) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_controller));
        return deployAsync(FTBTokenFactory.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static Future<FTBTokenFactory> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Address _controller) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_controller));
        return deployAsync(FTBTokenFactory.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static FTBTokenFactory load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FTBTokenFactory(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static FTBTokenFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FTBTokenFactory(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static FTBTokenFactory loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FTBTokenFactory(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static FTBTokenFactory loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FTBTokenFactory(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static class CreateSuccessEventEventResponse {
        public Address addr;
    }
}
