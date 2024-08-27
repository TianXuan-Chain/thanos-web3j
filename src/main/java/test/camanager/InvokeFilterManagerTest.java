package test.camanager;

import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.common.utils.ByteUtil;
import com.thanos.web3j.abi.FunctionEncoder;
import com.thanos.web3j.abi.TypeReference;
import com.thanos.web3j.abi.datatypes.Function;
import com.thanos.web3j.abi.datatypes.Type;
import com.thanos.web3j.abi.datatypes.generated.Uint256;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.eventgen.InvokeFilterManager;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.request.RawTransaction;
import com.thanos.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import com.thanos.web3j.protocol.core.methods.response.EthSendTransaction;
import com.thanos.web3j.protocol.exceptions.TransactionTimeoutException;
import com.thanos.web3j.tx.RawTransactionManager;
import com.thanos.web3j.tx.TransactionConstant;
import com.thanos.web3j.utils.SystemConstant;
import org.spongycastle.util.encoders.Hex;
import test.add.Add;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * 类InvokeFilterManagerTest.java的实现描述：
 *
 * @author xuhao create on 2021/6/17 15:57
 */

public class InvokeFilterManagerTest extends ManageTestBase {

    public static void main(String[] args) {
        try {
            invokeFail(web3Manager.getWeb3jRandomly(), committee0, committee0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //部署成功，调用失败
    public static void invokeFail(Web3j web3j, SecureKey sender, SecureKey invoker) throws ExecutionException, InterruptedException {
        Credentials committeeCred = Credentials.create(sender);
        InvokeFilterManager.setEnableInvokeCheck(web3j, sender);
        Add add = Add.deploy(web3j, committeeCred, SystemConstant.GAS_PRICE, SystemConstant.GAS_LIMIT, BigInteger.ZERO).get();
        String contractAddress = add.getContractAddress();
        InvokeFilterManager.setContractInvokeBlackMode(web3j, sender, Hex.decode(contractAddress));
        //新增合约调用检测的黑名单用户
        Credentials invokeCred = Credentials.create(invoker);
        InvokeFilterManager.addContractInvokeBlackUser(web3j, sender, Hex.decode(contractAddress), Hex.decode(invokeCred.getAddress().substring(2)));
        Add proxy = Add.load(contractAddress, web3j, invokeCred, BigInteger.ONE, BigInteger.valueOf(3000000));
        ThanosTransactionReceipt receipt = proxy.test(new Uint256(1), new Uint256(2)).get();
        logger.info("调用结果：{}", ByteUtil.byteArrayToInt(receipt.getExecutionResult()));
    }


    public static void setContractWhiteModeAndAddUser() throws Exception {
        Web3j web3j = web3Manager.getWeb3jRandomly();
        //1、设置启用合约调用检测
        InvokeFilterManager.setEnableInvokeCheck(web3j, opStaff0);
        //2、设置指定合约调用检测为白名单模式
        InvokeFilterManager.setContractInvokeWhiteMode(web3j, opStaff0, Hex.decode("62ca5b8cf70b911f0e4c22476bb0efae5db5266d"));
        //3、新增合约调用检测的白名单用户
        InvokeFilterManager.addContractInvokeWhiteUser(web3j, opStaff0, Hex.decode("62ca5b8cf70b911f0e4c22476bb0efae5db5266d"), Hex.decode("11b8b06a8ff4f057df6ae9754380314a5a9f476e"));


        RawTransactionManager transactionManager = new RawTransactionManager(web3j, Credentials.create(committeeCandidate1));
        sendTransaction(transactionManager);

        transactionManager = new RawTransactionManager(web3j, Credentials.create(opStaff0));
        sendTransaction(transactionManager);
    }


    private static void sendTransaction(RawTransactionManager transactionManager) throws IOException, TransactionTimeoutException, InterruptedException {
        Function function = new Function("setHelloInfo", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        String data = FunctionEncoder.encode(function);
        UUID uuid = UUID.randomUUID();
        BigInteger randomid = new BigInteger(uuid.toString().replaceAll("-", ""), 16);
        long futureEventNum = transactionManager.getThanosLatestConsensusNumber() + TransactionConstant.DS_CHECK_MAX_FUTURE_NUM;

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                randomid,
                new BigInteger("0"),
                new BigInteger("300000000"),
                "62ca5b8cf70b911f0e4c22476bb0efae5db5266d",
                BigInteger.ZERO,
                data,
                null,
                futureEventNum);
        EthSendTransaction transaction = transactionManager.signAndSend(rawTransaction);
        logger.info("tnx hash: {}", transaction.getTransactionHash());
        ThanosTransactionReceipt receipt = transactionManager.waitForTransactionReceipt(transaction.getTransactionHash());
        logger.info("receipt executeResult:{}, error:{}.", Hex.toHexString(receipt.getExecutionResult()), receipt.getError());
    }


}
