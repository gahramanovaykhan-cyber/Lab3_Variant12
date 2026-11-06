package com.lab3;

public final class Enterprise {
    private final String name;
    private final AssetRegister register;

    public Enterprise(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Enterprise name is required");
        this.name = name;
        this.register = new AssetRegister(name + " asset register");
    }
    public String getName() { return name; }
    public AssetRegister getRegister() { return register; }
}
