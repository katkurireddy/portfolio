
public class EmptyPortfolioException extends Exception {
    public EmptyPortfolioException(String message) {
        super(message);
    }

    public EmptyPortfolioException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPortfolioException(Throwable cause) {
        super(cause);
    }

    public EmptyPortfolioException() {
    }
}
