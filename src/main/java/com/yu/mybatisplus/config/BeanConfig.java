package com.yu.mybatisplus.config;

import com.fr.web.ReportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean
    public ServletRegistrationBean reportServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ReportServlet(), "/ReportServer");
        registration.setLoadOnStartup(0);
        return registration;
    }
}
