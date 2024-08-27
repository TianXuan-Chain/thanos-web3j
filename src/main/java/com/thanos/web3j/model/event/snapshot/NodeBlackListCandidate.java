package com.thanos.web3j.model.event.snapshot;

import com.thanos.common.utils.ByteUtil;
import com.thanos.common.utils.rlp.RLP;
import com.thanos.common.utils.rlp.RLPList;
import com.thanos.web3j.model.RLPModel;
import com.thanos.web3j.model.event.VoteNodeBlackListCandidateEvent;
import org.spongycastle.util.encoders.Hex;

import java.util.Arrays;
import java.util.Objects;

/**
 * NodeBlackListCandidate.java description：
 *
 * @Author laiyiyu create on 2021-05-12 16:44:04
 */
public class NodeBlackListCandidate extends RLPModel {


    int processType;

    byte[] proposalId;

    //node public key
    byte[] caHash;

    public NodeBlackListCandidate(byte[] encode) {
        super(encode);
    }

    public NodeBlackListCandidate(int processType, byte[] proposalId, byte[] caHash) {
        super(null);
        this.processType = processType;
        this.proposalId = proposalId;
        this.caHash = caHash;
        this.rlpEncoded = rlpEncoded();
    }

    public int getProcessType() {
        return processType;
    }

    public byte[] getProposalId() {
        return proposalId;
    }

    public byte[] getCaHash() {
        return caHash;
    }

    @Override
    protected byte[] rlpEncoded() {
        byte[] voteType = RLP.encodeInt(this.processType);
        byte[] proposalId = RLP.encodeElement(this.proposalId);
        byte[] caHash = RLP.encodeElement(this.caHash);
        return RLP.encodeList(voteType, proposalId, caHash);
    }

    @Override
    protected void rlpDecoded() {
        RLPList rlpInfo = (RLPList) RLP.decode2(rlpEncoded).get(0);
        this.processType = ByteUtil.byteArrayToInt(rlpInfo.get(0).getRLPData());
        this.proposalId = ByteUtil.copyFrom(rlpInfo.get(1).getRLPData());
        this.caHash = ByteUtil.copyFrom(rlpInfo.get(2).getRLPData());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeBlackListCandidate that = (NodeBlackListCandidate) o;
        return processType == that.processType &&
                Arrays.equals(proposalId, that.proposalId) &&
                Arrays.equals(caHash, that.caHash);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(processType);
        result = 31 * result + Arrays.hashCode(proposalId);
        result = 31 * result + Arrays.hashCode(caHash);
        return result;
    }

    @Override
    public String toString() {
        return "NodeBlackListCandidate{" +
                "processType=" + processType +
                ", proposalId=" + Hex.toHexString(proposalId) +
                ", caHash=" + Hex.toHexString(caHash) +
                '}';
    }
}
