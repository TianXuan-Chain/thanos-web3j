package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed64x168 extends Ufixed {
    public static final Ufixed64x168 DEFAULT = new Ufixed64x168(BigInteger.ZERO);

    public Ufixed64x168(BigInteger value) {
        super(64, 168, value);
    }

    public Ufixed64x168(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(64, 168, m, n);
    }
}
