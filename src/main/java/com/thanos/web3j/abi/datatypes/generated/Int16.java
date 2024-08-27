package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Int;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Int16 extends Int {
    public static final Int16 DEFAULT = new Int16(BigInteger.ZERO);

    public Int16(BigInteger value) {
        super(16, value);
    }

    public Int16(long value) {
        this(BigInteger.valueOf(value));
    }
}
