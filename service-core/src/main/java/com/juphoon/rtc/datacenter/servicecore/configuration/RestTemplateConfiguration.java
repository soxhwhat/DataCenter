package com.juphoon.rtc.datacenter.servicecore.configuration;

import com.juphoon.rtc.datacenter.servicecore.property.HttpClientPoolProperties;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * <p>HTTP连接配置管理</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/21/22 6:10 PM
 *
 * // TODO https 未测试
 */
@Configuration
public class RestTemplateConfiguration {

    @Autowired
    private HttpClientPoolProperties config;

    /**
     * http连接管理器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpClientConnectionManager poolingHttpClientConnectionManager() {
        // 注册http和https请求
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
                new PoolingHttpClientConnectionManager(registry);

        // 最大连接数
        poolingHttpClientConnectionManager.setMaxTotal(config.getMaxTotalConnect());

        // 同路由并发数（每个主机的并发）
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(config.getMaxConnectPerRoute());

        return poolingHttpClientConnectionManager;
    }

    /**
     * HttpClient
     * @param poolingHttpClientConnectionManager
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpClient httpClient(HttpClientConnectionManager poolingHttpClientConnectionManager) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        // 设置http连接管理器
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);

        // 设置重试次数
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(config.getRetryTimes(), true));

        return httpClientBuilder.build();
    }

    /**
     * 请求连接池配置
     * @param httpClient
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ClientHttpRequestFactory clientHttpRequestFactory(HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

        // httpClient创建器
        clientHttpRequestFactory.setHttpClient(httpClient);

        // 连接超时时间/毫秒（连接上服务器(握手成功)的时间，超出抛出connect timeout）
        clientHttpRequestFactory.setConnectTimeout(config.getConnectTimeout());

        // 数据读取超时时间(socketTimeout)/毫秒（务器返回数据(response)的时间，超过抛出read timeout）
        clientHttpRequestFactory.setReadTimeout(config.getReadTimeout());

        // 连接池获取请求连接的超时时间，不宜过长，必须设置/毫秒（超时间未拿到可用连接，
        // 会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool）
        clientHttpRequestFactory.setConnectionRequestTimeout(config.getConnectionRequestTimout());

        return clientHttpRequestFactory;
    }

    /**
     * rest模板
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        // boot中可使用RestTemplateBuilder.build创建
        RestTemplate restTemplate = new RestTemplate();

        // 配置请求工厂
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }

}
