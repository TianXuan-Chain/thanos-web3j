package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed24x160 extends Ufixed {
    public static final Ufixed24x160 DEFAULT = new Ufixed24x160(BigInteger.ZERO);

    public Ufixed24x160(BigInteger value) {
        super(24, 160, value);
    }

    public Ufixed24x160(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(24, 160, m, n);
    }
}
