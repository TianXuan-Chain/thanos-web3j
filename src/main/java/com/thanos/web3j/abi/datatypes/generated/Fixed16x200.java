package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed16x200 extends Fixed {
    public static final Fixed16x200 DEFAULT = new Fixed16x200(BigInteger.ZERO);

    public Fixed16x200(BigInteger value) {
        super(16, 200, value);
    }

    public Fixed16x200(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(16, 200, m, n);
    }
}
