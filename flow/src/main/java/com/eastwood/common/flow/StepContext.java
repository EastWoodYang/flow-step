package com.eastwood.common.flow;

public class StepContext extends FlowResult {

    private FlowContext flowContext;

    public void setFlowContext(FlowContext context) {
        flowContext = context;
    }

    public FlowContext getFlowContext() {
        return flowContext;
    }

}
