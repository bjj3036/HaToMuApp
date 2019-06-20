package kr.hs.dgsw.hatomuapp.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kr.hs.dgsw.hatomuapp.beans.PostBean;
import kr.hs.dgsw.hatomuapp.databinding.ItemPostBinding;
import kr.hs.dgsw.hatomuapp.databinding.ItemPostPreviewBinding;

public class PostPreviewAdapter extends RecyclerView.Adapter<PostPreviewAdapter.PostPreviewViewHolder> {

    private List<PostBean> postList;
    private OnPreviewClickListener listener;

    public PostPreviewAdapter(OnPreviewClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostPreviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemPostPreviewBinding binding = ItemPostPreviewBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new PostPreviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostPreviewViewHolder vh, int i) {
        vh.binding.setPost(postList.get(i));
        vh.binding.setListener(this.listener);
    }

    @Override
    public int getItemCount() {
        if (postList == null)
            return 0;
        return postList.size();
    }

    public void setPosts(List postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }

    class PostPreviewViewHolder extends RecyclerView.ViewHolder {

        ItemPostPreviewBinding binding;

        public PostPreviewViewHolder(@NonNull ItemPostPreviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnPreviewClickListener {
        void OnPreviewClick(View v, PostBean postBean);
    }
}
