package test.mail.contractCode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

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
import com.thanos.web3j.protocol.core.methods.response.TransactionReceipt;
import com.thanos.web3j.tx.Contract;
import com.thanos.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,to update.
 */
public final class NotarizationMailStorageFactory extends Contract {
//    private static String BINARY = "6060604052341561000c57fe5b604051602080612178833981016040528080519060200190919050505b80600060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b505b6120fc8061007c6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806398b9071714610046578063f77c479114610098575bfe5b341561004e57fe5b6100566100ea565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156100a057fe5b6100a8610368565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b600060006100f661038e565b809050604051809103906000f080151561010c57fe5b9050600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16631e59c529826000604051602001526040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825260178152602001807f4e6f746172697a6174696f6e4d61696c53746f7261676500000000000000000081525060200192505050602060405180830381600087803b151561020957fe5b6102c65a03f1151561021757fe5b50505060405180519050508073ffffffffffffffffffffffffffffffffffffffff166392eefe9b600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166000604051602001526040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050602060405180830381600087803b15156102e457fe5b6102c65a03f115156102f257fe5b50505060405180519050507fd48a3367e9d4b68bf56c2f3a09c4f33ac41b10a851d74b6290cba55aa4e9ecc681604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390a18091505b5090565b600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b604051611d328061039f83390190560060606040525b32600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b611cdb806100576000396000f30060606040523615610097576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063057af1c41461009957806338278cda1461010b5780637c26192914610246578063893d20e8146104c85780638da5cb5b1461051a57806392eefe9b1461056c578063b99c4079146105ba578063e4224d38146106f5578063f77c4791146108ca575bfe5b34156100a157fe5b6100f1600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190505061091c565b604051808215151515815260200191505060405180910390f35b341561011357fe5b61022c600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506109e3565b604051808215151515815260200191505060405180910390f35b341561024e57fe5b61029e600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610de5565b604051808060200180602001806020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018060200185810385528a81815181526020019150805190602001908083836000831461032b575b80518252602083111561032b57602082019150602081019050602083039250610307565b505050905090810190601f1680156103575780820380516001836020036101000a031916815260200191505b5085810384528981815181526020019150805190602001908083836000831461039f575b80518252602083111561039f5760208201915060208101905060208303925061037b565b505050905090810190601f1680156103cb5780820380516001836020036101000a031916815260200191505b50858103835288818151815260200191508051906020019080838360008314610413575b805182526020831115610413576020820191506020810190506020830392506103ef565b505050905090810190601f16801561043f5780820380516001836020036101000a031916815260200191505b50858103825286818151815260200191508051906020019080838360008314610487575b80518252602083111561048757602082019150602081019050602083039250610463565b505050905090810190601f1680156104b35780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390f35b34156104d057fe5b6104d86112c8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561052257fe5b61052a6112f3565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561057457fe5b6105a0600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050611319565b604051808215151515815260200191505060405180910390f35b34156105c257fe5b6106db600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506113f3565b604051808215151515815260200191505060405180910390f35b34156106fd57fe5b61074d600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050611812565b604051808060200180602001806020018481038452878181518152602001915080519060200190808383600083146107a4575b8051825260208311156107a457602082019150602081019050602083039250610780565b505050905090810190601f1680156107d05780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360008314610818575b805182526020831115610818576020820191506020810190506020830392506107f4565b505050905090810190601f1680156108445780820380516001836020036101000a031916815260200191505b5084810382528581815181526020019150805190602001908083836000831461088c575b80518252602083111561088c57602082019150602081019050602083039250610868565b505050905090810190601f1680156108b85780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b34156108d257fe5b6108da611b50565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000600073ffffffffffffffffffffffffffffffffffffffff166002836040518082805190602001908083835b6020831061096c5780518252602082019150602081019050602083039250610949565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141590505b919050565b6000604060405190810160405280601781526020017f4e6f746172697a6174696f6e4d61696c48616e646c6572000000000000000000815250600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825283818151815260200191508051906020019080838360008314610ade575b805182526020831115610ade57602082019150602081019050602083039250610aba565b505050905090810190601f168015610b0a5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b1515610b2557fe5b6102c65a03f11515610b3357fe5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610b775760006000fd5b600073ffffffffffffffffffffffffffffffffffffffff166002876040518082805190602001908083835b60208310610bc55780518252602082019150602081019050602083039250610ba2565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610c425760009150610ddb565b60a0604051908101604052808781526020018681526020018581526020013273ffffffffffffffffffffffffffffffffffffffff168152602001848152506002876040518082805190602001908083835b60208310610cb65780518252602082019150602081019050602083039250610c93565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000820151816000019080519060200190610d05929190611b76565b506020820151816001019080519060200190610d22929190611b76565b506040820151816002019080519060200190610d3f929190611b76565b5060608201518160030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506080820151816004019080519060200190610da3929190611b76565b509050507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405180905060405180910390a1600191505b5b50949350505050565b610ded611bf6565b610df5611bf6565b610dfd611bf6565b6000610e07611bf6565b6002866040518082805190602001908083835b60208310610e3d5780518252602082019150602081019050602083039250610e1a565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000016002876040518082805190602001908083835b60208310610ea95780518252602082019150602081019050602083039250610e86565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206001016002886040518082805190602001908083835b60208310610f155780518252602082019150602081019050602083039250610ef2565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206002016002896040518082805190602001908083835b60208310610f815780518252602082019150602081019050602083039250610f5e565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1660028a6040518082805190602001908083835b6020831061100e5780518252602082019150602081019050602083039250610feb565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600401848054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156110d95780601f106110ae576101008083540402835291602001916110d9565b820191906000526020600020905b8154815290600101906020018083116110bc57829003601f168201915b50505050509450838054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156111755780601f1061114a57610100808354040283529160200191611175565b820191906000526020600020905b81548152906001019060200180831161115857829003601f168201915b50505050509350828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156112115780601f106111e657610100808354040283529160200191611211565b820191906000526020600020905b8154815290600101906020018083116111f457829003601f168201915b50505050509250808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156112ad5780601f10611282576101008083540402835291602001916112ad565b820191906000526020600020905b81548152906001019060200180831161129057829003601f168201915b50505050509050945094509450945094505b91939590929450565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690505b90565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff161415156113785760006000fd5b81600060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405180905060405180910390a1600190505b5b919050565b6000604060405190810160405280601781526020017f4e6f746172697a6174696f6e4d61696c48616e646c6572000000000000000000815250600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f67187ac826000604051602001526040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252838181518152602001915080519060200190808383600083146114ee575b8051825260208311156114ee576020820191506020810190506020830392506114ca565b505050905090810190601f16801561151a5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b151561153557fe5b6102c65a03f1151561154357fe5b5050506040518051905073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156115875760006000fd5b600073ffffffffffffffffffffffffffffffffffffffff166002876040518082805190602001908083835b602083106115d557805182526020820191506020810190506020830392506115b2565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156116515760009150611808565b846002876040518082805190602001908083835b602083106116885780518252602082019150602081019050602083039250611665565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010190805190602001906116d1929190611c0a565b50836002876040518082805190602001908083835b6020831061170957805182526020820191506020810190506020830392506116e6565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206002019080519060200190611752929190611c0a565b50826002876040518082805190602001908083835b6020831061178a5780518252602082019150602081019050602083039250611767565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060040190805190602001906117d3929190611c0a565b507f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec60405180905060405180910390a1600191505b5b50949350505050565b61181a611bf6565b611822611bf6565b61182a611bf6565b6002846040518082805190602001908083835b60208310611860578051825260208201915060208101905060208303925061183d565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000016002856040518082805190602001908083835b602083106118cc57805182526020820191506020810190506020830392506118a9565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206001016002866040518082805190602001908083835b602083106119385780518252602082019150602081019050602083039250611915565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600201828054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611a035780601f106119d857610100808354040283529160200191611a03565b820191906000526020600020905b8154815290600101906020018083116119e657829003601f168201915b50505050509250818054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611a9f5780601f10611a7457610100808354040283529160200191611a9f565b820191906000526020600020905b815481529060010190602001808311611a8257829003601f168201915b50505050509150808054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611b3b5780601f10611b1057610100808354040283529160200191611b3b565b820191906000526020600020905b815481529060010190602001808311611b1e57829003601f168201915b505050505090509250925092505b9193909250565b600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611bb757805160ff1916838001178555611be5565b82800160010185558215611be5579182015b82811115611be4578251825591602001919060010190611bc9565b5b509050611bf29190611c8a565b5090565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611c4b57805160ff1916838001178555611c79565b82800160010185558215611c79579182015b82811115611c78578251825591602001919060010190611c5d565b5b509050611c869190611c8a565b5090565b611cac91905b80821115611ca8576000816000905550600101611c90565b5090565b905600a165627a7a723058206d24de7047a81dfe770ef098dfc51f730678c25726a21b296f8e2db4d52e9a5e0029a165627a7a72305820c305861385f3e2c37185f49e9ec0e1d1465e9f3c97a94d81657684ab50d049750029";
    private static String BINARY = "608060405234801561001057600080fd5b506040516020806119ee833981016040525160008054600160a060020a03909216600160a060020a031990921691909117905561199c806100526000396000f30060806040526004361061004b5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166398b907178114610050578063f77c47911461008e575b600080fd5b34801561005c57600080fd5b506100656100a3565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b34801561009a57600080fd5b50610065610294565b6000806100ae6102b0565b604051809103906000f0801580156100ca573d6000803e3d6000fd5b5060008054604080517f1e59c52900000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff808616602483015260048201839052601760448301527f4e6f746172697a6174696f6e4d61696c53746f7261676500000000000000000060648301529151949550911692631e59c52992608480840193602093929083900390910190829087803b15801561017857600080fd5b505af115801561018c573d6000803e3d6000fd5b505050506040513d60208110156101a257600080fd5b505060008054604080517f92eefe9b00000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff92831660048201529051918416926392eefe9b926024808401936020939083900390910190829087803b15801561021a57600080fd5b505af115801561022e573d6000803e3d6000fd5b505050506040513d602081101561024457600080fd5b50506040805173ffffffffffffffffffffffffffffffffffffffff8316815290517fd48a3367e9d4b68bf56c2f3a09c4f33ac41b10a851d74b6290cba55aa4e9ecc69181900360200190a1919050565b60005473ffffffffffffffffffffffffffffffffffffffff1681565b6040516116b0806102c1833901905600608060405260018054600160a060020a0319163217905561168b806100256000396000f3006080604052600436106100985763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663057af1c4811461009d57806338278cda1461010a5780637c2619291461021d578063893d20e8146104395780638da5cb5b1461046a57806392eefe9b1461047f578063b99c4079146104a0578063e4224d38146105b3578063f77c479114610750575b600080fd5b3480156100a957600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100f69436949293602493928401919081908401838280828437509497506107659650505050505050565b604080519115158252519081900360200190f35b34801561011657600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100f694369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506107e89650505050505050565b34801561022957600080fd5b506040805160206004803580820135601f8101849004840285018401909552848452610276943694929360249392840191908190840183828082843750949750610a809650505050505050565b6040518080602001806020018060200186600160a060020a0316600160a060020a031681526020018060200185810385528a818151815260200191508051906020019080838360005b838110156102d75781810151838201526020016102bf565b50505050905090810190601f1680156103045780820380516001836020036101000a031916815260200191505b5085810384528951815289516020918201918b019080838360005b8381101561033757818101518382015260200161031f565b50505050905090810190601f1680156103645780820380516001836020036101000a031916815260200191505b5085810383528851815288516020918201918a019080838360005b8381101561039757818101518382015260200161037f565b50505050905090810190601f1680156103c45780820380516001836020036101000a031916815260200191505b50858103825286518152865160209182019188019080838360005b838110156103f75781810151838201526020016103df565b50505050905090810190601f1680156104245780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390f35b34801561044557600080fd5b5061044e610ed9565b60408051600160a060020a039092168252519081900360200190f35b34801561047657600080fd5b5061044e610ee9565b34801561048b57600080fd5b506100f6600160a060020a0360043516610ef8565b3480156104ac57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100f694369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a999881019791965091820194509250829150840183828082843750949750610f699650505050505050565b3480156105bf57600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261060c9436949293602493928401919081908401838280828437509497506112ce9650505050505050565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b83811015610651578181015183820152602001610639565b50505050905090810190601f16801561067e5780820380516001836020036101000a031916815260200191505b50848103835286518152865160209182019188019080838360005b838110156106b1578181015183820152602001610699565b50505050905090810190601f1680156106de5780820380516001836020036101000a031916815260200191505b50848103825285518152855160209182019187019080838360005b838110156107115781810151838201526020016106f9565b50505050905090810190601f16801561073e5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b34801561075c57600080fd5b5061044e6115b8565b600080600160a060020a03166002836040518082805190602001908083835b602083106107a35780518252601f199092019160209182019101610784565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922060030154600160a060020a03169290921415949350505050565b604080518082018252601781527f4e6f746172697a6174696f6e4d61696c48616e646c657200000000000000000060208083019182526000805494517ff67187ac000000000000000000000000000000000000000000000000000000008152600481019283528451602482015284519195600160a060020a03169363f67187ac938693909283926044909101918083838c5b8381101561089257818101518382015260200161087a565b50505050905090810190601f1680156108bf5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b1580156108de57600080fd5b505af11580156108f2573d6000803e3d6000fd5b505050506040513d602081101561090857600080fd5b5051600160a060020a0316331461091e57600080fd5b60a06040519081016040528087815260200186815260200185815260200132600160a060020a03168152602001848152506002876040518082805190602001908083835b602083106109815780518252601f199092019160209182019101610962565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381019093208451805191946109c2945085935001906115c7565b5060208281015180516109db92600185019201906115c7565b50604082015180516109f79160028401916020909101906115c7565b50606082015160038201805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0390921691909117905560808201518051610a469160048401916020909101906115c7565b50506040517f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec9150600090a1600191505b50949350505050565b6060806060600060606002866040518082805190602001908083835b60208310610abb5780518252601f199092019160209182019101610a9c565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810184208a519094600294508b9350918291908401908083835b60208310610b1b5780518252601f199092019160209182019101610afc565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206001016002886040518082805190602001908083835b60208310610b835780518252601f199092019160209182019101610b64565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206002016002896040518082805190602001908083835b60208310610beb5780518252601f199092019160209182019101610bcc565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381018420600301548d51600160a060020a0390911694600294508e9350918291908401908083835b60208310610c595780518252601f199092019160209182019101610c3a565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208c54601f60026001831615909802909501169590950492830182900482028801820190528187526004909301959450899350918401905082828015610d155780601f10610cea57610100808354040283529160200191610d15565b820191906000526020600020905b815481529060010190602001808311610cf857829003601f168201915b5050875460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959a5089945092508401905082828015610da35780601f10610d7857610100808354040283529160200191610da3565b820191906000526020600020905b815481529060010190602001808311610d8657829003601f168201915b5050865460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815295995088945092508401905082828015610e315780601f10610e0657610100808354040283529160200191610e31565b820191906000526020600020905b815481529060010190602001808311610e1457829003601f168201915b5050845460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815295985086945092508401905082828015610ebf5780601f10610e9457610100808354040283529160200191610ebf565b820191906000526020600020905b815481529060010190602001808311610ea257829003601f168201915b505050505090509450945094509450945091939590929450565b600154600160a060020a03165b90565b600154600160a060020a031681565b600154600090600160a060020a03163214610f1257600080fd5b6000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0384161781556040517f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec9190a1506001919050565b604080518082018252601781527f4e6f746172697a6174696f6e4d61696c48616e646c657200000000000000000060208083019182526000805494517ff67187ac000000000000000000000000000000000000000000000000000000008152600481019283528451602482015284519195600160a060020a03169363f67187ac938693909283926044909101918083838c5b83811015611013578181015183820152602001610ffb565b50505050905090810190601f1680156110405780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b15801561105f57600080fd5b505af1158015611073573d6000803e3d6000fd5b505050506040513d602081101561108957600080fd5b5051600160a060020a0316331461109f57600080fd5b6000600160a060020a03166002876040518082805190602001908083835b602083106110dc5780518252601f1990920191602091820191016110bd565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922060030154600160a060020a03169290921415915061112a90505760009150610a77565b846002876040518082805190602001908083835b6020831061115d5780518252601f19909201916020918201910161113e565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010190805190602001906111a69291906115c7565b50836002876040518082805190602001908083835b602083106111da5780518252601f1990920191602091820191016111bb565b51815160209384036101000a6000190180199092169116179052920194855250604051938490038101909320845161121f9560029092019491909101925090506115c7565b50826002876040518082805190602001908083835b602083106112535780518252601f199092019160209182019101611234565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516112989560049092019491909101925090506115c7565b506040517f15504e11775a2cc1976320e3c666e5965057d329614934f0b707fc711dfb82ec90600090a150600195945050505050565b60608060606002846040518082805190602001908083835b602083106113055780518252601f1990920191602091820191016112e6565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810184208851909460029450899350918291908401908083835b602083106113655780518252601f199092019160209182019101611346565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206001016002866040518082805190602001908083835b602083106113cd5780518252601f1990920191602091820191016113ae565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208a54601f6002600183161590980290950116869004938401839004830289018301909152828852939093019594508793509184019050828280156114885780601f1061145d57610100808354040283529160200191611488565b820191906000526020600020905b81548152906001019060200180831161146b57829003601f168201915b5050855460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959850879450925084019050828280156115165780601f106114eb57610100808354040283529160200191611516565b820191906000526020600020905b8154815290600101906020018083116114f957829003601f168201915b5050845460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959750869450925084019050828280156115a45780601f10611579576101008083540402835291602001916115a4565b820191906000526020600020905b81548152906001019060200180831161158757829003601f168201915b505050505090509250925092509193909250565b600054600160a060020a031681565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061160857805160ff1916838001178555611635565b82800160010185558215611635579182015b8281111561163557825182559160200191906001019061161a565b50611641929150611645565b5090565b610ee691905b80821115611641576000815560010161164b5600a165627a7a723058204531a15aefc152725cdc83846f1c5f73cb08297cba1b0d63b8a91fab1a6609400029a165627a7a72305820d582d63b3f9695821be6647d61bfef178c63fe11c3f4b6f17796926c355f59390029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[],\"name\":\"createMailStorage\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"controller\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"inputs\":[{\"name\":\"_controller\",\"type\":\"address\"}],\"payable\":false,\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"createSuccessEvent\",\"type\":\"event\"}]";

    private NotarizationMailStorageFactory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private NotarizationMailStorageFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private NotarizationMailStorageFactory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private NotarizationMailStorageFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
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

    public Future<ThanosTransactionReceipt> createMailStorage() {
        Function function = new Function("createMailStorage", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void createMailStorage(TransactionSucCallback callback) {
        Function function = new Function("createMailStorage", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Address> controller() {
        Function function = new Function("controller",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<NotarizationMailStorageFactory> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Address _controller) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_controller));
        return deployAsync(NotarizationMailStorageFactory.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static Future<NotarizationMailStorageFactory> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Address _controller) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_controller));
        return deployAsync(NotarizationMailStorageFactory.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static NotarizationMailStorageFactory load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotarizationMailStorageFactory(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static NotarizationMailStorageFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotarizationMailStorageFactory(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static NotarizationMailStorageFactory loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotarizationMailStorageFactory(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static NotarizationMailStorageFactory loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotarizationMailStorageFactory(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static class CreateSuccessEventEventResponse {
        public Address addr;
    }
}
