package info.developia.ratpack.poc;

import ratpack.server.RatpackServer;

public class App {
    public static String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        RatpackServer.start(server -> server.handlers(chain -> chain
                .get(ctx -> ctx.render(getGreeting()))
                .get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
        ));
    }
}
