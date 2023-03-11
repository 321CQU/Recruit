package pojo;

import java.util.ArrayList;
import java.util.List;

public class ScoresData {
    private int status;
    private String msg;
    private Data data;

    public ScoresData() {
        data = new Data();
        data.scores = new ArrayList<>();
    }

    // getter and setter methods for all fields

    public static class Data {
        private List<Scores> scores;

        // getter and setter methods for scores

        public static class Scores {
            private Session session;
            private Course course;
            private String score;
            private String study_nature;
            private String course_nature;

            // getter and setter methods for all fields

            public String toString() {
                return "{\"session\":" + session.toString() +
                        ",\"course\":" + course.toString() +
                        ",\"score\":\"" + score + "\"" +
                        ",\"study_nature\":\"" + study_nature + "\"" +
                        ",\"course_nature\":\"" + course_nature + "\"}";
            }

            public static class Session {
                private int id;
                private int year;
                private boolean is_autumn;

                // getter and setter methods for all fields

                public String toString() {
                    return "{\"id\":" + id +
                            ",\"year\":" + year +
                            ",\"is_autumn\":" + is_autumn + "}";
                }
            }

            public static class Course {
                private String name;
                private String code;
                private String course_num;
                private String dept;
                private int credit;
                private String instructor;
                private Session session;

                // getter and setter methods for all fields

                public String toString() {
                    return "{\"name\":\"" + name + "\"" +
                            ",\"code\":\"" + code + "\"" +
                            ",\"course_num\":\"" + course_num + "\"" +
                            ",\"dept\":\"" + dept + "\"" +
                            ",\"credit\":" + credit +
                            ",\"instructor\":\"" + instructor + "\"" +
                            ",\"session\":" + session.toString() + "}";
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"scores\":[");
            for (int i = 0; i < scores.size(); i++) {
                sb.append(scores.get(i).toString());
                if (i < scores.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]}");
            return sb.toString();
        }
    }

    public String toString() {
        return "{\"status\":" + status +
                ",\"msg\":\"" + msg + "\"" +
                ",\"data\":" + data.toString() +
                "}";
    }
}
