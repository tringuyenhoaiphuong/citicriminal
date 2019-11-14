package com.lemycanh.citycriminal;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvTimestamp;
    private CheckBox mCkResolved;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Problem problem = event.getProblem();
        mTvTitle.setText(problem.getTitle());
        mTvContent.setText(problem.getContent());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        mTvTimestamp.setText(dateFormat.format(problem.getTimestamp()));

        mCkResolved.setChecked(problem.isResolved());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        this.mTvTitle = view.findViewById(R.id.tv_problem_title);
        this.mTvContent = view.findViewById(R.id.tv_problem_content);
        this.mTvTimestamp = view.findViewById(R.id.tv_problem_timestamp);
        this.mCkResolved = view.findViewById(R.id.ck_problem_resolved);
        return view;
    }

    public static android.app.Fragment CreateInstance() {
        return new DetailFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
