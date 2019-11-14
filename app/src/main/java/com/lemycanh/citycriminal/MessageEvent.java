package com.lemycanh.citycriminal;

/**
 * Created by lemycanh on 7/11/2019.
 */

public class MessageEvent {
    Problem problem;


    public MessageEvent(Problem problem) {
        this.problem = problem;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
