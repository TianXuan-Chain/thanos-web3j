package com.thanos.web3j.protocol.core.methods.response;

import com.thanos.web3j.protocol.core.Response;

/**
 * db_getHex.
 */
public class DbGetHex extends Response<String> {

    public String getStoredValue() {
        return getResult();
    }
}
