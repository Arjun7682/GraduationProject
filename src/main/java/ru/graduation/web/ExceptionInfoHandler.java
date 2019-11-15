package ru.graduation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.graduation.util.ValidationUtil;
import ru.graduation.util.exception.ErrorInfo;
import ru.graduation.util.exception.ErrorType;
import ru.graduation.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static ru.graduation.util.exception.ErrorType.*;


@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);
    
    private static final Map<String, String> CONSTRAINS_I18N_MAP = new HashMap<>();

    static {
        CONSTRAINS_I18N_MAP.put("restaurants_unique_name_idx", "Restaurant with this name already exists");
        CONSTRAINS_I18N_MAP.put("users_unique_email_idx", "User with this email already exists");
        CONSTRAINS_I18N_MAP.put("votes_unique_user_datetime_idx", "Vote already exists");
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo notFound(HttpServletRequest req, NotFoundException exception) {
        return logAndGetErrorInfo(req.getRequestURL(), ErrorType.DATA_NOT_FOUND, exception);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo badRequest(HttpServletRequest req, NoHandlerFoundException exception) {
        return logAndGetErrorInfo(req.getRequestURL(), ErrorType.WRONG_REQUEST, exception);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            Optional<Map.Entry<String, String>> entry = CONSTRAINS_I18N_MAP.entrySet().stream()
                    .filter(it -> lowerCaseMsg.contains(it.getKey()))
                    .findAny();
            if (entry.isPresent()) {
                return logAndGetErrorInfo(req.getRequestURL(), VALIDATION_ERROR, Collections.singletonList(entry.get().getValue()));
            }
        }
        return logAndGetErrorInfo(req.getRequestURL(), DATA_ERROR, e);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ErrorInfo bindValidationError(HttpServletRequest req, Exception e) {
        BindingResult result = e instanceof BindException ?
                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();

        List<String> details = result.getFieldErrors().stream()
                .map(fe -> String.format("%s - %s", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return logAndGetErrorInfo(req.getRequestURL(), VALIDATION_ERROR, details);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req.getRequestURL(), VALIDATION_ERROR, e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req.getRequestURL(), APP_ERROR, e);
    }

    private ErrorInfo logAndGetErrorInfo(CharSequence url, ErrorType errorType, Exception e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log.warn("{} at request  {}: {}", errorType, url.toString(), rootCause.toString());
        return new ErrorInfo(url, errorType, rootCause.getMessage());
    }

    private ErrorInfo logAndGetErrorInfo(CharSequence url, ErrorType errorType, List<String> errors) {
        log.warn("{} at request  {}: {}", errorType, url.toString(), errors);
        return new ErrorInfo(url, errorType, errors);
    }
}