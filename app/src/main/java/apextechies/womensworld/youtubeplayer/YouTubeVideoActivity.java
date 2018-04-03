package apextechies.womensworld.youtubeplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

import apextechies.womensworld.R;
import apextechies.womensworld.adapter.VideoAdapter;
import apextechies.womensworld.allinterface.OnClick;
import apextechies.womensworld.model.VideoModel;

/**
 * Created by shankar on 14/11/17.
 */

public class YouTubeVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private ArrayList<VideoModel> arrayList = new ArrayList<>();
    private int pos;
    private RecyclerView rv_cat;
    private boolean wasRestored;
    private  YouTubePlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);

        arrayList = getIntent().getParcelableArrayListExtra("list");
        try {
            pos = getIntent().getIntExtra("pos", 0);
        }
        catch (Exception e)
        {
            pos = 0;
            e.printStackTrace();
        }
        rv_cat = (RecyclerView)findViewById(R.id.rv_cat);
        rv_cat.setLayoutManager(new LinearLayoutManager(YouTubeVideoActivity.this));
        rv_cat.setAdapter(new VideoAdapter(YouTubeVideoActivity.this, arrayList, new OnClick() {
            @Override
            public void onClick(int pos) {

                if (!wasRestored) {
                    player.cueVideo(arrayList.get(pos).getVideo_id()); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
                }
            }
        }));
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        this.wasRestored = wasRestored;
        this.player = player;
        if (!wasRestored) {
            player.cueVideo(arrayList.get(pos).getVideo_id()); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }
}