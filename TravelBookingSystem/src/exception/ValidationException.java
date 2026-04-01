package exception;

// Custom exception untuk handle validasi input
// Author: Habibi Putra Rizqullah (2411531001)
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}