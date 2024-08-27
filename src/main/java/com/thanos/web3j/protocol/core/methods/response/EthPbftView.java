package com.thanos.web3j.protocol.core.methods.response;

import java.math.BigInteger;

import com.thanos.web3j.protocol.core.Response;
import com.thanos.web3j.utils.Numeric;

public class EthPbftView extends Response<String>{
	
	 public BigInteger getEthPbftView() {
	        return Numeric.decodeQuantity(getResult());
	    }

}
