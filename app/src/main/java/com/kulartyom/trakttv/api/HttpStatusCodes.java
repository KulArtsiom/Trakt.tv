
package com.kulartyom.trakttv.api;


public class HttpStatusCodes {

    /**
     * Status code for a successful request.
     */
    public static final int STATUS_CODE_OK = 200;

    /**
     * Status code for a resource corresponding to any one of a set of
     * representations.
     */
    public static final int STATUS_CODE_MULTIPLE_CHOICES = 300;

    /**
     * Status code for a request that requires user authentication.
     */
    public static final int STATUS_CODE_UNAUTHORIZED = 401;

    /**
     * Status code for a server that understood the request, but is refusing to
     * fulfill it.
     */
    public static final int STATUS_CODE_FORBIDDEN = 403;

    /**
     * Status code for a server that has not found anything matching the
     * Request-URI.
     */
    public static final int STATUS_CODE_NOT_FOUND = 404;

    /**
     * Status code for a server that detects an error in the request such as
     * missing or inconsistent parameters
     */
    public static final int STATUS_CODE_UNPROCESSABLE_ENTITY = 422;

    /**
     * Status code for an internal server error. For example exception is thrown
     * at the back-end.
     */
    public static final int STATUS_CODE_SERVER_ERROR = 500;

    /**
     * Status code for a bad gateway.
     */
    public static final int STATUS_CODE_BAD_GATEWAY = 502;

    /**
     * Status code for a service that is unavailable on the server.
     */
    public static final int STATUS_CODE_SERVICE_UNAVAILABLE = 503;

    /**
     * Returns whether the given HTTP response status code is a success code
     * {@code >= 200 and < 300}.
     */
    public static boolean isSuccess(int statusCode) {
        return statusCode >= STATUS_CODE_OK && statusCode < STATUS_CODE_MULTIPLE_CHOICES;
    }
}
