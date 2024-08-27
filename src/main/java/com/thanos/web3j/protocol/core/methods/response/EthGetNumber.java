package com.thanos.web3j.protocol.core.methods.response;

import com.thanos.web3j.protocol.core.Response;

/**
 * eth_sendTransaction.
 */
public class EthGetNumber extends Response<Long> {
    public Long getNumber() {
        return getResult();
    }
}
