package com.thanos.web3j.tx;

import java.math.BigInteger;

/**
 * Created by blockchain.dev on 2017/11/15.
 */
public class TransactionConstant {
    public static final BigInteger version = BigInteger.ONE;
    public static final BigInteger callType = BigInteger.ZERO;
    public static final BigInteger creatType = BigInteger.ONE;

    public static final int DS_CHECK_MAX_FUTURE_NUM = 50;
}
