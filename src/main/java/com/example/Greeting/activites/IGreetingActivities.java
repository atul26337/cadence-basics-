package com.example.Greeting.activites;

import com.uber.cadence.activity.ActivityMethod;

public interface IGreetingActivities {
     @ActivityMethod(scheduleToCloseTimeoutSeconds = 30)
     public String composeGreeting(String greeting, String name);
}
