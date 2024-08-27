package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed120x72 extends Fixed {
    public static final Fixed120x72 DEFAULT = new Fixed120x72(BigInteger.ZERO);

    public Fixed120x72(BigInteger value) {
        super(120, 72, value);
    }

    public Fixed120x72(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(120, 72, m, n);
    }
}
