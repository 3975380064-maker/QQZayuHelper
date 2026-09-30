# QQ杂鱼助手

QQ 聊天输入框文字替换工具，基于 Android 无障碍服务实现。

## 功能

- 文字替换：我 → 本喵（可自定义）、你 → 主人（可自定义）
- 句尾追加后缀（可自定义：喵、唔喵、咩…）
- 追加猫颜文字（内置 100+ 条，可自定义）；按文本内容确定性选择，同一句话不会每次都变
- 开头加前缀（呼喵…、唔…，可自定义）：按内容确定性选择，且**一条消息内前缀固定不变**，续写时不会跳变
- 两种处理模式：智能模式（空闲延迟触发）/ 标点模式（输入标点后触发）
- 智能模式空闲延迟时间可自定义
- 自定义替换规则（每行一条，格式 `原词=替换词`）
- 总开关，一键关闭所有替换
- 自动跳过 @mention 场景，不破坏 @ 功能
- 电池优化白名单申请入口、各厂商自启动管理跳转
- 应用内检查更新

## 使用方式

1. 安装 APK 后打开 App
2. 前往系统设置开启无障碍服务（找到「杂鱼助手」并开启）
3. 返回 App 确认服务状态显示「已开启」
4. 打开 QQ 聊天窗口，输入文字即可自动替换

## 权限说明

| 权限 | 用途 |
| --- | --- |
| `BIND_ACCESSIBILITY_SERVICE` | 无障碍服务，核心功能必需 |
| `INTERNET` / `ACCESS_NETWORK_STATE` | 检查更新、下载新版 APK |
| `REQUEST_INSTALL_PACKAGES` | 应用内更新时拉起系统安装器 |
| `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS` | 引导加入电池优化白名单，降低被系统冻结的概率 |

> 不再申请 `WAKE_LOCK`：无障碍服务本身由系统保活，之前那把它 acquire(30s) 一次且不续期的唤醒锁对防止休眠没有任何作用，只是白耗电。

## 更新机制（重要）

因为 GitHub 官方源在国内不稳定，更新检查与下载都允许走第三方镜像。因此**镜像返回的任何文件都视为不可信**，安装前会依次校验：

1. 能被解析成合法的 APK（被镜像截断的包会在这里被拒）
2. 包名与自身一致
3. **签名与当前已安装版本完全一致**
4. `versionCode` 高于当前版本

任何一条不过就删除文件、换下一个源；全部失败则不会安装，并提示「安装包校验未通过，已拒绝安装」。

> 实测意义：`ghproxy.net` 会返回 HTTP 200 但只有 1.4MB 的截断文件（魔数仍是 `PK`），只查魔数会直接装出损坏包。签名校验是兜住这类问题的关键。

更新包下载到 `Android/data/<包名>/files/updates/`，不占用公共存储，也不需要存储权限。

## 签名说明

自 v1.8 及以前的所有 Release 均使用**开发机的 debug keystore** 签名（证书 SHA-256：`70eba2c6…37ef`）。这意味着：

- 这个 keystore 文件就是 App 的「身份」，必须妥善备份，丢失后老用户将无法覆盖安装新版本；
- CI 发布需要使用同一个 keystore。仓库根目录放 `keystore.properties`（已 gitignore）即可自动启用签名：

```properties
storeFile=release.jks
storePassword=******
keyAlias=androiddebugkey
keyPassword=******
```

若将来要换成正式 keystore，所有老用户都需要卸载重装一次（签名不同无法覆盖安装）。

## 构建

```bash
./gradlew assembleDebug     # 调试包
./gradlew testDebugUnitTest # 单元测试
./gradlew lintDebug         # 静态检查
./gradlew assembleRelease   # 需要 keystore.properties，否则产出未签名包
```

- JDK 17、Android SDK（compileSdk 35）、Gradle 9.1.0（Wrapper 自动下载）
- `app/build.gradle.kts` 里保留了 proot/ARM64 环境用的 `aapt2` 替换规则，在普通 x86 环境同样能正常构建

## 项目结构

```
app/src/main/java/com/java/myapplication/
├── MainActivity.kt            生命周期、服务状态、权限引导、更新入口
├── MainActivityUi.kt          界面布局搭建（只摆控件、接线回调）
├── SettingsForm.kt            配置控件持有 + CatConfig 双向绑定
├── UiKit.kt                   配色与控件工厂
├── QQAccessibilityService.kt  无障碍服务，只做事件路由
├── TextReplaceEngine.kt       核心引擎：事件处理、输入框定位、写回、光标映射
├── TextProcessor.kt           文本加工流水线（纯函数，可单测）
├── ReplaceRules.kt            替换规则的正向/逆向变换（纯函数，可单测）
├── CatConfig.kt               SharedPreferences 配置读写
├── UpdateChecker.kt           版本检查、下载与安装包校验
└── AutoStartHelper.kt         各厂商自启动管理页跳转
```

## 已知局限

- 反向还原用户原文时，是靠「把引擎替换出来的词再换回去」实现的。如果用户**自己手打**了替换后的词（例如开启「我→本喵」时手打「本喵」），引擎无法区分它和替换结果。彻底解决需要改成增量跟踪（只记录引擎追加的尾巴），属于后续工作。
- 自定义规则若存在链式覆盖（如 `说=曰`、`曰=云`），还原按逆序处理；多条规则映射到同一个词时无法确定原始词。
- 开启开头前缀后，如果用户自己手打的内容正好以某个已配置的前缀开头（例如手打「唔…」），引擎会把它当成自己加的前缀，不会重复添加——但也无法区分。
- 目前只适配 `com.tencent.mobileqq` / `com.tencent.mobileqqi`。

## 开源许可

本项目基于 MIT 许可证开源。
