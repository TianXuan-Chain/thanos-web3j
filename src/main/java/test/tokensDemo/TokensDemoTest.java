package test.tokensDemo;

import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.abi.datatypes.Address;
import com.thanos.web3j.abi.datatypes.generated.Int32;
import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.manage.Web3Manager;
import com.thanos.web3j.utils.ConfigResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class TokensDemoTest {

    private static final Logger logger = LoggerFactory.getLogger("test");

    private static Web3Manager web3Manager;
    private static Credentials credentials;
    private static SystemConfig systemConfig;

    static {
        systemConfig = ConfigResourceUtil.loadSystemConfig();
        ConfigResourceUtil.loadLogConfig(systemConfig.logConfigPath());
        web3Manager=new Web3Manager(systemConfig);
        try {
//            SecureKey secureKey = SecureKey.getInstance();
//            credentials = Credentials.create(secureKey);
            SecureKey sender = SecureKey.fromPrivate(Hex.decode("0100013ec771c31cac8c0dba77a69e503765701d3c2bb62435888d4ffa38fed60c445c"));
            credentials = Credentials.create(sender);
        } catch (Exception e) {
            logger.error("NotarizationMailTest create credentials failed.", e);
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Web3j web3j = web3Manager.getWeb3jRandomly();

//        String test = "hehe";
//        Set<String> testSet = new HashSet<>();
//        testSet.add(test);
//
//        //部署
//        NBDToken.executeStatesByDeploy = testSet;
        TokensDemo dp = TokensDemo.deploy(web3j, credentials, BigInteger.valueOf(1), BigInteger.valueOf(3000000), BigInteger.valueOf(0)).get();
        System.out.println("token contract address: " + dp.getContractAddress());

        SecureKey secureKey_1 = SecureKey.getInstance("ECDSA",1);
        Credentials user_1 = Credentials.create(secureKey_1);
        //挖矿
        Set<String> u_1_set = new HashSet<>();
        u_1_set.add(user_1.getAddress());
        dp.setExecuteStates(u_1_set);
        dp.setBalance(new Address(user_1.getAddress()), new Int32(1000)).get();
        //查询挖矿后余额
        Int32 bl_1 = dp.getBalance(new Address(user_1.getAddress())).get();
        System.out.println("token user_1: " + user_1.getAddress() + ", balanceOf: " + bl_1.getValue());


        SecureKey secureKey_2 = SecureKey.getInstance("ECDSA",1);
        Credentials user_2 = Credentials.create(secureKey_2);
        //挖矿
        Set<String> u_2_set = new HashSet<>();
        u_2_set.add(user_2.getAddress());
        dp.setExecuteStates(u_2_set);
        dp.setBalance(new Address(user_2.getAddress()), new Int32(9000)).get();
        //查询挖矿后余额
        Int32 bl_2 = dp.getBalance(new Address(user_2.getAddress())).get();
        System.out.println("token user_2: " + user_2.getAddress() + ", balanceOf: " + bl_2.getValue());

        dp.transfer(new Address(user_2.getAddress()), new Address(user_1.getAddress()), new Int32(1)).get();
        System.out.println("token user_2 transfer user1 for 1 coin!!!");

        Int32 bl_1_ = dp.getBalance(new Address(user_1.getAddress())).get();
        System.out.println("token user_1: " + user_1.getAddress() + ", balanceOf: " + bl_1_.getValue());
        Int32 bl_2_ = dp.getBalance(new Address(user_2.getAddress())).get();
        System.out.println("token user_2: " + user_2.getAddress() + ", balanceOf: " + bl_2_.getValue());

    }

}
