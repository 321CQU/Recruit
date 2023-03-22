package com.wyn.pojo;

public class Course {
    private Integer code;
    private String instructor;
    private String name;
    private Float credit;
    private String course_num;
    private Integer sessionId;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public String getCourse_num() {
        return course_num;
    }

    public void setCourse_num(String course_num) {
        this.course_num = course_num;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "code=" + code +
                ", instructor='" + instructor + '\'' +
                ", name='" + name + '\'' +
                ", credit=" + credit +
                ", course_num='" + course_num + '\'' +
                ", sessionId=" + sessionId +
                '}';
    }

    public Course(Integer code, String instructor, String name, Float credit, String course_num, Integer sessionId) {
        this.code = code;
        this.instructor = instructor;
        this.name = name;
        this.credit = credit;
        this.course_num = course_num;
        this.sessionId = sessionId;
    }

    public Course() {
    }


}
