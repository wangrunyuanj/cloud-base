package com.runyuanj.core.processor;

@Deprecated
public class MultiRequestBodyArgumentProcessor {
//    /**
//     * Whether the given {@linkplain MethodParameter method parameter} is
//     * supported by this resolver.
//     *
//     * @param parameter the method parameter to check
//     * @return {@code true} if this resolver supports the supplied parameter;
//     * {@code false} otherwise
//     */
//    //@Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.hasParameterAnnotation(MultiRequestBody.class);
//    }
//
//    /**
//     * Resolves a method parameter into an argument value from a given request.
//     * A {@link ModelAndViewContainer} provides access to the model for the
//     * request. A {@link WebDataBinderFactory} provides a way to create
//     * a {@link WebDataBinder} instance when needed for data binding and
//     * type conversion purposes.
//     *
//     * @param parameter     the method parameter to resolve. This parameter must
//     *                      have previously been passed to {@link #supportsParameter} which must
//     *                      have returned {@code true}.
//     * @param mavContainer  the ModelAndViewContainer for the current request
//     * @param webRequest    the current request
//     * @param binderFactory a factory for creating {@link WebDataBinder} instances
//     * @return the resolved argument value, or {@code null} if not resolvable
//     * @throws Exception in case of errors with the preparation of argument values
//     */
//    //@Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        String jsonBody = this.getRequestBody(webRequest);
//
//        return null;
//    }
//
//    private String getRequestBody(NativeWebRequest webRequest) {
//        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
//
//        String jsonRequestBody = (String) webRequest.getAttribute("JSON_REQUEST_BODY", 0);
//        if (jsonRequestBody == null) {
//            try {
//                jsonRequestBody = IOUtils.toString(servletRequest.getReader());
//                webRequest.setAttribute("JSON_REQUEST_BODY", jsonRequestBody, 0);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//
//        return null;
//    }
}
