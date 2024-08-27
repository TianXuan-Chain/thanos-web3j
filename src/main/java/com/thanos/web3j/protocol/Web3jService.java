package com.thanos.web3j.protocol;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import com.thanos.web3j.protocol.core.Request;
import com.thanos.web3j.protocol.core.Response;

/**
 * Services API.
 */
public interface Web3jService {
    <T extends Response> T send(
            Request request, Class<T> responseType) throws IOException;

    <T extends Response> CompletableFuture<T> sendAsync(
            Request request, Class<T> responseType);

    void close();

    boolean isActive();
    }
