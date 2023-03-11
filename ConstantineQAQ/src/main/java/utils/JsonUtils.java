package utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;


public class JsonUtils {
    public static void parseJson(String jsonString) {
        JSONObject json = JSONObject.parseObject(jsonString);
                 int status = json.getIntValue("status");
                 String msg = json.getString("msg");
                 JSONArray scoresArray = json.getJSONObject("data").getJSONArray("scores");

                 System.out.println("Status: " + status);
                 System.out.println("Message: " + msg);
                 System.out.println("Scores:");

                 for (int i = 0; i < scoresArray.size(); i++) {
                     JSONObject scoreObject = scoresArray.getJSONObject(i);
                     JSONObject sessionObject = scoreObject.getJSONObject("session");
                     JSONObject courseObject = scoreObject.getJSONObject("course");

                     int sessionId = sessionObject.getIntValue("id");
                     int sessionYear = sessionObject.getIntValue("year");
                     boolean isAutumn = sessionObject.getBooleanValue("is_autumn");

                     String courseName = courseObject.getString("name");
                     String courseCode = courseObject.getString("code");
                     String courseNum = courseObject.getString("course_num");
                     String dept = courseObject.getString("dept");
                     int credit = courseObject.getIntValue("credit");
                     String instructor = courseObject.getString("instructor");

                     String score = scoreObject.getString("score");
                     String studyNature = scoreObject.getString("study_nature");
                     String courseNature = scoreObject.getString("course_nature");

                     System.out.println("  Session:");
                     System.out.println("    ID: " + sessionId);
                     System.out.println("    Year: " + sessionYear);
                     System.out.println("    Autumn: " + isAutumn);

                     System.out.println("  Course:");
                     System.out.println("    Name: " + courseName);
                     System.out.println("    Code: " + courseCode);
                     System.out.println("    Course Num: " + courseNum);
                     System.out.println("    Department: " + dept);
                     System.out.println("    Credit: " + credit);
                     System.out.println("    Instructor: " + instructor);

                     System.out.println("  Score:");
                     System.out.println("    Value: " + score);
                     System.out.println("    Study Nature: " + studyNature);
                     System.out.println("    Course Nature: " + courseNature);
                     System.out.println("***********************************");
                 }
    }

/*    public static void parseJson(String jsonString){
        System.out.println(jsonString);
        ScoresData data = JSON.parseObject(jsonString, ScoresData.class);
        System.out.println(data.toString());
    }*/



}

