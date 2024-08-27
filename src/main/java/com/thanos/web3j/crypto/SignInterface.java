package com.thanos.web3j.crypto;
/**
 * Created by blockchain.dev on 2018/3/22.
 */
public interface SignInterface {
    Sign.SignatureData signMessage(byte[] message, ECKeyPair keyPair);
}
