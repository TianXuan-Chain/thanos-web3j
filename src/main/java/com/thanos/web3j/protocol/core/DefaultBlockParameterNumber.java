package com.thanos.web3j.protocol.core;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonValue;

import com.thanos.web3j.utils.Numeric;

/**
 * DefaultBlockParameter implementation that takes a numeric value.
 */
public class DefaultBlockParameterNumber implements DefaultBlockParameter {

    private BigInteger blockNumber;

    public DefaultBlockParameterNumber(BigInteger blockNumber) {
        this.blockNumber = blockNumber;
    }

    public DefaultBlockParameterNumber(long blockNumber) {
        this(BigInteger.valueOf(blockNumber));
    }

    @Override
    @JsonValue
    public String getValue() {
        return Numeric.encodeQuantity(blockNumber);
    }

    public BigInteger getBlockNumber() {
        return blockNumber;
    }
}
