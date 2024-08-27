package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed112x128 extends Ufixed {
    public static final Ufixed112x128 DEFAULT = new Ufixed112x128(BigInteger.ZERO);

    public Ufixed112x128(BigInteger value) {
        super(112, 128, value);
    }

    public Ufixed112x128(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(112, 128, m, n);
    }
}
