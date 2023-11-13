package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

public class MockExchange extends HttpExchange {
    String url;
    String data;

    public MockExchange(String url, String data) {
        this.url = url;
        this.data = data;
    }
    @Override
    public void close() {
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

    @Override
    public Object getAttribute(String arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'getAttribute'");
    }

    @Override
    public HttpContext getHttpContext() {
        throw new UnsupportedOperationException("Unimplemented method 'getHttpContext'");
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        throw new UnsupportedOperationException("Unimplemented method 'getLocalAddress'");
    }

    @Override
    public HttpPrincipal getPrincipal() {
        throw new UnsupportedOperationException("Unimplemented method 'getPrincipal'");
    }

    @Override
    public String getProtocol() {
        throw new UnsupportedOperationException("Unimplemented method 'getProtocol'");
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        throw new UnsupportedOperationException("Unimplemented method 'getRemoteAddress'");
    }

    @Override
    public InputStream getRequestBody() {
        /**
            Source: https://stackoverflow.com/questions/5720524/how-does-one-create-an-inputstream-from-a-string
            Title:
            Date Accessed: 11/12/2023
            Use: used to learn how to create input stream using string
         */
        InputStream stream = new ByteArrayInputStream(this.data.getBytes() );
        return stream;
    }

    @Override
    public Headers getRequestHeaders() {
        throw new UnsupportedOperationException("Unimplemented method 'getRequestHeaders'");
    }

    @Override
    public String getRequestMethod() {
        throw new UnsupportedOperationException("Unimplemented method 'getRequestMethod'");
    }

    @Override
    public URI getRequestURI() {
        try {
            return new URI(this.url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OutputStream getResponseBody() {
        throw new UnsupportedOperationException("Unimplemented method 'getResponseBody'");
    }

    @Override
    public int getResponseCode() {
        throw new UnsupportedOperationException("Unimplemented method 'getResponseCode'");
    }

    @Override
    public Headers getResponseHeaders() {
        throw new UnsupportedOperationException("Unimplemented method 'getResponseHeaders'");
    }

    @Override
    public void sendResponseHeaders(int arg0, long arg1) throws IOException {
        throw new UnsupportedOperationException("Unimplemented method 'sendResponseHeaders'");
    }

    @Override
    public void setAttribute(String arg0, Object arg1) {
        throw new UnsupportedOperationException("Unimplemented method 'setAttribute'");
    }

    @Override
    public void setStreams(InputStream arg0, OutputStream arg1) {
        throw new UnsupportedOperationException("Unimplemented method 'setStreams'");
    }

}
