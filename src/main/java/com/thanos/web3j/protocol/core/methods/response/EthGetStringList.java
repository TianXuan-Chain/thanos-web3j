package com.thanos.web3j.protocol.core.methods.response;

import com.thanos.web3j.protocol.core.Response;

import java.util.List;

/**
 * db_getHex.
 */
public class EthGetStringList extends Response<List<String>> {

    public List<String> getString() {
        return getResult();
    }
}
