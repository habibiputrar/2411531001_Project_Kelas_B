package exception;

// Custom exception untuk handle kegagalan login
// Author: Habibi Putra Rizqullah (2411531001)
public class InvalidLoginException extends Exception {

    public InvalidLoginException(String message) {
        super(message);
    }

    public InvalidLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}