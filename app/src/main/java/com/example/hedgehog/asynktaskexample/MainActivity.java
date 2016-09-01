package com.example.hedgehog.asynktaskexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    static TextView textView;
    MyTask mt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        mt = (MyTask) getLastCustomNonConfigurationInstance();
        if (mt == null) {
            mt = new MyTask();
            mt.execute();
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mt;
    }

    class MyTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                for (int i = 1; i <= 30; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    publishProgress(i);
                    Log.d("qwe", "i = " + i
                            + ", MyTask: " + this.hashCode()
                            + ", MainActivity: " + MainActivity.this.hashCode());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textView.setText("i = " + values[0]);
        }
    }
}

