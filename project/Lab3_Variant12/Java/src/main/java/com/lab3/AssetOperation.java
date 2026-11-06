package com.lab3;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class AssetOperation {
    private final Asset asset;
    private final OperationType type;
    private final BigDecimal amount;
    private final LocalDate date;

    public AssetOperation(Asset asset, OperationType type, BigDecimal amount, LocalDate date) {
        if (asset == null || type == null || date == null)
            throw new IllegalArgumentException("Asset, operation type and date are required");
        if (amount == null || amount.signum() <= 0)
            throw new IllegalArgumentException("Operation amount must be positive");
        this.asset = asset; this.type = type; this.amount = amount; this.date = date;
    }
    public Asset getAsset() { return asset; }
    public OperationType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getDate() { return date; }
}
