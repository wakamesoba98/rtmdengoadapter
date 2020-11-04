# RTM Dengo Adapter FOR 1.7.10

Train controller adapter for RealTrainMod.

MinecraftのRealTrainModにおける列車操作を電車でGO! 専用コントローラで行うためのMODです。

## 注意事項

当MODはwakamesoba98氏開発のrtmdengoadapterを1.7.10で動作するようにしたものです。

当MODは無保証で提供されます。MODを使用した際に発生したあらゆる問題に対し私Kaiz_JPは一切の責任を負いません。

使用前にあらかじめMinecraftセーブデータのバックアップを保管してください。

## LiteLoader併用時の注意

liteconfig\liteloader.properties内の
```
disableJInput=false
```
を
```
disableJInput=true
```
に変更してください。

## ビルド環境

- IntelliJ IDEA
- forge-1.7.10-10.13.4.1614-src

## 動作確認済み環境

- Minecraft: 1.7.10
- Forge: Forge-10.13.4.1614
- RealTrainMod: 1.7.10.40
- ATSAssist: 1.4.1beta_v8.3 又は ATSAssistSimple: 3.0

---

- USBゲームパッドコンバータ: 
  - JC-PS201U
    - ドライバより Remap メニューの Use Direction Buttons as: を `Button` に設定してください
  - JC-PS101U
- 電車でGO! 専用コントローラ
  - ツーハンドルコントローラ SLPH-00051
  - ワンハンドルコントローラ TCPP-20001
- 三鶯重工 OHC-PC01

上記以外の動作確認は行っておりません。対応機種を増やすPull Requestは歓迎します。

## 使用方法

- 対応するUSBゲームパッドコンバータを使用して、電車でGO! 専用コントローラをコンピュータに接続します。
- Minecraftを起動します。
- 以上です

## 各ボタン機能対照表

| コンバーター/コントローラー | 警笛 | 右ドア | 左ドア |
| -- | -- | -- | -- |
| JC-PS101U | A | START | SELECT |
| JC-PS201U | A | START | SELECT |
| OHC-PC01 | 手前の黒ボタン | 白ボタン | 黄色ボタン |

## ビルド

`lib` ディレクトリを作成し、

- [RTM1.7.10.40_Forge10.13.4.1558.jar](https://www.curseforge.com/minecraft/mc-mods/realtrainmod/files/2940834)
- [ATSAssistMod_Simple3.0.jar](https://github.com/Kai-Z-JP/ATSAssistModSimple/releases/tag/Simple3.0)

を `lib` ディレクトリに入れてください。

### Windows

```shell
.\gradlew.bat setupDecompWorkspace
.\gradlew.bat idea genIntellijRuns
.\gradlew.bat build
```

### Linux

```shell
./gradlew setupDecompWorkspace
./gradlew idea genIntellijRuns
./gradlew build
```

## ライセンス

The MIT License (MIT)
