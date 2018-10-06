package com.charter.tomcatvault;

import com.bettercloud.vault.VaultException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(VaultClient.getVault());
        } catch (VaultException e) {
            e.printStackTrace();
        }
    }
}
