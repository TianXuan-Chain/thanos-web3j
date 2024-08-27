package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed16x16 extends Ufixed {
    public static final Ufixed16x16 DEFAULT = new Ufixed16x16(BigInteger.ZERO);

    public Ufixed16x16(BigInteger value) {
        super(16, 16, value);
    }

    public Ufixed16x16(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(16, 16, m, n);
    }
}
