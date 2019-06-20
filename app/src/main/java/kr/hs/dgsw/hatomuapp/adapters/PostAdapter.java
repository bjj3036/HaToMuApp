package kr.hs.dgsw.hatomuapp.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Random;

import kr.hs.dgsw.hatomuapp.beans.PostBean;
import kr.hs.dgsw.hatomuapp.databinding.ItemPostBinding;
import kr.hs.dgsw.hatomuapp.viewmodels.PostViewModel;

/**
 * Post RecyclerView Adapter
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<PostViewModel> postList;
    private PostEventListener postEventListener;
    private FragmentManager fragmentManager;

    public PostAdapter(PostEventListener pel, FragmentManager fragmentManager) {
        postEventListener = pel;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Post Item 데이터 바인딩
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        //Post안에 이미지 슬라이드 Adapter
        //Post안의 이벤트들을 Main에서 처리하기 위해
        binding.setPostEvent(this.postEventListener);
        return new PostViewHolder(binding, this.fragmentManager);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        postViewHolder.bind(this.postList.get(i));
    }

    public void setItem(List<PostViewModel> list) {
        if (list == null)
            return;
        this.postList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.postList == null)
            return 0;
        return this.postList.size();
    }

    public void addItem(List<PostBean> addList) {
        for (int i = 0; i < addList.size(); i++)
            addItem(addList.get(i));
    }

    public void addItem(PostBean post) {
        PostViewModel postVM = new PostViewModel(post);
        if (this.postList.contains(postVM)) {
            return;
        }
        this.postList.add(postVM);
        notifyDataSetChanged();
    }

    public void clearPost() {
        this.postList.clear();
        notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        ItemPostBinding binding;
        FragmentManager fragmentManager;

        public PostViewHolder(ItemPostBinding binding, FragmentManager fragmentManager) {
            super(binding.getRoot());
            this.binding = binding;
            this.fragmentManager = fragmentManager;
        }

        public void bind(PostViewModel postBean) {
            binding.setPost(postBean);
            PostImageAdapter adapter = new PostImageAdapter(this.fragmentManager);
            TabLayout tabLayout = binding.tabLayout;
            binding.imagePager.setId(new Random().nextInt(1000));
            adapter.setList(postBean.images.get());
            binding.imagePager.setAdapter(adapter);
            tabLayout.removeAllTabs();
            if (postBean.images.get().size() != 1) {
                for (int i = 0; i < postBean.images.get().size(); i++) {
                    tabLayout.addTab(tabLayout.newTab());
                }
                tabLayout.setVisibility(View.VISIBLE);
            } else {
                tabLayout.setVisibility(View.GONE);
            }

            ViewGroup tabs = (ViewGroup) tabLayout.getChildAt(0);
            for (int i = 0; i < tabs.getChildCount() - 1; i++) {
                View tab = tabs.getChildAt(i);
                //터치 막기
                tab.setOnTouchListener((v, event) -> false);
                //tab들 사이에 margin 설정
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();
                layoutParams.setMarginEnd(4);
                tab.setLayoutParams(layoutParams);
                tabLayout.requestLayout();
            }
            binding.imagePager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            binding.imagePager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    postBean.currentItem.set(position);
                }
            });
            binding.imagePager.setCurrentItem(postBean.currentItem.get());
        }
    }

    public interface PostEventListener {
        void OnLogoClick(View v, PostViewModel post);

        void OnLikeClick(View v, PostViewModel post);

        void OnTwitchClick(View v, PostViewModel post);
    }
}
