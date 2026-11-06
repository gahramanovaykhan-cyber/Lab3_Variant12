package com.lab3;

import java.math.BigDecimal;
import java.util.Objects;

public final class Asset {
    private final String inventoryNumber;
    private final String name;
    private final BigDecimal initialValue;

    public Asset(String inventoryNumber, String name, BigDecimal initialValue) {
        if (inventoryNumber == null || inventoryNumber.isBlank())
            throw new IllegalArgumentException("Inventory number is required");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Asset name is required");
        if (initialValue == null || initialValue.signum() <= 0)
            throw new IllegalArgumentException("Initial value must be positive");
        this.inventoryNumber = inventoryNumber;
        this.name = name;
        this.initialValue = initialValue;
    }
    public String getInventoryNumber() { return inventoryNumber; }
    public String getName() { return name; }
    public BigDecimal getInitialValue() { return initialValue; }
    @Override public boolean equals(Object o) {
        return o instanceof Asset a && inventoryNumber.equals(a.inventoryNumber);
    }
    @Override public int hashCode() { return Objects.hash(inventoryNumber); }
}
