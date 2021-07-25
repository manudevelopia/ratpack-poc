package info.developia.ratpack.poc.controller;

import info.developia.ratpack.poc.service.TaskService;
import ratpack.handling.Context;
import ratpack.jackson.Jackson;


public class TaskController {
    private final TaskService taskService = new TaskService();

    public void getTask(Context ctx) {
        var tid = ctx.getPathTokens().get("id");
        taskService.getTask(tid).ifPresentOrElse(
                task -> ctx.render(Jackson.json(task)),
                () -> ctx.getResponse().status(404).send());
    }

    public void getAllTasks(Context ctx) {
        ctx.render(Jackson.json(taskService.getTasks()));
    }
}
