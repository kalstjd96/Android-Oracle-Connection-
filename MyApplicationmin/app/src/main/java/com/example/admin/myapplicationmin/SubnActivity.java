package com.example.admin.myapplicationmin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SubnActivity extends AppCompatActivity {
    private EditText product_num;
     private Button search_btn;
     private TextView resultView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        product_num = (EditText) findViewById(R.id.product_num);
         search_btn = (Button) findViewById(R.id.search_btn);
         resultView = (TextView)findViewById(R.id.resultView);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sMessage = product_num.getText().toString();
                String result = SendByHttp(sMessage);
                String[][] parsedData = jsonParserList(result);

                Toast.makeText(getApplicationContext(),
                        result, Toast.LENGTH_SHORT).show();
                if(parsedData[0][0] != null) {


                    /*Toast.makeText(getApplicationContext(),
                            "조회 성공", Toast.LENGTH_SHORT).show();
                    */
                    Intent intent = new Intent(SubnActivity.this,ResultActivity.class);
                    startActivity(intent);//액티비티 띄우기

                }else{
                    Toast.makeText(getApplicationContext(),
                            "일치하는 우편번호가 없습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private String SendByHttp(String msg) {
        if (msg == null) {
            msg = "";
        }

        String URL =
        DefaultHttpClient client = new DefaultHttpClient();//HttpClient 통신을 합니다.


        try {
            HttpPost post = new HttpPost(URL + "?numbers=" + product_num.getText().toString());
            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), "utf-8"));

            String line = null;
            String result = "";

            while ((line = bufreader.readLine()) != null) {
                result += line;
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            client.getConnectionManager().shutdown();

            return "";
        }
    }

    public String[][] jsonParserList(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);

        try{

            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for(int i = 0; i<jArr.length();i++){
                json = jArr.getJSONObject(i);
                for (int j=0;j<jsonName.length; j++){
                    parseredData[i][j] = json.getString(jsonName[j]);
                }

            }

            for(int i=0;i<parseredData.length;i++)
            {
                Log.i("JSON을 분석한 데이터"+i+":",parseredData[i][0]);
            }


            return parseredData;

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
