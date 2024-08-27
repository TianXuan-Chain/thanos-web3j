package com.thanos.web3j.model.state;

import com.thanos.common.utils.ByteArrayWrapper;
import com.thanos.common.utils.ByteUtil;
import com.thanos.common.utils.rlp.RLP;
import com.thanos.common.utils.rlp.RLPElement;
import com.thanos.common.utils.rlp.RLPList;
import com.thanos.web3j.model.RLPModel;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ValidatorVerifier.java description：
 *
 * @Author laiyiyu create on 2020-03-02 14:59:49
 */
public class ValidatorVerifier extends RLPModel {

    public static ValidatorVerifier convertFrom(List<ValidatorPublicKeyInfo> validatorKeys) {
        return new ValidatorVerifier(validatorKeys);
    }

    TreeMap<ByteArrayWrapper, ValidatorPublicKeyInfo> pk2ValidatorInfo;

    /**
     *  The minimum voting power required to achieve a quorum
     */
    long quorumVotingPower;

    /**
     *  The remaining voting power required
     */
    long remainingVotingPower;

    /**
     * Total voting power of all validators (cached from address_to_validator_info)
     */
    long totalVotingPower;


    public ValidatorVerifier(byte[] encode) {
        super(encode);
    }

    public ValidatorVerifier(TreeMap<ByteArrayWrapper, ValidatorPublicKeyInfo> pk2ValidatorInfo) {
        super(null);
        this.pk2ValidatorInfo = pk2ValidatorInfo;
        calculate(pk2ValidatorInfo.values());
        this.rlpEncoded = rlpEncoded();
    }

    public ValidatorVerifier(List<ValidatorPublicKeyInfo> validatorPublicKeyInfos) {
        super(null);
        covertMap(validatorPublicKeyInfos);
        calculate(this.pk2ValidatorInfo.values());
        this.rlpEncoded = rlpEncoded();
    }

    private void covertMap(List<ValidatorPublicKeyInfo> validatorPublicKeyInfos) {
        TreeMap<ByteArrayWrapper, ValidatorPublicKeyInfo> pk2ValidatorInfo = new TreeMap<>();
        if (!CollectionUtils.isEmpty(validatorPublicKeyInfos)) {
            validatorPublicKeyInfos.forEach(validatorPublicKeyInfo -> pk2ValidatorInfo.put(
                    new ByteArrayWrapper(ByteUtil.copyFrom(validatorPublicKeyInfo.getConsensusPublicKey().getKey()))
                    , validatorPublicKeyInfo.clone()));
        }
        this.pk2ValidatorInfo = pk2ValidatorInfo;
    }

    private void calculate(Collection<ValidatorPublicKeyInfo> validatorPublicKeyInfos) {
        long totalVotingPower = 0;
        if (!CollectionUtils.isEmpty(validatorPublicKeyInfos)) {
            for (ValidatorPublicKeyInfo validatorInfo: validatorPublicKeyInfos) {
                totalVotingPower += validatorInfo.getConsensusVotingPower();
            }
            this.totalVotingPower = totalVotingPower;
            this.quorumVotingPower = totalVotingPower * 2 / 3 + 1;
            this.remainingVotingPower = this.totalVotingPower - this.quorumVotingPower;
        }
    }


    public ValidatorVerifier addNewValidator(ValidatorPublicKeyInfo newValidatorPKeyInfo) {
        List<ValidatorPublicKeyInfo> validatorPublicKeyInfos = exportPKInfos();

        ValidatorPublicKeyInfo oldPkInfo = this.pk2ValidatorInfo.get(new ByteArrayWrapper(newValidatorPKeyInfo.getAccountAddress()));
        if (oldPkInfo == null) {
            validatorPublicKeyInfos.add(newValidatorPKeyInfo);
        }

        return new ValidatorVerifier(validatorPublicKeyInfos);
    }

