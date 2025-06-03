package com.example.dacs3.model;

import java.util.List;

public class LoaidlModel {
    boolean success;
    String message;
    List<Loaidl> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Loaidl> getResult() {
        return result;
    }

    public void setResult(List<Loaidl> result) {
        this.result = result;
    }
}
