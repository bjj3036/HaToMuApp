package kr.hs.dgsw.hatomuapp.custombind;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import kr.hs.dgsw.hatomuapp.R;

public class ImageUrlBinding {
    @BindingAdapter("imageUrl")
    public static void bindImageUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_hatomu_placeholder_image)
                .skipMemoryCache(true)
                .override(Target.SIZE_ORIGINAL)
                .into(imageView);
    }

    @BindingAdapter("bannerImageUrl")
    public static void bindBannerImageUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.default_profile_banner)
                .skipMemoryCache(true)
                .override(Target.SIZE_ORIGINAL)
                .into(imageView);
    }

    @BindingAdapter("profileImageUrl")
    public static void bindProfileImageUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.user_profile)
                .skipMemoryCache(true)
                .override(Target.SIZE_ORIGINAL)
                .into(imageView);
    }
}
