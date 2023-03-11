import controller.GetData;
import controller.GetToken;
import org.junit.Test;
import utils.OkHttpApi;

import java.io.IOException;


public class ApiTest {
    @Test
    public void testGetToken() throws IOException {
        String token = GetToken.getToken();
        System.out.println(token);
    }

    @Test
    public void testGetScore() throws IOException {
        OkHttpApi api = new OkHttpApi();
        String run = api.run("https://api.321cqu.com/v1/recruit/score");
        System.out.println(run);
    }

    @Test
    public void testPrintScore() throws IOException{
        GetData.getScore();
    }
}
