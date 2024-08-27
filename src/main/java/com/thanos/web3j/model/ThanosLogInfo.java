package com.thanos.web3j.model;

import com.thanos.common.utils.rlp.*;
import com.thanos.common.utils.Numeric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThanosLogInfo {

    private final byte[] rlpEncode;

    private byte[] address;
    private List<byte[]> topics = new ArrayList<>();
    private byte[] data;

    public ThanosLogInfo(byte[] rlpEncode) {
        this.rlpEncode = rlpEncode;
        this.rlpDecoded(rlpEncode);
    }

    public byte[] getAddress() {
        return address;
    }

    public List<byte[]> getTopics() {
        return topics;
    }

    public byte[] getData() {
        return data;
    }


    private void rlpDecoded(byte[] rlpEncode) {
        RLPList params = RLP.decode2(rlpEncode);
        RLPList logInfo = (RLPList) params.get(0);

        RLPItem address = (RLPItem) logInfo.get(0);
        RLPList topics = (RLPList) logInfo.get(1);
        RLPItem data = (RLPItem) logInfo.get(2);

        this.address = address.getRLPData() != null ? address.getRLPData() : new byte[]{};
        this.data = data.getRLPData() != null ? data.getRLPData() : new byte[]{};

        for (RLPElement topic1 : topics) {
            byte[] topic = topic1.getRLPData();
            this.topics.add(topic);
        }
    }

    //store to db
    @Override
    public String toString() {
        return Numeric.toHexString(rlpEncode);
    }

    //read from db
    public static ThanosLogInfo convertFromHex(String hexStr) {
        ThanosLogInfo thanosLogInfo = new ThanosLogInfo(Numeric.hexStringToByteArray(hexStr));
        return thanosLogInfo;
    }
}
