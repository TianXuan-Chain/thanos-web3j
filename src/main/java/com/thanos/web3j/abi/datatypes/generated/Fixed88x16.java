package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed88x16 extends Fixed {
    public static final Fixed88x16 DEFAULT = new Fixed88x16(BigInteger.ZERO);

    public Fixed88x16(BigInteger value) {
        super(88, 16, value);
    }

    public Fixed88x16(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(88, 16, m, n);
    }
}
