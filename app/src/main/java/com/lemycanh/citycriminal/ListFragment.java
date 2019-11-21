package com.lemycanh.citycriminal;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    RecyclerView mRvProblems;

    public ListFragment() {
        // Required empty public constructor
    }

    public static Fragment CreateInstance() {
        return new ListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRvProblems = view.findViewById(R.id.rv_problems);
        ProblemAdapter2 adapter2 = new ProblemAdapter2(getActivity());
        mRvProblems.setAdapter(adapter2);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnProblemUpdated(ProblemUpdatedEvent event) {
        mRvProblems.getAdapter().notifyDataSetChanged();
        mRvProblems.invalidate();
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
