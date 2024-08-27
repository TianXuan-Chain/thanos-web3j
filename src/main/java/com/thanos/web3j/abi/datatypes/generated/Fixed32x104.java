package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed32x104 extends Fixed {
    public static final Fixed32x104 DEFAULT = new Fixed32x104(BigInteger.ZERO);

    public Fixed32x104(BigInteger value) {
        super(32, 104, value);
    }

    public Fixed32x104(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(32, 104, m, n);
    }
}
