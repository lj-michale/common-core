package common.core.app.lang;

public class ReadOnly<T> {
	private T value;
	private boolean assigned;

	public boolean assigned() {
		return assigned;
	}

	public T value() {
		return value;
	}

	public void set(T value) {
		if (assigned)
			throw new IllegalStateException("value has been assigned, oldValue=" + this.value + ", newValue=" + value);

		assigned = true;
		this.value = value;
	}

	public boolean valueEquals(T otherValue) {
		return (value == null && otherValue == null) || (otherValue != null && otherValue.equals(value));
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
