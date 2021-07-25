package info.developia.ratpack.poc;

import info.developia.ratpack.poc.controller.TaskController;
import info.developia.ratpack.poc.filter.CORSFilter;
import info.developia.ratpack.poc.filter.RequestFilter;
import info.developia.ratpack.poc.model.Task;
import ratpack.server.RatpackServer;

import java.util.List;

public class App {
    public static String getGreeting() {
        return "Hello World!";
    }

    public static List<Task> getTasks() {
        return List.of(
                new Task("7c3706cb","title 1", "description 1"),
                new Task("7410dfaa","title 2", "description 2")
        );
    }

    public static void main(String[] args) throws Exception {
        final TaskController taskController = new TaskController();

        RatpackServer.start(server -> server.handlers(chain -> chain
                .all(new CORSFilter())
                .all(new RequestFilter())
                .get(ctx -> ctx.render(getGreeting()))
                .get("greetings/:name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
                .path("tasks", ctx -> ctx.byMethod(action -> action.get(taskController::getAllTasks)))
                .path("tasks/:id", ctx -> ctx.byMethod(action -> action.get(taskController::getTask)))
        ));
    }
}
