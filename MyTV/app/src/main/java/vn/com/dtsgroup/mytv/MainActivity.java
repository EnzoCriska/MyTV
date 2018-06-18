package vn.com.dtsgroup.mytv;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.JsonReader;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {
    static VideoView videoView;
    RecyclerView recyclerView;
    AdapterRecyclerView adapterRecyclerView;
    static LinkedList<Chanel> list = new LinkedList<Chanel>();
    String lst[] = {"5253", "52524","50075","2052","50074","50071","73420","73421","73422","2058","52454","80250","80251",
            "74756","74757","73428","73430","73426","73427","73425","73414","73413","73424","73423","73418","73416","73415","73412","3681","3582","3207","366","358","27081","2263","79141","79140","79042",
            "2631","2669","2535","2528","256","2667","2399","2264","2394","4009","2393","2398","2396","2328","2395","2184","2265","	",
            "899","52406","78219","70471","1"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.video);
        recyclerView = findViewById(R.id.recyclerviewList);
        new getData().execute();



    }

    class getData extends AsyncTask<Void,Void,LinkedList<Chanel> >
    {

        @Override
        protected LinkedList<Chanel>  doInBackground(Void... voids) {
            LinkedList listbg = new LinkedList();
            for (int i = 0; i < lst.length; i++) {
                String response = null;

                String linkgetAPI = "http://api.hplus.com.vn/detail/index/" + lst[i] + "&version=1.4&device=android&lang_id=1&token=2b3640fccb8944b21b24c7f5285eb767";
                response = getResponse(linkgetAPI);
                String name=null, linklogo = null, link = null;
                String arrres[] = response.split(",");
                // đọc từng dòng trong giá trị trả về
                for (int j =0; j<arrres.length; j++) {
                    if (arrres[j].length() > 5) {
                        if (arrres[j].substring(0, 6).equals("\"name\"")) {
                            name = arrres[j].substring(8, arrres[j].length() - 1);
                        }
                        if (arrres[j].substring(0, 7).equals("\"image\"")){
                            linklogo = arrres[j].substring(9, arrres[j].length() - 1);
                            linklogo = linklogo.replace("\\", "");
                        }
                        if (arrres[j].substring(0, 6).equals("\"link\"")) {
                            link = arrres[j].substring(8, arrres[j].length() - 1);
                            link = link.replace("\\", "");
                            listbg.add(new Chanel(name, link, linklogo));

                            break;
                        }
                    }
                }
            }
            return listbg;
        }

        @Override
        protected void onPostExecute(LinkedList<Chanel>  aVoid) {
            list=aVoid;
            System.out.println(list.size());
            adapterRecyclerView = new AdapterRecyclerView(list);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterRecyclerView);


            String videoAdrres = list.get(33).getUrl();
            streamVideo(videoAdrres);

//            ImageView image = findViewById(R.id.imageTest);
//            URL path = null;
////            try {
//                path = new URL("http://img.hplus.com.vn/320x180/banner/2018/06/05/135114-BBC-WN.png");
//                HttpURLConnection connection = (HttpURLConnection) path.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//
//                InputStream inputStream = (InputStream) connection.getInputStream();
//                image.setImageBitmap(BitmapFactory.decodeStream(inputStream));
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            super.onPostExecute(aVoid);

        }


    }

    public String getResponse(String linkgetAPI){
        String response = null;
        try {
            URL url = new URL(linkgetAPI);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream reader = new BufferedInputStream(conn.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reader));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            response = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void streamVideo(String url){
        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);
//        FullSMediaController mediaController = new FullSMediaController(MainActivity.this);
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);
        videoView.forceLayout();
        videoView.start();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    public  void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        int position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
    }
}

