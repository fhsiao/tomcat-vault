package com.ztalk.tomcatvault;

import com.bettercloud.vault.VaultException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VaultClientTest {
    private static final Logger logger = Logger.getLogger(VaultClientTest.class);

    @Before
    public void setUp() throws Exception {
        BasicConfigurator.configure();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getVaultDefaultConfigurations() {
        String s = null;
        try {
            s = VaultClient.getVault();
        } catch (VaultException e) {
            e.printStackTrace();
        }
        assertNotNull(s);
    }

    @Test
    public void getVaultWithResourceName() {
        String resourceName = "test";
        PROP.addResource(resourceName);
        String s = null;
        try {
            s = VaultClient.getVault(resourceName);
        } catch (VaultException e) {
            e.printStackTrace();
        }
        assertNotNull(s);
    }
}