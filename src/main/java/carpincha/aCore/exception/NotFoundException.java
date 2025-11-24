package carpincha.aCore.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String resourceName) {
        super(resourceName + " no encontrado.");
    }
}
