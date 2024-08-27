package com.thanos.web3j.protocol.parity.methods.response;

import com.thanos.web3j.protocol.core.Response;

/**
 * personal_ecRecover.
 */
public class PersonalEcRecover extends Response<String> {
    public String getRecoverAccountId() {
        return getResult();
    }
}
