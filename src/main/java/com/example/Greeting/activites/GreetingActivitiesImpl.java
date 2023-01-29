package com.example.Greeting.activites;

public class GreetingActivitiesImpl implements IGreetingActivities {
    @Override
    public String composeGreeting(String greeting, String name) {
        System.out.println("Inside the composeGreeting activity");
        return greeting + "" + name + "!";
    }
}