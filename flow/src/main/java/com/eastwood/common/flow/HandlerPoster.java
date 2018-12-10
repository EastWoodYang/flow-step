package com.eastwood.common.flow;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class HandlerPoster extends Handler {

    protected HandlerPoster(Looper looper) {
        super(looper);
    }

    public void enqueue(int what, Object object) {
        enqueue(what, object, null);
    }

    public synchronized void enqueue(int what, Object object, Bundle bundle) {
        Message message = obtainMessage(what, object);
        message.setData(bundle);
        if (!sendMessage(message)) {
            throw new RuntimeException("Could not send handler message");
        }
    }

    @Override
    public void handleMessage(Message msg) {
        FlowManager flowManager = FlowManager.getInstance();
        switch (msg.what) {
            case FlowResult.FLOW_START:
            case FlowResult.STEP_START:
                flowManager.nextStep((Step) msg.obj, msg.getData());
                break;
            case FlowResult.STEP_FINISH:
                flowManager.finishStep((Step) msg.obj);
                break;
            case FlowResult.FLOW_FINISH:
                flowManager.finishFlow((Flow) msg.obj);
                break;
        }
    }

}