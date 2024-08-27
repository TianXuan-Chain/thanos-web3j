package com.thanos.web3j.crypto;
/**
 * Created by blockchain.dev on 2018/3/4.
 */

public interface HashInterface {
      String hash(String hexInput);
      byte[] hash(byte[] input, int offset, int length);
      byte[] hash(byte[] input);
}
