package com.nan.launcher;

import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.root_layout);
        layout.setBackground(wallpaperDrawable);
        launch_apps();

    }





    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public List <Intent> launch_apps () {
        Intent shortcutsIntent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        List <Intent> list = new ArrayList<>();
        Intent intent = null;
        String launchers = "";
        final PackageManager packageManager=getPackageManager();
        for(final ResolveInfo resolveInfo:packageManager.queryIntentActivities(shortcutsIntent,   0)) {
            launchers = launchers + "\n" + resolveInfo.activityInfo.packageName;
            intent = packageManager
                    .getLaunchIntentForPackage(resolveInfo.activityInfo.packageName);
            list.add(intent);
        }

        Toast.makeText(MainActivity.this,list.toString(),Toast.LENGTH_LONG).show();
        Log.d("<--",list.toString());
        return list;
    }

    @Override
    public void onBackPressed() {
    }
}
