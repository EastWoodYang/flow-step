package com.eastwood.common.flow;

import android.content.Context;
import android.os.Bundle;

public class FlowContext extends FlowResult {

    private Context mContext;
    private Bundle mFlowData;

    public void setContext(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public void setFlowData(Bundle bundle) {
        mFlowData = bundle;
    }

    public Bundle getFlowData() {
        return mFlowData;
    }

}
