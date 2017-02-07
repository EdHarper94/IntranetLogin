package egwh.intranetlogin;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new performLogin().execute();
    }

    private class performLogin extends AsyncTask<Void, Void, Void>{
        final String baseUrl = "https://science.swansea.ac.uk/intranet/accounts/login/?next=/intranet/";
        final String loginUrl = "https://science.swansea.ac.uk/intranet/accounts/login/";

        String user = "XXXXX";
        String pass = "XXXXX";
        String next = "/next/";

        protected Void doInBackground(Void... params) {
            try {
                //Get Response
                Connection.Response res = Jsoup
                        .connect(baseUrl)
                        .method(Connection.Method.GET)
                        .execute();
                // Get cookies
                Map<String, String> cookies = res.cookies();

                System.out.println(cookies);

                String csrftok = cookies.get("csrftoken");

                Connection.Response res2 = Jsoup
                        .connect(loginUrl)
                        .data("csrfmiddlewaretoken", csrftok)
                        .data("username", user)
                        .data("password", pass)
                        .data("/next/", next)
                        .cookies(cookies)
                        .method(Connection.Method.POST)
                        .execute();
                System.out.println("RESPONSE CODE:" + res2.statusCode());

            } catch (IOException e) {
                System.out.println("Cannot login");
                e.printStackTrace();
            }
            return null;
        }
    }
}
