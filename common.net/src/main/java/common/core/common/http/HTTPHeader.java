package common.core.common.http;

public class HTTPHeader {
    private final String name;
    private final String value;

    public HTTPHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String name() {
        return name;
    }

    public String value() {
        return value;
    }
}
