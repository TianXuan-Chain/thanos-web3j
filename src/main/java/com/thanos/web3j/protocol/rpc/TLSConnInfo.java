package com.thanos.web3j.protocol.rpc;

import com.thanos.web3j.utils.Files;
import com.thanos.web3j.utils.Strings;

import java.io.File;
import java.util.Map;

/**
 * 类RpcConnInfo.java的实现描述：
 *
 * @author xuhao create on 2021/3/15 16:39
 */

public class TLSConnInfo {
    //是否需要tls
    private boolean needTls;
    //节点私钥文件路径
    private String keyPath;
    //证书链文件路径
    private String certsPath;

    //gateway rpc ip:port
    private String ipPort;

    public TLSConnInfo(boolean needTls, String keyPath, String certsPath, String ipPort) {
        this.needTls = needTls;
        this.keyPath = keyPath;
        this.certsPath = certsPath;
        this.ipPort = ipPort;
    }

    public static TLSConnInfo convertFromIniFile(String filePath) {
        Map<String, String> infoMap = Files.getInfoMapFromFile(new File(filePath));
        String needTlsStr = infoMap.get("needTls");
        boolean needTls = false;
        if (!Strings.isEmpty(needTlsStr) && needTlsStr.equals("true")) {
            needTls = true;
        }
        String keyPath = infoMap.get("keyPath");
        String certsPath = infoMap.get("certsPath");
        String ipPort = infoMap.get("ipPort");
        return new TLSConnInfo(needTls, keyPath, certsPath, ipPort);
    }

    public boolean isNeedTls() {
        return needTls;
    }

    public String getKeyPath() {
        return keyPath;
    }
    public String getCertsPath() {
        return certsPath;
    }

    public String getIpPort() {
        return ipPort;
    }
}
