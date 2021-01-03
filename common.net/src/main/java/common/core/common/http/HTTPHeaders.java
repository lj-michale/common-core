package common.core.common.http;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HTTPHeaders implements Iterable<HTTPHeader> {
    // header's name can be duplicated according to HTTP Spec, so use List not Map
    final List<HTTPHeader> headers = new ArrayList<>();

    public void add(String name, String value) {
        headers.add(new HTTPHeader(name, value));
    }

    public String firstHeaderValue(String name) {
        for (HTTPHeader header : headers) {
            if (header.name().equals(name)) return header.value();
        }
        return null;
    }

    @Override
    public Iterator<HTTPHeader> iterator() {
        return headers.iterator();
    }
}
