package cn.zxbetter.mybatisplus.generator;

import lombok.Getter;

/**
 * 所有的配置项
 *
 * @author zhangxin
 */
@Getter
public enum PropertyKey {

    /**
     * 工作目录
     */
    USER_DIR("user.dir"),

    /**
     * 开发者
     */
    GLOBAL_AUTHOR("global.author"),

    /**
     * 是否打开输出目录
     */
    GLOBAL_OPEN("global.open"),

    /**
     * mapper.xml 中开启 baseResultMap
     */
    GLOBAL_BASE_RESULT_MAP("global.base_result_map"),

    /**
     * mapper.xml 中开启 baseColumnList
     */
    GLOBAL_BASE_COLUMN_LIST("global.base_column_list"),

    /**
     * 数据库连接地址
     */
    DB_URL("db.url"),

    /**
     * 数据库连接驱动类
     */
    DB_DRIVER("db.driver"),

    /**
     * 数据库连接名
     */
    DB_USERNAME("db.username"),

    /**
     * 数据库连接密码
     */
    DB_PASSWORD("db.password"),

    /**
     * 模块名
     */
    PACKAGE_MODULE("package.module"),

    /**
     * 父包名
     */
    PACKAGE_PARENT("package.parent"),

    /**
     * controller 的包名
     */
    PACKAGE_CONTROLLER("package.controller"),

    /**
     * service 的包名
     */
    PACKAGE_SERVICE("package.service"),

    /**
     * service 实现类的包名
     */
    PACKAGE_SERVICE_IMPL("package.service_impl"),

    /**
     * mapper.java 的包名
     */
    PACKAGE_MAPPER("package.mapper"),

    /**
     * entity 的包名
     */
    PACKAGE_ENTITY("package.entity"),

    /**
     * entity 的父类
     */
    CORE_SUPER_ENTITY_CLASS("core.super_entity_class"),

    /**
     * entity 父类的字段
     */
    CORE_SUPER_ENTITY_COLUMNS("core.super_entity_columns"),

    /**
     * controller 的父类
     */
    CORE_SUPER_CONTROLLER_CLASS("core.super_controller_class"),

    /**
     * 生成代码需要的表名
     */
    CORE_TABLES("core.tables"),

    /**
     * entity 是否加 lombok 注解
     */
    CORE_ENTITY_LOMBOK_MODEL("core.entity_lombok_model"),

    /**
     * 模板引擎
     */
    CORE_TEMPLATE_ENGINE("core.template_engine")
    ;

    private String key;
    PropertyKey(String key) {
        this.key = key;
    }
}
