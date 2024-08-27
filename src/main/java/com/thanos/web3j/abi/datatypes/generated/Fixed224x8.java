package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Fixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Fixed224x8 extends Fixed {
    public static final Fixed224x8 DEFAULT = new Fixed224x8(BigInteger.ZERO);

    public Fixed224x8(BigInteger value) {
        super(224, 8, value);
    }

    public Fixed224x8(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(224, 8, m, n);
    }
}
