package com.eastwood.common.flow;

import android.os.Bundle;

public class FlowResult {

    /**
     * Standard step result: canceled.
     */
    public static final int RESULT_CANCELED = 0;
    /**
     * Standard step result: succeeded.
     */
    public static final int RESULT_OK = 1;

    final static int FLOW_START = 1;
    final static int FLOW_FINISH = 2;
    final static int STEP_START = 3;
    final static int STEP_FINISH = 4;

    private int mResultCode = RESULT_CANCELED;
    private Bundle mResultDate;

    protected void setResult(int resultCode) {
        mResultCode = resultCode;
    }

    protected void setResult(int resultCode, Bundle resultDate) {
        mResultCode = resultCode;
        mResultDate = resultDate;
    }

    protected int getResultCode() {
        return mResultCode;
    }

    protected Bundle getResultDate() {
        return mResultDate;
    }

}
