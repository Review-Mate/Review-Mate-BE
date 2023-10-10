package com.somartreview.reviewmate.performance;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
@Profile("performance")
public class PerformanceInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    private final PerformanceMonitor performanceMonitor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isPreflight(request)) {
            return true;
        }

        performanceMonitor.start(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (isPreflight(request) || performanceMonitor.getUri() == null) {
            return;
        }

        performanceMonitor.end();
        logPerformanceMonitorResult();
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        if (isPreflight(request) || performanceMonitor.getUri() == null) {
//            return;
//        }
//
//        performanceMonitor.end();
//        logPerformanceMonitorResult();
//    }

    private void logPerformanceMonitorResult() {
        if (performanceMonitor.isWarning()) {
            log.warn(performanceMonitor.toString());
            return;
        }

        log.info(performanceMonitor.toString());
    }

    private boolean isPreflight(HttpServletRequest request) {
        return request.getMethod().equals("OPTIONS");
    }
}
