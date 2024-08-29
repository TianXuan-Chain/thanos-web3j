# 介绍
## 功能介绍
thanos-web3j 是用来访问 thanos-chain 节点的 java API
主要由两部分组成：
- AMOP（链上链下）系统旨在为联盟链提供一个安全高效的消息信道
- web3j (Web3 Java Ethereum Ðapp API) ,这个部分来源于 [web3j](https://github.com/web3j/web3j) ，并针对 thanos 的做了相应改动。主要改动点为：
  - 交易结构中增加了 randomid 和 blocklimit 这个改动对于 sdk 的使用者是透明的
  - 为 web3 增加了 AMOP 消息信道

## 特性介绍
- 系统合约工具和测试工具的使用
- 提供将合约代码转换成 java 代码的工具
- 提供系统合约部署和使用工具
- 支持国密算法

# 运行环境
- 需要首先搭建 thanos chain 区块链环境 (可参考[快速搭建天玄链](https://netease-blockchain.gitbook.io/thanos-tech-docs/quick-start/depoly-thanos-chain))
- [JDK1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

# sdk打包
## 依赖软件 
编译打包 thanos-web3j 之前需要安装下述依赖软件:
- git
- Oracle JDK 1.8
- Maven 3.3.9
- Gradle
- dos2unix && lsof: 用于处理windows文件上传到linux服务器时，文件格式无法被linux正确解析的问题

## 编译sdk源码
- 从 git 上拉取代码
  ```bash
  #=== 进入 thanos-web3j 源码放置目录（假设为/mydata/）=====
  $ mkdir -p /mydata
  $ cd /mydata

  #==== 拉取 git 代码 ====
  $ git clone https://github.com/netease-blockchain/thanos-web3j
  ```
- 编译 thanos-web3j 源码，生成 jar 包
  ```bash
  #===编译 thanos-web3j 源码，生成 dist 目录 ===
  $ cd thanos-web3j
  $ gradle build
  ```
编译成功后，会在 thanos-web3j 目录下生成 dist 目录，该目录主要包括如下内容：
| 目录             | 说明                                       |
| -------------- | ---------------------------------------- |
| dist/apps      | thanos-web3j 项目编译生成的 jar 包 thanos-web3j.jar             |
| dist/bin       | - thanos-web3j: 可执行脚本，调用 thanos-web3j.jar 执行 thanos-web3j 内方法(如部署系统合约、调用合约工具方法等) <br>  - compile.sh: 调用该脚本可将 dist/contract s目录下的合约代码转换成 java 代码，该功能便于用户基于 thanos-web3j 开发更多应用 |
| dist/conf      | 配置目录, 用于配置节点信息、证书信息、日志目录等，详细信息会在下节叙述     |
| dist/contracts | 合约存放目录，调用 compile.sh 脚本可将存放于该目录下的 .sol 格式合约代码转换成 java 代码 |
| dist/lib       | 存放 thanos-web3j 依赖库的 jar 包                         |

# 使用SDK
## Gradle
通过 gradle 引入 thanos-web3j SDK
```
compile ('com.netease.blockchain.thanos:thanos-web3j:1.6.7')
```

## Maven
使用 maven 引入 thanos-web3j SDK
```
<dependency>
    <groupId>com.netease.blockchain.thanos</groupId>
    <artifactId>thanos-web3j</artifactId>
    <version>1.7.3-SNAPSHOT</version>
</dependency>
```

# License
Apache 2.0
