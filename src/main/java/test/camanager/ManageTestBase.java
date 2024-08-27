package test.camanager;

import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.crypto.sm2.util.encoders.Hex;
import com.thanos.web3j.model.state.EpochState;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.core.methods.response.EthGetNumber;
import com.thanos.web3j.protocol.core.methods.response.EthGetString;
import com.thanos.web3j.protocol.manage.Web3Manager;
import com.thanos.web3j.utils.ConfigResourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 类ManageTestBase.java的实现描述：
 *
 * @author xuhao create on 2021/6/1 10:02
 */

public abstract class ManageTestBase {
    protected static final Logger logger = LoggerFactory.getLogger("test");

    protected static ThreadPoolExecutor executorSerialize = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    protected static Web3Manager web3Manager;
    //origin committee, address:098055e7af6fcae48e7136add461e27288deff5d
    protected static SecureKey committee0;
    //address:c28ca5aae6f428d9d103719f4b04aa8e725b1a8c
    protected static SecureKey committeeCandidate1;
    //address:165644721fb30f9ad2bee25726208b852f2359e9
    protected static SecureKey committeeCandidate2;
    //address:255040b339809da81a1b7ae6478ec2af0a7e0bdb
    protected static SecureKey committeeCandidate3;


    //address:a14e6d3572df85a13ced0c7c4b8ed91240a54dd7
    protected static SecureKey opStaff0;

    protected static SystemConfig systemConfig;

    static {
        systemConfig = ConfigResourceUtil.loadSystemConfig();
        ConfigResourceUtil.loadLogConfig(systemConfig.logConfigPath());
        web3Manager = new Web3Manager(systemConfig);
        try {
            committee0 = SecureKey.fromPrivate(Hex.decode("0100015a95eca94d50c76a93f8e1de5f7aa3819403a5bb924fd858e3ebde2401b1637a"));
            committeeCandidate1 = SecureKey.fromPrivate(Hex.decode("010001dbdaa496b0a5bba113ca954a0deecda2d47275d8cdb763f16f356d3b0d955f03"));
            committeeCandidate2 = SecureKey.fromPrivate(Hex.decode("01000134ba0cedec1470fe8540cb856c031f3083c694c4941a0f83576f49f2aebb72f8"));
            committeeCandidate3 = SecureKey.fromPrivate(Hex.decode("01000125d805a3eefc6441906893b8fefc9ad0d231885d65b37440de0f88e853d04322"));
            opStaff0 = SecureKey.fromPrivate(Hex.decode("010001308f761b30da0baa33457550420bb8938d040a0c6f0582d9351fd5cead86ff11"));
        } catch (Exception e) {
            logger.error("NodeManagerTest create credentials failed.", e);
        }
    }

    public static EpochState getEpochState() {
        try {
            Web3j web3j = web3Manager.getWeb3jRandomly();
            EthGetString result = web3j.thanosGetEpochState().send();
            String stateStr = result.getString();
            if (StringUtils.isNotBlank(stateStr)) {
                byte[] data = Hex.decode(stateStr);
                EpochState epochState = new EpochState(data);
                return epochState;
            }
        } catch (Exception e) {
            logger.error("getEpochState failed. e:{}", e);
        }
        return null;
    }

    public static Long getCurrentCommitRound() {
        try {
            Web3j web3j = web3Manager.getWeb3jRandomly();
            EthGetNumber result = web3j.thanosGetCurrentCommitRound().send();
            return result.getNumber();
        } catch (Exception e) {
            logger.error("getEpochState failed. e:{}", e);
        }
        return null;
    }

    public static void main(String[] args) {
        EpochState epochState = getEpochState();
        logger.info("epochState: {}", epochState);
        System.out.println(Hex.toHexString(committee0.getAddress()));
        System.out.println(Hex.toHexString(committeeCandidate1.getAddress()));
        System.out.println(Hex.toHexString(committeeCandidate2.getAddress()));
        System.out.println(Hex.toHexString(committeeCandidate3.getAddress()));
        System.out.println(Hex.toHexString(opStaff0.getAddress()));

    }
}
