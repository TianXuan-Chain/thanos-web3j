package com.thanos.web3j.channel.proxy;

import com.thanos.web3j.channel.handler.Message;

public interface ResponseCallback {
	public void onResponse(Message response);
}
