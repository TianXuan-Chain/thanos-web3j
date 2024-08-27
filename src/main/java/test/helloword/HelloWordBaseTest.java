package test.helloword;

import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.abi.datatypes.*;
import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.manage.Web3Manager;
import com.thanos.web3j.utils.ConfigResourceUtil;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HelloWordBaseTest {

    private static ThreadPoolExecutor executorSerialize = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

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

        //exeState执行修改变量；需要提前判断合约中修改的key是什么
//        String test = "hehe";
//        Set<String> testSet = new HashSet<>();
//        testSet.add(test);
//
//        //部署HelloWorld合约
//        HelloWorld.executeStatesByDeploy = testSet;
//        HelloWorld.futureEventNumber = 100l;
        HelloWorld world = HelloWorld.deploy(web3j, credentials, BigInteger.valueOf(1), BigInteger.valueOf(3000000), BigInteger.valueOf(0)).get();
        String add = world.getContractAddress();
        //打印合约部署后地址
        System.out.println("contract address: " + add);

        //set值
//        world.setExecuteStates(testSet);
        world.set(new Utf8String("100")).get();
        System.out.println("contract setValue complete.");

        Thread.sleep(1000);

        //get查询值
        Utf8String u = world.get().get();
        System.out.println("contract getValue: " + u.getValue());
        while (true) {
            Thread.sleep(5000);
            try {
                //get查询值
                u = world.get().get();
                System.out.println("contract getValue: " + u.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
