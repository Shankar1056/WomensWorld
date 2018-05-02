package apextechies.womensworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apextechies.womensworld.R;
import apextechies.womensworld.adapter.CategoryAdapter;
import apextechies.womensworld.allinterface.OnClick;
import apextechies.womensworld.allinterface.OnTaskCompleted;
import apextechies.womensworld.model.CategoryModel;
import apextechies.womensworld.utilz.Download_web;
import apextechies.womensworld.webservices.WebServices;

public class MainActivity extends AppCompatActivity {

    //@BindView(R.id.rv_cat)
    RecyclerView rv_cat;
    private AdView mAdView;

    private ArrayList<CategoryModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // ButterKnife.bind(this);
        initWidgit();

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(getString(R.string.admob_app_id));

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("C04B1BFFB0774708339BC273F8A43708")
                .build();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


    private void initWidgit() {
        rv_cat = (RecyclerView)findViewById(R.id.rv_cat);
        rv_cat.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        getAllCategory();

    }

    private void getAllCategory() {

        Download_web web = new Download_web(MainActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optString("status").equals("true")){
                        JSONArray array = object.getJSONArray("data");
                        for (int i=0; i<array.length(); i++){
                            JSONObject jo = array.getJSONObject(i);
                            arrayList.add(new CategoryModel(jo.optString("id"), jo.optString("cat_name")));
                        }
                        rv_cat.setAdapter(new CategoryAdapter(MainActivity.this, arrayList, new OnClick() {
                            @Override
                            public void onClick(int pos) {
                                startActivity(new Intent(MainActivity.this, VideoList.class)
                                .putExtra("name", arrayList.get(pos).getCat_name())
                                .putExtra("id", arrayList.get(pos).getId()));
                            }
                        }));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
        web.setReqType(true);
        web.execute(WebServices.CATEGORY);

    }
}
