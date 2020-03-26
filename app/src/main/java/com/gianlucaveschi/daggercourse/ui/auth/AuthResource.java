package com.gianlucaveschi.daggercourse.ui.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Wrapper class to add a status to the Response from retrofit according to google standards.
 *
 * AuthResource could be a normal but class but instead it is a generic class of type User because
 * normally in an app you can login with Twitter, Facebook, Github, Google, etc...
 * There could be different data structures for each.
 * */
public class AuthResource<T> {

    @NonNull
    public final AuthStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public AuthResource(@NonNull AuthStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> AuthResource<T> authenticated (@Nullable T data) {
        return new AuthResource<>(AuthStatus.AUTHENTICATED, data, null);
    }

    public static <T> AuthResource<T> error(@NonNull String msg, @Nullable T data) {
        return new AuthResource<>(AuthStatus.ERROR, data, msg);
    }

    public static <T> AuthResource<T> loading(@Nullable T data) {
        return new AuthResource<>(AuthStatus.LOADING, data, null);
    }

    public static <T> AuthResource<T> logout () {
        return new AuthResource<>(AuthStatus.NOT_AUTHENTICATED, null, null);
    }

    public enum AuthStatus { AUTHENTICATED, ERROR, LOADING, NOT_AUTHENTICATED}

}
