package com.thanos.web3j.eventgen;

import com.thanos.web3j.utils.Files;

import java.io.File;
import java.util.Map;

/**
 * 类NodeInfo.java的实现描述：
 *
 * @author xuhao create on 2021/1/21 15:16
 */

public class NodeInfo {
    //节点公钥（含前缀）
    private String publicKey;
    //节点名称
    private String name;
    //节点所属机构
    private String agency;
    //节点caHash
    private String caHash;
    //节点私钥
    private String privateKey;


    public NodeInfo(String publicKey, String name, String agency, String caHash, String privateKey) {
        this.publicKey = publicKey;
        this.name = name;
        this.agency = agency;
        this.caHash = caHash;
        this.privateKey = privateKey;
    }

    public static NodeInfo convertFromIniFile(String filePath) {
        Map<String, String> infoMap = Files.getInfoMapFromFile(new File(filePath));
        String publicKey = infoMap.get("publicKey");
        String name = infoMap.get("name");
        String agency = infoMap.get("agency");
        String caHash = infoMap.get("caHash");
        String privateKey = infoMap.get("privateKey");

        return new NodeInfo(publicKey, name, agency, caHash, privateKey);
    }


    public String getPublicKey() {
        return publicKey;
    }

    public String getName() {
        return name;
    }

    public String getAgency() {
        return agency;
    }

    public String getCaHash() {
        return caHash;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
