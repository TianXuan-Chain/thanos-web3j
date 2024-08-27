package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed56x32 extends Ufixed {
    public static final Ufixed56x32 DEFAULT = new Ufixed56x32(BigInteger.ZERO);

    public Ufixed56x32(BigInteger value) {
        super(56, 32, value);
    }

    public Ufixed56x32(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(56, 32, m, n);
    }
}
