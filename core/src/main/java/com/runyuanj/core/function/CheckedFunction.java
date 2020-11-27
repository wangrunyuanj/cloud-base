package com.runyuanj.core.function;

@FunctionalInterface
public interface CheckedFunction<T, R> {
    /**
     * 执行
     *
     * @param t 入参
     * @return R 出参
     * @throws Exception
     */
    R apply(T t) throws Exception;
}
