package kr.hs.dgsw.hatomuapp.beans;

import android.databinding.BaseObservable;

import java.util.ArrayList;

import kr.hs.dgsw.hatomuapp.BR;

public class PostBean {
    private ArrayList<String> images;
    private ArrayList<String> likes;
    private String _id;
    private UserBean writer;
    private String content;
    private Long written;
    private Long modified;
    private int index;
    private int likeCnt;
    private boolean isLiked;

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public UserBean getWriter() {
        return writer;
    }

    public void setWriter(UserBean writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getWritten() {
        return written;
    }

    public void setWritten(Long written) {
        this.written = written;
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
