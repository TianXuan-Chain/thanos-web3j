package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed224x32 extends Ufixed {
    public static final Ufixed224x32 DEFAULT = new Ufixed224x32(BigInteger.ZERO);

    public Ufixed224x32(BigInteger value) {
        super(224, 32, value);
    }

    public Ufixed224x32(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(224, 32, m, n);
    }
}
