package com.runyuanj.core.web.advice;

import com.runyuanj.common.exception.type.DbErrorType;
import com.runyuanj.common.exception.type.SystemErrorType;
import com.runyuanj.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import static com.runyuanj.common.exception.type.DbErrorType.DUPLICATE_PRIMARY_KEY;
import static com.runyuanj.common.exception.type.NullPointerErrorType.NULL_POINTER;
import static com.runyuanj.common.exception.type.SystemErrorType.PARAM_INVALID;
import static com.runyuanj.common.exception.type.SystemErrorType.UPLOAD_FILE_SIZE_LIMIT;

@ControllerAdvice
@Slf4j
public class MiddleExceptionHandlerAdvice {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result illegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException: {}", e.getMessage());
        return Result.fail(PARAM_INVALID);
    }

    @ExceptionHandler(value = {MultipartException.class})
    public Result uploadFileLimitException(MultipartException ex) {
        log.error("upload file size limit:{}", ex.getMessage());
        return Result.fail(UPLOAD_FILE_SIZE_LIMIT);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result serviceException(MethodArgumentNotValidException ex) {
        log.error("service exception:{}", ex.getMessage());
        return Result.fail(PARAM_INVALID, null, ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(value = {DuplicateKeyException.class})
    public Result duplicateKeyException(DuplicateKeyException ex) {
        log.error("primary key duplication exception:{}", ex.getMessage());
        return Result.fail(DUPLICATE_PRIMARY_KEY);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public Result baseException(NullPointerException ex) {
        log.error("NullPointerException {}", ex.getMessage());
        return Result.fail(NULL_POINTER);
    }

}
