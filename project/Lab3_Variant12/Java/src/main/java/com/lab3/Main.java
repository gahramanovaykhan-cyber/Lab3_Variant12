package com.lab3;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Enterprise enterprise = new Enterprise("Demo Enterprise");
        AssetRegister register = enterprise.getRegister();
        register.addAsset(new Asset("INV-001", "Production machine", new BigDecimal("15000.00")));
        register.recordAcquisition("INV-001", new BigDecimal("15000.00"), LocalDate.now());
        register.recordDisposal("INV-001", new BigDecimal("2000.00"), LocalDate.now());
        System.out.println("Enterprise: " + enterprise.getName());
        System.out.println("Asset balance: " + register.getBalance("INV-001"));
    }
}
