package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed64x176 extends Fixed {
    public static final Fixed64x176 DEFAULT = new Fixed64x176(BigInteger.ZERO);

    public Fixed64x176(BigInteger value) {
        super(64, 176, value);
    }

    public Fixed64x176(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(64, 176, m, n);
    }
}
