package com.thanos.core.sync;

import com.google.protobuf.ByteString;
import com.thanos.common.utils.ByteUtil;
import com.thanos.common.utils.HashUtil;
import com.thanos.common.utils.rlp.RLP;
import com.thanos.common.utils.rlp.RLPList;
import org.spongycastle.util.encoders.Hex;

import java.util.List;

public class BlockCache {

    private ByteString blockBaseInfo;

    private List<ByteString> blockTransaction;

    private List<ByteString> blockTransactionRecpit;

    private byte[] hash;
    private byte[] eventId;
    private Long number;

    public BlockCache(ByteString blockBaseInfo, List<ByteString> blockTransaction, List<ByteString> blockTransactionRecpit) {
        this.blockBaseInfo = blockBaseInfo;
        this.blockTransaction = blockTransaction;
        this.blockTransactionRecpit = blockTransactionRecpit;
    }

    public ByteString getBlockBaseInfo() {
        return blockBaseInfo;
    }

    public List<ByteString> getBlockTransaction() {
        return blockTransaction;
    }

    public List<ByteString> getBlockTransactionRecpit() {
        return blockTransactionRecpit;
    }

    public byte[] getEventId() {
        return eventId;
    }

    public byte[] getHash() {
        return hash;
    }

    public Long getNumber() {
        return number;
    }

    public void rlpDecodedBlock() {
        byte[] bytes = blockBaseInfo.toByteArray();
        RLPList params = RLP.decode2(bytes);
        RLPList block = (RLPList) params.get(0);
        this.eventId = block.get(0).getRLPData();
        byte[] preEventId = block.get(1).getRLPData();
        byte[] coinbase = block.get(2).getRLPData();
        byte[] stateRoot = block.get(3).getRLPData();
        byte[] receiptsRoot = block.get(4).getRLPData();
        Long epoch = ByteUtil.byteArrayToLong(block.get(5).getRLPData());
        this.number = ByteUtil.byteArrayToLong(block.get(6).getRLPData());
        Long timestamp = ByteUtil.byteArrayToLong(block.get(7).getRLPData());
        this.hash = HashUtil.sha3Dynamic(eventId, preEventId, coinbase, stateRoot, receiptsRoot, block.get(5).getRLPData(), block.get(6).getRLPData(), block.get(7).getRLPData());
    }

    public String getTxReceiptHash(byte[] receipt) {
        // receipt rlp decoded
        RLPList params = RLP.decode2(receipt);
        RLPList receiptD = (RLPList) params.get(0);
        RLPList transactionRLP = (RLPList) receiptD.get(1);

        // transaction rlp decoded
        RLPList decodedTxList = RLP.decode2(transactionRLP.getRLPData());
        RLPList transaction = (RLPList) decodedTxList.get(0);
        //cal hash
        return Hex.toHexString(transaction.get(10).getRLPData());
    }

}

