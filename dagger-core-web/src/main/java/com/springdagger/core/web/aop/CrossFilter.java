package com.springdagger.core.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: kexiong
 * @date: 2020/10/20 11:01
 * @Description: TODO
 */
@Slf4j
@WebFilter(filterName = "CorsFilter ")
@Configuration
public class CrossFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String origin = request.getHeader("Origin");
        if (StringUtils.isEmpty(origin)) {
            origin = "*";
        }
//        log.info("CrossFilter跨域配置===================================" + origin);
        response.setHeader("Access-Control-Allow-Origin",origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");//如果要把Cookie发到服务器，需要指定Access-Control-Allow-Credentials字段为true
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "36000");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token, Authorization");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return;
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
