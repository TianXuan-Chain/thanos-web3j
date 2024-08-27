package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed16x144 extends Fixed {
    public static final Fixed16x144 DEFAULT = new Fixed16x144(BigInteger.ZERO);

    public Fixed16x144(BigInteger value) {
        super(16, 144, value);
    }

    public Fixed16x144(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(16, 144, m, n);
    }
}
