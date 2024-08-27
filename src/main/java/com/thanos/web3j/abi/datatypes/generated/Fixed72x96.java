package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed72x96 extends Fixed {
    public static final Fixed72x96 DEFAULT = new Fixed72x96(BigInteger.ZERO);

    public Fixed72x96(BigInteger value) {
        super(72, 96, value);
    }

    public Fixed72x96(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(72, 96, m, n);
    }
}
