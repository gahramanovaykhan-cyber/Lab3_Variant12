```mermaid
classDiagram
    class Enterprise {
        -String name
        -AssetRegister register
        +getRegister() AssetRegister
    }

    class AssetRegister {
        -String registerName
        -Map~String, Asset~ assets
        -List~AssetOperation~ operations
        +addAsset(asset)
        +recordAcquisition(asset, amount)
        +recordDisposal(asset, amount)
        +getBalance(inventoryNumber) decimal
        +getAssets() List
    }

    class Asset {
        -String inventoryNumber
        -String name
        -decimal initialValue
        +getInventoryNumber() String
    }

    class AssetOperation {
        -Asset asset
        -OperationType type
        -decimal amount
        -LocalDate date
    }

    class OperationType {
        <<enumeration>>
        ACQUISITION
        DISPOSAL
    }

    Enterprise "1" --> "1" AssetRegister : owns
    AssetRegister "1" --> "0..*" Asset : registers
    AssetOperation "*" --> "1" Asset : concerns
    AssetRegister "1" --> "0..*" AssetOperation : records