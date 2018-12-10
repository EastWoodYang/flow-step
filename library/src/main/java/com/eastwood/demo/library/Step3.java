package com.eastwood.demo.library;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.eastwood.common.flow.FlowContext;
import com.eastwood.common.flow.FlowResult;
import com.eastwood.common.flow.FlowStep;
import com.eastwood.common.flow.OnFlowListener;
import com.eastwood.common.flow.Step;

@FlowStep(name = "step3")
public class Step3 extends Step {

    @Override
    public void execute(Bundle bundle) {
        Log.d("execute", "begin " + this.getStepName());
        FlowContext flowContext = getFlowContext();
        Context context = flowContext.getContext();

        InnerFlow innerFlow = new InnerFlow();
        innerFlow.setContext(context);
        innerFlow.start(new OnFlowListener() {
            @Override
            public void onFlowResult(int result, Bundle bundle) {
                Log.d("InnerFlow", "onFlowResult");
                setResult(FlowResult.RESULT_OK, bundle);
                finish();
            }
        });
        Log.d("execute", "end   " + this.getStepName());
    }

}
