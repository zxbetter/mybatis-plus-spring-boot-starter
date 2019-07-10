package cn.zxbetter.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 所有 Entity 类的基类
 *
 * @author zhangxin
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String FIELD_CREATION_TIME = "creationTime";
    public static final String FIELD_UPDATE_TIME = "updateTime";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_VERSION_NUMBER = "versionNumber";

    public static final String DB_CREATION_TIME = "creation_time";
    public static final String DB_UPDATE_TIME = "update_time";
    public static final String DB_DELETE_FLAG = "delete_flag";
    public static final String DB_VERSION_NUMBER = "version_number";

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime creationTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 版本号 - 乐观锁
     */
    @Version
    private Long versionNumber;

    /**
     * 删除标识
     */
    @TableLogic
    private Integer deleteFlag;
}
