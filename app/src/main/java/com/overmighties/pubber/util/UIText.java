package com.overmighties.pubber.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UIText {
    private DynamicString dynamicString=null;
    private ResourceString resourceString=null;
    public UIText(DynamicString dynamicString){
        this.dynamicString=dynamicString;
    }
    public UIText(ResourceString resourceString){
        this.resourceString=resourceString;
    }
    @Getter
    @AllArgsConstructor
    public static final class DynamicString extends UIText{
        private String val;
    }
    @Getter
    @AllArgsConstructor
    public static final class ResourceString extends UIText{
        @StringRes
        private int resId;
        private String getString(@NonNull Context context){
            return context.getText(resId).toString();
        }
    }
}
