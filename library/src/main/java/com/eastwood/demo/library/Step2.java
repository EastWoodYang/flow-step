package com.eastwood.demo.library;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.eastwood.common.flow.FlowContext;
import com.eastwood.common.flow.FlowResult;
import com.eastwood.common.flow.FlowStep;
import com.eastwood.common.flow.Step;

@FlowStep(name = "step2")
public class Step2 extends Step {

    @Override
    public void execute(Bundle bundle) {
        Log.d("execute", "begin " + this.getStepName());
        FlowContext flowContext = getFlowContext();
        Context context = flowContext.getContext();
        Bundle flowData = flowContext.getFlowData();

        // do something

        setResult(FlowResult.RESULT_OK, bundle);
        finish();

        Log.d("execute", "end   " + this.getStepName());
    }

}
