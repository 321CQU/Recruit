import com.wyn.controller.GetData;
import com.wyn.pojo.Cache;
import com.wyn.pojo.Score;
import com.wyn.utils.CacheUtils;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String key= CacheUtils.findAllScore;
        for(;;){
            System.out.println("请输入你要执行的操作:(查询成绩|退出)");
            String command = sc.next();
            boolean flag = false;
            switch (command) {
                case "查询成绩" -> {
                    int n = 0;
                    try {
                        List<Score> scores = GetData.getScores();
                        n = scores.size();
                        for (int i = 0; i < n; i++) {
                            System.out.print(scores.get(i).getCourse().getName() + ":");
                            System.out.println(scores.get(i).getScore());
                        }
                    } catch (IOException e) {
                        System.out.println("查询错误" + e.getMessage());
                    }
                }
                case "退出" -> flag = true;

                default -> System.out.println("你的输入有误，请重新输入");
            }
            if(flag) {
                CacheUtils.clearOnly(key);
                sc.close();
                System.exit(-1);
            }
        }
    }
}
