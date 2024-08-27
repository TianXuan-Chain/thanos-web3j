package com.thanos.web3j.protocol.core.methods.response;

import java.math.BigInteger;

import com.thanos.web3j.protocol.core.Response;
import com.thanos.web3j.utils.Numeric;

/**
 * eth_hashrate.
 */
public class EthHashrate extends Response<String> {
    public BigInteger getHashrate() {
        return Numeric.decodeQuantity(getResult());
    }
}
