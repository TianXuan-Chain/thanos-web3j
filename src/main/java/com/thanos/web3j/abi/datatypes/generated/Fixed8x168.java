package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed8x168 extends Fixed {
    public static final Fixed8x168 DEFAULT = new Fixed8x168(BigInteger.ZERO);

    public Fixed8x168(BigInteger value) {
        super(8, 168, value);
    }

    public Fixed8x168(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(8, 168, m, n);
    }
}
