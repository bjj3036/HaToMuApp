package kr.hs.dgsw.hatomuapp.custombind;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import kr.hs.dgsw.hatomuapp.adapters.PostAdapter;
import kr.hs.dgsw.hatomuapp.viewmodels.PostViewModel;

public class AdapterBinding {
    @BindingAdapter("item")
    public static void bindItem(RecyclerView recyclerView, List<PostViewModel> post) {
        PostAdapter adapter = (PostAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(post);
        }
    }
}
