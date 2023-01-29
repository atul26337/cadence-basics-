package com.example.Greeting;

import com.example.Greeting.activites.GreetingActivitiesImpl;
import com.example.Greeting.definition.GreetingWorkflowImpl;
import com.example.Greeting.definition.IGreetingWorkflow;
import com.uber.cadence.WorkflowExecution;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerOptions;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.event.ServiceReadyEvent;
import jakarta.inject.Singleton;
@Singleton
public class CadenceRunner implements ApplicationEventListener<ServiceReadyEvent> {
    @Override
    public void onApplicationEvent(ServiceReadyEvent event) {
        //Uncomment this for workflow start
        runWorkflow();

    }
    public boolean runWorkflow(){
        try {
            WorkflowClient workflowClient = WorkflowClient.newInstance("localhost", 7933, "Test");
            Worker.Factory factory = new Worker.Factory("localhost", 7933, "Test");
            WorkerOptions workerOptions = new WorkerOptions.Builder().setEnableLoggingInReplay(true)
                    .setMaxConcurrentActivityExecutionSize(75)
                    .setMaxConcurrentWorkflowExecutionSize(50)
                    .setTaskListActivitiesPerSecond(75)
                    .build();
            Worker worker = factory.newWorker("GREETING", workerOptions);
            worker.registerWorkflowImplementationTypes(GreetingWorkflowImpl.class);
            worker.registerActivitiesImplementations(new GreetingActivitiesImpl());
            factory.start();

            IGreetingWorkflow workflow =
                    workflowClient.newWorkflowStub(IGreetingWorkflow.class);
            WorkflowExecution workflowExecution = WorkflowClient.start(
                    workflow::getGreeting, "World");
            return true;
        }catch (Exception e){
            throw e;
        }
    }
}
