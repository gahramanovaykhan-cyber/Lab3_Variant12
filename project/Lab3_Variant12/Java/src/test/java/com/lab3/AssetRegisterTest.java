package com.lab3;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class AssetRegisterTest {
    private Asset asset(String id) { return new Asset(id, "Machine", new BigDecimal("1000")); }

    @Test void recordsAcquisitionAndDisposal() {
        AssetRegister r = new AssetRegister("Main");
        r.addAsset(asset("A-1"));
        r.recordAcquisition("A-1", new BigDecimal("1000"), LocalDate.now());
        r.recordDisposal("A-1", new BigDecimal("250"), LocalDate.now());
        assertEquals(new BigDecimal("750"), r.getBalance("A-1"));
    }
    @Test void rejectsDuplicateInventoryNumber() {
        AssetRegister r = new AssetRegister("Main");
        r.addAsset(asset("A-1"));
        assertThrows(IllegalArgumentException.class, () -> r.addAsset(asset("A-1")));
    }
    @Test void rejectsDisposalAboveBalance() {
        AssetRegister r = new AssetRegister("Main");
        r.addAsset(asset("A-1"));
        assertThrows(IllegalArgumentException.class,
            () -> r.recordDisposal("A-1", BigDecimal.ONE, LocalDate.now()));
    }
    @Test void rejectsUnknownAsset() {
        AssetRegister r = new AssetRegister("Main");
        assertThrows(IllegalArgumentException.class,
            () -> r.recordAcquisition("missing", BigDecimal.TEN, LocalDate.now()));
    }
    @Test void rejectsNonPositiveAssetValue() {
        assertThrows(IllegalArgumentException.class,
            () -> new Asset("A-1", "Machine", BigDecimal.ZERO));
    }
}
