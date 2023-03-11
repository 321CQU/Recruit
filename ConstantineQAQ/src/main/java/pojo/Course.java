package pojo;

public class Course {
    private String name;

    private String code;

    private String course_num;

    private String dept;

    private int credit;

    private String instructor;

    private Session session;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setCourse_num(String course_num){
        this.course_num = course_num;
    }
    public String getCourse_num(){
        return this.course_num;
    }
    public void setDept(String dept){
        this.dept = dept;
    }
    public String getDept(){
        return this.dept;
    }
    public void setCredit(int credit){
        this.credit = credit;
    }
    public int getCredit(){
        return this.credit;
    }
    public void setInstructor(String instructor){
        this.instructor = instructor;
    }
    public String getInstructor(){
        return this.instructor;
    }
    public void setSession(Session session){
        this.session = session;
    }
    public Session getSession(){
        return this.session;
    }

}
