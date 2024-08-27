package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed24x160 extends Fixed {
    public static final Fixed24x160 DEFAULT = new Fixed24x160(BigInteger.ZERO);

    public Fixed24x160(BigInteger value) {
        super(24, 160, value);
    }

    public Fixed24x160(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(24, 160, m, n);
    }
}
