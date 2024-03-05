package com.b2012202.mxhtknt.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * purpose: Cai dat 1 Filter de xu ly CORS
 * fix: Lỗi CORS (Cross-Origin Resource Sharing) thường xuyên xảy ra khi
 * bạn cố gắng thực hiện các yêu cầu từ một trang web có nguồn gốc khác (origin) so với máy chủ API của bạn.
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
