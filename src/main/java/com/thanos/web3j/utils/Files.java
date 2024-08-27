package com.thanos.web3j.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File utility functions.
 */
public class Files {
//    static Logger logger = LoggerFactory.getLogger(Files.class);

    private Files() {
    }

    public static byte[] readBytes(File file) throws IOException {
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
        } catch (Exception e) {
//            logger.error("readBytes error :", e);
            throw e;
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
        return bytes;
    }

    public static String readString(File file) throws IOException {
        return new String(readBytes(file));
    }

    public static Map<String, String> getInfoMapFromFile(File file) {
        Properties props = new Properties();
        if (file.canRead()) {
            try (Reader r = new FileReader(file)) {
                props.load(r);
                Map<String, String> result = new HashMap(props);
                return result;
            } catch (IOException e) {
                throw new RuntimeException("Error reading file:" + file.getPath(), e);
            }
        } else {
            throw new RuntimeException("Can't read file:" + file.getPath());
        }
    }
}
