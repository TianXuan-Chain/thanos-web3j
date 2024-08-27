package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed8x56 extends Ufixed {
    public static final Ufixed8x56 DEFAULT = new Ufixed8x56(BigInteger.ZERO);

    public Ufixed8x56(BigInteger value) {
        super(8, 56, value);
    }

    public Ufixed8x56(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(8, 56, m, n);
    }
}
