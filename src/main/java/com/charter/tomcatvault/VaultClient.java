package com.charter.tomcatvault;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;

public class VaultClient {
    private static final long serialVersionUID = 1L;
    public static String getVault() throws VaultException {
        final VaultConfig config =
                new VaultConfig()
                        .address(PROP.proto+"://"+PROP.host+":"+PROP.port)
                        .token(PROP.token)
                        .openTimeout(Integer.getInteger(PROP.openTimeout))
                        .readTimeout(Integer.getInteger(PROP.readTimeout))
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
                .read(PROP.path)
                .getData().get(PROP.user);
        return value;
    }
}
