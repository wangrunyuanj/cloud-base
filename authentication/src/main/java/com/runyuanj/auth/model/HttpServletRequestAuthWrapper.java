package com.runyuanj.auth.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpServletRequestAuthWrapper extends HttpServletRequestWrapper {

    private String url;

    private String method;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public HttpServletRequestAuthWrapper(HttpServletRequest request, String url, String method) {
        super(request);
        this.url = url;
        this.method = method;
    }

    /**
     * The default behavior of this method is to return getMethod() on the
     * wrapped request object.
     */
    @Override
    public String getMethod() {
        return this.method;
    }

    /**
     * The default behavior of this method is to return getServletPath() on the
     * wrapped request object.
     */
    @Override
    public String getServletPath() {
        return this.url;
    }
}
