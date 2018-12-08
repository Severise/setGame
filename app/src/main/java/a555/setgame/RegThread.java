package a555.setgame;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class RegThread extends Thread {
    private URL url = new URL("http://194.176.114.21:8050");
    private String data;
    public String res = "";
    public JSONObject result;

    public RegThread(String s, JSONObject res) throws MalformedURLException {
        this.data = s;
        this.result = res;
    }

    public void run() {
        HttpURLConnection urlConnection;
        Scanner in;
        OutputStream out;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            out = urlConnection.getOutputStream();
            out.write(data.getBytes());
            in = new Scanner(urlConnection.getInputStream());
            while (in.hasNextLine())
                res += in.nextLine();
            result = new JSONObject(res);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
