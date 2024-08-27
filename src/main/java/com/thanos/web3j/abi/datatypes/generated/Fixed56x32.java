package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed56x32 extends Fixed {
    public static final Fixed56x32 DEFAULT = new Fixed56x32(BigInteger.ZERO);

    public Fixed56x32(BigInteger value) {
        super(56, 32, value);
    }

    public Fixed56x32(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(56, 32, m, n);
    }
}
