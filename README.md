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
  下载开发部署工具的源码需要依赖git，安装命令如下：
  ```
  # Ubuntu
  $ sudo apt install -y git
  # CentOS
  $ sudo yum install -y git
  ```
  配置 git 密钥：
  1. 将自己的 github 账户私钥上传到 "~/.ssh/" 目录下
  2. 修改私钥访问权限 “chmod 600 ~/.ssh/id_rsa ~/.ssh/id_rsa.pub”
- Oracle JDK 1.8
  ```
  # 创建新的文件夹，安装Java 8或以上的版本，将下载的jdk放在software目录
  # 从Oracle官网( https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html ) 选择Java 8版本下载，推荐下载jdk-8u201-linux-x64.tar.gz
  $ mkdir /software
  # 解压jdk
  $ tar -zxvf jdk-8u201-linux-x64.tar.gz
  # 配置Java环境，编辑/etc/profile文件
  $ vim /etc/profile
  # 打开以后将下面三句输入到文件里面并退出
  export JAVA_HOME=/software/jdk1.8.0_201  #这是一个文件目录，非文件
  export PATH=$JAVA_HOME/bin:$PATH
  export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
  # 生效profile
  $ source /etc/profile
  # 查询Java版本，出现的版本是自己下载的版本，则安装成功。
  $ java -version
  ```
  配置 jdk 熵池
  ```
  打开$JAVA_PATH/jre/lib/security/java.security这个文件，找到下面的内容：
  securerandom.source=file:/dev/random
  替换成
  securerandom.source=file:/dev/urandom
  ```
- Maven 3.3.9
  ```
  # 下载安装文件
  $ cd /software
  $ wget http://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
  # 解压maven
  $ tar -zxvf apache-maven-3.3.9-bin.tar.gz
  # 配置环境变量
  # 使用vim编辑/etc/profile文件
  $ vim /etc/profile
  # 在/etc/profile文件末尾增加以下配置：
  MAVEN_HOME=/software/apache-maven-3.3.9
  $ export PATH=${MAVEN_HOME}/bin:${PATH}
  # 生效profile
  $ source /etc/profile
  # 查询Maven版本，出现的版本是自己下载的版本，则安装成功。
  $ mvn -v
  ```
  国内服务器有需要的话，在 maven 的 setting.xml 中更新一下 aliyun 的镜像源，后续在执行 maven 执行的时候，下载速度会快一些
  ```
  <id>nexus-aliyun</id>
  <mirrorOf>central</mirrorOf>
  <name>Nexus aliyun</name>
  <url>http://maven.aliyun.com/nexus/content/groups/public</url>
  ```
  在应用的maven打包过程中，需要将打包依赖的thanos系统组件上传至maven私库。因此，需要修改$MAVEN_HOME/conf/settings.xml文件，添加私库配置。示例如下：
  ```
  ...
  <!--添加私库管理者，用于上传jar包 -->
  <servers>
  ...
    <server>
      <id>nexus-releases</id>
      <username>admin</username>
      <password>123456</password>
    </server>
    <server>
      <id>nexus-snapshots</id>
      <username>admin</username>
      <password>123456</password>
    </server>
  ...
  </servers>

  <profiles>
  ...
  <!--添加maven私库配置，用于下载jar包-->
    <profile>
      <id>standard-extra-repos</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <repositories>
        <repository>
          <id>netease-public</id>
          <name>Netease Repos</name>
          <url>https://epaymvn.hz.netease.com/nexus/content/repositories/public/</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      </repositories>
    </profile>
  </profiles>
  ```
- Gradle
  ```
  # 下载 gradle 文件
  $ wget https://services.gradle.org/distributions/gradle-5.6.2-all.zip -P /software
  # 解压
  $ sudo unzip -d /software/gradle /software/gradle-5.6.2-all.zip
  # 修改配置
  $ sudo vim /etc/profile.d/gradle.sh
  # 将下面下面写入 gradle.sh 中
  # export GRADLE_HOME=/software/gradle/gradle-5.6.2
  # export PATH=${GRADLE_HOME}/bin:${PATH}
  # 执行脚本
  $ sudo chmod +x /etc/profile.d/gradle.sh
  $ source /etc/profile.d/gradle.sh
  # 验证 gradle 安装
  $ gradle -v
  ```
- dos2unix && lsof: 用于处理windows文件上传到linux服务器时，文件格式无法被linux正确解析的问题
  可用如下命令安装这些软件：
  ```shell
  [centos]
  sudo yum -y install git
  sudo yum -y install dos2unix
  sudo yum -y install lsof

  [ubuntu]
  sudo apt install git
  sudo apt install lsof
  sudo apt install tofrodos
  ln -s /usr/bin/todos /usr/bin/unxi2dos
  ln -s /usr/bin/fromdos /usr/bin/dos2unix
  ```

## 编译sdk源码
- 从 git 上拉取代码
- 编译 thanos-web3j 源码，生成 jar 包
  ```bash
  #=== 进入 thanos-web3j 源码放置目录（假设为/mydata/）=====
  $ mkdir -p /mydata
  $ cd /mydata

  #==== 拉取 git 代码 ====
  $ git clone https://github.com/netease-blockchain/thanos-web3j

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
