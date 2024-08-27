package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed16x128 extends Fixed {
    public static final Fixed16x128 DEFAULT = new Fixed16x128(BigInteger.ZERO);

    public Fixed16x128(BigInteger value) {
        super(16, 128, value);
    }

    public Fixed16x128(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(16, 128, m, n);
    }
}
