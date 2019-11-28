package com.lemycanh.citycriminal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lemycanh on 21/11/2019.
 */

public class ProblemAdapter2 extends RecyclerView.Adapter<ProblemAdapter2.ProblemViewHolder> {
    ArrayList<Problem> problems = new ArrayList<>();
    Context context;

    public ProblemAdapter2(Context context) {
        this.context = context;
        String titles[] = context.getResources().getStringArray(R.array.problemtitles);
        String contents[] = context.getResources().getStringArray(R.array.problemcontents);
        int resolveds[] = context.getResources().getIntArray(R.array.problemresolveds);
        for (int i = 0; i < titles.length; i++) {
            Problem problem = new Problem(titles[i], contents[i], new Date(), resolveds[i] ==  1);
            problems.add(problem);
        }
    }

    @NonNull
    @Override
    public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.problem_item, viewGroup, false);
        ProblemViewHolder vh = new ProblemViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemViewHolder problemViewHolder, int i) {
        Problem problem = problems.get(i);
        problemViewHolder.TvProblemTitle.setText(problem.getTitle());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        problemViewHolder.TvProblemTimestamp.setText(dateFormat.format(problem.getTimestamp()));

        problemViewHolder.CkProblemResolved.setChecked(problem.isResolved());

        problemViewHolder.itemView.setOnClickListener( v -> {
            EventBus.getDefault().post(new MessageEvent(problem));
        });

        problemViewHolder.CkProblemResolved.setOnClickListener(v -> {
            problem.setResolved(problemViewHolder.CkProblemResolved.isChecked());
            EventBus.getDefault().post(new MessageEvent(problem));
        });
    }

    @Override
    public int getItemCount() {
        return problems.size();
    }

    class ProblemViewHolder extends RecyclerView.ViewHolder {
        public CheckBox CkProblemResolved;
        public TextView TvProblemTimestamp;
        public TextView TvProblemTitle;

        public ProblemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.TvProblemTitle = itemView.findViewById(R.id.tv_problem_item_title);
            this.TvProblemTimestamp = itemView.findViewById(R.id.tv_problem_item_timestamp);
            this.CkProblemResolved = itemView.findViewById(R.id.ck_problem_item_resolved);
        }
    }
}
