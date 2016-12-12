package cgt.dop.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan({ "cgt.dop.*" })
public class ApplicationConfigurerAdapter extends WebMvcConfigurerAdapter {

    private static final String VIEW_DIR_HTML = "/WEB-INF/views/";
    private static final String VIEW_EXTN_HTML = ".html";

   
    @Bean
    public ViewResolver htmlViewResolver() {
        InternalResourceViewResolver viewResolver= new ApplicationInternalResourceViewResolver();
        viewResolver.setPrefix(VIEW_DIR_HTML);
        viewResolver.setSuffix(VIEW_EXTN_HTML);
        viewResolver.setOrder(0);
        return viewResolver;
    }
}
