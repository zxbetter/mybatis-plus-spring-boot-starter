package cn.zxbetter.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 在 {@link BaseEntity} 的基础上添加 [主键id] 字段
 *
 * @author zhangxin
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IdEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String DB_ID = "id";

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
}
