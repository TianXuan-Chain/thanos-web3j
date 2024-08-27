package com.thanos.web3j.channel.client;

import com.thanos.web3j.channel.dto.ChannelPush;

public abstract class ChannelPushCallback {
	public abstract void onPush(ChannelPush push);
}