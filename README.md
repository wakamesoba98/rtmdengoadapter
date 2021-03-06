# RTM Dengo Adapter

Train controller adapter for RealTrainMod.

MinecraftのRealTrainModにおける列車操作を電車でGO! 専用コントローラで行うためのMODです。

## 注意事項

当MODは無保証で提供されます。MODを使用した際に発生したあらゆる問題に対し私wakamesoba98は一切の責任を負いません。

使用前にあらかじめMinecraftセーブデータのバックアップを保管してください。

## ビルド環境

- IntelliJ IDEA
- forge-1.12.2-14.23.2.2611-mdk

## 動作確認済み環境

- Minecraft: 1.12.1
- Forge: 1.12.2-14.23.2.2611
- RealTrainMod: 2.4.12-31
- USBゲームパッドコンバータ: 
  - JC-PS201U
    - ドライバより Remap メニューの Use Direction Buttons as: を `Button` に設定してください
  - JC-PS101U
- 電車でGO! 専用コントローラ
  - ツーハンドルコントローラ SLPH-00051
  - ワンハンドルコントローラ TCPP-20001

上記以外の動作確認は行っておりません。対応機種を増やすPull Requestは歓迎します。

## ビルド

### Windows

```shell
.\gradlew.bat setupDecompWorkspace
.\gradlew.bat idea genIntellijRuns
.\gradlew.bat build
```

`build\libs\rtmdengoadapter-0.1.jar` が生成されます。

### Linux

```shell
./gradlew setupDecompWorkspace
./gradlew idea genIntellijRuns
./gradlew build
```

`build/libs/rtmdengoadapter-0.1.jar` が生成されます。

## 使用方法

- 対応するUSBゲームパッドコンバータを使用して、電車でGO! 専用コントローラをコンピュータに接続します。
- Minecraftを起動します。
- 列車乗車時に **必ず** 以下のキャリブレーションを行います。
  - マスコン・ブレーキを0に合わせる
  - マスコンを5に入れる
  - マスコンを0に戻す
  - ブレーキを非常に入れる
  - ブレーキを0に戻す

## ライセンス

The MIT License (MIT)
