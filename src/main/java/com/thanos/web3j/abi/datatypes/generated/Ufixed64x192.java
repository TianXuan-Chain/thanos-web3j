package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed64x192 extends Ufixed {
    public static final Ufixed64x192 DEFAULT = new Ufixed64x192(BigInteger.ZERO);

    public Ufixed64x192(BigInteger value) {
        super(64, 192, value);
    }

    public Ufixed64x192(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(64, 192, m, n);
    }
}
