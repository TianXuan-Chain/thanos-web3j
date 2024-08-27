package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed8x8 extends Ufixed {
    public static final Ufixed8x8 DEFAULT = new Ufixed8x8(BigInteger.ZERO);

    public Ufixed8x8(BigInteger value) {
        super(8, 8, value);
    }

    public Ufixed8x8(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(8, 8, m, n);
    }
}
