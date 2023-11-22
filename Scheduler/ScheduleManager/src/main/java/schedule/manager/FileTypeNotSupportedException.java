package schedule.manager;

/**
 * Represents an exception thrown when the specified file type is not supported.
 */
public class FileTypeNotSupportedException extends Exception {
    /**
     * Constructs a new FileTypeNotSupportedException with the specified error message.
     *
     * @param errorMessage the error message associated with the exception
     */
    public FileTypeNotSupportedException(String errorMessage) {
        super(errorMessage);
    }
}
