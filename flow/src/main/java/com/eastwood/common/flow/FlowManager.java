package com.eastwood.common.flow;

import android.os.Bundle;
import android.os.Looper;

import java.util.Stack;

class FlowManager {

    private static FlowManager flowManager;

    static FlowManager getInstance() {
        if (flowManager == null) {
            flowManager = new FlowManager();
        }
        return flowManager;
    }

    private HandlerPoster handlerPoster;
    private Stack<FlowIndex> mFlowIndexStack;

    private FlowManager() {
        Object looperOrNull = getAndroidMainLooperOrNull();
        handlerPoster = new HandlerPoster((Looper) looperOrNull);
        mFlowIndexStack = new Stack<>();
    }

    void startFlow(Flow flow, Bundle bundle, OnFlowListener onFlowListener) {
        Class<Step>[] steps = flow.getFlowSteps();
        if (steps[0] == null) {
            return;
        }

        FlowIndex flowIndex = new FlowIndex();
        flowIndex.flow = flow;
        flowIndex.onFlowListener = onFlowListener;
        flowIndex.index = -1;
        mFlowIndexStack.push(flowIndex);

        Step firstStep = newStep(steps[0]);
        if (firstStep == null) {
            throw new IllegalArgumentException("New instance failure of class " + steps[0].getName());
        }
        handlerPoster.enqueue(FlowResult.FLOW_START, firstStep, bundle);
    }

    void nextStep(Flow flow, String stepName, Bundle bundle) {
        FlowIndex topFlowIndex = mFlowIndexStack.peek();
        Class<Step>[] steps = topFlowIndex.flow.getFlowSteps();
        Class<Step> nextStepClass;
        int index;
        if (stepName != null) {
            index = indexOfStep(steps, stepName);
            if (index == -1) {
                throw new IllegalArgumentException("Can't find flow step class with name: [" + stepName + "]");
            }
            nextStepClass = steps[index];
        } else {
            if (topFlowIndex.index + 1 > steps.length) {
                finishFlow(flow);
                return;
            }
            index = topFlowIndex.index + 1;
            nextStepClass = steps[index];
        }
        if (nextStepClass == null) {
            return;
        }
        Step nextStep = newStep(nextStepClass);
        if (nextStep == null) {
            throw new IllegalArgumentException("New instance failure of class " + nextStepClass.getName());
        }
        handlerPoster.enqueue(FlowResult.STEP_START, nextStep, bundle);
    }

    void nextStep(Step nextStep, Bundle bundle) {
        FlowIndex topFlowIndex = mFlowIndexStack.peek();
        topFlowIndex.index += 1;
        nextStep.setFlowContext(topFlowIndex.flow);
        nextStep.execute(bundle);
    }

    void finish(Step step) {
        handlerPoster.enqueue(FlowResult.STEP_FINISH, step);
    }

    void finishStep(Step step) {
        FlowIndex flowIndex = mFlowIndexStack.peek();
        flowIndex.flow.onStepResult(step.getStepName(), step.getResultCode(), step.getResultDate());
        step.destroy();
    }

    void finish(Flow flow) {
        handlerPoster.enqueue(FlowResult.FLOW_FINISH, flow);
    }

    void finishFlow(Flow flow) {
        FlowIndex flowIndex = mFlowIndexStack.pop();
        if (flowIndex.onFlowListener != null) {
            flowIndex.onFlowListener.onFlowResult(flow.getResultCode(), flow.getResultDate());
        }

        if (mFlowIndexStack.empty()) {
            mFlowIndexStack = null;
            handlerPoster = null;
            flowManager = null;
        }

    }

    private int indexOfStep(Class<Step>[] stepClasses, String stepName) {
        for (int i = 0; i < stepClasses.length; i++) {
            FlowStep flowStep = stepClasses[i].getAnnotation(FlowStep.class);
            if (flowStep != null && flowStep.name().equals(stepName)) {
                return i;
            }
        }
        return -1;
    }

    private Step newStep(Class<Step> stepClass) {
        try {
            return stepClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    class FlowIndex {
        Flow flow;
        int index;
        OnFlowListener onFlowListener;
    }

    private Object getAndroidMainLooperOrNull() {
        try {
            return Looper.getMainLooper();
        } catch (RuntimeException e) {
            // Not really a functional Android (e.g. "Stub!" maven dependencies)
            return null;
        }
    }

}
