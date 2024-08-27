package com.thanos.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import com.thanos.web3j.abi.datatypes.Ufixed;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link com.thanos.web3j.codegen.AbiTypesGenerator} to update.</p>
 */
public class Ufixed128x104 extends Ufixed {
    public static final Ufixed128x104 DEFAULT = new Ufixed128x104(BigInteger.ZERO);

    public Ufixed128x104(BigInteger value) {
        super(128, 104, value);
    }

    public Ufixed128x104(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(128, 104, m, n);
    }
}
