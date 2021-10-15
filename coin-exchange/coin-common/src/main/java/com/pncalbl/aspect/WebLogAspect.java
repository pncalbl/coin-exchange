package com.pncalbl.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.pncalbl.model.WebLog;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author pncalbl
 * @date 2021/10/14 19:56
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Slf4j
@Aspect
@Component
@Order(1)
public class WebLogAspect {

	/**
	 * 1. 定义切入点
	 */
	@Pointcut("execution(* com.pncalbl.controller.*.*(..))")    // controller 包下的所有类, 类的所有方法都有该切面
	public void webLog() {

	}

	/**
	 * 2. 记录日志的环绕通知
	 */
	@Around("webLog()")
	public Object recodeWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = null;
		WebLog webLog = new WebLog();
		long stater = System.currentTimeMillis();
		// 执行放到的真实调用
		result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
		long end = System.currentTimeMillis();
		webLog.setSpendTime((int) (end - stater) / 1000);   // 请求该接口花费的时间
		// 获取当前请求的 request 对象
		ServletRequestAttributes requestAttributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		assert requestAttributes != null;
		HttpServletRequest request = requestAttributes.getRequest();
		String url = request.getRequestURL().toString();
		// 获取安全的上下文
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		webLog.setUri(request.getRequestURI());
		webLog.setUrl(url);
		webLog.setBasePath(StrUtil.removeSuffix(url, URLUtil.url(url).getPath()));   // http://ip:port/
		webLog.setUsername(authentication == null
				? "anonymous" : authentication.getPrincipal().toString());    // 获取用户id, 如果为空则为匿名访问
		webLog.setIp(request.getRemoteAddr());  // TODO 获取 ip 地址
		// 获取方法
		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		Method method = signature.getMethod();
		// 获取类的名称
		String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();
		// 因为我们会使用 swagger, 在方法上有注解 @ApiOperation(value = "")
		ApiOperation annotation = method.getAnnotation(ApiOperation.class);
		webLog.setDescription(annotation == null ? "no desc" : annotation.value());
		webLog.setMethod(targetClassName + "." + method.getName());
		webLog.setParameter(getMethodParameter(method, proceedingJoinPoint.getArgs()));  // {"key_参数名称": "value_参数的值"}
		webLog.setResult(result);
		log.info(JSON.toJSONString(webLog, true));
		return result;
	}

	/**
	 * 获方法的执行参数
	 *
	 * @param method 方法
	 * @param args   参数
	 * @return 参数
	 */
	private Object getMethodParameter(Method method, Object[] args) {
		Map<String, Object> methodParameterWithValues = new HashMap<>();
		LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer =
				new LocalVariableTableParameterNameDiscoverer();
		// 方法的形参名称
		String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
		for (int i = 0; i < Objects.requireNonNull(parameterNames).length; i++) {
			methodParameterWithValues.put(parameterNames[i], args[i]);
		}
		return parameterNames;
	}
}
