package liuxiaocong.com.youtubelistandplay;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.services.youtube.model.Video;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LiuXiaocong on 10/4/2016.
 */
public class YoutubeItemAdapter extends RecyclerView.Adapter<YoutubeItemAdapter.ViewHolder> {

    private List<YoutubeModel> mVideoList = new ArrayList<>();
    LayoutInflater mLayoutInflater;
    private Activity mActivity;

    public YoutubeItemAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_youtueb_video, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayActivity.start(mActivity, mVideoList.get(position));
            }
        });
        YoutubeModel youtubeModel = mVideoList.get(position);
        if (youtubeModel == null) return;
        holder.mTitle.setText(youtubeModel.getSnippet().getTitle());
        Uri uri = Uri.parse(youtubeModel.getSnippet().getThumbnails().getMedium().getUrl());
        holder.mCover.setImageURI(uri);
    }

    public void addData(List<Video> videos, boolean refresh) {
        if (refresh) {
            mVideoList.clear();
        }
        for (Video video : videos) {
            YoutubeModel youtubeModel = GsonImpl.get().toObject(video.toString(), YoutubeModel.class);
            mVideoList.add(youtubeModel);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cover)
        ImageView mCover;
        @BindView(R.id.title)
        TextView mTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
