package com.game.itstar.response;

import com.game.itstar.utile.Helpers;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 创建时间：2019/3/14 9:42
 * 版本：1.0
 * 描述：异常统一处理,能被spring加载扫描到即可
 */
//@Log4j
@ResponseBody//响应json转换,没有回出现404
@ControllerAdvice//定义成全局的handler
public class ExceptionAdvice {

    /**
     * 参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> validHandler(MethodArgumentNotValidException ve) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        ResStatus failed = ResStatus.BAD_REQUEST;
        FieldError error = ve.getBindingResult().getFieldError();
//        log.error("cause:" + ve.getCause() + "___" + "message:" + error.getDefaultMessage());

        jsonMap.put("success", failed.isSuccess());
        jsonMap.put("code", failed.getCode());
        jsonMap.put("msg", error.getDefaultMessage());
        jsonMap.put("data", "");
        return jsonMap;
    }

    /**
     * 参数验证异常
     */
    @ExceptionHandler(BindException.class)
    public Map<String, Object> resHandler(BindException bind) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        ResStatus failed = ResStatus.BAD_REQUEST;
//        log.error("cause:" + bind.getCause() + "___" + "message:" + bind.getMessage());

        jsonMap.put("code", failed.getCode());
        jsonMap.put("success", failed.isSuccess());
        jsonMap.put("msg", failed.getMsg());
        jsonMap.put("data", "");
        return jsonMap;
    }

    /**
     * 统一的异常
     */
    @ExceptionHandler(ResException.class)
    public Map<String, Object> resHandler(ResException res) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        ResStatus failed = ResStatus.FAILED;

        jsonMap.put("code", failed.getCode());
        jsonMap.put("success", failed.isSuccess());
        jsonMap.put("msg", Helpers.isNotNullAndEmpty(res.getMessage()) ? res.getMessage() : failed.getMsg());
        jsonMap.put("data", "");
        return jsonMap;
    }

    /**
     * 其余异常
     */
    @ExceptionHandler(Exception.class)
    public Map<String, Object> exHandler(Exception ex) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        ResStatus failed = ResStatus.FAILED;
//        log.error("cause:" + ex.getCause() + "___" + "message:" + ex.getMessage());

        jsonMap.put("success", failed.isSuccess());
        jsonMap.put("code", failed.getCode());
        jsonMap.put("msg", failed.getMsg());
        jsonMap.put("data", "");
        return jsonMap;
    }
}
