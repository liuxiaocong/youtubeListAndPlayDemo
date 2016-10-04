package liuxiaocong.com.youtubelistandplay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LiuXiaocong on 10/3/2016.
 */
public class MainActivity extends AppCompatActivity {
    private String mApiKey = "AIzaSyDz2jBO3cRIYqXnKxY0R3pcelCHDgui0BE";
    private String mKeyword;
    private String mLastToken;
    //private GoogleApiClient mGoogleApiClient;
    private double mLatitude = 24.7136;
    private double mLongitude = 46.6753;
    private String mCountryCode = Locale.getDefault().getCountry();
    private List<ShareYoutubeItem> mDatalist = new ArrayList<>();
    @BindView(R.id.youtube_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading)
    ProgressBar mProgressBar;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private YoutubeItemAdapter mYoutubeItemAdapter;
    private MyHandler mMyHandler;

    public final int msg_on_get_rsp = 0;

    //0 idel ,1 refresh ; 2 load more
    public int mLoadType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyHandler = new MyHandler(Looper.getMainLooper(), "main");
        ButterKnife.bind(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doGetDefaultYoutube(true);
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                doGetDefaultYoutube(true);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mYoutubeItemAdapter = new YoutubeItemAdapter(this);
        mRecyclerView.setAdapter(mYoutubeItemAdapter);
    }

    private void doGetDefaultYoutube(final boolean refresh) {
        if (mLoadType != 0) return;
        if (refresh) {
            mLastToken = null;
            mLoadType = 1;
        } else {
            mLoadType = 2;
        }
        new Thread() {
            public void run() {
                try {
                    YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                        public void initialize(HttpRequest request) throws IOException {
                        }
                    }).setApplicationName("Loops").build();

                    YouTube.Videos.List trends = youtube.videos().list("id,snippet");

                    String apiKey = mApiKey;
                    trends.setKey(apiKey);

                    trends.setChart("mostPopular");

                    trends.setFields("items(id,snippet/title,snippet/thumbnails/medium/url),nextPageToken");
                    trends.setMaxResults(20l);

                    trends.setRegionCode(mCountryCode);

                    if (!Util.isNullOrEmpty(mLastToken)) {
                        trends.setPageToken(mLastToken);
                    }

                    VideoListResponse searchResponse = trends.execute();
                    Message message = mMyHandler.obtainMessage(msg_on_get_rsp, searchResponse);
                    mMyHandler.sendMessage(message);
                    mLoadType = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    class MyHandler extends Handler {
        private String LOG_TAG = "";

        public MyHandler(Looper looper, String tag) {
            super(looper);
            LOG_TAG = tag;
        }

        public MyHandler(String tag) {
            LOG_TAG = tag;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case msg_on_get_rsp: {
                    if (msg.obj instanceof VideoListResponse) {
                        VideoListResponse videoListResponse = ((VideoListResponse) msg.obj);
                        List<Video> searchResultList = videoListResponse.getItems();
                        if (mLoadType == 1) {
                            mYoutubeItemAdapter.addData(searchResultList, true);
                        } else {
                            mYoutubeItemAdapter.addData(searchResultList, false);
                        }
                        mLastToken = videoListResponse.getNextPageToken();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                break;
            }
        }
    }

    private static class ShareYoutubeItem {
        protected String pVideoId;
        protected String pVideoTitle;
        protected String pVideoUrl;

        protected ShareYoutubeItem(String videoId, String videoTitle, String videoUrl) {
            pVideoId = videoId;
            pVideoTitle = videoTitle;
            pVideoUrl = videoUrl;
        }
    }


}
