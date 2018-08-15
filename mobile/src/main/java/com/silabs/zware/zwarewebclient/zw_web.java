package com.silabs.zware.zwarewebclient;

import android.os.StrictMode;
import java.io.IOException;
import java.util.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.security.cert.Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;

import javax.net.ssl.HttpsURLConnection;

public class zw_web {
    private static zw_web single_zw_web_instance = null;
    private String url;
    private String protocol;

    List<String> cookies;
    List<String> lastResponseList;//Contains last successful response code and, response

    public static zw_web getInstance(){
        return single_zw_web_instance;
    }
    public static zw_web getInstance(String protocol,String url){
        single_zw_web_instance = new zw_web(protocol,url);
        return single_zw_web_instance;
    }
    private zw_web(String protocol,String url){

        // make sure cookies is turn on
        CookieHandler.setDefault(new CookieManager());

        this.url = url;
        this.protocol = protocol;
        System.out.println("zw_web:url="+url);
        /*TO DO: Need to remove permitAll and use a child thread or AsyncTask to handle all networking calls*/
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public boolean zw_login(String username,String password){
        String login_api = "/register//login";
        String login_params = "usrname=" + username + "&passwd=" + password;
        List<String> responseList;
        String response;
        int responseCode = -400;
        try {
            System.out.println("zw_login:protocol=" + this.protocol);
            System.out.println("zw_login:url=" + this.url + login_api + " params="+login_params);
            responseList = sendPost(this.protocol,this.url,login_api, login_params);
            responseCode = Integer.parseInt(responseList.get(0));
            response = responseList.get(1);
            System.out.println("zw_login:Response Code:"+responseCode);
            if(responseCode == 200 && response.contains("<login>0</login>")){
                return true;
            }
        }
        catch (Exception e){
            System.out.println("zw_login:Caught exception:"+e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean zw_logout(){
        String api = "/register//logout";
        String apiParams = "";
        List<String> responseList;
        String response;
        int responseCode = -400;
        try {
            System.out.println("zw_logout:url=" + this.url + api + " params="+apiParams);
            responseList = sendPost(this.protocol,this.url,api, apiParams);
            responseCode = Integer.parseInt(responseList.get(0));
            response = responseList.get(1);
            System.out.println("zw_logout:Response Code:"+responseCode);
            if(responseCode == 200 && response.contains("<logout>0</logout>")){
                return true;
            }
        }
        catch (Exception e){
            System.out.println("zw_logout:Caught exception:"+e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public ZwVersion zw_version(StringBuffer zw_version){
        String api = "/cgi/zcgi/networks//version";
        String apiParams = "";
        List<String> responseList;
        String response;
        int responseCode = -400;
        try {
            System.out.println("zw_version:url=" + this.url + api + " params="+apiParams);
            responseList = sendPost(this.protocol,this.url,api, apiParams);
            responseCode = Integer.parseInt(responseList.get(0));
            response = responseList.get(1);
            System.out.println("zw_version:Response Code:"+responseCode);
            zw_version.append(response);
            String zwVersion = new String(zw_version);
            if(responseCode == 200 && response.contains("<zwave>")){
                ZwVersion zw_ver = new ZwVersion(zwVersion);
                zw_ver.deserialize();
                return zw_ver;
            }
        }
        catch (Exception e){
            System.out.println("zw_version:Caught exception:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public ZwDesc zw_desc(StringBuffer zw_desc){
        String api = "/cgi/zcgi/networks//zwnet_get_desc";
        String apiParams = "";
        List<String> responseList;
        String response;
        int responseCode = -400;
        try {
            System.out.println("zw_desc:url=" + this.url + api + " params="+apiParams);
            responseList = sendPost(this.protocol,this.url,api, apiParams);
            responseCode = Integer.parseInt(responseList.get(0));
            response = responseList.get(1);
            System.out.println("zw_desc:Response Code:"+responseCode);
            zw_desc.append(response);
            String zwDesc = new String(zw_desc);
            if(responseCode == 200 && response.contains("<zwave>")){
                ZwDesc zwDes = new ZwDesc(zwDesc);
                zwDes.deserialize();
                return zwDes;
            }
        }
        catch (Exception e){
            System.out.println("zw_desc:Caught exception:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public ZwNodeList zw_node_list(StringBuffer zw_node_list){
        String api = "/cgi/zcgi/networks//zwnet_get_node_list";
        String apiParams = "";
        List<String> responseList;
        String response;
        int responseCode = -400;
        try {
            System.out.println("zw_node_list:url=" + this.url + api + " params="+apiParams);
            responseList = sendPost(this.protocol,this.url,api, apiParams);
            responseCode = Integer.parseInt(responseList.get(0));
            response = responseList.get(1);
            System.out.println("zw_node_list:Response Code:"+responseCode);
            zw_node_list.append(response);
            String zwNodeList = new String(zw_node_list);
            if(responseCode == 200 && response.contains("<zwave>")){
                ZwNodeList zwNodeLst = new ZwNodeList(zwNodeList);
                zwNodeLst.deserialize();
                return zwNodeLst;
            }
        }
        catch (Exception e){
            System.out.println("zw_node_list:Caught exception:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public ZwNodeEPList zw_node_ep_list(StringBuffer zw_node_ep_list){
        String api = "/cgi/zcgi/networks//get_node_ep_list";
        String apiParams = "";
        List<String> responseList;
        String response;
        int responseCode = -400;
        try {
            System.out.println("zw_node_ep_list:url=" + this.url + api + " params="+apiParams);
            responseList = sendPost(this.protocol,this.url,api, apiParams);
            responseCode = Integer.parseInt(responseList.get(0));
            response = responseList.get(1);
            System.out.println("zw_node_ep_list:Response Code:"+responseCode);
            zw_node_ep_list.append(response);
            String zwNodeEPList = new String(zw_node_ep_list);
            if(responseCode == 200 && response.contains("<zwave>")){
                ZwNodeEPList zwNodeEPLst = new ZwNodeEPList(zwNodeEPList);
                zwNodeEPLst.deserialize();
                return zwNodeEPLst;
            }
        }
        catch (Exception e){
            System.out.println("zw_node_ep_list:Caught exception:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public ZwEPList zw_ep_list(String noded,StringBuffer zw_ep_list){
        String api = "/cgi/zcgi/networks//zwnode_get_ep_list";
        String apiParams = "noded=" + noded;
        List<String> responseList;
        String response;
        int responseCode = -400;
        try {
            System.out.println("zw_ep_list:url=" + this.url + api + " params="+apiParams);
            responseList = sendPost(this.protocol,this.url,api, apiParams);
            responseCode = Integer.parseInt(responseList.get(0));
            response = responseList.get(1);
            System.out.println("zw_ep_list:Response Code:"+responseCode);
            zw_ep_list.append(response);
            String zwEPList = new String(zw_ep_list);
            if(responseCode == 200 && response.contains("<zwave>")){
                ZwEPList zwEPLst = new ZwEPList(noded,zwEPList);
                zwEPLst.deserialize();
                return zwEPLst;
            }
        }
        catch (Exception e){
            System.out.println("zw_ep_list:Caught exception:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public ZwEPIFList zw_ep_if_list(String ep_desc,StringBuffer zw_ep_list){
        String api = "/cgi/zcgi/networks//zwep_get_if_list";
        String apiParams = "epd=" + ep_desc;
        List<String> responseList;
        String response;
        int responseCode = -400;
        try {
            System.out.println("zw_ep_if_list:url=" + this.url + api + " params="+apiParams);
            responseList = sendPost(this.protocol,this.url,api, apiParams);
            responseCode = Integer.parseInt(responseList.get(0));
            response = responseList.get(1);
            System.out.println("zw_ep_if_list:Response Code:"+responseCode);
            zw_ep_list.append(response);
            String zwEPList = new String(zw_ep_list);
            if(responseCode == 200 && response.contains("<zwave>")){
                ZwEPIFList zwEPIFLst = new ZwEPIFList(ep_desc,zwEPList);
                zwEPIFLst.deserialize();
                return zwEPIFLst;
            }
        }
        catch (Exception e){
            System.out.println("zw_ep_if_list:Caught exception:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public ZwDeviceStatus zwif_switch_setup(String if_desc,StringBuffer zwif_devicestatus){
        String api = "/cgi/zcgi/networks//zwif_switch";
        String zwintfcmd = ZwApiConstants.getInstance().zwInterfaceCommands.get("CMD_BSWITCH_SETUP");
        //String apiParams = "cmd=" + zwintfcmd;
        //apiParams = apiParams.concat("&ifd=" + if_desc);
        String apiParams = "cmd=" + zwintfcmd + "&ifd=" + if_desc;

        List<String> responseList;
        String response;
        int responseCode = -400;
        try {
            System.out.println("zwif_switch_setup:url=" + this.url + api + " params="+apiParams);
            responseList = sendPost(this.protocol,this.url,api, apiParams);
            responseCode = Integer.parseInt(responseList.get(0));
            response = responseList.get(1);
            System.out.println("zwif_switch_setup:Response Code:"+responseCode);
            zwif_devicestatus.append(response);
            String zwDevStatus_xml = new String(zwif_devicestatus);
            //response is empty if setup is success
            if(responseCode == 200 && response.contains("<zwave>")){
                System.out.println("zwif_switch_setup:Returned error:"+response);
                ZwDeviceStatus zwDevStatus = new ZwDeviceStatus(if_desc,zwintfcmd,zwDevStatus_xml);
                zwDevStatus.setError(ZwApiConstants.getInstance().zwErrorCodes.get("ZW_DEFAULT_ERROR"));
                return zwDevStatus;
            }
            else if(responseCode == 200){
                System.out.println("zwif_switch_setup:Returned success:"+response);
                ZwDeviceStatus zwDevStatus = new ZwDeviceStatus(if_desc,zwintfcmd,"");
                zwDevStatus.setError(ZwApiConstants.getInstance().zwErrorCodes.get("ZW_NO_ERROR"));
                return zwDevStatus;
            }
        }
        catch (Exception e){
            System.out.println("zwif_switch_setup:Caught exception:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public ZwDeviceStatus zwif_switch_get(String if_desc,StringBuffer zwif_devicestatus){
        String api = "/cgi/zcgi/networks//zwif_switch";
        String zwintfcmd = ZwApiConstants.getInstance().zwInterfaceCommands.get("CMD_BSWITCH_GET");
        //String apiParams = "cmd=" + zwintfcmd;
        //apiParams = apiParams.concat("&ifd=" + if_desc);
        String apiParams = "cmd=" + zwintfcmd + "&ifd=" + if_desc;

        List<String> responseList;
        String response;
        int responseCode = -400;
        try {
            System.out.println("zwif_switch_get:url=" + this.url + api + " params="+apiParams);
            responseList = sendPost(this.protocol,this.url,api, apiParams);
            responseCode = Integer.parseInt(responseList.get(0));
            response = responseList.get(1);
            System.out.println("zwif_switch_get:Response Code:"+responseCode);
            zwif_devicestatus.append(response);
            String zwDevStatus_xml = new String(zwif_devicestatus);
            //response is empty if setup is success
            if(responseCode == 200 && response.contains("<zwave>")){
                System.out.println("zwif_switch_get:Returned success:"+response);
                ZwDeviceStatus zwDevStatus = new ZwDeviceStatus(if_desc,zwintfcmd,zwDevStatus_xml);
                zwDevStatus.deserialize();
                zwDevStatus.setError(ZwApiConstants.getInstance().zwErrorCodes.get("ZW_NO_ERROR"));
                return zwDevStatus;
            }
            else if(responseCode == 200){
                System.out.println("zwif_switch_get:Returned error:"+response);
                ZwDeviceStatus zwDevStatus = new ZwDeviceStatus(if_desc,zwintfcmd,"");
                zwDevStatus.setError(ZwApiConstants.getInstance().zwErrorCodes.get("ZW_DEFAULT_ERROR"));
                return zwDevStatus;
            }
        }
        catch (Exception e){
            System.out.println("zwif_switch_get:Caught exception:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public ZwDeviceStatus zwif_switch_report(String if_desc,StringBuffer zwif_devicestatus){
        String api = "/cgi/zcgi/networks//zwif_switch";
        String zwintfcmd = ZwApiConstants.getInstance().zwInterfaceCommands.get("CMD_BSWITCH_REPORT");
        //String apiParams = "cmd=" + zwintfcmd;
        //apiParams = apiParams.concat("&ifd=" + if_desc);
        String apiParams = "cmd=" + zwintfcmd + "&ifd=" + if_desc;

        List<String> responseList;
        String response;
        int responseCode = -400;
        try {
            System.out.println("zwif_switch_report:url=" + this.url + api + " params="+apiParams);
            responseList = sendPost(this.protocol,this.url,api, apiParams);
            responseCode = Integer.parseInt(responseList.get(0));
            response = responseList.get(1);
            System.out.println("zwif_switch_report:Response Code:"+responseCode);
            zwif_devicestatus.append(response);
            String zwDevStatus_xml = new String(zwif_devicestatus);
            //response is empty if setup is success
            if(responseCode == 200 && response.contains("<zwave>")){
                System.out.println("zwif_switch_report:Returned success:"+response);
                ZwDeviceStatus zwDevStatus = new ZwDeviceStatus(if_desc,zwintfcmd,zwDevStatus_xml);
                zwDevStatus.deserialize();
                return zwDevStatus;
            }
            else if(responseCode == 200){
                System.out.println("zwif_switch_report:Returned error:"+response);
                ZwDeviceStatus zwDevStatus = new ZwDeviceStatus(if_desc,zwintfcmd,"");
                zwDevStatus.setError(ZwApiConstants.getInstance().zwErrorCodes.get("ZW_DEFAULT_ERROR"));
                return zwDevStatus;
            }
        }
        catch (Exception e){
            System.out.println("zwif_switch_report:Caught exception:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public ZwDeviceStatus zwif_switch_set(String if_desc,String value, StringBuffer zwif_devicestatus){
        String api = "/cgi/zcgi/networks//zwif_switch";
        String zwintfcmd = ZwApiConstants.getInstance().zwInterfaceCommands.get("CMD_BSWITCH_SET");
        //String apiParams = "cmd=" + zwintfcmd;
        //apiParams = apiParams.concat("&ifd=" + if_desc);
        String apiParams = "cmd=" + zwintfcmd + "&ifd=" + if_desc + "&value=" + value;

        List<String> responseList;
        String response;
        int responseCode = -400;
        try {
            System.out.println("zwif_switch_set:url=" + this.url + api + " params="+apiParams);
            responseList = sendPost(this.protocol,this.url,api, apiParams);
            responseCode = Integer.parseInt(responseList.get(0));
            response = responseList.get(1);
            System.out.println("zwif_switch_set:Response Code:"+responseCode);
            zwif_devicestatus.append(response);
            String zwDevStatus_xml = new String(zwif_devicestatus);
            //response is empty if setup is success
            if(responseCode == 200 && response.contains("<zwave>")){
                System.out.println("zwif_switch_set:Returned error:"+response);
                ZwDeviceStatus zwDevStatus = new ZwDeviceStatus(if_desc,zwintfcmd,zwDevStatus_xml);
                zwDevStatus.setError(ZwApiConstants.getInstance().zwErrorCodes.get("ZW_DEFAULT_ERROR"));
                return zwDevStatus;
            }
            else if(responseCode == 200){
                System.out.println("zwif_switch_set:Returned success:"+response);
                ZwDeviceStatus zwDevStatus = new ZwDeviceStatus(if_desc,zwintfcmd,"");
                zwDevStatus.setError(ZwApiConstants.getInstance().zwErrorCodes.get("ZW_NO_ERROR"));
                return zwDevStatus;
            }
        }
        catch (Exception e){
            System.out.println("zwif_switch_report:Caught exception:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private List<String> sendPost(String protocol, String url, String api, String postParams){

        URL obj;
        HttpURLConnection conn;
        HttpsURLConnection sconn;

        int responseCode = -400;
        List<String> responseList = new ArrayList<String>();
        responseList.add(String.valueOf(responseCode));
        responseList.add("");

        try {
            obj = new URL(protocol + url + api);
        }
        catch (Exception e){
            e.printStackTrace();
            //System.out.println("sendPost:Caught Exception:" + e.getMessage());
            //System.out.println("sendPost:Caught Exception:" + e.getCause());
            return responseList;
        }

        try {
            if(protocol.equals("http://")) {
                conn = (HttpURLConnection) obj.openConnection();
            }
            else {
                sconn = (HttpsURLConnection) obj.openConnection();
                conn = sconn;
                //conn = (HttpURLConnection) obj.openConnection();
            }
        }
        catch (Exception e){
            //System.out.println("sendPost:Caught Exception:" + e.getStackTrace());
            //System.out.println("sendPost:Caught Exception:" + e.getMessage());
            e.printStackTrace();
            return responseList;
        }

        // Acts like a browser
        conn.setUseCaches(false);
        try {
            conn.setRequestMethod("POST");
        }
        catch (Exception e){
            e.printStackTrace();
            return responseList;
        }
        conn.setRequestProperty("Host", url);
        //conn.setRequestProperty("Host","192.168.1.36:80");
        //conn.setRequestProperty("Host","192.168.1.3:8080");
        conn.setRequestProperty("User-Agent", "javaHTTPURLCon");
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        /*for (String cookie : this.cookies) {
            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
        }*/
        //conn.setRequestProperty("Connection", "keep-alive");
        //conn.setRequestProperty("Referer", "https://accounts.google.com/ServiceLoginAuth");
        //conn.setRequestProperty("Content-Type", "multipart/form-data");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

        conn.setDoOutput(true);
        conn.setDoInput(true);
        DataOutputStream wr;
        try {
            // Send post request
            wr = new DataOutputStream(conn.getOutputStream());
        }
        catch (Exception e){
            Exception ioe = e;
            ioe.printStackTrace();
            return responseList;
        }

        try{
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
        }
        catch (Exception e){
            e.printStackTrace();
            return responseList;
        }

        try {
            responseCode = conn.getResponseCode();
        }
        catch (Exception e){
            e.printStackTrace();
            return responseList;
        }
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postParams);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }

        catch (Exception e){
            e.printStackTrace();
            return responseList;
        }

        String inputLine;
        StringBuffer response = new StringBuffer();
        try {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return responseList;
        }

        // Get the response cookies
        setCookies(conn.getHeaderFields().get("Set-Cookie"));
        try {
            in.close();
            System.out.println("Response:" + response.toString());
            System.out.println("Cookie:" + getCookies());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        responseList.set(0,String.valueOf(responseCode));
        responseList.set(1,response.toString());

        this.lastResponseList = responseList;//We store last successful response in lastResponseList

        return responseList;
    }

    public List<String> getCookies() {
        return cookies;
    }

    public void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }
    public List<String> getLastResponseList(){
        return lastResponseList;
    }
}
