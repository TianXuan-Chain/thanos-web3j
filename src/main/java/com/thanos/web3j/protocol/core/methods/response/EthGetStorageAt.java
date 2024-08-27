package com.thanos.web3j.protocol.core.methods.response;

import com.thanos.web3j.protocol.core.Response;

/**
 * eth_getStorageAt.
 */
public class EthGetStorageAt extends Response<String> {
    public String getData() {
        return getResult();
    }
}
