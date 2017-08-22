package common.interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 公共拦截器
 * Created by reny on 2015/7/31.
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);
/*
    @Autowired
    private TUserRoleService tUserRoleService;

    @Autowired
    private TRoleResourceService tRoleResourceService;*/

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       /* Integer userId = null == SessionUtils.getCurrentUser() ? null : SessionUtils.getCurrentUser().getId();

        if (userId == null) {
            //未登录不允许任何操作
            request.getRequestDispatcher("/WEB-INF/jsp/登录.jsp").forward(request, response);
            return false;
        }

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        log.info("requestUri:" + requestUri);
        log.info("contextPath:" + contextPath);
        //获取url地址
        String url = requestUri.substring(contextPath.length());
        log.info("url:" + url);
        //获取关键url
        if (url.split("/").length <= 2) {
            return true;
        }
        String rootUrl = url.split("/")[2];
        log.info("rootUrl:" + rootUrl);
        TUserRole tUserRole = tUserRoleService.findByUserId(userId);
        //如果用户没有权限，则直接定回到没有任何操作权限的首页
        if (tUserRole == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/首页.jsp").forward(request, response);
            return false;
        }
        if (tUserRole.getRole() == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/首页.jsp").forward(request, response);
            return false;
        }
        //有权限则取出权限
        TRole tRole = tUserRole.getRole();

        List<TResource> tResources = tRoleResourceService.resourceList(tRole.getId());
        int isHas = 0;
        for (TResource tResource : tResources) {
            List<TResource> tResources1 = tRoleResourceService.findTResourceByUrl(tResource.getId(), "%" + rootUrl + "%");
            isHas = isHas + tResources1.size();
        }
        if (isHas > 0) {
            return true;
        }

        request.getRequestDispatcher("/WEB-INF/jsp/首页.jsp").forward(request, response);
        return false;*/
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }


}
