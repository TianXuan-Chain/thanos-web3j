package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed8x224 extends Fixed {
    public static final Fixed8x224 DEFAULT = new Fixed8x224(BigInteger.ZERO);

    public Fixed8x224(BigInteger value) {
        super(8, 224, value);
    }

    public Fixed8x224(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(8, 224, m, n);
    }
}
