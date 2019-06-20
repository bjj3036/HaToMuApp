package kr.hs.dgsw.hatomuapp.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserBean {
    private static UserBean _instance;

    @SerializedName("_id")
    private String id;
    private String url;
    private String description;
    private String name;
    @SerializedName("display_name")
    private String displayName;
    private String logo;
    @SerializedName("profile_banner")
    private String profileBanner;
    private String token;
    List<PostBean> posts;
    List<PostBean> likes;

    public static UserBean getInstance() {
        return _instance;
    }

    public static void setInstance(UserBean _instance) {
        UserBean._instance = _instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getProfileBanner() {
        return profileBanner;
    }

    public void setProfileBanner(String profileBanner) {
        this.profileBanner = profileBanner;
    }

    public List<PostBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PostBean> posts) {
        this.posts = posts;
    }

    public List<PostBean> getLikes() {
        return likes;
    }

    public void setLikes(List<PostBean> likes) {
        this.likes = likes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
