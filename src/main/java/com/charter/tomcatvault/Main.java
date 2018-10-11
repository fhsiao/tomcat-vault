package com.charter.tomcatvault;

import com.bettercloud.vault.VaultException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Arg arg = Arg.getInstance();
        arg.setArgs(args);
        try {
            if (PROP.getSize() > 0) {
                logger.debug(VaultClient.getVault());
            }
        } catch (VaultException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e.getMessage(), e.fillInStackTrace());
            }
            e.printStackTrace();
        }
    }
}
