package com.example.Greeting.definition;

import com.example.Greeting.activites.IGreetingActivities;
import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.common.RetryOptions;
import com.uber.cadence.workflow.Workflow;

import java.time.Duration;

public  class GreetingWorkflowImpl implements IGreetingWorkflow {
    private final ActivityOptions options =
            new ActivityOptions.Builder().setScheduleToCloseTimeout(Duration.ofHours(1)).setRetryOptions(new RetryOptions.Builder()
                    .setInitialInterval(Duration.ofSeconds(5)).setBackoffCoefficient(2).setExpiration(Duration.ofDays(7))
                            .setMaximumInterval(Duration.ofHours(24)).build()).build();
    private final IGreetingActivities activities = Workflow.newActivityStub(IGreetingActivities.class,options);
    @Override
    public String getGreeting(String name) {
       try {
           String greetingActivityResult = "Not set";
           System.out.println(
                   "About to run the composeGreeting activity. Activity result = " + greetingActivityResult);
           greetingActivityResult = activities.composeGreeting("Hello", name);
           System.out.println("About to sleep. Activity result = " + greetingActivityResult);
           Workflow.sleep(Duration.ofSeconds(10));
           System.out.println(
                   "Finished running workflow method. Activity result = " +
                           greetingActivityResult);
           return greetingActivityResult;
       }catch (Exception e){
           throw  e;
       }
    }
    @Override
    public String getWorkFlowStatus() {
        return null;
    }
    @Override
    public void exit() {

    }
}
