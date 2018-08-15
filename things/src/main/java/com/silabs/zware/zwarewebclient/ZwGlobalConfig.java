package com.silabs.zware.zwarewebclient;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

public class ZwGlobalConfig {
    private static ZwGlobalConfig single_zw_global_config_instance = null;
    private Context context;

    public static ZwGlobalConfig getInstance(Context context){
        if(single_zw_global_config_instance == null) {
            single_zw_global_config_instance = new ZwGlobalConfig(context);
        }
        return single_zw_global_config_instance;
    }
    private ZwGlobalConfig(Context context){
        this.context = context;
        zwGlobalConfig = new HashMap<String,String>();
        zwGlobalConfig.put("ZW_OFFLINE_SIMULATION_MODE","0");        /**< 0-disabled,1-enabled */
        zwGlobalConfig.put("ZW_DEFAULT_URL","http://192.168.1.10:80");            /**< Default Z-Ware URL */
        String filename = "ZwGlobalConfig.ini";
        String fileContents = "ZW_OFFLINE_SIMULATION_MODE=0\nZW_DEFAULT_URL=http://192.168.1.10:80\n\n";

        System.out.println("Context.getFileDIr:"+context.getFilesDir());

        FileInputStream inputStream;

        try {
            inputStream = context.openFileInput(filename);
            inputStream.close();
        }
        catch (Exception e){
            System.out.println("Config file not found, creating....");
            FileOutputStream outputStream;
            try {
                outputStream = context.openFileOutput(filename,Context.MODE_PRIVATE);
                outputStream.write(fileContents.getBytes());
                outputStream.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        /*File found, reading*/
        try {
            byte[] fileBytes = new byte[256];
            inputStream = context.openFileInput(filename);
            inputStream.read(fileBytes);
            String s_fileContents = new String(fileBytes);
            System.out.println("Config file found, reading..."/*+s_fileContents*/);

            String[] simulation_str_start = s_fileContents.split("ZW_OFFLINE_SIMULATION_MODE=",2);
            //System.out.println("simulation_str[0]="+simulation_str_start[0]+":simulation_str[1]="+simulation_str_start[1]);

            int simulation_str_start_index = 0;
            int simulation_str_end_index = simulation_str_start[1].indexOf("\n");
            //System.out.println("start="+simulation_str_start_index+":end="+simulation_str_end_index);
            String simulation_mode_str = simulation_str_start[1].substring(simulation_str_start_index,simulation_str_end_index);

            System.out.println("simulation_mode=" + simulation_mode_str);

            String[] url_str_start = s_fileContents.split("ZW_DEFAULT_URL=",2);
            //System.out.println("url_str[0]="+url_str_start[0]+":url_str[1]="+url_str_start[1]);

            int url_str_start_index = 0;
            int url_str_end_index = url_str_start[1].indexOf("\n");
            //System.out.println("start="+url_str_start_index+":end="+url_str_end_index);
            String url_str = url_str_start[1].substring(url_str_start_index,url_str_end_index);

            System.out.println("url=" + url_str);

            zwGlobalConfig.put("ZW_OFFLINE_SIMULATION_MODE",simulation_mode_str);        /**< 0-disabled,1-enabled */
            zwGlobalConfig.put("ZW_DEFAULT_URL",url_str);            /**< Default Z-Ware URL */
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void update(){
        String filename = "ZwGlobalConfig.ini";
        String fileContents = "ZW_OFFLINE_SIMULATION_MODE="+this.single_zw_global_config_instance.zwGlobalConfig.get("ZW_OFFLINE_SIMULATION_MODE")+"\n" +
                "ZW_DEFAULT_URL="+this.single_zw_global_config_instance.zwGlobalConfig.get("ZW_DEFAULT_URL")+"\n\n";
        FileInputStream inputStream;

        try {

            inputStream = context.openFileInput(filename);
            inputStream.close();
        }
        catch (Exception e){
            System.out.println("update:Config file not found, creating....");
            FileOutputStream outputStream;
            try {
                outputStream = context.openFileOutput(filename,Context.MODE_PRIVATE);
                outputStream.write(fileContents.getBytes());
                outputStream.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            return;
        }

        /*File found. deleting...*/
        System.out.println("update:Config file found, deleting....");
        try {
            context.deleteFile(filename);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        /*config file deleted, creating...*/
        System.out.println("update:Config file deleted, recreating....");
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename,Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
    public HashMap<String,String> zwGlobalConfig;//app, zware configurations
    public void dump(){
        System.out.println("dump:ZW_OFFLINE_SIMULATION_MODE:"+this.single_zw_global_config_instance.zwGlobalConfig.get("ZW_OFFLINE_SIMULATION_MODE"));
        System.out.println("dump:ZW_DEFAULT_URL:"+this.single_zw_global_config_instance.zwGlobalConfig.get("ZW_DEFAULT_URL"));
    }
}
