package com.example.Greeting.definition;

import com.uber.cadence.workflow.QueryMethod;
import com.uber.cadence.workflow.SignalMethod;
import com.uber.cadence.workflow.WorkflowMethod;

public interface IGreetingWorkflow {
    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 3600*24*7, taskList = "GREETING")
    public String getGreeting(String name);

    @QueryMethod
    String getWorkFlowStatus();

    @SignalMethod
    void exit();
}
