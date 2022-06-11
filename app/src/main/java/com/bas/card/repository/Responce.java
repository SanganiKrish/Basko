package com.bas.card.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Responce<T>{

    public @NonNull
    Status status;
    public @Nullable
    T data;
    public @Nullable
    String errorMessage;

    public Responce(@NonNull Status status, @Nullable T data, @Nullable String errorMessage) {
        this.status = status;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> Responce<T> loading() {
        return new Responce<>(Status.LOADING, null, null);
    }

    public static <T> Responce<T> success(@NonNull T data) {
        return new Responce<>(Status.SUCCESS, data, null);
    }

    public static <T> Responce<T> fail(@NonNull String errorMessage) {
        return new Responce<>(Status.FAIL, null, errorMessage);
    }
    public static <T> Responce<T> internet(String internetMessage) {
        return new Responce<>(Status.INTERNET, null, internetMessage);
    }
}
