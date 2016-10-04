package liuxiaocong.com.youtubelistandplay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class PlayActivity extends AppCompatActivity {
    private YouTubePlayerFragment mYouTubePlayerFragment;
    private YouTubePlayer mYouTubePlayer;
    private String mVideoId = "Hby-OTTxKaI";
    public final String VIDEO_ID = "video_id";

    public static void start(Context context, YoutubeModel youtubeModel) {
        Intent i = new Intent(context, PlayActivity.class);
        i.putExtra("video_id", youtubeModel.getId());
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        if (intent.hasExtra(VIDEO_ID)) {
            mVideoId = intent.getStringExtra(VIDEO_ID);
        }

        mYouTubePlayerFragment = new YouTubePlayerFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.youtube_player_fragment_container, mYouTubePlayerFragment)
                .commit();
        mYouTubePlayerFragment.initialize("123", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                mYouTubePlayer = youTubePlayer;
                mYouTubePlayer.setFullscreen(false);
                //mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                mYouTubePlayer.setPlaybackEventListener(mPlaybackEventListener);
                mYouTubePlayer.loadVideo(mVideoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                YouTubeInitializationResult mYouTubeInitializationResult = youTubeInitializationResult;
            }
        });
    }


    private YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {
            ViewGroup ytView = (ViewGroup) mYouTubePlayerFragment.getView();
            ProgressBar progressBar;
            try {
                ViewGroup child1 = (ViewGroup) ytView.getChildAt(0);
                ViewGroup child2 = (ViewGroup) child1.getChildAt(3);
                progressBar = (ProgressBar) child2.getChildAt(2);
            } catch (Throwable t) {
                progressBar = findProgressBar(ytView);
            }

            int visibility = b ? View.VISIBLE : View.INVISIBLE;
            if (progressBar != null) {
                progressBar.setVisibility(visibility);
            }
        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private ProgressBar findProgressBar(View view) {
        if (view instanceof ProgressBar) {
            return (ProgressBar) view;
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                ProgressBar res = findProgressBar(viewGroup.getChildAt(i));
                if (res != null) return res;
            }
        }
        return null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mYouTubePlayer != null) {
            mYouTubePlayer.release();
        }
    }
}
