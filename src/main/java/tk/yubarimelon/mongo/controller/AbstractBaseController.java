package tk.yubarimelon.mongo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;
import tk.yubarimelon.mongo.exception.BasicException;
import tk.yubarimelon.mongo.response.Response;

@Log4j2
public abstract class AbstractBaseController {

    /**
     * 返回错误信息
     *
     * @param errorMsg 错误信息
     * @param <T>      返回值泛型
     * @return 响应
     */
    public <T> Mono<ResponseEntity<Response<T>>> errorEntity(String errorMsg) {
        return Mono.just(new ResponseEntity<>(new Response<>(Response.C0DE_ERROR, errorMsg), HttpStatus.OK));
    }

    /**
     * 正常带数据返回
     *
     * @param returnMono 返回数据
     * @param <T>        返回值泛型
     * @return 带数据响应
     */
    public <T> Mono<ResponseEntity<Response<T>>> returnEntity(Mono<T> returnMono) {
        return returnEntity(returnMono, null, Response.CODE_OK, HttpStatus.OK);
    }

    /**
     * 正常带数据返回，附加返回信息
     *
     * @param returnMono 返回数据
     * @param message    返回信息
     * @param <T>        返回值泛型
     * @return 带数据响应
     */
    public <T> Mono<ResponseEntity<Response<T>>> returnEntity(Mono<T> returnMono, String message) {
        return returnEntity(returnMono, message, Response.CODE_OK, HttpStatus.OK);
    }

    /**
     * 正常带数据返回，附加返回信息和返回码
     *
     * @param returnMono 返回数据
     * @param message    返回信息
     * @param code       返回码
     * @param <T>        返回值泛型
     * @return 带数据响应
     */
    public <T> Mono<ResponseEntity<Response<T>>> returnEntity(Mono<T> returnMono, String message, Integer code) {
        return returnEntity(returnMono, message, code, HttpStatus.OK);
    }

    /**
     * 正常带数据返回，附加返回信息和返回码
     *
     * @param returnMono 返回数据
     * @param message    返回信息
     * @param code       返回码
     * @param httpStatus 状态码
     * @param <T>        返回值泛型
     * @return 带数据响应
     */
    public <T> Mono<ResponseEntity<Response<T>>> returnEntity(Mono<T> returnMono, @Nullable String message, Integer code, HttpStatus httpStatus) {
        return returnMono.map(entity -> new ResponseEntity<>(new Response<>(code, message, entity), httpStatus))
                .defaultIfEmpty(new ResponseEntity<>(new Response<>(Response.CODE_NOT_FOUND, "not found"), HttpStatus.OK));
    }

    /**
     * 捕获异常并返回异常
     *
     * @param exception 权限异常
     * @return 异常响应
     */
    @ExceptionHandler()
    public ResponseEntity<Response<Void>> exceptionCatch(BasicException exception) {
        log.error(exception.getMessage() + ": " + exception.getStackTrace()[0]);
        return new ResponseEntity<>(new Response<>(Response.C0DE_ERROR, exception.getMessage()), HttpStatus.OK);
    }
}
