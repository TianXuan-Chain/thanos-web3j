package test.block;

import com.google.gson.Gson;
import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.abi.datatypes.Utf8String;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.http.OkHttpService;
import com.thanos.web3j.tx.RawTransactionManager;
import org.spongycastle.util.encoders.Hex;
import test.helloword.HelloWorld;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class GetTransactionByHash3ByChainTest {


    private static List<Web3j> web3jList = new ArrayList<Web3j>();
    private static Credentials credentials;
    private static Gson gson = new Gson();

    static {
        List<String> nodeListStr = Arrays.asList(
                "http://127.0.0.1:8080/rpc",
                "http://127.0.0.1:8080/rpc",
                "http://127.0.0.1:8080/rpc",
                "http://127.0.0.1:8080/rpc");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        Long data = new Random().nextLong();
        int num = Math.abs(data.intValue());
        int k = num % 4;
        Web3j web3j = web3jList.get(k);

        String test = "hehe";
        Set<String> testSet = new HashSet<>();
        testSet.add(test);

        HelloWorld.executeStatesByDeploy = testSet;
        HelloWorld world = HelloWorld.deploy(web3j, credentials, BigInteger.valueOf(1), BigInteger.valueOf(3000000), BigInteger.valueOf(0)).get();
        String add = world.getContractAddress();

        world.setExecuteStates(testSet);

        System.out.println(add);
        ThanosTransactionReceipt tt = world.set(new Utf8String("100")).get();

        //批量查询交易回执
        String hash = Hex.toHexString(tt.getTransaction().getHash());

        Thread.sleep(5000);

        //通过链查询交易回执
        RawTransactionManager transactionManager = new RawTransactionManager(web3j, null);
        ThanosTransactionReceipt receipt = transactionManager.getThanosTransactionByHashByChain(hash);
        System.out.println(gson.toJson(receipt));
    }

}
