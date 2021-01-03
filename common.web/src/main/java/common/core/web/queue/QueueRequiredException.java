package common.core.web.queue;

public class QueueRequiredException extends RuntimeException {

	private static final long serialVersionUID = 4500076332971570832L;

	public QueueRequiredException(String message) {
		super(message);
	}

}
