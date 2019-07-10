package cn.zxbetter.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 在 {@link BaseEntity} 的基础上添加 [主键id] 和 [操作人] 字段
 *
 * @author zhangxin
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FullBaseEntity extends IdEntity {
    public static final String FIELD_OPERATOR = "operator";
    public static final String DB_OPERATOR = "operator";

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String operator;
}
