package kr.hs.dgsw.hatomuapp.custombind;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import kr.hs.dgsw.hatomuapp.utils.DateUtil;

public class TextBinding {
    @BindingAdapter("diffTime")
    public static void bindImageList(TextView viewPager, long before) {
        if(before != 0)
            viewPager.setText(DateUtil.diffFromCurrent(before));
    }
}
