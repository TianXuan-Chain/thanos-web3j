package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Uint;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Uint200 extends Uint {
    public static final Uint200 DEFAULT = new Uint200(BigInteger.ZERO);

    public Uint200(BigInteger value) {
        super(200, value);
    }

    public Uint200(long value) {
        this(BigInteger.valueOf(value));
    }
}
