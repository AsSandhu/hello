package com.example.hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textView);
        final Button button = (Button) findViewById(R.id.button);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://soccerbuzz.herokuapp.com/")
                .build();

        final HerokuService service = retrofit.create(HerokuService.class);
      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Call<ResponseBody> call=service.hello();
              call.enqueue(new Callback<ResponseBody>() {
                  @Override
                  public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                      try{
                          textView.setText(response.body().string());
                      }catch (Exception e)
                      {
                          e.printStackTrace();
                          textView.setText(e.getMessage());
                      }
                  }

                  @Override
                  public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    textView.setText(t.getMessage());
                  }
              });
          }
      });

    }
}
