package test.helloword;

import com.google.gson.Gson;
import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.abi.datatypes.Utf8String;
import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.manage.Web3Manager;
import com.thanos.web3j.tx.RawTransactionManager;
import com.thanos.web3j.utils.ConfigResourceUtil;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class HelloWordBatchReceiptTest {

    private static Gson gson = new Gson();
    private static Web3Manager web3Manager;
    private static Credentials credentials;
    private static SystemConfig systemConfig;

    static {
        systemConfig = ConfigResourceUtil.loadSystemConfig();
        ConfigResourceUtil.loadLogConfig(systemConfig.logConfigPath());
        web3Manager = new Web3Manager(systemConfig);

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
        Web3j web3j = web3Manager.getWeb3jRandomly();

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
        System.out.println(hash);

        RawTransactionManager transactionManager = new RawTransactionManager(web3j, null);
        List<ThanosTransactionReceipt> receipts = transactionManager.getThanosTransactionsByHashes(Arrays.asList(hash));
        System.out.println(Hex.toHexString(receipts.get(0).getTransaction().getHash()));

        ThanosTransactionReceipt receipt = transactionManager.getThanosTransactionByHashByChain(Hex.toHexString(receipts.get(0).getTransaction().getHash()));
        System.out.println(gson.toJson(receipt));
    }

}
