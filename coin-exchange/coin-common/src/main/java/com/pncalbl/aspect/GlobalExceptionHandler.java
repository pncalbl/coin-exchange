package com.pncalbl.aspect;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.pncalbl.model.R;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author pncalbl
 * @date 2021/10/14 20:28
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * 1. 内部 API 调用的异常处理
	 */
	@ExceptionHandler(value = ApiException.class)
	public R handlerApiException(ApiException exception) {
		IErrorCode errorCode = exception.getErrorCode();
		if (errorCode != null) {
			return R.fail(errorCode);
		}
		return R.fail(exception.getMessage());
	}

	/**
	 * 2. 方法参数校验失败的异常
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			if (fieldError != null) {
				return R.fail(fieldError.getField() + fieldError.getDefaultMessage());
			}
		}
		return R.fail(exception.getMessage());
	}

	/**
	 * 3. 对象内部使用 Validate 没有校验成功的异常
	 */
	@ExceptionHandler(BindException.class)
	public R handlerBindException(BindException bindException) {
		BindingResult bindingResult = bindException.getBindingResult();
		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			if (fieldError != null) {
				return R.fail(fieldError.getField() + fieldError.getDefaultMessage());
			}
		}
		return R.fail(bindException.getMessage());
	}
}
