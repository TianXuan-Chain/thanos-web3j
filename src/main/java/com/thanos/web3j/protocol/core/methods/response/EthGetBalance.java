package com.thanos.web3j.protocol.core.methods.response;

import java.math.BigInteger;

import com.thanos.web3j.protocol.core.Response;
import com.thanos.web3j.utils.Numeric;

/**
 * eth_getBalance.
 */
public class EthGetBalance extends Response<String> {
    public BigInteger getBalance() {
        return Numeric.decodeQuantity(getResult());
    }
}
