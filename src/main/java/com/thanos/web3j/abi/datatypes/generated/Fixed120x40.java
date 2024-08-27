package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed120x40 extends Fixed {
    public static final Fixed120x40 DEFAULT = new Fixed120x40(BigInteger.ZERO);

    public Fixed120x40(BigInteger value) {
        super(120, 40, value);
    }

    public Fixed120x40(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(120, 40, m, n);
    }
}
