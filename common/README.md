## 定义返回值,错误类型,错误类型常量,异常
1. ErrorType
2. BaseException
3. ResponseException
4. ServiceException
5. UncheckedException

## pom.xml
1. lombok
2. jackson-databind
3. com.alibaba.fastjson
4. commons-lang3
5. cn.hutool.hutool-all: 工具类包, 包含db, io, http, cache, bloomFilter, cron, regex, thread, XML等utils

## 异常处理
### 错误码
1. 当抛出异常时, 必须使用com.runyuanj.common.exception中定义的异常
2. 异常的构造方法中, 错误码必须实现ErrorType.
3. ErrorType中的错误码由 preCode 和 subCode 组成. preCode范围 100 - 999, subCode范围 0 - 9999

### hutool
docs url: https://github.com/dromara/hutool/blob/v5-master/README.md

主要模块: 

| 模块                |     介绍                                                                          |
| -------------------|---------------------------------------------------------------------------------- |
| hutool-aop         |     JDK动态代理封装，提供非IOC下的切面支持                                              |
| hutool-bloomFilter |     布隆过滤，提供一些Hash算法的布隆过滤                                                |
| hutool-cache       |     简单缓存实现                                                                     |
| hutool-core        |     核心，包括Bean操作、日期、各种Util等                                               |
| hutool-cron        |     定时任务模块，提供类Crontab表达式的定时任务                                          |
| hutool-crypto      |     加密解密模块，提供对称、非对称和摘要算法封装                                          |
| hutool-db          |     JDBC封装后的数据操作，基于ActiveRecord思想                                         |
| hutool-dfa         |     基于DFA模型的多关键字查找                                                         |
| hutool-extra       |     扩展模块，对第三方封装（模板引擎、邮件、Servlet、二维码、Emoji、FTP、分词等）            |
| hutool-http        |     基于HttpUrlConnection的Http客户端封装                                            |
| hutool-log         |     自动识别日志实现的日志门面                                                         |
| hutool-script      |     脚本执行封装，例如Javascript                                                     |
| hutool-setting     |     功能更强大的Setting配置文件和Properties封装                                        |
| hutool-system      |     系统参数调用封装（JVM信息等）                                                      |
| hutool-json        |     JSON实现                                                                       |
| hutool-captcha     |     图片验证码实现                                                                   |
| hutool-poi         |     针对POI中Excel和Word的封装                                                       |
| hutool-socket      |     基于Java的NIO和AIO的Socket封装                                                   |