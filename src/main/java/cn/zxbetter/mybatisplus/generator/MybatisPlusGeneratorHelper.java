package cn.zxbetter.mybatisplus.generator;

import cn.zxbetter.mybatisplus.entity.BaseEntity;
import cn.zxbetter.mybatisplus.entity.FullBaseEntity;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.*;

/**
 * 代码生成器帮助类
 *
 * @author zhangxin
 */
public class MybatisPlusGeneratorHelper {
    /**
     * 配置
     */
    private GeneratorConfiguration configuration;

    /**
     * 代码生成器入口
     */
    public void generate() {
        configuration = GeneratorConfiguration.getInstance();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        mpg.setGlobalConfig(buildGlobalConfig());

        // 数据源配置
        mpg.setDataSource(buildDataSourceConfig());

        // 包配置
        PackageConfig pc = buildPackageConfig();
        mpg.setPackageInfo(pc);

        // 自定义配置
        mpg.setCfg(buildInjectionConfig(pc));

        // 配置模板
        mpg.setTemplate(buildTemplateConfig());

        // 数据库表策略配置
        mpg.setStrategy(buildStrategyConfig());

        // 使用 freemarker 作为模板引擎
        mpg.setTemplateEngine(getTemplateEngine());
        mpg.execute();
    }

    /**
     * 获取模板引擎
     *
     * @return 目标引擎对象
     */
    private AbstractTemplateEngine getTemplateEngine() {
        String property = configuration.getProperty(PropertyKey.CORE_TEMPLATE_ENGINE);
        switch (property.toLowerCase()) {
            case "freemarker":
                return new FreemarkerTemplateEngine();
            case "beetl":
                return new BeetlTemplateEngine();
            case "velocity":
                return new VelocityTemplateEngine();
            default:
                return null;
        }
    }

    /**
     * 数据库表策略配置
     *
     * @return 数据库表策略配置
     */
    private StrategyConfig buildStrategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        // 数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);

        // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        // 自定义继承的Entity类全称，带包名
        strategy.setSuperEntityClass(configuration.getProperty(PropertyKey.CORE_SUPER_ENTITY_CLASS));

        // 自定义基础的Entity类，公共字段（数据库字段）
        setSuperEntityColumns(strategy);

        // 自定义继承的Controller类全称，带包名
        strategy.setSuperControllerClass(configuration.getProperty(PropertyKey.CORE_SUPER_CONTROLLER_CLASS));

        // 需要包含的表名，允许正则表达式（与exclude二选一配置）
        strategy.setInclude(configuration.getProperty(PropertyKey.CORE_TABLES).split(","));

        // 【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(configuration.getBooleanProperty(PropertyKey.CORE_ENTITY_LOMBOK_MODEL));

