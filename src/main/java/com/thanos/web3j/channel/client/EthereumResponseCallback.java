package com.thanos.web3j.channel.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thanos.web3j.channel.dto.EthereumResponse;
import io.netty.util.Timeout;

public abstract class EthereumResponseCallback {
	private static Logger logger = LoggerFactory.getLogger(EthereumResponseCallback.class);
	
	public abstract void onResponse(EthereumResponse response);
	
	public void onTimeout() {
		logger.error("Processing Ethereum message timeout:{}");
		
		EthereumResponse response = new EthereumResponse();
		response.setErrorCode(102);
		response.setErrorMessage("Processing Ethereum message timeout");
		
		response.setContent("");
		
		onResponse(response);
	}
	
	public Timeout getTimeout() {
		return timeout;
	}

	public void setTimeout(Timeout timeout) {
		this.timeout = timeout;
	}

	private Timeout timeout;
}