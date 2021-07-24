package info.developia.ratpack.poc;

import info.developia.ratpack.poc.filter.RequestFilter;
import info.developia.ratpack.poc.model.Task;
import ratpack.handling.Context;
import ratpack.jackson.Jackson;
import ratpack.server.RatpackServer;

import java.util.List;

public class App {
    public static String getGreeting() {
        return "Hello World!";
    }

    public static List<Task> getTasks() {
        return List.of(
                new Task("title 1", "description 1"),
                new Task("title 2", "description 2")
        );
    }

    public static void main(String[] args) throws Exception {
        RatpackServer.start(server -> server.handlers(chain -> chain
                .all(new RequestFilter())
                .get(ctx -> ctx.render(getGreeting()))
                .get("greetings/:name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
                .path("tasks", ctx -> ctx.byMethod(action -> action.get(App::getAllTasks)))
                .path("tasks/:id", ctx -> ctx.byMethod(action -> action
                        .get(App::getTask)))
        ));
    }

    private static void getTask(Context ctx) {
        int id = ctx.getPathTokens().asInt("id");
        ctx.render(Jackson.json(getTasks().get(0)));
    }

    private static void getAllTasks(Context ctx) {
        ctx.render(Jackson.json(getTasks()));
    }
}
