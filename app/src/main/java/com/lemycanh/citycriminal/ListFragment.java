package com.lemycanh.citycriminal;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    private Button mBtnSend;
    private EditText mEdtMessage;

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
        this.mBtnSend = view.findViewById(R.id.btn_send);
        this.mEdtMessage = view.findViewById(R.id.edt_message);
        this.mBtnSend.setOnClickListener(v -> {
            String message = this.mEdtMessage.getText().toString();
            EventBus.getDefault().post(new MessageEvent(message));
        });
        return view;
    }

}
