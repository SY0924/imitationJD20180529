package com.rookie.imitationjd.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fynn.fluidlayout.FluidLayout;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.adapter.SearchRecoedAdapter;
import com.rookie.imitationjd.bean.SearchBean;
import com.rookie.imitationjd.gen.DaoMaster;
import com.rookie.imitationjd.gen.DaoSession;
import com.rookie.imitationjd.gen.Record;
import com.rookie.imitationjd.gen.RecordDao;
import com.rookie.imitationjd.presenter.SearchPresenter;
import com.rookie.imitationjd.view.customview.MySearchView;
import com.rookie.imitationjd.view.interfaces.ISearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements ISearchView {


    String[] arrs = {"手机", "电脑", "月饼", "三只松鼠", "单机斗地主", "天堂战记", "妖精的尾巴", "极限挑战", "我们相爱吧", "倚天屠龙记", "明星大侦探"
    };
    @BindView(R.id.search_back)
    ImageView mSearchBack;
    @BindView(R.id.search_edit)
    MySearchView mSearchEdit;
    @BindView(R.id.search_btn)
    TextView mSearchBtn;
    @BindView(R.id.search_clear)
    Button mSearchClear;
    @BindView(R.id.search_list)
    RecyclerView mSearchList;
    private Button button;
    private FluidLayout fluid_layout;
    private int pscid;

    private List<SearchBean.DataBean> list = new ArrayList<>();
    private SearchPresenter searchPresenter;
    private EditText search_content;
    private int page = 1;
    private RecordDao recordDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);


        fluid_layout = (FluidLayout) findViewById(R.id.fluid_layout);

        search_content = (EditText) findViewById(R.id.search_content);
        search_content.setFocusable(true);
        search_content.setFocusableInTouchMode(true);

        //搜索商品持有的presenter
        searchPresenter = new SearchPresenter(this);
        initData();

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "search.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        recordDao = daoSession.getRecordDao();


    }

    private void initData() {
        for (int i = 0; i < arrs.length; i++) {
            button = new Button(this);
            button.setText(arrs[i]);
            //文字大小
            button.setTextSize(13);
            //搜索的字
            final String s = button.getText().toString();
            //上下左右的距离
            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //上下左右的距离
            params.setMargins(6, 6, 6, 6);
            fluid_layout.addView(button, params);
        }
    }


    @Override
    public void showSearchData(List<SearchBean.DataBean> data) {
        if (data != null) {
            list.addAll(data);
            for (int i = 0; i < data.size(); i++) {
                pscid = data.get(i).getPscid();
            }
            Intent intent = new Intent(SearchActivity.this, GoodsListActivity.class);
            intent.putExtra("pscid", pscid + "");
            startActivity(intent);
        } else {
            Toast.makeText(this, "没有这个商品", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.search_back, R.id.search_btn, R.id.search_clear})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.search_back:
                finish();
                break;
            case R.id.search_btn:
                String context = search_content.getText().toString();
                if(TextUtils.isEmpty(context.trim())){
                    Toast.makeText(this, "请输入要搜索的内容", Toast.LENGTH_SHORT).show();
                }else {
                    searchPresenter.searchParams(context, page + "");
                    Record record = new Record(null, context);
                    recordDao.insert(record);
                    select();
                }

                break;
            case R.id.search_clear:
                recordDao.deleteAll();
                select();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        select();
    }

    private void select() {
        List<Record> records = recordDao.loadAll();
        SearchRecoedAdapter adapter = new SearchRecoedAdapter(records, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mSearchList.setLayoutManager(linearLayoutManager);
        //设置默认分隔线
        DividerItemDecoration dec = new DividerItemDecoration(SearchActivity.this,DividerItemDecoration.VERTICAL);
        mSearchList.addItemDecoration(dec);
        mSearchList.setAdapter(adapter);

    }
}





