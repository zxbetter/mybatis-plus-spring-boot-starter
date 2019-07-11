package cn.zxbetter.mybatisplus.generator;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * 加载配置文件
 *
 * @author zhangxin
 */
@Slf4j
public class GeneratorConfiguration {

    /**
     * 配置
     */
    private Properties properties;

    private GeneratorConfiguration() {
        properties = new Properties();
    }

    public static GeneratorConfiguration getInstance() {
        GeneratorConfiguration configuration = new GeneratorConfiguration();

        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("mybatis-plus-generator.properties")) {
            log.debug("加载配置文件 mybatis-plus-generator.properties");
            configuration.properties.load(inputStream);
        } catch (IOException e) {
            log.debug("配置文件加载失败");
            e.printStackTrace();
        }

        log.debug("加载配置文件：完成");

        // 校验配置文件
        checkRequiredProperties(configuration.properties);

        return configuration;
    }

    /**
     * 获取布尔型配置
     *
     * @param key 配置的键值
     * @return 配置的值
     */
    public boolean getBooleanProperty(PropertyKey key) {
        Objects.requireNonNull(key, "key 值不能为空");
        String stringKey = key.getKey();
        if (!properties.containsKey(stringKey)) {
            return false;
        }
        return "true".equalsIgnoreCase(properties.getProperty(stringKey));
    }

    /**
     * 获取指定的配置
     *
     * @param key 配置的键值
     * @return 配置的值
     */
    public String getProperty(PropertyKey key) {
        String property = properties.getProperty(key.getKey());
        return StringUtils.isEmpty(property) ? getDefaultProperty(key) : property;
    }

    /**
     * 获取默认的配置
     *
     * @param key 配置的键值
     * @return 配置的默认值
     */
    private String getDefaultProperty(PropertyKey key) {
        switch (key) {
            case USER_DIR:
                return System.getProperty("user.dir");
            case GLOBAL_AUTHOR:
                return System.getProperty("user.name");
            case CORE_TEMPLATE_ENGINE:
                return "freemarker";
            case PACKAGE_CONTROLLER:
                return "controller";
            case PACKAGE_SERVICE:
                return "service";
            case PACKAGE_SERVICE_IMPL:
                return "service.impl";
            case PACKAGE_MAPPER:
                return "mapper";
            case PACKAGE_ENTITY:
                return "entity";
            default:
                return null;
        }
    }

    /**
     * 校验必须的配置
     *
     * @param properties 加载的配置
     */
    private static void checkRequiredProperties(Properties properties) {
        Objects.requireNonNull(properties, "没有加载到配置文件 mybatis-plus-generator.properties");

        log.debug("检查配置文件：开始");

        boolean checkFlag = true;
        if (StringUtils.isEmpty(properties.getProperty(PropertyKey.DB_URL.getKey()))) {
            checkFlag = false;
            log.debug("数据库连接地址[{}]不能为空", PropertyKey.DB_URL.getKey());
        }
        if (StringUtils.isEmpty(properties.getProperty(PropertyKey.DB_DRIVER.getKey()))) {
            checkFlag = false;
            log.debug("数据库连接驱动[{}]不能为空", PropertyKey.DB_DRIVER.getKey());
        }
        if (StringUtils.isEmpty(properties.getProperty(PropertyKey.DB_USERNAME.getKey()))) {
            checkFlag = false;
            log.debug("数据库连接用户名[{}]不能为空", PropertyKey.DB_USERNAME.getKey());
        }
        if (StringUtils.isEmpty(properties.getProperty(PropertyKey.DB_PASSWORD.getKey()))) {
            checkFlag = false;
            log.debug("数据库连接密码[{}]不能为空", PropertyKey.DB_PASSWORD.getKey());
        }
        if (StringUtils.isEmpty(properties.getProperty(PropertyKey.CORE_TABLES.getKey()))) {
            checkFlag = false;
            log.debug("生成代码需要的表名[{}]不能为空", PropertyKey.CORE_TABLES.getKey());
        }

        // 校验模板引擎是否有效，暂不支持自定义模板
        String templateEngine = properties.getProperty(PropertyKey.CORE_TEMPLATE_ENGINE.getKey());
        boolean invalidEngine = StringUtils.isNotEmpty(templateEngine)
                && !"freemarker".equalsIgnoreCase(templateEngine)
                && !"beetl".equalsIgnoreCase(templateEngine)
                && !"velocity".equalsIgnoreCase(templateEngine);
        if (invalidEngine) {
            checkFlag = false;
            log.debug("模板引擎[{}]不无效", PropertyKey.CORE_TEMPLATE_ENGINE.getKey());
        }

        if (!checkFlag) {
            throw new RuntimeException("未配置必须项");
        }

        log.debug("检查配置文件：完成");
    }
}
