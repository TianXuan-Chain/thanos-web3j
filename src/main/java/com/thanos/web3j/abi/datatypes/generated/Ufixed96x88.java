package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed96x88 extends Ufixed {
    public static final Ufixed96x88 DEFAULT = new Ufixed96x88(BigInteger.ZERO);

    public Ufixed96x88(BigInteger value) {
        super(96, 88, value);
    }

    public Ufixed96x88(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(96, 88, m, n);
    }
}
