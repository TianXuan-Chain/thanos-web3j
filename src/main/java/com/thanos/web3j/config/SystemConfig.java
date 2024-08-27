/*
 * Copyright (c) [2016] [ <ether.camp> ]
 * This file is part of the ethereumJ library.
 *
 * The ethereumJ library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ethereumJ library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ethereumJ library. If not, see <http://www.gnu.org/licenses/>.
 */
package com.thanos.web3j.config;


import com.thanos.common.crypto.key.asymmetric.SecureKey;
import com.thanos.web3j.utils.BuildInfo;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;


/**
 * Utility class to retrieve property values from the thanos-chain.conf files
 * <p>
 * The properties are taken from different sources and merged in the following order
 * (the config option from the next source overrides option from previous):
 * - resource thanos-chain.conf : normally used as a reference config with default values
 * and shouldn't be changed
 * - system property : each config entry might be altered via -D VM option
 * - [user dir]/config/thanos-chain.conf
 * - config specified with the -Dx-chain.conf.file=[file.conf] VM option
 * - CLI options
 *
 * @author laiyiyu
 * @since 22.05.2014
 */
public class SystemConfig {

    //private static Logger logger = LoggerFactory.getLogger("general");

    private static SystemConfig CONFIG;

    public static SystemConfig getDefault() {

        if (CONFIG == null) {
            CONFIG = new SystemConfig();
        }
        return CONFIG;
    }

    public byte defaultP2PVersion() {
        return 1;
    }

    /**
     * Marks config accessor methods which need to be called (for value validation)
     * upon config creation or modification
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface ValidateMe {
    }

    ;


    public Config config;


    private String projectVersion = null;

    private String projectVersionModifier = null;

    private String bindIp = null;

    private final ClassLoader classLoader;
    //sign msg
    private SecureKey myKey;

    private Integer web3Size;
    private Integer checkInterval;
    private List<String> gatewayHttpIPList;
    private List<String> gatewayRpcIPList;
    private Boolean needTLS;
    private String certsPath;
    private String keyPath;
    private String logConfigPath;

    public SystemConfig(Integer web3Size, Integer checkInterval, List<String> gatewayHttpIPList, List<String> gatewayRpcIPList,
                        Boolean needTLS, String certsPath, String keyPath, String logConfigPath) {
        this.web3Size = web3Size;
        this.checkInterval = checkInterval;
        this.gatewayHttpIPList = gatewayHttpIPList;
        this.gatewayRpcIPList = gatewayRpcIPList;
        this.needTLS = needTLS;
        this.certsPath = certsPath;
        this.keyPath = keyPath;
        this.logConfigPath = logConfigPath;
        this.classLoader = SystemConfig.class.getClassLoader();
    }

    public SystemConfig() {
        this("thanos-web3j.conf");
    }

    public SystemConfig(File configFile) {
        this(ConfigFactory.parseFile(configFile));
    }

    public SystemConfig(String configResource) {
        this(ConfigFactory.parseResources(configResource));
    }

    public SystemConfig(Config apiConfig) {
        this(apiConfig, SystemConfig.class.getClassLoader());
    }

    public SystemConfig(Config apiConfig, ClassLoader classLoader) {
        try {
            this.classLoader = classLoader;

            Config javaSystemProperties = ConfigFactory.load("no-such-resource-only-system-props");
            Config referenceConfig = ConfigFactory.parseResources("thanos-web3j.conf");
            //logger.info("Config (" + (referenceConfig.entrySet().size() > 0 ? " yes " : " no  ") + "): default properties from resource 'thanos-chain.conf'");
            config = apiConfig;
            config = apiConfig
                    .withFallback(referenceConfig);

//            logger.debug("Config trace: " + config.root().render(ConfigRenderOptions.defaults().
//                    setComments(false).setJson(false)));

            config = javaSystemProperties.withFallback(config)
                    .resolve();     // substitute variables in config if any
            validateConfig();

            // There could be several files with the same name from other packages,
            // "version.properties" is a very common name
            List<InputStream> iStreams = loadResources("version.properties", this.getClass().getClassLoader());
            for (InputStream is : iStreams) {
                Properties props = new Properties();
                props.load(is);
                if (props.getProperty("versionNumber") == null || props.getProperty("databaseVersion") == null) {
                    continue;
                }
                this.projectVersion = props.getProperty("versionNumber");
                this.projectVersion = this.projectVersion.replaceAll("'", "");

                if (this.projectVersion == null) this.projectVersion = "-.-.-";

                this.projectVersionModifier = "master".equals(BuildInfo.buildBranch) ? "RELEASE" : "SNAPSHOT";

                break;
            }
        } catch (Exception e) {
            //logger.error("Can't read config.", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads resources using given ClassLoader assuming, there could be several resources
     * with the same name
     */
    public static List<InputStream> loadResources(
            final String name, final ClassLoader classLoader) throws IOException {
        final List<InputStream> list = new ArrayList<InputStream>();
        final Enumeration<URL> systemResources =
                (classLoader == null ? ClassLoader.getSystemClassLoader() : classLoader)
                        .getResources(name);
        while (systemResources.hasMoreElements()) {
            list.add(systemResources.nextElement().openStream());
        }
        return list;
    }

    public Config getConfig() {
        return config;
    }

    private void validateConfig() {
        for (Method method : getClass().getMethods()) {
            try {
                if (method.isAnnotationPresent(ValidateMe.class)) {
                    method.invoke(this);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error validating config method: " + method, e);
            }
        }
    }


    @ValidateMe
    public String logConfigPath() {
        if (logConfigPath == null) {
            logConfigPath = config.getString("resource.logConfigPath");
        }
        return logConfigPath;
    }


    public List<String> gatewayRpcIPList() {
        if (gatewayRpcIPList == null) {
            gatewayRpcIPList = config.getStringList("gateway.rpc.ip.list");
        }
        return gatewayRpcIPList;
    }

    public int getWeb3Size() {
        if (web3Size == null) {
            web3Size = config.hasPath("gateway.web3Size") ? config.getInt("gateway.web3Size") : 3;
        }
        return web3Size;
    }

    public int getCheckInterval() {
        if (checkInterval == null) {
            checkInterval = config.hasPath("gateway.checkInterval") ? config.getInt("gateway.checkInterval") : 60;
        }
        return checkInterval;
    }

    public List<String> gatewayHttpIPList() {
        if (gatewayHttpIPList == null) {
            gatewayHttpIPList = config.getStringList("gateway.http.ip.list");
        }
        return gatewayHttpIPList;
    }


    public boolean needTLS() {
        if (needTLS == null) {
            needTLS = config.hasPath("tls.needTLS") ? config.getBoolean("tls.needTLS") : false;
        }
        return needTLS;
    }

    public String getCertsPath() {
        if (certsPath == null) {
            certsPath = config.getString("tls.certsPath");
        }
        return certsPath;
    }

    public String getKeyPath() {
        if (keyPath == null) {
            keyPath = config.getString("tls.keyPath");
        }
        return keyPath;
    }
}

