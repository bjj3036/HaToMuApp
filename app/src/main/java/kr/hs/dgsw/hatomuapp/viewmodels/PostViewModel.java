package kr.hs.dgsw.hatomuapp.viewmodels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import kr.hs.dgsw.hatomuapp.beans.PostBean;
import kr.hs.dgsw.hatomuapp.beans.UserBean;

/**
 * Post ViewModel
 */

public class PostViewModel implements Comparable<PostViewModel> {
    public final ObservableField<ArrayList<String>> images = new ObservableField<>();
    public final ObservableField<ArrayList<String>> likes = new ObservableField<>();
    public final ObservableField<String> _id = new ObservableField<>();
    public final ObservableField<UserBean> writer = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableLong written = new ObservableLong();
    public final ObservableLong modified = new ObservableLong();
    public final ObservableInt index = new ObservableInt();
    public final ObservableInt likeCnt = new ObservableInt();
    public final ObservableBoolean isLiked = new ObservableBoolean();
    public final ObservableInt currentItem = new ObservableInt();

    public PostViewModel(PostBean postBean) {
        images.set(postBean.getImages());
        likes.set(postBean.getLikes());
        _id.set(postBean.get_id());
        writer.set(postBean.getWriter());
        content.set(postBean.getContent());
        written.set(postBean.getWritten());
        modified.set(postBean.getModified());
        index.set(postBean.getIndex());
        likeCnt.set(postBean.getLikeCnt());
        isLiked.set(postBean.isLiked());
        currentItem.set(0);
    }

    public void addLikeCnt(int add){
        this.likeCnt.set(this.likeCnt.get()+add);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof PostViewModel))
            return false;
        PostViewModel target = (PostViewModel) obj;
        return this._id.get().equals(target._id.get());
    }

    @Override
    public int compareTo(@NonNull PostViewModel o) {
        if (this.index.get() < o.index.get())
            return 1;
        else if (this.index.get() == o.index.get())
            return 0;
        return -1;
    }
}
