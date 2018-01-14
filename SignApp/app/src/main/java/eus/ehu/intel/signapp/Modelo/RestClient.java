package eus.ehu.intel.signapp.Modelo;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iubuntu on 12/01/18.
 */

public class RestClient {
    private final String baseUrl;
    private final Map<String,String> properties = new HashMap<>();

    public RestClient(String baseUrl){
        this.baseUrl=baseUrl;
    }

    private HttpURLConnection getConnection(String path) throws IOException {
        URL url = new URL(String.format("%s/%s",baseUrl,path));
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        for(Map.Entry<String,String> property : properties.entrySet()){
            conn.setRequestProperty(property.getKey(),property.getValue());
            conn.setUseCaches(false);
        }

        return  conn;
    }

    public String getString(String path) throws IOException{
        HttpURLConnection conn = null;
        try {
            conn=getConnection(path);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
                return br.readLine();
            }
        }finally {
            if (conn!=null)
                conn.disconnect();
        }
    }

    public JSONObject getJson(String path) throws IOException,JSONException{
        return new JSONObject(getString(path));
    }


    public String postJson(final JSONObject json, String path) throws IOException{
        HttpURLConnection conn = null;
        try {
            conn = getConnection(path);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            pw.print(json.toString());
            pw.close();
            if(conn.getInputStream()!=null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
                    return br.readLine();
                }
            }
            else
                return (Integer.toString(conn.getResponseCode()));

        }finally {
            if (conn!=null)
                conn.disconnect();
        }

    }
}
