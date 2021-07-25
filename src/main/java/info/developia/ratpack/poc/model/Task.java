package info.developia.ratpack.poc.model;

import lombok.Data;

@Data
public class Task {
    private final String tid;
    private final String title;
    private final String description;
    private final boolean done;
}
