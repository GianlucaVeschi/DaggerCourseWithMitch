package com.gianlucaveschi.daggercourse.di;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey//Key needed to map more ViewModels
public @interface ViewModelKey {
    Class<? extends ViewModel> value(); //"?" could be any class that wants to extend ViewModel
}