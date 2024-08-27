package test.tokensDemo;

import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.abi.FunctionEncoder;
import com.thanos.web3j.abi.FunctionReturnDecoder;
import com.thanos.web3j.abi.datatypes.Address;
import com.thanos.web3j.abi.datatypes.Function;
import com.thanos.web3j.abi.datatypes.Type;
import com.thanos.web3j.abi.datatypes.generated.Int32;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.request.RawTransaction;
import com.thanos.web3j.protocol.core.methods.response.EthSendTransactionList;
import com.thanos.web3j.protocol.exceptions.TransactionTimeoutException;
import com.thanos.web3j.protocol.http.OkHttpService;
import com.thanos.web3j.tx.RawTransactionManager;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class TokensBatchTest {

    private static List<Web3j> web3jList = new ArrayList<Web3j>();
    private static Credentials credentials;

    static {
        List<String> nodeListStr = Arrays.asList(
                "http://127.0.0.1:7087/rpc",
                "http://127.0.0.1:7088/rpc",
                "http://127.0.0.1:7089/rpc");
        Web3j web3j = null;
        for (String str : nodeListStr) {
            web3j = Web3j.build(new OkHttpService(str));
            web3jList.add(web3j);
        }
        try {
//            SecureKey secureKey = SecureKey.getInstance();
//            credentials = Credentials.create(secureKey);
            SecureKey sender = SecureKey.fromPrivate(Hex.decode("0100013ec771c31cac8c0dba77a69e503765701d3c2bb62435888d4ffa38fed60c445c"));
            credentials = Credentials.create(sender);
            System.out.println(credentials.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException, TransactionTimeoutException {
        Web3j web3j_0 = web3jList.get(0);
        Web3j web3j_1 = web3jList.get(1);
        Web3j web3j_2 = web3jList.get(2);

        String test = "hehe";
        Set<String> testSet = new HashSet<>();
        testSet.add(test);

        TokensDemo.executeStatesByDeploy = testSet;
        TokensDemo dp = TokensDemo.deploy(web3j_0, credentials, BigInteger.valueOf(1), BigInteger.valueOf(3000000), BigInteger.valueOf(0)).get();
        String address = dp.getContractAddress();
        System.out.println(address);

        RawTransactionManager transactionManager_0 = new RawTransactionManager(web3j_0, credentials);
        RawTransactionManager transactionManager_1 = new RawTransactionManager(web3j_1, credentials);
        RawTransactionManager transactionManager_2 = new RawTransactionManager(web3j_2, credentials);

        List<Credentials> userList = new ArrayList<>();

        //给用户设置值，9w笔
        List<RawTransaction> rawTransactions = new ArrayList<>();
        for (int i = 0; i < 90000; i++) {
            SecureKey secureKey_1 = SecureKey.getInstance("ECDSA", 1);
            Credentials user_1 = Credentials.create(secureKey_1);
            userList.add(user_1);

            String setBalanceData = TokensDemo.setBalanceData(new Address(user_1.getAddress()), new Int32(1000000));
            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            Long consensusNumber = 100l;//transactionManager.getThanosLatestConsensusNumber();
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    randomid,
                    BigInteger.valueOf(1),
                    BigInteger.valueOf(3000000),
                    address,
                    setBalanceData,
                    testSet, consensusNumber + 5);
            rawTransaction.setPublicKey(credentials.getSecureKey().getPubKey());
            rawTransaction.setCredentials(credentials);
            rawTransactions.add(rawTransaction);
            System.out.println("number" + " : " + i);
        }

        List<RawTransaction> rawTransactions_0 = rawTransactions.subList(0, 30000);
        EthSendTransactionList res_0 = transactionManager_0.SendTransactionList(rawTransactions_0);

        List<RawTransaction> rawTransactions_1 = rawTransactions.subList(30000, 60000);
        EthSendTransactionList res_1 = transactionManager_1.SendTransactionList(rawTransactions_1);

        List<RawTransaction> rawTransactions_2 = rawTransactions.subList(60000, 90000);
        EthSendTransactionList res_2 = transactionManager_2.SendTransactionList(rawTransactions_2);

        Thread.sleep(60000);

        //获取值
        int number = 0;
        for (Credentials ct : userList) {
            Function function = TokensDemo.getBalanceData(new Address(ct.getAddress()));
            ThanosTransactionReceipt receipt = transactionManager_0.thanosCall(BigInteger.valueOf(1),
                    BigInteger.valueOf(3000000),
                    address,
                    FunctionEncoder.encode(function),
                    BigInteger.ZERO,
                    testSet);
            String value = Hex.toHexString(receipt.getExecutionResult());
            List<Type> f = FunctionReturnDecoder.decode(value, function.getOutputParameters());
            BigInteger b = ((Int32) f.get(0)).getValue();
            if (10000 != b.intValue()) {
                Thread.sleep(10000);
            }
            System.out.println(number + " : " + ct.getAddress() + " : " + b);
            number++;
        }

        //给用户设置值，9w笔
        List<RawTransaction> rawTransactions_tf = new ArrayList<>();
        Set<String> exeSta;
        Credentials user_t = userList.get(0);
        for (int i = 1; i < 90000; i++) {

            Credentials user_r = userList.get(i);

            String data = TokensDemo.transferData(new Address(user_t.getAddress()), new Address(user_r.getAddress()), new Int32(1));

            exeSta = new HashSet<>();
            exeSta.add(user_t.getAddress());
            exeSta.add(user_r.getAddress());

            Random r = new SecureRandom();
            BigInteger randomid = new BigInteger(250, r);
            Long consensusNumber = 100l;//transactionManager.getThanosLatestConsensusNumber();
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    randomid,
                    BigInteger.valueOf(1),
                    BigInteger.valueOf(3000000),
                    address,
                    data,
                    exeSta, consensusNumber + 5);
            rawTransaction.setPublicKey(credentials.getSecureKey().getPubKey());
            rawTransaction.setCredentials(credentials);
            rawTransactions.add(rawTransaction);
            System.out.println("number" + " : " + i);
        }


    }

}
