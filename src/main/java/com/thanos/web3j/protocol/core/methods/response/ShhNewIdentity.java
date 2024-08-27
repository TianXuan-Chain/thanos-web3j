package com.thanos.web3j.protocol.core.methods.response;

import com.thanos.web3j.protocol.core.Response;

/**
 * shh_newIdentity.
 */
public class ShhNewIdentity extends Response<String> {

    public String getAddress() {
        return getResult();
    }
}
