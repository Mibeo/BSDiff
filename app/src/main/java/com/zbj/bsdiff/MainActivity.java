package com.zbj.bsdiff;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Activity activity;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.version);
        tv.setText(BuildConfig.VERSION_NAME);

    }


    public native void bsPatch(String oldApk, String patch, String output);

    public void update(View view) {
        new AsyncTask<Void, Void, File>() {
            @Override
            protected File doInBackground(Void... voids) {
                String oldApk = getApplicationInfo().sourceDir;
                String patch = new File(Environment.getExternalStorageDirectory(), "patch").getAbsolutePath();
                String output = createNewApk().getAbsolutePath();
                bsPatch(oldApk, patch, output);
                return new File(output);
            }

            @Override
            protected void onPostExecute(File file) {
                super.onPostExecute(file);
                UriParseUtils.installApk(activity, file);
            }
        }.execute();
    }

    private File createNewApk() {
        File newApk = new File(Environment.getExternalStorageDirectory(), "bsdiff.apk");
        if (!newApk.exists()) {
            try {
                newApk.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newApk;
    }
}
