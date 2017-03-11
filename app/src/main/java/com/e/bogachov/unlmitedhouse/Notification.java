package com.e.bogachov.unlmitedhouse;

import android.util.Log;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Bogachov on 26.11.16.
 */

public class Notification {


    public Notification(){}

    public void sendNotif(final String whoo, final String id, final String contend){
        new Thread(new Runnable() {
            public void run() {

                String who ="include_player_ids";
                String iid ="6ad2bdfd-c608-452a-8a71-de6da6c751cf";
                //"include_player_ids";
                //"6ad2bdfd-c608-452a-8a71-de6da6c751cf"

                try {
                    String jsonResponse;

                    URL url = new URL("https://onesignal.com/api/v1/notifications");
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    con.setUseCaches(false);
                    con.setDoOutput(true);
                    con.setDoInput(true);

                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    con.setRequestProperty("Authorization", "Basic YzViZWZmZjgtYTRhNC00ODcxLWE5YjAtMjVkMDE3NWQyYjIw");
                    con.setRequestMethod("POST");
                    String strJsonBody = "{"
                            +   "\"app_id\": \"e7d85b5a-1faf-407d-b7df-5890a8557f5b\","
                            +   "\""+whoo+"\": [\""+id+"\"],"
                            +   "\"data\": {\"foo\": \"bar\"},"
                            +   "\"contents\": {\"en\": \""+contend+"\"}"
                            + "}";





                    System.out.println("strJsonBody:\n" + strJsonBody);

                    byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                    con.setFixedLengthStreamingMode(sendBytes.length);

                    OutputStream outputStream = con.getOutputStream();
                    outputStream.write(sendBytes);

                    int httpResponse = con.getResponseCode();
                    System.out.println("httpResponse: " + httpResponse);

                    if (  httpResponse >= HttpURLConnection.HTTP_OK
                            && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                        Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                        scanner.close();
                    }
                    else {
                        Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                        scanner.close();
                    }
                    System.out.println("jsonResponse:\n" + jsonResponse);
                    Log.d("jsonResponse:",jsonResponse);

                } catch(Throwable t) {
                    t.printStackTrace();
                }

            }
        }).start();
    }
}
