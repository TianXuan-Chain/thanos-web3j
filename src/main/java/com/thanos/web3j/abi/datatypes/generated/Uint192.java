package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Uint;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Uint192 extends Uint {
    public static final Uint192 DEFAULT = new Uint192(BigInteger.ZERO);

    public Uint192(BigInteger value) {
        super(192, value);
    }

    public Uint192(long value) {
        this(BigInteger.valueOf(value));
    }
}
