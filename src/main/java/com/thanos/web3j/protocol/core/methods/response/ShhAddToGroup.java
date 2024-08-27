package com.thanos.web3j.protocol.core.methods.response;

import com.thanos.web3j.protocol.core.Response;

/**
 * shh_addToGroup.
 */
public class ShhAddToGroup extends Response<Boolean> {

    public boolean addedToGroup() {
        return getResult();
    }
}
