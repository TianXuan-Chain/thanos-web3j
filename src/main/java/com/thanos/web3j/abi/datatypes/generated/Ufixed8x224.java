package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed8x224 extends Ufixed {
    public static final Ufixed8x224 DEFAULT = new Ufixed8x224(BigInteger.ZERO);

    public Ufixed8x224(BigInteger value) {
        super(8, 224, value);
    }

    public Ufixed8x224(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(8, 224, m, n);
    }
}
