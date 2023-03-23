package com.wyn.pojo;

public class Score {
    private Integer id;
    private Integer score;
    private String study_nature;
    private String course_nature;
    private Course course;
    private Session session;

    public Score(Integer id, Integer score, String study_nature, String course_nature, Course course, Session session) {
        this.id = id;
        this.score = score;
        this.study_nature = study_nature;
        this.course_nature = course_nature;
        this.course = course;
        this.session = session;
    }

    public Score() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getStudy_nature() {
        return study_nature;
    }

    public void setStudy_nature(String study_nature) {
        this.study_nature = study_nature;
    }

    public String getCourse_nature() {
        return course_nature;
    }

    public void setCourse_nature(String course_nature) {
        this.course_nature = course_nature;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", score=" + score +
                ", study_nature='" + study_nature + '\'' +
                ", course_nature='" + course_nature + '\'' +
                ", course=" + course +
                ", session=" + session +
                '}';
    }
}
