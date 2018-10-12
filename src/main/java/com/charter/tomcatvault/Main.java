package com.charter.tomcatvault;

import com.bettercloud.vault.VaultException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 *
 *  The Main class with the main() method is part of the executable jar application to facilitate the need of testing the functionality
 *          without Tomcat to be involved.
 *
 * Auther: Frank
 */

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Arg arg = Arg.getInstance();
        arg.setArgs(args);
        //// Testing global settings
        if (PROP.getGlobalSize() > 0) {
            try {
                logger.debug("default: "+VaultClient.getVault());
            } catch (VaultException e) {
                logger.debug(e.getMessage(),e.fillInStackTrace());
            }
        }
        //// Testing resource settings
        String resourceName = "resource-1";
        PROP.addResource(resourceName);
        if (PROP.getGlobalSize() > 0) {
            try {
                logger.debug(resourceName + ": " + VaultClient.getVault(resourceName) + " -- from Main()");
            } catch (VaultException e) {
                logger.debug(e.getMessage(), e.fillInStackTrace());
            }
        } else {
            logger.debug("Unexpecting size of global map to be 0");
        }
        //// Testing args resources if any
        HashMap map = PROP.getResourceMap();
        map.forEach((k,v) -> {
            try {
                logger.debug(k+": "+VaultClient.getVault((String)k));
            } catch (VaultException e) {
                logger.debug(e.getMessage(),e.fillInStackTrace());
            }
        });
    }
}
