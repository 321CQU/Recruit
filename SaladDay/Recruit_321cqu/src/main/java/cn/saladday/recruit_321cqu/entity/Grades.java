package cn.saladday.recruit_321cqu.entity;


import lombok.Data;



@Data
public class Grades {
    private static final long serialVersionUID = 1L;

    private String userId;

    private String sessionId;
    private Integer sessionYear;
    private Boolean sessionIsAutumn;

    private String courseName;

    private String score;
    private String studyNature;
    private String courseNature;

}
