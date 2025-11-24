package carpincha.aCore.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Credenciales de acceso inválidas.");
    }
}
