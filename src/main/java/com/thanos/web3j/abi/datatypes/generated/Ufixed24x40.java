package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed24x40 extends Ufixed {
    public static final Ufixed24x40 DEFAULT = new Ufixed24x40(BigInteger.ZERO);

    public Ufixed24x40(BigInteger value) {
        super(24, 40, value);
    }

    public Ufixed24x40(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(24, 40, m, n);
    }
}
