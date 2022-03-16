package cloud.juphoon.jrtc.util;

import cloud.juphoon.jrtc.config.HttpClientPoolConfig;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/6 22:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    // 池化管理
    private PoolingHttpClientConnectionManager poolConnManager = null;

    private CloseableHttpClient httpClient;

    public HttpClientUtil(HttpClientPoolConfig httpClientPoolConfig) {
        try {
            log.info("初始化HttpClientTest~~~开始");
            SSLContextBuilder builder = new SSLContextBuilder();
            try {
                builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            } catch (NoSuchAlgorithmException e) {
                log.error("", e);
            } catch (KeyStoreException e) {
                log.error("", e);
            }
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
            // 初始化连接管理器
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            //总连接数
            poolConnManager.setMaxTotal(httpClientPoolConfig.getMaxTotalConnect());
            // 设置最大路由，每个路由最大同时请求数
            poolConnManager.setDefaultMaxPerRoute(httpClientPoolConfig.getMaxConnectPerRoute());
            httpClient = getConnection(httpClientPoolConfig);
            log.info("初始化HttpClientTest~~~结束");
        } catch (NoSuchAlgorithmException e) {
            log.error("", e);
        } catch (KeyManagementException e) {
            log.error("", e);
        }
    }


    private CloseableHttpClient getConnection(HttpClientPoolConfig httpClientPoolConfig) {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(httpClientPoolConfig.getConnectTimeout())
                .setConnectionRequestTimeout(httpClientPoolConfig.getConnectionRequestTimout())
                .setSocketTimeout(httpClientPoolConfig.getReadTimeout()).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(poolConnManager)
                .setDefaultRequestConfig(config)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(httpClientPoolConfig.getRetryTimes(), false)).build();
        return httpClient;
    }


    public String httpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String result = "";
        try {
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            int code = response.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                return result;
            } else {
                logger.error("请求{}返回错误码：{},{}", url, code, result);
                return result;
            }
        } catch (IOException e) {
            logger.error("http请求异常，{}", url, e);
        } finally {
            try {
                if (response != null) {
                    EntityUtils.consume(entity);
                    // 不要调用close方法。这是关闭连接，不会自动返回到连接池
//                    response.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return null;
    }

    
    public String post(String uri, Map<String, Object> params, Header... heads) {
        HttpPost httpPost = new HttpPost(uri);
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String result = "";
        try {
            String paramStr = JSON.toJSONString(params);

            StringEntity paramEntity = new StringEntity(paramStr);
            paramEntity.setContentEncoding("UTF-8");
            paramEntity.setContentType("application/json");
            httpPost.setEntity(paramEntity);
            if (heads != null) {
                httpPost.setHeaders(heads);
            }
            response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            if (code == HttpStatus.SC_OK) {
                return result;
            } else {
                logger.error("请求{}返回错误码:{},请求参数:{}", uri, code, params);
                return result;
            }
        } catch (IOException e) {
            logger.error("收集服务配置http请求异常");
        } finally {
            try {
                if (response != null) {
                    EntityUtils.consume(entity);
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return null;
    }


}
