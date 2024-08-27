package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed48x56 extends Fixed {
    public static final Fixed48x56 DEFAULT = new Fixed48x56(BigInteger.ZERO);

    public Fixed48x56(BigInteger value) {
        super(48, 56, value);
    }

    public Fixed48x56(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(48, 56, m, n);
    }
}
