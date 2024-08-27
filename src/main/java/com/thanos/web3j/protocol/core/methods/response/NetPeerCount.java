package com.thanos.web3j.protocol.core.methods.response;

import java.math.BigInteger;

import com.thanos.web3j.protocol.core.Response;
import com.thanos.web3j.utils.Numeric;

/**
 * net_peerCount.
 */
public class NetPeerCount extends Response<String> {

    public BigInteger getQuantity() {
        return Numeric.decodeQuantity(getResult());
    }
}
