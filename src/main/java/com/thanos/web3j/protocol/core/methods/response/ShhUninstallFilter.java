package com.thanos.web3j.protocol.core.methods.response;

import com.thanos.web3j.protocol.core.Response;

/**
 * shh_uninstallFilter.
 */
public class ShhUninstallFilter extends Response<Boolean> {

    public boolean isUninstalled() {
        return getResult();
    }
}