        // 生成 @RestController 控制器
        strategy.setRestControllerStyle(true);

        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        return strategy;
    }

    /**
     * 公共字段
     *
     * @param strategy 策略配置
     */
    private void setSuperEntityColumns(StrategyConfig strategy) {
        String superEntityClass = strategy.getSuperEntityClass();
        String property = configuration.getProperty(PropertyKey.CORE_SUPER_ENTITY_COLUMNS);
        Set<String> columns = new HashSet<>();
        if (!Objects.isNull(property)) {
            columns.addAll(Arrays.asList(property.split(",")));
        }

        boolean baseFlag = "cn.zxbetter.mybatisplus.entity.BaseEntity".equalsIgnoreCase(superEntityClass)
                || "cn.zxbetter.mybatisplus.entity.FullBaseEntity".equalsIgnoreCase(superEntityClass)
                || "cn.zxbetter.mybatisplus.entity.IdEntity".equalsIgnoreCase(superEntityClass)
                || "cn.zxbetter.mybatisplus.entity.OperatorEntity".equalsIgnoreCase(superEntityClass);
        if (baseFlag) {
            columns.add(BaseEntity.DB_CREATION_TIME);
            columns.add(BaseEntity.DB_UPDATE_TIME);
            columns.add(BaseEntity.DB_DELETE_FLAG);
            columns.add(BaseEntity.DB_VERSION_NUMBER);
        }
        if ("cn.zxbetter.mybatisplus.entity.FullBaseEntity".equalsIgnoreCase(superEntityClass)) {
            columns.add(FullBaseEntity.DB_ID);
            columns.add(FullBaseEntity.DB_OPERATOR);
        } else if ("cn.zxbetter.mybatisplus.entity.IdEntity".equalsIgnoreCase(superEntityClass)) {
            columns.add(FullBaseEntity.DB_ID);
        } else if ("cn.zxbetter.mybatisplus.entity.OperatorEntity".equalsIgnoreCase(superEntityClass)) {
            columns.add(FullBaseEntity.DB_OPERATOR);
        }
        strategy.setSuperEntityColumns(columns.toArray(new String[0]));
    }

    /**
     * 获取模板配置
     *
     * @return 模板配置
     */
    private TemplateConfig buildTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        templateConfig.setXml(null);
        return templateConfig;
    }

    /**
     * 自定义配置
     *
     * @param pc          包的信息
     * @return 自定义配置
     */
    private InjectionConfig buildInjectionConfig(PackageConfig pc) {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(getMapperTemplate()) {

            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！
                return configuration.getProperty(PropertyKey.USER_DIR)
                        + "/src/main/resources/mapper/"
                        + (StringUtils.isEmpty(pc.getModuleName()) ? "" : pc.getModuleName())
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 包配置
     *
     * @return 包配置
     */
    private PackageConfig buildPackageConfig() {
        PackageConfig pc = new PackageConfig();
        // 模块名
        pc.setModuleName(configuration.getProperty(PropertyKey.PACKAGE_MODULE));

        // 父包名
        pc.setParent(configuration.getProperty(PropertyKey.PACKAGE_PARENT));

        // controller 包名
        pc.setController(configuration.getProperty(PropertyKey.PACKAGE_CONTROLLER));

        // service 包名
        pc.setService(configuration.getProperty(PropertyKey.PACKAGE_SERVICE));

        // service 实现类包名
        pc.setServiceImpl(configuration.getProperty(PropertyKey.PACKAGE_SERVICE_IMPL));

        // mapper 包名
        pc.setMapper(configuration.getProperty(PropertyKey.PACKAGE_MAPPER));

        // entity 包名
        pc.setEntity(configuration.getProperty(PropertyKey.PACKAGE_ENTITY));
        return pc;
    }

    /**
     * 数据源配置
     *
     * @return 数据源配置
     */
    private DataSourceConfig buildDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(configuration.getProperty(PropertyKey.DB_URL));
        dsc.setDriverName(configuration.getProperty(PropertyKey.DB_DRIVER));
        dsc.setUsername(configuration.getProperty(PropertyKey.DB_USERNAME));
        dsc.setPassword(configuration.getProperty(PropertyKey.DB_PASSWORD));
        return dsc;
    }

    /**
     * 全局配置
     *
     * @return 全局配置
     */
    private GlobalConfig buildGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();

        // 生成文件的输出目录
        gc.setOutputDir(configuration.getProperty(PropertyKey.USER_DIR) + "/src/main/java");

        // 开发者
        gc.setAuthor(configuration.getProperty(PropertyKey.GLOBAL_AUTHOR));

        // 不打开目录
        gc.setOpen(configuration.getBooleanProperty(PropertyKey.GLOBAL_OPEN));

        // mapper.xml 中开启 BaseResultMap
        gc.setBaseResultMap(configuration.getBooleanProperty(PropertyKey.GLOBAL_BASE_RESULT_MAP));

        // mapper.xml 中开启 baseColumnList
        gc.setBaseColumnList(configuration.getBooleanProperty(PropertyKey.GLOBAL_BASE_COLUMN_LIST));

        return gc;
    }

    /**
     * 获取 mapper.xml 的模板
     *
     * @return 模板的路径
     */
    private String getMapperTemplate() {
        String property = configuration.getProperty(PropertyKey.CORE_TEMPLATE_ENGINE);
        switch (property.toLowerCase()) {
            case "freemarker":
                return "/templates/mapper.xml.ftl";
            case "beetl":
                return "/templates/mapper.xml.btl";
            case "velocity":
                return "/templates/mapper.xml.vm";
            default:
                return "";
        }
    }
}
