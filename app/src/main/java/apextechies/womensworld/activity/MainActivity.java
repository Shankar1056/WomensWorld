package apextechies.womensworld.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apextechies.womensworld.R;
import apextechies.womensworld.adapter.CategoryAdapter;
import apextechies.womensworld.allinterface.OnTaskCompleted;
import apextechies.womensworld.model.CategoryModel;
import apextechies.womensworld.utilz.Download_web;
import apextechies.womensworld.webservices.WebServices;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //@BindView(R.id.rv_cat)
    RecyclerView rv_cat;

    private ArrayList<CategoryModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // ButterKnife.bind(this);
        initWidgit();

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
                        rv_cat.setAdapter(new CategoryAdapter(MainActivity.this, arrayList));
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
