package vn.lequan.vivovideo;

import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.face.Face_Chanel;
import com.squareup.okhttp.ResponseBody;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Represents youtube video information retriever.
 */
public class YouTubeVideoInfoRetriever  implements Callback
{
    private static final String URL_YOUTUBE_GET_VIDEO_INFO = "http://www.youtube.com/get_video_info?&video_id=";

    public static final String KEY_DASH_VIDEO = "dashmpd";
    public static final String KEY_HLS_VIDEO = "hlsvp";

    private TreeMap<String, String> kvpList = new TreeMap<>();

    public void retrieve(String videoId) throws IOException
    {


        new getUrl().execute(URL_YOUTUBE_GET_VIDEO_INFO);
    }

    public String getInfo(String key)
    {
        return kvpList.get(key);
    }
    class getUrl extends AsyncTask<String, String, String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS )
                .retryOnConnectionFailure(true)
                .build();

        @Override
        protected String doInBackground(String... params) {
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();
            try {
                okhttp3.Response response =  okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("")){
                try {
                    Debug.e(s);
                    parse(s);
                    Debug.e(parseYoutubeDirectDASHUrl(s));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(s);
        }
    }
    public void printAll()
    {
        System.out.println("TOTAL VARIABLES=" + kvpList.size());

        for(Map.Entry<String, String> entry : kvpList.entrySet())
        {
            System.out.print( "" + entry.getKey() + "=");
            System.out.println("" + entry.getValue() + "");
        }
    }

    private void parse(String data) throws UnsupportedEncodingException
    {
        String[] splits = data.split("&");
        String kvpStr = "";

        if(splits.length < 1)
        {
            return;
        }

        kvpList.clear();

        for(int i = 0; i < splits.length; ++i)
        {
            kvpStr = splits[i];

            try
            {
                // Data is encoded multiple times
                kvpStr = URLDecoder.decode(kvpStr, SimpleHttpClient.ENCODING_UTF_8);
                kvpStr = URLDecoder.decode(kvpStr, SimpleHttpClient.ENCODING_UTF_8);

                String[] kvpSplits = kvpStr.split("=", 2);

                if(kvpSplits.length == 2)
                {
                    kvpList.put(kvpSplits[0], kvpSplits[1]);
                }
                else if(kvpSplits.length == 1)
                {
                    kvpList.put(kvpSplits[0], "");
                }
            }
            catch (UnsupportedEncodingException ex)
            {
                throw ex;
            }
        }
    }

    public static String parseYoutubeDirectDASHUrl (String response){
        String dashMark = "dashmpd=";
        int start = response.indexOf(dashMark);
        int end = response.indexOf("&",start);
        String url = "";
        try {
            url = response.substring(start + dashMark.length(), end);
        }catch (Exception ex){
            throw new RuntimeException("Failed to get dashmpd");
        }
        try {
            url = URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Debug.e(e.toString());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            parse(response.body().string());
            Debug.e(response.body().string());
        }
    }

    public static class SimpleHttpClient
    {
        public static final String ENCODING_UTF_8 = "UTF-8";
        public static final int DEFAULT_TIMEOUT = 10000;

        public static final String HTTP_GET = "GET";

        public String execute(String urlStr, String httpMethod, int timeout) throws IOException
        {
            URL url = null;
            HttpURLConnection conn = null;
            InputStream inStream = null;
            OutputStream outStream = null;
            String response = null;

            try
            {
                url = new URL(urlStr);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(timeout);
                conn.setRequestMethod(httpMethod);

                inStream = new BufferedInputStream(conn.getInputStream());
                response = getInput(inStream);
            }
            finally
            {
                if(conn != null && conn.getErrorStream() != null)
                {
                    String errorResponse = " : ";
                    errorResponse = errorResponse + getInput(conn.getErrorStream());
                    response = response + errorResponse;
                }

                if (conn != null)
                {
                    conn.disconnect();
                }
            }

            return response;
        }

        private String getInput(InputStream in) throws IOException
        {
            StringBuilder sb = new StringBuilder(8192);
            byte[] b = new byte[1024];
            int bytesRead = 0;

            while (true)
            {
                bytesRead = in.read(b);
                if (bytesRead < 0)
                {
                    break;
                }
                String s = new String(b, 0, bytesRead, ENCODING_UTF_8);
                sb.append(s);
            }

            return sb.toString();
        }

    }
}