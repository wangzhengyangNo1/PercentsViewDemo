package com.wzhy.percentsviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PercentsView mPercentsView;
    private ArrayList<Float> mPercentList;
    private Button mBtnRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mPercentsView = (PercentsView) findViewById(R.id.percents_view);
        mBtnRefresh = (Button) findViewById(R.id.btn_refresh);

        mBtnRefresh.setOnClickListener(getOnClickListener());

        initData();

        mPercentsView.setLimitPercent(0.03f);
        mPercentsView.setPercentList(mPercentList);
        mPercentsView.setOnItemSelectedListener(new PercentsView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View contentView, int index) {
                //Toast.makeText(getApplicationContext(), "" + index, Toast.LENGTH_SHORT).show();
                TextView tvPercent = (TextView) contentView.findViewById(R.id.tv_percent);
                tvPercent.setText(String.valueOf(Math.round(100 * mPercentList.get(index))).concat("%"));
            }
        });

    }

    private void initData() {
        mPercentList = new ArrayList<>();

        mPercentList.add(0.18f);
        mPercentList.add(0.15f);
        mPercentList.add(0.12f);
        mPercentList.add(0.10f);
        mPercentList.add(0.109f);
        mPercentList.add(0.042f);
        mPercentList.add(0.07f);
        mPercentList.add(0.09f);
        mPercentList.add(0.02f);
        mPercentList.add(0.03f);
        mPercentList.add(0.05f);
        mPercentList.add(0.04f);

    }

    private View.OnClickListener mOnClickListener;

    public View.OnClickListener getOnClickListener() {
        if (null == mOnClickListener) {
            mOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPercentList.clear();
                    mPercentList.add(0.13f);
                    mPercentList.add(0.15f);
                    mPercentList.add(0.10f);
                    mPercentList.add(0.12f);
                    mPercentList.add(0.13f);
                    mPercentList.add(0.07f);
                    mPercentList.add(0.04f);
                    mPercentList.add(0.07f);
                    mPercentList.add(0.04f);
                    mPercentList.add(0.01f);
                    mPercentList.add(0.07f);
                    mPercentList.add(0.04f);
                    mPercentsView.updatePercentList(mPercentList);
                }
            };
        }
        return mOnClickListener;
    }
}
