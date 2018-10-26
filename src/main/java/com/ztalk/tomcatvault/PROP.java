package com.ztalk.tomcatvault;

import org.apache.log4j.Logger;

import java.io.*;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Properties;

/**
 * PROP class generates and reads the vault.properties file from the file system, stores it in a hash map that later can be cloned
 *            for vault configurations of resource specified hash maps with different modifications.
 *
 * Auther: Frank
 */
public class PROP{
    static private final long serialVersionUID = 1L;
    static private final Logger logger = Logger.getLogger(PROP.class);
    static private final HashMap<String, String> globalPropMap = new HashMap<>();
    static private final HashMap<String, HashMap<String,String>> resourceMap = new HashMap<>();

    static {
        // Load a vault.properties file from where the jar file is run to a globalPropMap
        File f = new File("vault.properties");
        if(f.exists() && !f.isDirectory()) {
            try (InputStream in = new FileInputStream("vault.properties")) {
                Properties prop = new Properties();
                prop.load(in);
                for (String name: prop.stringPropertyNames()) {
                    globalPropMap.put(name, prop.getProperty(name));
                }
                if(logger.isDebugEnabled()) {
                    globalPropMap.forEach((k, v) -> logger.debug("GLOBAL PROP: "+k + "::" + v));
                }
                // Validate the globalPropMap
                if(!globalPropMap.containsKey("token")|| globalPropMap.get("token").isEmpty()|| globalPropMap.get("token")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "token"));
                    //throw new RuntimeException(MessageFormat.format("Missing value for key {0}!", token));
                if(!globalPropMap.containsKey("poto")|| globalPropMap.get("poto").isEmpty()|| globalPropMap.get("poto")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "poto"));
                if(!globalPropMap.containsKey("host")|| globalPropMap.get("host").isEmpty()|| globalPropMap.get("host")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "host"));
                if(!globalPropMap.containsKey("port")|| globalPropMap.get("port").isEmpty()|| globalPropMap.get("port")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "port"));
                if(!globalPropMap.containsKey("path")|| globalPropMap.get("path").isEmpty()|| globalPropMap.get("path")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "path"));
                if(!globalPropMap.containsKey("user")|| globalPropMap.get("user").isEmpty()|| globalPropMap.get("user")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "user"));
                if(!globalPropMap.containsKey("openTimeout")|| globalPropMap.get("openTimeout").isEmpty()|| globalPropMap.get("openTimeout")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "openTimeout"));
                if(!globalPropMap.containsKey("readTimeout")|| globalPropMap.get("readTimeout").isEmpty()|| globalPropMap.get("readTimeout")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "readTimeout"));
            } catch (IOException e) {
                if(logger.isDebugEnabled()) {
                    logger.debug(e.getMessage(), e.fillInStackTrace());
                }
                e.printStackTrace();
            }
        } else {
            // Create a default vault.properties file
            String name              = "vault.properties";
            String version           = "v.1";
            String token             = "25e4df62-4633-603c-1bdb-001d5f0154b9";
            String poto              = "http";
            String host              = "172.17.0.5";
            String port              = "8200";
            String path              = "secret/hello";
            String user              = "spectrum_admin";
            String openTimeout       = "5";
            String readTimeout       = "5";
            try (OutputStream out = new FileOutputStream("vault.properties")) {
                Properties properties = new Properties();
                properties.setProperty("name"       ,"Vault Properties");
                properties.setProperty("version"    ,"1");
                properties.setProperty("host"       ,host);
                properties.setProperty("poto"       ,poto);
                properties.setProperty("port"       ,port);
                properties.setProperty("token"      ,token);
                properties.setProperty("path"       ,path);
                properties.setProperty("user"       ,user);
                properties.setProperty("openTimeout",openTimeout);
                properties.setProperty("readTimeout",readTimeout);
                properties.store(out,
                        "Generating vault.properties for a Tomcat server\n" +
                        "name       ="+name         +"\n" +
                        "version    ="+version      +"\n" +
                        "token      ="+token        +"\n" +
                        "poto       ="+poto         +"\n" +
                        "host       ="+host         +"\n" +
                        "port       ="+port         +"\n" +
                        "path       ="+path         +"\n" +
                        "user       ="+user         +"\n" +
                        "openTimeout="+openTimeout  +"\n" +
                        "readTimeout="+readTimeout);
                if(logger.isDebugEnabled()) {
                    logger.debug("Generating vault.properties file");
                }
            } catch (IOException e) {
                if(logger.isDebugEnabled()) {
                    logger.debug(e.getMessage(), e.fillInStackTrace());
                }
                e.printStackTrace();
            }
        }
    }

    public static boolean addResource(String resourceName){
        if(!resourceMap.containsKey(resourceName)) {
            logger.debug("Adding a new property map for the resource: " + resourceName);
            resourceMap.put(resourceName,(HashMap<String, String>) globalPropMap.clone());
            return true;
        }
        return false;
    }

    public static HashMap getResourceMap(){
        return resourceMap;
    }

    public static int getResourceSize(String resourceName){
        if(resourceMap.containsKey(resourceName)) {
            return resourceMap.get(resourceName).size();
        }
        return 0;
    }

    public static String getResourceToken(String resourceName) {
        if(resourceMap.containsKey(resourceName)) {
            HashMap<String,String> map = resourceMap.get(resourceName);
            if (map.containsKey("token")) {
                return map.get("token");
            }
        }
        return null;
    }

    public static void setResourceToken(String resourceName, String token) {
        if(resourceMap.containsKey(resourceName)) {
            updateKVWarning(resourceName, "token", token);
        }
        resourceMap.get(resourceName).replace("token", token);
    }

    public static String getResourcePoto(String resourceName) {
        if(resourceMap.containsKey(resourceName)) {
            HashMap<String,String> map = resourceMap.get(resourceName);
            if (map.containsKey("poto")) {
                return map.get("poto");
            }
        }
        return null;
    }

    public static void setResourcePoto(String resourceName, String poto) {
        if(resourceMap.containsKey(resourceName)) {
            updateKVWarning(resourceName, "poto", poto);
        }
        resourceMap.get(resourceName).replace("poto", poto);
    }

    public static String getResourceHost(String resourceName) {
        if(resourceMap.containsKey(resourceName)) {
            HashMap<String,String> map = resourceMap.get(resourceName);
            if (map.containsKey("host")) {
                return map.get("host");
            }
        }
        return null;
    }

    public static void setResourceHost(String resourceName, String host) {
        if(resourceMap.containsKey(resourceName)) {
            updateKVWarning(resourceName, "host", host);
        }
        resourceMap.get(resourceName).replace("host", host);
    }

    public static String getResourcePort(String resourceName) {
        if(resourceMap.containsKey(resourceName)) {
            HashMap<String,String> map = resourceMap.get(resourceName);
            if (map.containsKey("port")) {
                return map.get("port");
            }
        }
        return null;
    }

    public static void setResourcePort(String resourceName, String port) {
        if(resourceMap.containsKey(resourceName)) {
            updateKVWarning(resourceName, "port", port);
        }
        resourceMap.get(resourceName).replace("port", port);
    }

    public static String getResourcePath(String resourceName) {
        if(resourceMap.containsKey(resourceName)) {
            HashMap<String,String> map = resourceMap.get(resourceName);
            if (map.containsKey("path")) {
                return map.get("path");
            }
        }
        return null;
    }

    public static void setResourcePath(String resourceName, String path) {
        if(resourceMap.containsKey(resourceName)) {
            updateKVWarning(resourceName, "path", path);
        }
        resourceMap.get(resourceName).replace("path", path);
    }

    public static String getResourceUser(String resourceName) {
        if(resourceMap.containsKey(resourceName)) {
            HashMap<String,String> map = resourceMap.get(resourceName);
            if (map.containsKey("user")) {
                return map.get("user");
            }
        }
        return null;
    }

    public static void setResourceUser(String resourceName, String user) {
        if(resourceMap.containsKey(resourceName)) {
            updateKVWarning(resourceName, "user", user);
        }
        resourceMap.get(resourceName).replace("user", user);
    }

    public static String getResourceOpenTimeout(String resourceName) {
        if(resourceMap.containsKey(resourceName)) {
            HashMap<String,String> map = resourceMap.get(resourceName);
            if (map.containsKey("openTimeout")) {
                return map.get("openTimeout");
            }
        }
        return null;
    }

    public static void setResourceOpenTimeout(String resourceName, String openTimeout) {
        if(resourceMap.containsKey(resourceName)) {
            updateKVWarning(resourceName, "openTimeout", openTimeout);
        }
        resourceMap.get(resourceName).replace("openTimeout", openTimeout);
    }

    public static String getResourceReadTimeout(String resourceName) {
        if(resourceMap.containsKey(resourceName)) {
            HashMap<String,String> map = resourceMap.get(resourceName);
            if (map.containsKey("readTimeout")) {
                return map.get("readTimeout");
            }
        }
        return null;
    }

    public static void setResourceReadTimeout(String resourceName, String readTimeout) {
        if(resourceMap.containsKey(resourceName)) {
            updateKVWarning(resourceName, "readTimeout", readTimeout);
        }
        resourceMap.get(resourceName).replace("readTimeout", readTimeout);
    }

    public static void updateKVWarning(String resourceName, String key, String value){
        HashMap<String,String> map = resourceMap.get(resourceName);
        if(map.containsKey(key)){
            logger.warn("Overwriting the value of the " + key + " to " + value + " for the resource of::" + resourceName);
        }
    }

    public static int getGlobalSize(){
        return globalPropMap.size();
    }

    public static String getGlobalToken() {
        if(globalPropMap.containsKey("token")){
            return globalPropMap.get("token");
        }
        return null;
    }

    public static void setGlobalToken(String token) {
        globalPropMap.put(token, globalPropMap.get(token) + 1);
    }

    public static String getGlobalPoto() {
        if(globalPropMap.containsKey("poto")){
            return globalPropMap.get("poto");
        }
        return null;
    }

    public static void setGlobalPoto(String poto) {
        globalPropMap.put(poto, globalPropMap.get(poto) + 1);
    }

    public static String getGlobalHost() {
        if(globalPropMap.containsKey("host")){
            return globalPropMap.get("host");
        }
        return null;
    }

    public static void setGlobalHost(String host) {
        globalPropMap.put(host, globalPropMap.get(host) + 1);
    }

    public static String getGlobalPort() {
        if(globalPropMap.containsKey("port")){
            return globalPropMap.get("port");
        }
        return null;
    }

    public static void setGlobalPort(String port) {
        globalPropMap.put(port, globalPropMap.get(port) + 1);
    }

    public static String getGlobalPath() {
        if(globalPropMap.containsKey("path")){
            return globalPropMap.get("path");
        }
        return null;
    }

    public static void setGlobalPath(String path) {
        globalPropMap.put(path, globalPropMap.get(path) + 1);
    }

    public static String getGlobalUser() {
        if(globalPropMap.containsKey("user")){
            return globalPropMap.get("user");
        }
        return null;
    }

    public static void setGlobalUser(String user) {
        globalPropMap.put(user, globalPropMap.get(user) + 1);
    }

    public static String getGlobalOpenTimeout() {
        if(globalPropMap.containsKey("openTimeout")){
            return globalPropMap.get("openTimeout");
        }
        return null;
    }

    public static void setGlobalOpenTimeout(String openTimeout) {
        globalPropMap.put(openTimeout, globalPropMap.get(openTimeout) + 1);
    }

    public static String getGlobalReadTimeout() {
        if(globalPropMap.containsKey("readTimeout")){
            return globalPropMap.get("readTimeout");
        }
        return null;
    }

    public static void setGlobalReadTimeout(String readTimeout) {
        globalPropMap.put(readTimeout, globalPropMap.get(readTimeout) + 1);
    }

}