package org.jianeng.books.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/5 16:47
 */
@Configuration
public class SpringConfig implements WebMvcConfigurer {

    /**
     * 解决 MockMvc 编码问题
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .findFirst()
                .ifPresent(converter ->
                        ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(Charset.forName("utf-8"))
                );
    }
}
