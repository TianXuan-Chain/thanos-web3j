package com.thanos.web3j.protocol.core.methods.response;

import com.thanos.web3j.protocol.core.Response;

/**
 * eth_sendTransaction.
 */
public class EthSendTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
