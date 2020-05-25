package exception;

/**
 * Ilya Fedchenkov, group 13
 * Variant 4
 */

public class OutOfBoundsException extends Exception {
	public OutOfBoundsException(String message) {	}

	public OutOfBoundsException(String message, Throwable e) {
	}

	public String getMessage() {
		return super.getMessage();
	}

	public void printStackTrace() {
		super.printStackTrace();
	}
}
