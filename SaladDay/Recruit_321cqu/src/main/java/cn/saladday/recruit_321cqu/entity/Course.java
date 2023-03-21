package cn.saladday.recruit_321cqu.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Course {

   private static final long serialVersionUID = 1L;
   @TableId
   private String name;
   private String code;
   private String dept;
   private String course_num;
   private String instructor;
   private String session;
   private Double credit;
}
