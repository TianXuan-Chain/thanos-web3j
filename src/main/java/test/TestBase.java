package test;

import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.exceptions.TransactionTimeoutException;
import com.thanos.web3j.protocol.manage.Web3Manager;
import com.thanos.web3j.protocol.rpc.RpcSocketService;
import com.thanos.web3j.tx.TransactionManager;
import com.thanos.web3j.utils.ConfigResourceUtil;
import com.typesafe.config.ConfigRenderOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 类TestBase.java的实现描述：
 *
 * @author xuhao create on 2020/12/17 10:43
 */

public class TestBase {
    private static final Logger logger = LoggerFactory.getLogger("test");

    private static Web3Manager web3Manager;
    private static SystemConfig systemConfig;

    static {
        systemConfig = ConfigResourceUtil.loadSystemConfig();
        ConfigResourceUtil.loadLogConfig(systemConfig.logConfigPath());
        logger.debug("Config trace: " + systemConfig.config.root().render(ConfigRenderOptions.defaults().setComments(false).setJson(false)));
        web3Manager = new Web3Manager(systemConfig);
    }

    //length用户要求产生字符串的长度
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public List<ThanosTransactionReceipt> waitForTnxReceiptList(TransactionManager transactionManager, List<String> tnxHashList) throws InterruptedException, TransactionTimeoutException, IOException {
        if (tnxHashList == null) {
            return null;
        }
        List<ThanosTransactionReceipt> receiptList = new ArrayList<>();
        for (String tnxHash : tnxHashList) {
            ThanosTransactionReceipt receipt = transactionManager.waitForTransactionReceipt(tnxHash);
            receiptList.add(receipt);
        }
        return receiptList;
    }

    public Web3j getWeb3jRandomly() {
        return web3Manager.getWeb3jRandomly();
    }

    public Web3j getHttpWeb3jRandomly() {
        return web3Manager.getHttpWeb3jRandomly();
    }
}
