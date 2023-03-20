import controller.GetData;
import controller.GetToken;
import org.junit.Test;
import utils.OkHttpUtils;

import java.io.IOException;
import java.util.Scanner;


public class ApiTest {
    @Test
    public void testGetToken() throws IOException {
        String token = GetToken.getToken();
        System.out.println(token);
    }

    @Test
    public void testGetScore() throws IOException {
        OkHttpUtils api = new OkHttpUtils();
        String run = api.run("https://api.321cqu.com/v1/recruit/score");
        System.out.println(run);
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void testPrintScore() throws IOException {
        GetData.getScores();
        GetData.getScores();
    }

    @Test
    public void testPojo() {
        try {
            GetData.getScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCache() throws IOException {
        GetData.getScores();
        System.out.println(GetData.getCourseName());
    }

    @Test
    public void testGetCacheData() throws IOException{
        Scanner sc = new Scanner(System.in);
        String command = sc.next();
        while(true){
            switch (command){
                case "查询成绩":
                    int n = GetData.getCourseName().size();
                    for (int i = 0; i < n; i++) {
                        System.out.println(GetData.getCourseName().get(i));
                        System.out.println();
                    }
                    break;
                case "退出":
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        while(true){
            String command = sc.next();
            boolean flag = false;
            switch (command) {
                case "查询成绩" -> {
                    int n = GetData.getCourseName().size();
                    for (int i = 0; i < n; i++) {
                        System.out.print(GetData.getCourseInstructor().get(i) + ":  ");
                        System.out.print(GetData.getCourseName().get(i) + "成绩:");
                        System.out.println(+GetData.getCourseScore().get(i));
                        System.out.println("---------------------");
                    }
                }
                case "退出" -> flag = true;
            }
            if(flag) break;
        }
    }
}
