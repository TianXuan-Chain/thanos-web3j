package com.thanos.web3j.protocol.core.methods.response;

import java.math.BigInteger;

import com.thanos.web3j.protocol.core.Response;
import com.thanos.web3j.utils.Numeric;

/**
 * shh_newFilter.
 */
public class ShhNewFilter extends Response<String> {

    public BigInteger getFilterId() {
        return Numeric.decodeQuantity(getResult());
    }
}
