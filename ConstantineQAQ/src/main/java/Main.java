import com.wyn.controller.GetData;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

@Slf4j
public class Main {
    public static final Logger logger= LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true){
            String command = sc.next();
            boolean flag = false;
            switch (command) {
                case "查询成绩" -> {
                    int n = 0;
                    try {
                        n = GetData.getCourseName().size();
                    } catch (IOException e) {
                        logger.info("查询错误" + e.getMessage());
                    }
                }
                case "退出" -> flag = true;
            }
            if(flag) break;
        }
    }
}
