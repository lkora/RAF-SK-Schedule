package schedule.manager;

public class FileTypeNotSupportedException extends Exception {
    public FileTypeNotSupportedException(String errorMessage) {
        super(errorMessage);
    }
}
