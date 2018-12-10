# Flow Step

**流程由多个步骤(Step)组成，把每个Step的具体实现（做什么、怎么做等）从流程中剥离，流程只关心Step的结果和调度Step的顺序。**

## Usage

### 1. 步骤(Step)的具体实现
继承抽象类*Step*，并通过注解@FlowStep配置该Step名称。
```
@FlowStep(name = "step1")
public class Step1 extends Step {
 
    @Override
    public void execute(Bundle bundle) {
        ...
    }
    
}
```
*execute*方法为该步骤(Step)的具体实现（做什么、怎么做等）。

**该Step执行结束时，需调用finish方法**，与*Activity*的*finish*方法用法相似。
```
setResult(RESULT_OK); // 若不设置该Step执行结果，则默认为RESULT_CANCELED
finish();
```

必要时，可重写*destroy*方法。
```
@Override
protected void destroy() {
    super.destroy();
    // to something.
}
```

### 2. 流程(Flow)的配置与调度
继承抽象类*Flow*，并在*getFlowSteps*方法中返回这个流程所需步骤的Class数组。
```
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
```

每个步骤执行结束，将会回调 **void onStepResult(String stepName, int resultCode, Bundle bundle)** 这个方法。

回调参数说明：
- stepName： Step的名称。
- resultCode：Step的执行结果。
- bundle：Step的执行返回的一些数据信息。

根据每个步骤执行结果，结合业务逻辑，通过*next()*或*finish()* 方法进行流程的调度或终止。

**流程调度next方法** 
执行下一个步骤，默认为步骤Class数组中当前步骤的下一个步骤。也可直接指定Step的名称并通过Bundle传值。

- *void next()*
- *void next(String stepName)*
- *void next(Bundle bundle)*
- *void next(String stepName, Bundle bundle)*

**流程终止finish方法**
与*Activity*的*finish*方法用法相似。注意和Step的finish的区别。
```
setResult(RESULT_OK); // 若不设置该Flow执行结果，则默认为RESULT_CANCELED
finish();
```

**注意：若最后一步Step继续调用next()方法，将会调用finish()方法。**

### 3. Flow流程初始化与执行
```
TestFlow testFlow = new TestFlow();
testFlow.setContext(MainActivity.this);
testFlow.start(new OnFlowListener() {
    @Override
    public void onFlowResult(int resultCode, Bundle bundle) {
 
    }
});
```
**setContext**

设置整个流程的Context上下文。在Step中，可通过getFlowContext.getContext()获取。

**setFlowData**

设置整个流程的公共数据。在Step中，可通过getFlowContext.getFlowData()获取。

**start**

开始执行流程。可传递bundle给第一个Step，并设置流程结果回调。
- *void start()*
- *void start(OnFlowListener onFlowListener)*
- *void start(Bundle bundle)*
- *void start(Bundle bundle, OnFlowListener onFlowListener)*

