# Spider-for-video

## 介绍
- spider for videos in some website
- 采用jdk1.8编写，需要java运行时环境
- 采用aria2作为默认下载器


## 使用方法
#### 1. windows
**在名称包含jre的release包中，java运行时环境已提供，无需再次安装**
- 下载对应的release版本并解压
- 点击`start.cmd`以启动程序
- 点击`clear.cmd`以重置
- **aria2可执行文件已提供**

#### 2. linux
- 下载对应的release版本并解压
- 执行`start.sh`以启动程序
- 执行`clear.sh`以重置
- **需要提前安装aria2和java运行时环境**

## 配置文件
#### spider-prop
主配置文件
- start: 开始的video序号(可以修改，最大40500)
- end: 结束的video序号(可以修改，最大40500)
- prefix: 网站前缀(不要修改)
- suffix: 网站后缀(不要修改)
- path: 下载路径(可以修改)
- type: 文件扩展名(不要修改)
- queue-size: 任务队列大小(可以修改)
- rpc-address: aria2的rpc下载地址(不要修改)
- max-alive: aria2可以同时处理的最大任务数(可以修改)

#### spider-rec
用于记录断点以实现从断点位置继续爬取数据
- cnt: 已提交的任务数目
- mark: 上一次提交的任务序号