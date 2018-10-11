package com.charter.tomcatvault;

import org.apache.log4j.Logger;

import java.io.*;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Properties;

public class PROP{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(PROP.class);
    static private HashMap<String, String> map = new HashMap<>();

    static {
        // Load a vault.properties file from where the jar file is run to a map
        File f = new File("vault.properties");
        if(f.exists() && !f.isDirectory()) {
            try (InputStream in = new FileInputStream("vault.properties")) {
                Properties prop = new Properties();
                prop.load(in);
                for (String name: prop.stringPropertyNames()) {
                    map.put(name, prop.getProperty(name));
                }
                if(logger.isDebugEnabled()) {
                    map.forEach((k, v) -> logger.debug("PROP: "+k + "::" + v));
                }
                // Validate the map
                if(!map.containsKey("token")||map.get("token").isEmpty()||map.get("token")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "token"));
                    //throw new RuntimeException(MessageFormat.format("Missing value for key {0}!", token));
                if(!map.containsKey("proto")||map.get("proto").isEmpty()||map.get("proto")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "proto"));
                if(!map.containsKey("host")||map.get("host").isEmpty()||map.get("host")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "host"));
                if(!map.containsKey("port")||map.get("port").isEmpty()||map.get("port")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "port"));
                if(!map.containsKey("path")||map.get("path").isEmpty()||map.get("path")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "path"));
                if(!map.containsKey("user")||map.get("user").isEmpty()||map.get("user")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "user"));
                if(!map.containsKey("openTimeout")||map.get("openTimeout").isEmpty()||map.get("openTimeout")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key:: {0}!", "openTimeout"));
                if(!map.containsKey("readTimeout")||map.get("readTimeout").isEmpty()||map.get("readTimeout")==null)
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
            String proto             = "http";
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
                properties.setProperty("proto"      ,proto);
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
                        "proto      ="+proto        +"\n" +
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

    public static int getSize(){
        return map.size();
    }

    public static String getToken() {
        if(map.containsKey("token")){
            return map.get("token");
        }
        return null;
    }

    public static void setToken(String token) {
        map.put(token, map.get(token) + 1);
    }

    public static String getProto() {
        if(map.containsKey("proto")){
            return map.get("proto");
        }
        return null;
    }

    public static void setProto(String proto) {
        map.put(proto, map.get(proto) + 1);
    }

    public static String getHost() {
        if(map.containsKey("host")){
            return map.get("host");
        }
        return null;
    }

    public static void setHost(String host) {
        map.put(host, map.get(host) + 1);
    }

    public static String getPort() {
        if(map.containsKey("port")){
            return map.get("port");
        }
        return null;
    }

    public static void setPort(String port) {
        map.put(port, map.get(port) + 1);
    }

    public static String getPath() {
        if(map.containsKey("path")){
            return map.get("path");
        }
        return null;
    }

    public static void setPath(String path) {
        map.put(path, map.get(path) + 1);
    }

    public static String getUser() {
        if(map.containsKey("user")){
            return map.get("user");
        }
        return null;
    }

    public static void setUser(String user) {
        map.put(user, map.get(user) + 1);
    }

    public static String getOpenTimeout() {
        if(map.containsKey("openTimeout")){
            return map.get("openTimeout");
        }
        return null;
    }

    public static void setOpenTimeout(String openTimeout) {
        map.put(openTimeout, map.get(openTimeout) + 1);
    }

    public static String getReadTimeout() {
        if(map.containsKey("readTimeout")){
            return map.get("readTimeout");
        }
        return null;
    }

    public static void setReadTimeout(String readTimeout) {
        map.put(readTimeout, map.get(readTimeout) + 1);
    }

}