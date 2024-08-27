package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed48x192 extends Fixed {
    public static final Fixed48x192 DEFAULT = new Fixed48x192(BigInteger.ZERO);

    public Fixed48x192(BigInteger value) {
        super(48, 192, value);
    }

    public Fixed48x192(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(48, 192, m, n);
    }
}
