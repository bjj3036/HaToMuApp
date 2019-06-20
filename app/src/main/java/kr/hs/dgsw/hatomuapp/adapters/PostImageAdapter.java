package kr.hs.dgsw.hatomuapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.hatomuapp.fragments.PostImageFragment;

public class PostImageAdapter extends FragmentPagerAdapter {

    private List<String> imgUrlList;
    private List<Fragment> imageList;

    public PostImageAdapter(FragmentManager fm) {
        super(fm);
        imageList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        return imageList.get(i);
    }

    @Override
    public int getCount() {
        if (imageList == null)
            return 0;
        return imageList.size();
    }

    public void setList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
        this.imageList.clear();
        for (String s : this.imgUrlList) {
            imageList.add(PostImageFragment.newInstance(s));
        }
        notifyDataSetChanged();
    }

}
