package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed168x16 extends Fixed {
    public static final Fixed168x16 DEFAULT = new Fixed168x16(BigInteger.ZERO);

    public Fixed168x16(BigInteger value) {
        super(168, 16, value);
    }

    public Fixed168x16(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(168, 16, m, n);
    }
}
