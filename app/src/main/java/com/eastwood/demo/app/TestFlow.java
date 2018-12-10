package com.eastwood.demo.app;

import android.os.Bundle;

import com.eastwood.common.flow.Flow;
import com.eastwood.common.flow.Step;
import com.eastwood.demo.library.Step1;
import com.eastwood.demo.library.Step2;
import com.eastwood.demo.library.Step3;

public class TestFlow extends Flow {

    @Override
    public Class<Step>[] getFlowSteps() {
        return new Class[] {Step1.class, Step2.class, Step3.class};
    }

    @Override
    public void onStepResult(String stepName, int resultCode, Bundle bundle) {
        if (stepName == "step1") {
            next();
        } else if (stepName == "step2") {
            next();
        } else {
            finish();
        }
    }

}
