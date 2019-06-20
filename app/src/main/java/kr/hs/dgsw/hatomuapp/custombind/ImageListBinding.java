package kr.hs.dgsw.hatomuapp.custombind;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

import kr.hs.dgsw.hatomuapp.adapters.PostImageAdapter;

public class ImageListBinding {
    @BindingAdapter("imageList")
    public static void bindImageList(ViewPager viewPager, List<String> imageList) {
        PostImageAdapter postImageAdapter = (PostImageAdapter) viewPager.getAdapter();
        if (postImageAdapter != null)
            postImageAdapter.setList(imageList);
    }
}
