package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed56x96 extends Fixed {
    public static final Fixed56x96 DEFAULT = new Fixed56x96(BigInteger.ZERO);

    public Fixed56x96(BigInteger value) {
        super(56, 96, value);
    }

    public Fixed56x96(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(56, 96, m, n);
    }
}
