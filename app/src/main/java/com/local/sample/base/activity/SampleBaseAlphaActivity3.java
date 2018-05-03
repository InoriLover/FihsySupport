package com.local.sample.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import fishy.support.base.activity.BaseAlphaActivity;

/**
 * Created by DN2017030300 on 2018/5/3.
 */

public class SampleBaseAlphaActivity3 extends BaseAlphaActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Mode chooseAlphaMode() {
        return null;
    }
}
