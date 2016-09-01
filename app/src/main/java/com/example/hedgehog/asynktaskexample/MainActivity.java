package com.example.hedgehog.asynktaskexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    MyTask mt;
    static int currentCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("i = " + currentCount);
        mt = (MyTask) getLastCustomNonConfigurationInstance();
        if (mt == null) {
            mt = new MyTask();
            mt.execute();
        }
        mt.link(this);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        mt.unLink();
        return mt;
    }

    class MyTask extends AsyncTask<String, Integer, Void> {

        MainActivity activity;
        void link(MainActivity act) {
            activity = act;
        }

        void unLink() {
            activity = null;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                for (int i = 1; i <= 30; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    publishProgress(i);
                    MainActivity.currentCount = i;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (activity != null) {
                activity.textView.setText("i = " + values[0]);
            }
        }
    }
}

