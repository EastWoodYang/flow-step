package com.eastwood.demo.library;

import android.os.Bundle;

import com.eastwood.common.flow.Flow;
import com.eastwood.common.flow.Step;

public class InnerFlow extends Flow {

    @Override
    public Class<Step>[] getFlowSteps() {
        return new Class[] {Step1.class, Step2.class};
    }

    @Override
    public void onStepResult(String stepName, int status, Bundle bundle) {
        if ("step1".equals(stepName)) {
            next();
        } else {
            finish();
        }
    }

}
