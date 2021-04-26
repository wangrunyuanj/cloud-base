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
4. slf4j-api
5. cn.hutool.hutool-all
6. com.google.guava
7. commons-lang3

## 异常处理
### 错误码
1. 当抛出异常时, 必须使用com.runyuanj.common.exception中定义的异常
2. 异常的构造方法中, 错误码必须实现ErrorType.
3. ErrorType中的错误码由 preCode 和 subCode 组成. preCode范围 100 - 999, subCode范围 0 - 9999