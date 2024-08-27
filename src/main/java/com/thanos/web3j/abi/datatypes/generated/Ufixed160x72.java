package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed160x72 extends Ufixed {
    public static final Ufixed160x72 DEFAULT = new Ufixed160x72(BigInteger.ZERO);

    public Ufixed160x72(BigInteger value) {
        super(160, 72, value);
    }

    public Ufixed160x72(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(160, 72, m, n);
    }
}