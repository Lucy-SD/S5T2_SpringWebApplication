package carpincha.aCore.exception;

public class NameAlreadyExistsException extends RuntimeException {
    public NameAlreadyExistsException() {
        super("El nombre solicitado ya está ocupado. Por favor, elija otro.");
    }
}
