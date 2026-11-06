package com.lab3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public final class AssetRegister {
    private final String registerName;
    private final Map<String, Asset> assets = new LinkedHashMap<>();
    private final List<AssetOperation> operations = new ArrayList<>();
    private final Map<String, BigDecimal> balances = new HashMap<>();

    public AssetRegister(String registerName) {
        if (registerName == null || registerName.isBlank())
            throw new IllegalArgumentException("Register name is required");
        this.registerName = registerName;
    }
    public String getRegisterName() { return registerName; }

    public void addAsset(Asset asset) {
        Objects.requireNonNull(asset, "Asset is required");
        if (assets.containsKey(asset.getInventoryNumber()))
            throw new IllegalArgumentException("Duplicate inventory number");
        assets.put(asset.getInventoryNumber(), asset);
        balances.put(asset.getInventoryNumber(), BigDecimal.ZERO);
    }
    public void recordAcquisition(String inventoryNumber, BigDecimal amount, LocalDate date) {
        record(inventoryNumber, OperationType.ACQUISITION, amount, date);
    }
    public void recordDisposal(String inventoryNumber, BigDecimal amount, LocalDate date) {
        record(inventoryNumber, OperationType.DISPOSAL, amount, date);
    }
    private void record(String number, OperationType type, BigDecimal amount, LocalDate date) {
        Asset asset = assets.get(number);
        if (asset == null) throw new IllegalArgumentException("Asset is not registered");
        AssetOperation operation = new AssetOperation(asset, type, amount, date);
        BigDecimal current = balances.get(number);
        BigDecimal next = type == OperationType.ACQUISITION
                ? current.add(amount) : current.subtract(amount);
        if (next.signum() < 0) throw new IllegalArgumentException("Disposal exceeds available balance");
        balances.put(number, next);
        operations.add(operation);
    }
    public BigDecimal getBalance(String number) {
        if (!assets.containsKey(number)) throw new IllegalArgumentException("Asset is not registered");
        return balances.get(number);
    }
    public List<Asset> getAssets() { return List.copyOf(assets.values()); }
    public List<AssetOperation> getOperations() { return List.copyOf(operations); }
}
