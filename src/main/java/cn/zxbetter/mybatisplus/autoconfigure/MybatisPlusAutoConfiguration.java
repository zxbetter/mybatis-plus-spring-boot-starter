package cn.zxbetter.mybatisplus.autoconfigure;

import cn.zxbetter.mybatisplus.entity.BaseEntity;
import cn.zxbetter.mybatisplus.entity.OperatorEntity;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * Mybatis Plus 配置类
 *
 * @author zhangxin
 */
@Slf4j
@Configuration
@ConditionalOnClass(MybatisSqlSessionFactoryBean.class)
public class MybatisPlusAutoConfiguration {
    /**
     * 分页插件
     */
    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor() {
        log.debug("加载分页拦截器");
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁插件
     */
    @Bean
    @ConditionalOnMissingBean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        log.debug("加载乐观锁拦截器");
        return new OptimisticLockerInterceptor();
    }

    @Bean
    public MetaObjectHandler baseEntityMetaHandler() {
        log.debug("加载 BaseEntity 自动填充处理器");
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                LocalDateTime now = LocalDateTime.now();
                // 填充创建时间
                debug(BaseEntity.FIELD_CREATION_TIME, now);
                this.setInsertFieldValByName(BaseEntity.FIELD_CREATION_TIME, now, metaObject);

                // 填充更新时间
                debug(BaseEntity.FIELD_UPDATE_TIME, now);
                this.setInsertFieldValByName(BaseEntity.FIELD_UPDATE_TIME, now, metaObject);

                // 填充逻辑删除标识
                debug(BaseEntity.FIELD_DELETE_FLAG, 0);
                this.setInsertFieldValByName(BaseEntity.FIELD_DELETE_FLAG, 0, metaObject);

                // 填充乐观锁版本号
                debug(BaseEntity.FIELD_VERSION_NUMBER, 1L);
                this.setInsertFieldValByName(BaseEntity.FIELD_VERSION_NUMBER, 1L, metaObject);

                // 填充操作人
                debug(OperatorEntity.FIELD_OPERATOR, "default");
                this.setInsertFieldValByName(OperatorEntity.FIELD_OPERATOR, "default", metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                // 填充更新时间
                LocalDateTime now = LocalDateTime.now();
                debug(BaseEntity.FIELD_UPDATE_TIME, now);
                this.setUpdateFieldValByName(BaseEntity.FIELD_UPDATE_TIME, now, metaObject);

                // 填充操作人
                debug(OperatorEntity.FIELD_OPERATOR, "default");
                this.setUpdateFieldValByName(OperatorEntity.FIELD_OPERATOR, "default", metaObject);
            }

            /**
             * 打印填充日志
             *
             * @param field 填充的字段名称
             * @param value 填充的字段值
             */
            private void debug(String field, Object value) {
                log.debug("填充[{}]字段为[{}]", field, value);
            }
        };
    }
}
