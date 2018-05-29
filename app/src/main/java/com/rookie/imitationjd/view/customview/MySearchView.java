package com.rookie.imitationjd.view.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.rookie.imitationjd.R;


/**
 * Created by 暗夜 on 2018/4/11.
 */

public class MySearchView extends LinearLayout{

    private EditText search_content;

    public MySearchView(Context context) {
        this(context,null);
    }

    public MySearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MySearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.layout_custom_search,this);
        search_content = view.findViewById(R.id.search_content);
    }
}
