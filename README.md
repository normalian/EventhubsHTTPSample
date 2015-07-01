# EventhubsHTTPSample 

## 何者？
Azure EventHubs を HTTP で疎通するサンプル

## どうやって通信する？
- 以下の URL に記載する HTTP リクエストを作成すれば EventHubs に対して HTTP 疎通が可能 https://msdn.microsoft.com/en-us/library/azure/dn790664.aspx
- ハッシュ値の計算等はこちらを参考 https://github.com/Azure/azure-storage-java/blob/master/microsoft-azure-storage/src/com/microsoft/azure/storage/StorageKey.java
- Base64 エンコードは Azure SDK で提供される方法を使う https://github.com/Azure/azure-storage-java/blob/master/microsoft-azure-storage/src/com/microsoft/azure/storage/core/Base64.java
