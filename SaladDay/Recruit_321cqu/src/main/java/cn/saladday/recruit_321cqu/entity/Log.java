package cn.saladday.recruit_321cqu.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Log {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String userId;
    private String operate;
//    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime operateTime;
    private String method;
}