    public ValidatorVerifier removeOldValidator(ValidatorPublicKeyInfo removeValidatorPKInfo) {
        List<ValidatorPublicKeyInfo> validatorPublicKeyInfos = new ArrayList<>(pk2ValidatorInfo.size());

        for (ValidatorPublicKeyInfo validatorPublicKeyInfo: pk2ValidatorInfo.values()) {
            if (removeValidatorPKInfo.equals(validatorPublicKeyInfo)) {
                continue;
            }

            validatorPublicKeyInfos.add(validatorPublicKeyInfo.clone());
        }

        return new ValidatorVerifier(validatorPublicKeyInfos);
    }

    public ValidatorVerifier removeOldValidator(byte[] caHashBytes) {
        List<ValidatorPublicKeyInfo> validatorPublicKeyInfos = new ArrayList<>(pk2ValidatorInfo.size());
        String caHash = new String(caHashBytes);

        for (ValidatorPublicKeyInfo validatorPublicKeyInfo: pk2ValidatorInfo.values()) {
            if (caHash.equals(validatorPublicKeyInfo.getCaHash())) {
                continue;
            }

            validatorPublicKeyInfos.add(validatorPublicKeyInfo.clone());
        }

        return new ValidatorVerifier(validatorPublicKeyInfos);
    }

    public TreeMap<ByteArrayWrapper, ValidatorPublicKeyInfo> getPk2ValidatorInfo() {
        return pk2ValidatorInfo;
    }

    public List<byte[]> getOrderedPublishKeys() {
        // Since `address_to_validator_info` is a `BTreeMap`, the `.keys()` iterator
        // is guaranteed to be sorted.
        return pk2ValidatorInfo.keySet().stream().map(key -> key.getData()).collect(Collectors.toList());
    }

    public List<ValidatorPublicKeyInfo> exportPKInfos() {
        List<ValidatorPublicKeyInfo> result = new ArrayList<>(pk2ValidatorInfo.values().size());
        pk2ValidatorInfo.values().forEach(validatorInfo -> result.add(validatorInfo.clone()));
        return result;
    }



    private ValidatorPublicKeyInfo getPublicKey(ByteArrayWrapper author) {
        return this.pk2ValidatorInfo.get(author);
    }

    public boolean containPublicKey(ByteArrayWrapper pk) {
        return this.pk2ValidatorInfo.containsKey(pk);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidatorVerifier that = (ValidatorVerifier) o;
        return quorumVotingPower == that.quorumVotingPower &&
                totalVotingPower == that.totalVotingPower &&
                Objects.equals(pk2ValidatorInfo, that.pk2ValidatorInfo);
    }

    @Override
    protected byte[] rlpEncoded() {
        byte[][] validatorPublicKeyInfos = new byte[pk2ValidatorInfo.size()][];
        int i = 0;
        for (ValidatorPublicKeyInfo validatorPublicKeyInfo: pk2ValidatorInfo.values()) {
            validatorPublicKeyInfos[i] = validatorPublicKeyInfo.getEncoded();
            i++;
        }

        return RLP.encodeList(validatorPublicKeyInfos);
    }

    @Override
    protected void rlpDecoded() {
        RLPList params = RLP.decode2(rlpEncoded);
        RLPList verifier = (RLPList) params.get(0);
        //RLPList validatorPublicKeyInfos = (RLPList) verifier.get(0);

        TreeMap<ByteArrayWrapper, ValidatorPublicKeyInfo> address2ValidatorInfo = new TreeMap<>();
        for (RLPElement rlpElement: verifier) {
            ValidatorPublicKeyInfo validatorPublicKeyInfo = new ValidatorPublicKeyInfo(rlpElement.getRLPData());
            address2ValidatorInfo.put(new ByteArrayWrapper(validatorPublicKeyInfo.getAccountAddress()), validatorPublicKeyInfo);
        }

        this.pk2ValidatorInfo = address2ValidatorInfo;
        calculate(this.pk2ValidatorInfo.values());
    }

    @Override
    public String toString() {
        return "ValidatorVerifier{" +
                "pk2ValidatorInfo=" + pk2ValidatorInfo +
                ", quorumVotingPower=" + quorumVotingPower +
                ", totalVotingPower=" + totalVotingPower +
                '}';
    }
}

