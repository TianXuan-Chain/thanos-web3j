package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Uint;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Uint232 extends Uint {
    public static final Uint232 DEFAULT = new Uint232(BigInteger.ZERO);

    public Uint232(BigInteger value) {
        super(232, value);
    }

    public Uint232(long value) {
        this(BigInteger.valueOf(value));
    }
}
