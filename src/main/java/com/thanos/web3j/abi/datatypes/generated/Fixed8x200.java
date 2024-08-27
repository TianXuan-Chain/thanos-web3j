package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed8x200 extends Fixed {
    public static final Fixed8x200 DEFAULT = new Fixed8x200(BigInteger.ZERO);

    public Fixed8x200(BigInteger value) {
        super(8, 200, value);
    }

    public Fixed8x200(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(8, 200, m, n);
    }
}
