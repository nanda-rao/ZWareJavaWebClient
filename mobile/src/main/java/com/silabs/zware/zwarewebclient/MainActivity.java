package com.silabs.zware.zwarewebclient;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.silabs.zware.zwarewebclient.zw_web;

import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private zw_web zwweb;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public void onResume(){
        super.onResume();
        TextView uiURLView = findViewById(R.id.ui_zware_url);
        uiURLView.setText(ZwGlobalConfig.getInstance(this.getBaseContext()).zwGlobalConfig.get("ZW_DEFAULT_URL"));
        System.out.println("MainActivity:OnResume");
    }
    public void onPause(){
        super.onPause();

        System.out.println("MainActivity:OnPause");
    }

    public void onClick(View view){
        if(view.getId() == R.id.ui_settings_fab) {
            System.out.println("FAB click received...");
            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
        } else if(view.getId() == R.id.ui_login_button) {
            Button uiLoginButton = findViewById(R.id.ui_login_button);
            if(String.valueOf(uiLoginButton.getText()).equals("LOGIN")){
                EditText uiUserNameView = findViewById(R.id.ui_user_name);
                EditText uiPasswordView = findViewById(R.id.ui_password);
                TextView uiURLView = findViewById(R.id.ui_zware_url);
                System.out.println("UserName:"+uiUserNameView.getText());
                System.out.println("Password:"+uiPasswordView.getText());
                String username = String.valueOf(uiUserNameView.getText());
                String password = String.valueOf(uiPasswordView.getText());
                //String url = "http://192.168.1.36:80";
                //String protocol = "http://";
                //String url = "zware-portal-rpi.com:80";
                uiURLView.setText(ZwGlobalConfig.getInstance(this.getBaseContext()).zwGlobalConfig.get("ZW_DEFAULT_URL"));
                String protocol_url = String.valueOf(uiURLView.getText());
                String[] url_str_start = protocol_url.split("://",2);
                String protocol = url_str_start[0]+"://";
                String url = url_str_start[1];
                System.out.println("protocol = " + protocol + ":url=" + url);

                if(protocol.equals("http://") || protocol.equals("https://")) {
                    boolean login_status = zware_connect(protocol, url, username, password);
                    if (login_status) {
                        System.out.println("Login Success!");
                        uiLoginButton.setText("LOGOUT");
                        Button uiNextButton = findViewById(R.id.ui_next_button);
                        uiNextButton.setEnabled(true);
                        Intent intent = new Intent(MainActivity.this, DeviceDiscoveryActivity.class);
                        startActivity(intent);
                    } else {
                        System.out.println("Login Failed!");
                    }
                }
                else {
                    System.out.println("Login Failed! Invalid protocol!!");
                }
            }
            else if(String.valueOf(uiLoginButton.getText()).equals("LOGOUT")){
                boolean logout_status = zware_disconnect();
                if(logout_status) {
                    System.out.println("Logout Success!");
                    uiLoginButton.setText("LOGIN");
                    Button uiNextButton = findViewById(R.id.ui_next_button);
                    uiNextButton.setEnabled(false);
                    zwweb = null;
                }
                else {
                    System.out.println("Logout Failed!");
                }
            }
            else {
                System.out.println("Click Receive Failed!"+uiLoginButton.getText());
            }
        }
        else if(view.getId() == R.id.ui_next_button){
            Intent intent = new Intent(MainActivity.this,DeviceDiscoveryActivity.class);
            startActivity(intent);
        }
        else
            System.out.println("Click received for id:" + view.getId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button uiLoginButton = findViewById(R.id.ui_login_button);
        uiLoginButton.setOnClickListener(this);
        Button uiNextButton = findViewById(R.id.ui_next_button);
        uiNextButton.setOnClickListener(this);
        uiNextButton.setEnabled(false);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.ui_settings_fab);
        myFab.setOnClickListener(this);

        TextView uiURLView = findViewById(R.id.ui_zware_url);
        uiURLView.setText(ZwGlobalConfig.getInstance(this.getBaseContext()).zwGlobalConfig.get("ZW_DEFAULT_URL"));

        zwweb = null;
        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    protected boolean zware_connect(String protocol,String url,String username,String password){
        //zwweb = new zw_web("http://zware-portal-rpi.com:80");
        //zwweb = new zw_web("http://192.168.1.32:80");
        //zwweb = new zw_web("http://192.168.1.3:8080");
        //zwweb = new zw_web(protocol,url);
        zwweb = zw_web.getInstance(protocol,url);
        return(zwweb.zw_login(username,password));
    }

    protected boolean zware_disconnect(){
        //zwweb = new zw_web("http://zware-portal-rpi.com:80");
        //zwweb = new zw_web("http://192.168.1.32:80");
        //zwweb = new zw_web("http://192.168.1.3:8080");
        return(zwweb.zw_logout());
        // Example of a call to a native method
        //sample_text.text = stringFromJNI()
    }

    public zw_web getZWWebInterface(){
        return zwweb;
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

}
