package carpincha.aCore.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String details) {
        super("Datos inválidos: " + details);
    }
}
