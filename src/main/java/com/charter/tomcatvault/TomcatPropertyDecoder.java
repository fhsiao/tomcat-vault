package com.charter.tomcatvault;

import com.bettercloud.vault.VaultException;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.IntrospectionUtils;


public class TomcatPropertyDecoder implements IntrospectionUtils.PropertySource {
    private static final Logger logger = Logger.getLogger(TomcatPropertyDecoder.class);

    @Override
    public String getProperty(String arg0) {
        try {
            return VaultClient.getVault();
        } catch (VaultException e) {
            logger.debug("getProperty() error: "+e.fillInStackTrace());
            e.printStackTrace();
        }
        return "";
    }

}
