package info.developia.ratpack.poc.repository;

public class PersistenceException extends RuntimeException {
    public PersistenceException(String message) {
        super(message);
    }
}
