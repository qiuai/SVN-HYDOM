package com.hydom.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Web端统一错误处理Handler.
 * @author Holen
 * @version 1.0.0 2014.12.1 新建
 */
@Log4j
public class WebExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception e) {

		// 打印出错误日志
		log.error(e);
		e.printStackTrace();
        Map<String, Object> model = new HashMap<String, Object>();  
        model.put("e", e);  
          
        // 根据不同错误转向不同页面  
//        if(ex instanceof BusinessException) {  
//            return new ModelAndView("error-business", model);  
//        }else if(ex instanceof ParameterException) {  
//            return new ModelAndView("error-parameter", model);  
//        } else {  
//            return new ModelAndView("error", model);  
//        }
        return null;
	}

}
