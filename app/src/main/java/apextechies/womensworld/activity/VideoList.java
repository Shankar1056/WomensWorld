package apextechies.womensworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apextechies.womensworld.R;
import apextechies.womensworld.adapter.VideoAdapter;
import apextechies.womensworld.allinterface.OnClick;
import apextechies.womensworld.allinterface.OnTaskCompleted;
import apextechies.womensworld.model.VideoModel;
import apextechies.womensworld.utilz.Download_web;
import apextechies.womensworld.webservices.WebServices;
import apextechies.womensworld.youtubeplayer.YouTubeVideoActivity;

/**
 * Created by Shankar on 4/3/2018.
 */

public class VideoList extends AppCompatActivity {
    private RecyclerView rv_cat;
    private ArrayList<VideoModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolist);
        initWidgit();
    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("name"));
        rv_cat = (RecyclerView)findViewById(R.id.rv_cat);
        rv_cat.setLayoutManager(new GridLayoutManager(VideoList.this, 2));
        getAllVideo();
    }

    private void getAllVideo() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(VideoList.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optString("status").equals("true")){
                        JSONArray array = object.getJSONArray("data");
                        for (int i=0; i<array.length(); i++){
                            JSONObject jo = array.getJSONObject(i);
                            arrayList.add(new VideoModel(jo.optString("pro_id"), jo.optString("cat_id"),
                                    jo.optString("prod_name"), jo.optString("video_url"), jo.optString("video_id")));
                        }
                        rv_cat.setAdapter(new VideoAdapter(VideoList.this, arrayList, new OnClick() {
                            @Override
                            public void onClick(int pos) {
                                startActivity(new Intent(VideoList.this, YouTubeVideoActivity.class)
                                        .putParcelableArrayListExtra("list", arrayList)
                                        .putExtra("pos", pos));
                            }
                        }));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
        nameValuePairs.add(new BasicNameValuePair("cat_id", getIntent().getStringExtra("id")));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.PRODUCT);

    }
}
