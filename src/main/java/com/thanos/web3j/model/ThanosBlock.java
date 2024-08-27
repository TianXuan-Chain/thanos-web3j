package com.thanos.web3j.model;


import com.thanos.common.utils.ByteUtil;
import com.thanos.common.utils.HashUtil;
import com.thanos.common.utils.rlp.RLP;
import com.thanos.common.utils.rlp.RLPList;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ThanosBlock {

    //event publicKey, after execute transaction ,it become include receipts root hash
    private byte[] hash;

    private byte[] eventId;

    /* The SHA3 256-bit getHash of the parent event, only read for vm */
    private byte[] preEventId;
    /* The 160-bit address to which all fees collected from the
     * successful mining of this block be transferred; formally */
    private byte[] coinbase;
    /* The SHA3 256-bit getHash of the root node of the state trie,
     * after all transactions are executed and finalisations applied */
    private byte[] stateRoot;
    /* The SHA3 256-bit getHash of the root node of the trie structure
     * populated with each transaction recipe in the transaction recipes
     * list */
    private byte[] receiptsRoot;

    private long epoch;

    private long number;

    /* A scalar value equal to the reasonable output of Unix's time()  at this block's inception */
    private long timestamp;


    private List<byte[]> receipts;
    private List<byte[]> globalEvents;
    //pk to sign
    private TreeMap<byte[], byte[]> signatures = new TreeMap<>();

    private List<ThanosTransactionReceipt> receiptList;
    private List<ThanosGlobalNodeEvent> globalEventList;

    public ThanosBlock(byte[] data) {
        rlpDecoded(data);
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getEventId() {
        return eventId;
    }

    public void setEventId(byte[] eventId) {
        this.eventId = eventId;
    }

    public byte[] getPreEventId() {
        return preEventId;
    }

    public void setPreEventId(byte[] preEventId) {
        this.preEventId = preEventId;
    }

    public byte[] getCoinbase() {
        return coinbase;
    }

    public void setCoinbase(byte[] coinbase) {
        this.coinbase = coinbase;
    }

    public byte[] getStateRoot() {
        return stateRoot;
    }

    public void setStateRoot(byte[] stateRoot) {
        this.stateRoot = stateRoot;
    }

    public byte[] getReceiptsRoot() {
        return receiptsRoot;
    }

    public void setReceiptsRoot(byte[] receiptsRoot) {
        this.receiptsRoot = receiptsRoot;
    }

    public long getEpoch() {
        return epoch;
    }

    public void setEpoch(long epoch) {
        this.epoch = epoch;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public TreeMap<byte[], byte[]> getSignatures() {
        return signatures;
    }

    public void setSignatures(TreeMap<byte[], byte[]> signatures) {
        this.signatures = signatures;
    }

    public List<ThanosTransactionReceipt> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<ThanosTransactionReceipt> receiptList) {
        this.receiptList = receiptList;
    }

    public List<byte[]> getReceipts() {
        return receipts;
    }

    public List<byte[]> getGlobalEvents() {
        return globalEvents;
    }

    public List<ThanosGlobalNodeEvent> getGlobalEventList() {
        return globalEventList;
    }

    protected void rlpDecoded(byte[] data) {
        RLPList params = RLP.decode2(data);
        RLPList block = (RLPList) params.get(0);
        this.eventId = block.get(0).getRLPData();
        this.preEventId = block.get(1).getRLPData();
        this.coinbase = block.get(2).getRLPData() == null ? ByteUtil.ZERO_BYTE_ARRAY : block.get(2).getRLPData();
        this.stateRoot = block.get(3).getRLPData();
        this.receiptsRoot = block.get(4).getRLPData();
        this.epoch = ByteUtil.byteArrayToLong(block.get(5).getRLPData());
        this.number = ByteUtil.byteArrayToLong(block.get(6).getRLPData());
        this.timestamp = ByteUtil.byteArrayToLong(block.get(7).getRLPData());

        //反序列化全局事件
        byte[] globalEvent = block.get(8).getRLPData();
        rlpDecodeGlobalEvent(globalEvent);

        this.hash = HashUtil.sha3Dynamic(eventId, preEventId, coinbase, stateRoot, receiptsRoot, ByteUtil.longToBytes(epoch), ByteUtil.longToBytes(number), ByteUtil.longToBytes(timestamp));

        //反序列化回执
        byte[] rcdata = block.get(9).getRLPData();
        if (rcdata != null && rcdata.length > 0) {
            int receiptsSize = ByteUtil.byteArrayToInt(rcdata);
            List<byte[]> receipts = new ArrayList<>(receiptsSize);

            int receiptEnd = receiptsSize + 10;
            for (int i = 10; i < receiptEnd; i++) {
                receipts.add(block.get(i).getRLPData());
            }
            this.receipts = receipts;

            receiptList = new ArrayList<>();
            for (int i = 0; i < receipts.size(); i++) {
                ThanosTransactionReceipt tr = new ThanosTransactionReceipt();
                tr.rlpDecoded(receipts.get(i));
                receiptList.add(tr);
            }
        }
    }

    public void rlpDecodeGlobalEvent(byte[] rlpEncoded) {
        if (rlpEncoded == null) {
            return;
        }
        if (rlpEncoded.length <= 0) {
            return;
        }
        RLPList params = RLP.decode2(rlpEncoded);
        RLPList payload = (RLPList) params.get(0);
        int globalNodeEventsSize = ByteUtil.byteArrayToInt(payload.get(0).getRLPData());
        List<byte[]> globalNodeEvents = new ArrayList<>(globalNodeEventsSize);
        for (int i = 1; i < globalNodeEventsSize + 1; i++) {
            globalNodeEvents.add(payload.get(i).getRLPData());
        }
        this.globalEvents = globalNodeEvents;

        globalEventList = new ArrayList<>();
        for (int j = 0; j < globalEvents.size(); j++) {
            ThanosGlobalNodeEvent tr = new ThanosGlobalNodeEvent(globalEvents.get(j));
            globalEventList.add(tr);
        }
    }

}
