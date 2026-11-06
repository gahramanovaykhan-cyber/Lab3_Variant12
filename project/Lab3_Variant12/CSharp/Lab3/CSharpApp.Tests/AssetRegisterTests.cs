using CSharpApp;
using Xunit;

namespace CSharpApp.Tests;

public class AssetRegisterTests
{
    private static Asset NewAsset(string id = "A-1") => new(id, "Machine", 1000m);

    [Fact]
    public void RecordsAcquisitionAndDisposal()
    {
        var register = new AssetRegister("Main");
        register.AddAsset(NewAsset());
        register.RecordAcquisition("A-1", 1000m, DateOnly.FromDateTime(DateTime.Today));
        register.RecordDisposal("A-1", 250m, DateOnly.FromDateTime(DateTime.Today));
        Assert.Equal(750m, register.GetBalance("A-1"));
    }

    [Fact]
    public void RejectsDuplicateInventoryNumber()
    {
        var register = new AssetRegister("Main");
        register.AddAsset(NewAsset());
        Assert.Throws<ArgumentException>(() => register.AddAsset(NewAsset()));
    }

    [Fact]
    public void RejectsDisposalAboveBalance()
    {
        var register = new AssetRegister("Main");
        register.AddAsset(NewAsset());
        Assert.Throws<ArgumentException>(() => register.RecordDisposal("A-1", 1m, DateOnly.FromDateTime(DateTime.Today)));
    }

    [Fact]
    public void RejectsUnknownAsset()
    {
        var register = new AssetRegister("Main");
        Assert.Throws<ArgumentException>(() => register.RecordAcquisition("missing", 10m, DateOnly.FromDateTime(DateTime.Today)));
    }

    [Fact]
    public void RejectsNonPositiveAssetValue()
    {
        Assert.Throws<ArgumentException>(() => new Asset("A-1", "Machine", 0m));
    }
}
