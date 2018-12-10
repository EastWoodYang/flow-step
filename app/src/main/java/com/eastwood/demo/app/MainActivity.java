package com.eastwood.demo.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.eastwood.common.flow.OnFlowListener;
import com.eastwood.demo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start_flow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestFlow testFlow = new TestFlow();
                testFlow.setContext(MainActivity.this);
                testFlow.start(new OnFlowListener() {
                    @Override
                    public void onFlowResult(int resultCode, Bundle bundle) {
                        Log.d("TestFlow", "onFlowResult");
                        Toast.makeText(MainActivity.this, "onFlowResult: " + resultCode, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}
