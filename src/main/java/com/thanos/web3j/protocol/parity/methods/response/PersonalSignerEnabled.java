package com.thanos.web3j.protocol.parity.methods.response;

import com.thanos.web3j.protocol.core.Response;

/**
 * personal_signerEnabled.
 */
public class PersonalSignerEnabled extends Response<Boolean> {
    public boolean isSignerEnabled() {
        return getResult();
    }
}
