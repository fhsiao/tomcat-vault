package com.charter.tomcatvault;

import com.bettercloud.vault.VaultException;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.IntrospectionUtils;
import java.util.regex.Pattern;


public class TomcatPropertyDecoder implements IntrospectionUtils.PropertySource {
    private static final Logger logger = Logger.getLogger(TomcatPropertyDecoder.class);
    @Override
    public String getProperty(String arg0) {
        if(arg0.startsWith("-")) {      // get rid of noises
            logger.debug("Receiving ARG::" + arg0);
            String[] args = arg0.split(Pattern.quote("|"));
            for (String s : args) {
                logger.debug("Processed ARG::" + s);
            }
            Arg arg = Arg.getInstance();
            try {
                String res = arg.setArgs(args);
                logger.debug("Receiving a resource name::" + res);
                return VaultClient.getVault(res);
            } catch (VaultException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
