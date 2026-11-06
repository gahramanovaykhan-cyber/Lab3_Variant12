namespace CSharpApp;

public enum OperationType { Acquisition, Disposal }

public sealed class Asset
{
    public string InventoryNumber { get; }
    public string Name { get; }
    public decimal InitialValue { get; }

    public Asset(string inventoryNumber, string name, decimal initialValue)
    {
        if (string.IsNullOrWhiteSpace(inventoryNumber)) throw new ArgumentException("Inventory number is required");
        if (string.IsNullOrWhiteSpace(name)) throw new ArgumentException("Asset name is required");
        if (initialValue <= 0) throw new ArgumentException("Initial value must be positive");
        InventoryNumber = inventoryNumber; Name = name; InitialValue = initialValue;
    }
}

public sealed class AssetOperation
{
    public Asset Asset { get; }
    public OperationType Type { get; }
    public decimal Amount { get; }
    public DateOnly Date { get; }

    public AssetOperation(Asset asset, OperationType type, decimal amount, DateOnly date)
    {
        Asset = asset ?? throw new ArgumentNullException(nameof(asset));
        if (amount <= 0) throw new ArgumentException("Operation amount must be positive");
        Type = type; Amount = amount; Date = date;
    }
}

public sealed class AssetRegister
{
    private readonly Dictionary<string, Asset> _assets = new();
    private readonly List<AssetOperation> _operations = new();
    private readonly Dictionary<string, decimal> _balances = new();
    public string RegisterName { get; }

    public AssetRegister(string registerName)
    {
        if (string.IsNullOrWhiteSpace(registerName)) throw new ArgumentException("Register name is required");
        RegisterName = registerName;
    }

    public void AddAsset(Asset asset)
    {
        ArgumentNullException.ThrowIfNull(asset);
        if (_assets.ContainsKey(asset.InventoryNumber)) throw new ArgumentException("Duplicate inventory number");
        _assets.Add(asset.InventoryNumber, asset);
        _balances.Add(asset.InventoryNumber, 0m);
    }

    public void RecordAcquisition(string number, decimal amount, DateOnly date) =>
        Record(number, OperationType.Acquisition, amount, date);

    public void RecordDisposal(string number, decimal amount, DateOnly date) =>
        Record(number, OperationType.Disposal, amount, date);

    private void Record(string number, OperationType type, decimal amount, DateOnly date)
    {
        if (!_assets.TryGetValue(number, out var asset)) throw new ArgumentException("Asset is not registered");
        var operation = new AssetOperation(asset, type, amount, date);
        var current = _balances[number];
        var next = type == OperationType.Acquisition ? current + amount : current - amount;
        if (next < 0) throw new ArgumentException("Disposal exceeds available balance");
        _balances[number] = next;
        _operations.Add(operation);
    }

    public decimal GetBalance(string number) =>
        _balances.TryGetValue(number, out var balance) ? balance : throw new ArgumentException("Asset is not registered");

    public IReadOnlyCollection<Asset> GetAssets() => _assets.Values.ToList().AsReadOnly();
    public IReadOnlyList<AssetOperation> GetOperations() => _operations.AsReadOnly();
}

public sealed class Enterprise
{
    public string Name { get; }
    public AssetRegister Register { get; }

    public Enterprise(string name)
    {
        if (string.IsNullOrWhiteSpace(name)) throw new ArgumentException("Enterprise name is required");
        Name = name;
        Register = new AssetRegister($"{name} asset register");
    }
}
