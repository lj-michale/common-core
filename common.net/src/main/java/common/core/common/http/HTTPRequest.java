package common.core.common.http;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;

public abstract class HTTPRequest {
    public static HeaderBuilder get(String url) {
        return new HTTPRequestBuilder(url, HTTPMethod.GET);
    }

    public static BodyBuilder post(String url) {
        return new HTTPRequestBuilder(url, HTTPMethod.POST);
    }

    public static BodyBuilder put(String url) {
        return new HTTPRequestBuilder(url, HTTPMethod.PUT);
    }

    public static HeaderBuilder delete(String url) {
        return new HTTPRequestBuilder(url, HTTPMethod.DELETE);
    }

    public abstract HTTPMethod method();

    public abstract String body();

    public abstract String url();

    public abstract HeaderBuilder addHeader(String key, String value);

    public abstract HeaderBuilder accept(String contentType);

    abstract HTTPHeaders headers();

    abstract List<NameValuePair> parameters();

    abstract HttpRequestBase getRawRequest();

    abstract AbstractHttpEntity getEntity();
}
