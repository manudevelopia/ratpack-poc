package info.developia.ratpack.poc.controller;

import ratpack.handling.Context;
import ratpack.jackson.Jackson;

import static info.developia.ratpack.poc.App.getTasks;

public class TaskController {

    public void getTask(Context ctx) {
        var id = ctx.getPathTokens().get("id");
        getTasks().stream().filter(t -> t.id().equals(id)).findFirst().ifPresentOrElse(
                task -> ctx.render(Jackson.json(task)),
                () -> ctx.getResponse().status(404).send());
    }

    public void getAllTasks(Context ctx) {
        ctx.render(Jackson.json(getTasks()));
    }
}
