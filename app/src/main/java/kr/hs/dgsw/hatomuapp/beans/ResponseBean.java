package kr.hs.dgsw.hatomuapp.beans;

import com.google.gson.GsonBuilder;

public class ResponseBean<T> {
    private boolean status;
    private String message;
    T data;

    public ResponseBean() {
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, ResponseBean.class);
    }
}
