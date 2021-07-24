package info.developia.ratpack.poc.model;

public record Task(String title, String description) {
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
