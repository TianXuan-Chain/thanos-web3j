package test;



import com.thanos.web3j.crypto.Credentials;
import com.thanos.web3j.crypto.ECKeyPair;

import java.math.BigInteger;

/**
 * CredentialsConvert.java的实现描述：
 *
 * @author dumaobing  on 2018/6/28 21:07
 */
public class CredentialsConvert {

    public static Credentials getAccountCredentials(String hexStr) {
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(hexStr, 16));
        return Credentials.create(ecKeyPair);
    }

}
