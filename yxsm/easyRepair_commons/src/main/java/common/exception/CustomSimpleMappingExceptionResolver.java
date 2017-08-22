package common.exception;



import common.core.EASY_ERROR_CODE;
import common.core.bean.Result;
import common.utils.WebUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {

        try {
            HandlerMethod handlerMethod =	(HandlerMethod)handler;
            if(handlerMethod!=null){
                String methodName =  handlerMethod.getMethod().getName();
                String beanName = handlerMethod.getBean().getClass().getName();
                GeneralExceptionHandler.log(beanName + "类" + methodName + "方法" + "异常", ex);
                WebUtil.printApi(response, new Result(false).msg(EASY_ERROR_CODE.ERROR_CODE_0002));
            }
        }catch (Exception e){
            GeneralExceptionHandler.log(e);
        }

        return  null;
	}
}
