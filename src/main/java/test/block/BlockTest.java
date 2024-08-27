package test.block;

import com.thanos.web3j.config.SystemConfig;
import com.thanos.web3j.model.ThanosBlock;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.model.ThanosTransactionReceipt;
import com.thanos.web3j.protocol.Web3j;
import com.thanos.web3j.protocol.manage.Web3Manager;
import com.thanos.web3j.tx.RawTransactionManager;
import com.thanos.web3j.utils.ConfigResourceUtil;
import org.apache.commons.collections.CollectionUtils;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.*;

public class BlockTest {

    private static Web3Manager web3Manager;
    private static SystemConfig systemConfig;

    static {
        systemConfig = ConfigResourceUtil.loadSystemConfig();
        ConfigResourceUtil.loadLogConfig(systemConfig.logConfigPath());
        web3Manager = new Web3Manager(systemConfig);
    }

    public static void main(String[] args) throws IOException {
        Web3j web3j = web3Manager.getWeb3jRandomly();

        RawTransactionManager transactionManager = new RawTransactionManager(web3j, null);

        Long blockNumber = transactionManager.getThanosLatestConsensusNumber();

        int idx = blockNumber.intValue();
        while (idx > 12) {
            //查询区块链
            ThanosBlock block = transactionManager.getThanosBlockByNumber(Integer.valueOf(idx).toString());
            //打印区块blockHash
            System.out.println("=============================================================");
            System.out.println("blockNumber" + idx + ", " + "block hash: " + Hex.toHexString(block.getHash()));

            //打印普通交易
            if (CollectionUtils.isNotEmpty(block.getReceiptList())) {
                System.out.println("transaction iterator list::: ");
                List<ThanosTransactionReceipt> receiptList = block.getReceiptList();
                for (int t = 0; t < block.getReceiptList().size(); t++) {
                    ThanosTransactionReceipt rc = receiptList.get(t);
                    System.out.println("transactions -" + t + ", hash:" + Hex.toHexString(rc.getTransaction().getHash()));
                }
            }

            //打印节点准入准出交易
            if (CollectionUtils.isNotEmpty(block.getGlobalEventList())) {
                System.out.println("globalEvent iterator list::: ");
                List<ThanosGlobalNodeEvent> globalEventList = block.getGlobalEventList();
                for (int t = 0; t < globalEventList.size(); t++) {
                    ThanosGlobalNodeEvent rc = globalEventList.get(t);
                    System.out.println("globalEvent -" + t + ", event: " + Hex.toHexString(rc.getHash()));
                    System.out.println("                data:" + rc.getCommandCode());
                }
            }
            idx--;
        }

    }

}
