package cn.zxbetter.mybatisplus.autoconfigure;

import cn.zxbetter.mybatisplus.entity.BaseEntity;
import cn.zxbetter.mybatisplus.entity.FullBaseEntity;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
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
@Configuration
@ConditionalOnClass(MybatisSqlSessionFactoryBean.class)
public class MybatisPlusAutoConfiguration {
    /**
     * 分页插件
     */
    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁插件
     */
    @Bean
    @ConditionalOnMissingBean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    @Bean
    public MetaObjectHandler baseEntityMetaHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                LocalDateTime now = LocalDateTime.now();
                this.setInsertFieldValByName(FullBaseEntity.FIELD_CREATION_TIME, now, metaObject);
                this.setInsertFieldValByName(FullBaseEntity.FIELD_UPDATE_TIME, now, metaObject);
                this.setInsertFieldValByName(FullBaseEntity.FIELD_OPERATOR, "default", metaObject);
                this.setInsertFieldValByName(FullBaseEntity.FIELD_DELETE_FLAG, 0, metaObject);
                this.setInsertFieldValByName(FullBaseEntity.FIELD_VERSION_NUMBER, 1, metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setInsertFieldValByName(FullBaseEntity.FIELD_UPDATE_TIME, LocalDateTime.now(), metaObject);
                this.setInsertFieldValByName(FullBaseEntity.FIELD_OPERATOR, "default", metaObject);
            }
        };
    }
}
