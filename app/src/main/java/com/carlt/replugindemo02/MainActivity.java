package com.carlt.replugindemo02;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PluginInfo install;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        installPluginApk();
                    }
                }).start();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                Intent intent = new Intent();
                //                intent.setComponent(new ComponentName("plugin_demo", "com.carlt.replugindemo03.ScrollingActivity"));
                //                intent.putExtra("data", "我是主工程传递的数据") ;
                //                RePlugin.startActivity(MainActivity.this, intent);

                Intent intent = RePlugin.createIntent("com.carlt.replugindemo03", "com.carlt.replugindemo03.ScrollingActivity");
                intent.putExtra("data", "我是主工程传递的数据");
                RePlugin.startActivity(MainActivity.this, intent);
            }
        });
    }

    private void installPluginApk() {
        String apkPath = Environment.getExternalStorageDirectory().getPath() + "/yema/plugin_demo.apk";
        install = RePlugin.install(apkPath);
        if (install == null) {
            Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
        } else {
            RePlugin.preload(install);
            Log.e("------", install.getName());
            Toast.makeText(MainActivity.this, "install external plugin success", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
