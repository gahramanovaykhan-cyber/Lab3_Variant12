using CSharpApp;

var enterprise = new Enterprise("Demo Enterprise");
var register = enterprise.Register;
register.AddAsset(new Asset("INV-001", "Production machine", 15000m));
register.RecordAcquisition("INV-001", 15000m, DateOnly.FromDateTime(DateTime.Today));
register.RecordDisposal("INV-001", 2000m, DateOnly.FromDateTime(DateTime.Today));
Console.WriteLine($"Enterprise: {enterprise.Name}");
Console.WriteLine($"Asset balance: {register.GetBalance("INV-001")}");
