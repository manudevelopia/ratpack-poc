package info.developia.ratpack.poc;

import info.developia.ratpack.poc.filter.RequestFilter;
import info.developia.ratpack.poc.model.Task;
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
                .get("tasks", ctx -> ctx.render(Jackson.json(getTasks())))
        ));
    }
}
