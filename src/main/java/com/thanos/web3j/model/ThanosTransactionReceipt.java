package com.thanos.web3j.model;


import com.thanos.common.utils.rlp.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.thanos.common.utils.ByteUtil.EMPTY_BYTE_ARRAY;

public class ThanosTransactionReceipt {

    private ThanosEthTransaction thanosTransaction;

    private List<ThanosLogInfo> thanosLogInfoList;

    private byte[] gasUsed;
    private byte[] executionResult;
    private String error;

    public ThanosEthTransaction getTransaction() {
        return thanosTransaction;
    }

    public void setTransaction(ThanosEthTransaction thanosTransaction) {
        this.thanosTransaction = thanosTransaction;
    }

    public List<ThanosLogInfo> getLogInfoList() {
        return thanosLogInfoList;
    }

    public void setLogInfoList(List<ThanosLogInfo> thanosLogInfoList) {
        this.thanosLogInfoList = thanosLogInfoList;
    }

    public byte[] getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(byte[] gasUsed) {
        this.gasUsed = gasUsed;
    }

    public byte[] getExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(byte[] executionResult) {
        this.executionResult = executionResult;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void rlpDecoded(byte[] bytes) {
        RLPList params = RLP.decode2(bytes);
        RLPList receipt = (RLPList) params.get(0);

        RLPList logs = (RLPList) receipt.get(0);
        RLPList transactionRLP = (RLPList) receipt.get(1);
        RLPItem gasUsedRLP = (RLPItem) receipt.get(2);
        RLPItem result = (RLPItem) receipt.get(3);

        if (thanosLogInfoList == null) {
            thanosLogInfoList = new ArrayList<>(4);
        }

        for (RLPElement log : logs) {
            ThanosLogInfo logInfo = new ThanosLogInfo(log.getRLPData());
            thanosLogInfoList.add(logInfo);
        }
        thanosTransaction = new ThanosEthTransaction(transactionRLP.getRLPData());
        gasUsed = gasUsedRLP.getRLPData();
        executionResult = (executionResult = result.getRLPData()) == null ? EMPTY_BYTE_ARRAY : executionResult;

        if (receipt.size() > 4) {
            byte[] errBytes = receipt.get(4).getRLPData();
            error = errBytes != null ? new String(errBytes, StandardCharsets.UTF_8) : "";
        }

        //this.recepitHash = HashUtil.sha3(this.rlpEncoded);
    }


}
