package pojo;

public class Scores {
    private Session session;
    private Course course;
    private String score;
    private String studyNature;
    private String courseNature;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStudyNature() {
        return studyNature;
    }

    public void setStudyNature(String studyNature) {
        this.studyNature = studyNature;
    }

    public String getCourseNature() {
        return courseNature;
    }

    public void setCourseNature(String courseNature) {
        this.courseNature = courseNature;
    }

    public static class Session {
        private int id;
        private int year;
        private boolean isAutumn;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public boolean isAutumn() {
            return isAutumn;
        }

        public void setAutumn(boolean autumn) {
            isAutumn = autumn;
        }
    }


}

