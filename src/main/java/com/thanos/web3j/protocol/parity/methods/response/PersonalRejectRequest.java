package com.thanos.web3j.protocol.parity.methods.response;

import com.thanos.web3j.protocol.core.Response;

/**
 * personal_rejectRequest.
 */
public class PersonalRejectRequest extends Response<Boolean> {
    public boolean isRejected() {
        return getResult();
    }
}
