# QQ杂鱼助手

QQ聊天输入框文字替换工具，基于Android无障碍服务实现。

## 功能

- 文字替换：我 -> 本喵（可自定义）、你 -> 主人（可自定义）
- 句尾追加后缀（可自定义：喵、唔喵、咩...）
- 随机追加猫颜文字（内置105个，可自定义）
- 两种处理模式：智能模式（空闲延迟触发）/ 标点模式（输入标点后触发）
- 智能模式空闲延迟时间可自定义
- 总开关，一键关闭所有替换
- 自动跳过 @mention 场景，不破坏 @ 功能
- WAKE_LOCK 唤醒锁（防止CPU休眠）
- 电池优化白名单申请入口

## 使用方式

1. 安装APK后打开APP
2. 前往系统设置开启无障碍服务（找到QQ杂鱼助手并开启）
3. 返回APP确认服务状态显示"已开启"
4. 打开QQ聊天窗口，输入文字即可自动替换

## 下载

从 [Releases](https://github.com/3975380064-maker/QQZayuHelper/releases) 页面下载最新APK。

## 权限说明

- BIND_ACCESSIBILITY_SERVICE：无障碍服务，核心功能必需
- WAKE_LOCK：保持CPU唤醒，防止屏幕关闭后功能暂停
- REQUEST_IGNORE_BATTERY_OPTIMIZATIONS：请求电池优化白名单
- FOREGROUND_SERVICE：后台服务运行

## 开源许可

本项目基于 MIT 许可证开源。