# 贡献指南

谢谢你愿意改 One。这是韩寒「ONE · 一个」的第三方 Android 客户端，历史代码写于 2015–2016。改动请保持小而可核对，并和现有 Java 风格一致。

English: small, reviewable changes. Match the surrounding Java. Do not add claimed features, download counts, or user numbers that the code does not implement.

## 环境

- JDK 17
- Android SDK Platform 33
- Android SDK Build-Tools 33.0.2
- 环境变量 `ANDROID_SDK_ROOT`（或 `ANDROID_HOME`），或者在仓库根目录放一份**不提交**的 `local.properties`：

```properties
sdk.dir=/path/to/android/sdk
```

## 构建

在仓库根目录执行：

```bash
./gradlew assembleDebug --no-daemon --max-workers=2
```

内存有限时请保持这条命令里的参数。`gradle.properties` 里已经写了 `org.gradle.jvmargs=-Xmx1536m`。

调试 APK 输出在 `app/build/outputs/apk/debug/app-debug.apk`。`minSdkVersion` 是 21。请装到 Android 5.0 及以上的设备上看界面。本仓库不要求、也不建议为了日常改动去启动模拟器。

`app/src/androidTest` 里的 `ApplicationTest` 继承已从新 SDK 移除的 `android.test.ApplicationTestCase`。当前的检查是 `assembleDebug`，它不编译这条仪器测试。

## 提交前

- 对上功能说明和代码。README 只写仓库里真实存在的行为。
- 不要提交 `local.properties`、`build/`、`.gradle/`、`.idea/`、`*.iml`、`*.apk`、密钥或签名文件。
- 提交说明用英文 conventional commit，例如 `fix:`、`docs:`、`build:`、`ci:`。
- 一个提交只做一件事。

## Pull request

- 用 Pull request 模板写清改了什么、怎么验证的。
- 界面或请求相关的改动，请写明你在哪台设备或哪一个接口响应上看过。
- 讨论用 GitHub Issues。Bug 和功能建议分别用对应模板。

## 问题反馈

复现步骤、设备 Android 版本、出现问题时的接口响应或 logcat，比形容词有用。关于页里留的历史联系方式是新浪微博 `@优菜米`。
