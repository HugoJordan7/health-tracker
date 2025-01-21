package com.example.healthtracker.common.base;

public interface RequestCallback<T> {
    void onSuccess(T data);
    void onFailure(String message);
}
