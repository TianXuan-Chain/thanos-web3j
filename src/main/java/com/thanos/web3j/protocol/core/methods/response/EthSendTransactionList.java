package com.thanos.web3j.protocol.core.methods.response;

import com.thanos.web3j.protocol.core.Response;

import java.util.List;

/**
 * eth_sendTransaction.
 */
public class EthSendTransactionList extends Response<List<String>> {
    public List<String> getTransactionHash() {
        return getResult();
    }
}
