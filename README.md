## mybatis-plus-spring-boot-starter

让 [mybatis-plus](https://github.com/baomidou/mybatis-plus) 开箱即用（就目前我常用的场景😄）:

1. 提供 **乐观锁拦截器** 和 **分页拦截器** 的自动装配。
2. 提供多个版本的 `BaseEntity` 选择，并为这些 `BaseEntity` 提供了自动填充功能。

   当使用 `IdEntity` 或者 `FullBaseEntity` 时，需要修改全局默认主键类型：
  
   ```yaml
   mybatis-plus:
     global-config:
       db-config:
         id-type: auto
   ```

3. 提供代码生成器的封装

   ```java
   import cn.zxbetter.mybatisplus.generator.MybatisPlusGeneratorHelper;

   public class MybatisGenerator {
       public static void main(String[] args) {
           new MybatisPlusGeneratorHelper().generate();
       }
   }
   ```

## 使用

```xml
<dependency>
    <groupId>cn.zxbetter</groupId>
    <artifactId>mybatis-plus-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

需要引入 mybatis-plus 的依赖

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.1.2</version>
</dependency>
```

TIP: 使用代码生成器，需要引入下面的依赖，代码生成器的配置文件 mybatis-plus-generator.properties 都是对 mybatis-plus-generator 的封装。

```xml
<dependencies>
    <!-- ... -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-generator</artifactId>
        <version>3.1.2</version>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.47</version>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.freemarker</groupId>
        <artifactId>freemarker</artifactId>
        <version>2.3.28</version>
        <optional>true</optional>
    </dependency>
    <!-- ... -->
</dependencies>
```

最后还需要一个类似这样的配置类

```java
@Configuration
@EnableTransactionManagement
@MapperScan("xx.*.mapper*")
public class MybatisConfig {
    // ...
}
```
