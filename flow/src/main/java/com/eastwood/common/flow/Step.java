package com.eastwood.common.flow;

import android.os.Bundle;

public abstract class Step extends StepContext {

    abstract protected void execute(Bundle bundle);

    protected String getStepName() {
        String stepName = null;
        FlowStep flowStep = this.getClass().getAnnotation(FlowStep.class);
        if (flowStep != null) {
            stepName = flowStep.name();
        }
        return stepName;
    }

    protected void finish() {
        FlowManager.getInstance().finish(this);
    }

    protected void destroy() {

    }

}
