package com.charter.tomcatvault;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;

public class VaultClient {
    private static final long serialVersionUID = 1L;
    /**
     * Send a request to Vault server according to the default properties from vault.properties file
     *
     * @return the response from the Vault server
     * @throws VaultException if there are any problems encountered
     * while parsing the command line tokens.
     *
     * Auther: Frank
     */
    public static String getVault() throws VaultException {
        final VaultConfig config =
                new VaultConfig()
                        .address(PROP.getGlobalPoto()
                                + "://"+PROP.getGlobalHost()
                                + ":"+PROP.getGlobalPort())
                        .token(PROP.getGlobalToken())
                        .openTimeout(Integer.getInteger(PROP.getGlobalOpenTimeout()))
                        .readTimeout(Integer.getInteger(PROP.getGlobalReadTimeout()))
                        //    .sslPemFile("/path/on/disk.pem")
                        ////  See also: "sslPemUTF8()" and "sslPemResource()"
                        //    .sslVerify(false)
                        .build();

        final Vault vault = new Vault(config);

        //final Map<String, Object> secrets = new HashMap<>();
        //secrets.put("tomcat", "vault3123");

        // Write operation
        //final LogicalResponse writeResponse = vault.logical().write("secret/hello", secrets);

        // Read operation
        final String value = vault.logical()
                .read(PROP.getGlobalPath())
                .getData().get(PROP.getGlobalUser());
        return value;
    }
    /**
     * Parse the arguments according to the specified options and properties.
     *
     * @param resourceName         the specified key for the resource HashMap
     *
     * @return the response from the Vault server
     * @throws VaultException if there are any problems encountered
     * while parsing the command line tokens.
     *
     * Auther: Frank
     */
    public static String getVault(String resourceName) throws VaultException {
        final VaultConfig config =
                new VaultConfig()
                        .address(PROP.getResourcePoto(resourceName)
                                + "://"+PROP.getResourceHost(resourceName)
                                + ":"+PROP.getResourcePort(resourceName))
                        .token(PROP.getResourceToken(resourceName))
                        .openTimeout(Integer.getInteger(PROP.getResourceOpenTimeout(resourceName)))
                        .readTimeout(Integer.getInteger(PROP.getResourceReadTimeout(resourceName)))
                        //    .sslPemFile("/path/on/disk.pem")
                        ////  See also: "sslPemUTF8()" and "sslPemResource()"
                        //    .sslVerify(false)
                        .build();

        final Vault vault = new Vault(config);

        //final Map<String, Object> secrets = new HashMap<>();
        //secrets.put("tomcat", "vault3123");

        // Write operation
        //final LogicalResponse writeResponse = vault.logical().write("secret/hello", secrets);

        // Read operation
        final String value = vault.logical()
                .read(PROP.getResourcePath(resourceName))
                .getData().get(PROP.getResourceUser(resourceName));
        return value;
    }
}
