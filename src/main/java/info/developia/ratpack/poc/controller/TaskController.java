package info.developia.ratpack.poc.controller;

import ratpack.handling.Context;
import ratpack.jackson.Jackson;

import static info.developia.ratpack.poc.App.getTasks;

public class TaskController {

    public void getTask(Context ctx) {
        int id = ctx.getPathTokens().asInt("id");
        ctx.render(Jackson.json(getTasks().get(0)));
    }

    public void getAllTasks(Context ctx) {
        ctx.render(Jackson.json(getTasks()));
    }
}
