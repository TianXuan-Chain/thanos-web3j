package com.thanos.web3j.protocol.rpc;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 类NoCloseOutputStream.java的实现描述：
 *
 * @author xuhao create on 2020/12/22 19:37
 */

public class NoCloseOutputStream extends FilterOutputStream {
    public NoCloseOutputStream(OutputStream out) {
        super(out);
    }

    public void close() throws IOException {
    }

    public void doClose() throws IOException {
        super.close();
    }
}
