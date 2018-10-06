package com.charter.tomcatvault;

import java.io.*;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Properties;

public class PROP{
    private static final long serialVersionUID = 1L;
    static String name              = "vault.properties";
    static String version           = "v.1";
    static String token             = "25e4df62-4633-603c-1bdb-001d5f0154b9";
    static String proto             = "http";
    static String host              = "172.17.0.5";
    static String port              = "8200";
    static String path              = "secret/hello";
    static String user              = "spectrum_admin";
    static String openTimeout       = "5";
    static String readTimeout       = "5";
    static HashMap<String, String> map = new HashMap<>();
    static {
        File f = new File("vault.properties");
        if(f.exists() && !f.isDirectory()) {
            try (InputStream in = new FileInputStream("vault.properties")) {
                Properties prop = new Properties();
                prop.load(in);
                for (String name: prop.stringPropertyNames()) {
                    map.put(name, prop.getProperty(name));
                }
                //map.forEach((k, v) -> System.out.println((k + "::" + v)));
                if(!map.containsKey("token")&&map.get("token").isEmpty()&&map.get("token")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key {0}!", token));
                    //throw new RuntimeException(MessageFormat.format("Missing value for key {0}!", token));
                if(!map.containsKey("proto")&&map.get("proto").isEmpty()&&map.get("proto")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key {0}!", proto));
                if(!map.containsKey("host")&&map.get("host").isEmpty()&&map.get("host")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key {0}!", host));
                if(!map.containsKey("port")&&map.get("port").isEmpty()&&map.get("port")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key {0}!", port));
                if(!map.containsKey("path")&&map.get("path").isEmpty()&&map.get("path")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key {0}!", path));
                if(!map.containsKey("user")&&map.get("user").isEmpty()&&map.get("user")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key {0}!", user));
                if(!map.containsKey("openTimeout")&&map.get("openTimeout").isEmpty()&&map.get("openTimeout")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key {0}!", openTimeout));
                if(!map.containsKey("readTimeout")&&map.get("readTimeout").isEmpty()&&map.get("readTimeout")==null)
                    throw new InvalidParameterException(MessageFormat.format("Missing value for key {0}!", readTimeout));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println();
        } else {
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
                        "Generating vault.properties\n" +
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
