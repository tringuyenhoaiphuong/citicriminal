package com.lemycanh.citycriminal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lemycanh on 14/11/2019.
 */

public class ProblemAdapter extends BaseAdapter {
    Context context;
    ArrayList<Problem> problems = new ArrayList<>();

    public ProblemAdapter(Context context) {
        this.context = context;
        String titles[] = context.getResources().getStringArray(R.array.problemtitles);
        String contents[] = context.getResources().getStringArray(R.array.problemcontents);
        int resolveds[] = context.getResources().getIntArray(R.array.problemresolveds);
        for (int i = 0; i < titles.length; i++) {
            Problem problem = new Problem(titles[i], contents[i], new Date(), resolveds[i] ==  1);
            problems.add(problem);
        }
    }

    @Override
    public int getCount() {
        return problems.size();
    }

    @Override
    public Object getItem(int position) {
        return problems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            //read xml layout => view
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.problem_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.TvTitle = convertView.findViewById(R.id.tv_problem_item_title);
            viewHolder.TvTimestamp = convertView.findViewById(R.id.tv_problem_item_timestamp);
            viewHolder.CkResolved = convertView.findViewById(R.id.ck_problem_item_resolved);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //assign values to covertView
        Problem problem = problems.get(position);
        viewHolder.TvTitle.setText(problem.getTitle());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        viewHolder.TvTimestamp.setText(dateFormat.format(problem.getTimestamp()));
        viewHolder.CkResolved.setChecked(problem.isResolved());

        return convertView;
    }

    private class ViewHolder {
        public TextView TvTitle;
        public TextView TvTimestamp;
        public CheckBox CkResolved;
    }
}
