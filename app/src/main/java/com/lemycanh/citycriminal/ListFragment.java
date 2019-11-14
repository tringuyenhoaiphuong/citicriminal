package com.lemycanh.citycriminal;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    ListView mLvProblems;
    private AdapterView.OnItemClickListener onItemClickListener = (parent, view, position, id) -> {
        Problem problem = (Problem) mLvProblems.getAdapter().getItem(position);
        EventBus.getDefault().post(new MessageEvent(problem));
    };

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
        mLvProblems = view.findViewById(R.id.lv_problems);
        mLvProblems.setAdapter(new ProblemAdapter(getActivity()));
        mLvProblems.setOnItemClickListener(this.onItemClickListener);
        return view;
    }

}
