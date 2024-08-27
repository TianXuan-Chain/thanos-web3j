package com.thanos.web3j.crypto;

import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.utils.Numeric;

/**
 * Credentials wrapper.
 */
public class Credentials {

    private ECKeyPair ecKeyPair;
    private final String address;
    private SecureKey secureKey;


    private Credentials(ECKeyPair ecKeyPair, String address) {
        this.ecKeyPair = ecKeyPair;
        this.address = address;
    }

    private Credentials(SecureKey secureKey, String address) {
        this.secureKey = secureKey;
        this.address = address;
    }

    public SecureKey getSecureKey() {
        return secureKey;
    }

    public ECKeyPair getEcKeyPair() {
        return ecKeyPair;
    }

    public String getAddress() {
        return address;
    }

    public static Credentials create(ECKeyPair ecKeyPair) {
        String address = Numeric.prependHexPrefix(Keys.getAddress(ecKeyPair));
        return new Credentials(ecKeyPair, address);
    }

    public static Credentials create(String privateKey, String publicKey) {
        return create(new ECKeyPair(Numeric.toBigInt(privateKey), Numeric.toBigInt(publicKey)));
    }

    public static Credentials create(String privateKey) {
        return create(ECKeyPair.create(Numeric.toBigInt(privateKey)));
    }

    public static Credentials create(SecureKey secureKey) {
        String address = Numeric.toHexString(secureKey.getAddress());
        return new Credentials(secureKey, address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Credentials that = (Credentials) o;

        if (ecKeyPair != null ? !ecKeyPair.equals(that.ecKeyPair) : that.ecKeyPair != null) {
            return false;
        }

        if (secureKey != null ? !secureKey.equals(that.secureKey) : that.secureKey != null) {
            return false;
        }

        return address != null ? address.equals(that.address) : that.address == null;
    }

    @Override
    public int hashCode() {
        int result = secureKey != null ? secureKey.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
