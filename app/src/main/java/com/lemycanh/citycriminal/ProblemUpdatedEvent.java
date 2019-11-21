package com.lemycanh.citycriminal;

/**
 * Created by lemycanh on 21/11/2019.
 */

public class ProblemUpdatedEvent {
    private final Problem problem;

    public ProblemUpdatedEvent(Problem problem) {
        this.problem = problem;
    }

    public Problem getProblem() {
        return problem;
    }
}
