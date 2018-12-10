package com.eastwood.common.flow;

import android.os.Bundle;

public abstract class Flow extends FlowContext {

    abstract protected Class<Step>[] getFlowSteps();

    abstract protected void onStepResult(String stepName, int resultCode, Bundle bundle);

    public void start() {
        start(null, null);
    }

    public void start(OnFlowListener onFlowListener) {
        start(null, onFlowListener);
    }

    public void start(Bundle bundle) {
        start(bundle, null);
    }

    public void start(Bundle bundle, OnFlowListener onFlowListener) {
        FlowManager.getInstance().startFlow(this, bundle, onFlowListener);
    }

    protected void next() {
        next(null, null);
    }

    protected void next(String stepName) {
        next(stepName, null);
    }

    protected void next(Bundle bundle) {
        FlowManager.getInstance().nextStep(this, null, bundle);
    }

    protected void next(String stepName, Bundle bundle) {
        FlowManager.getInstance().nextStep(this, stepName, bundle);
    }

    protected void finish() {
        FlowManager.getInstance().finish(this);
    }

}
