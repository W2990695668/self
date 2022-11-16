package com.wqq.self.elk.utils;

import com.wqq.self.common.exception.BusinessException;
import com.wqq.self.common.utils.SpringBeanUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.io.IOException;

/**
 * @Description
 * @Author wqq
 * @Date 2022/8/23 11:04
 */
public class ElasticSearchUtils {

    private static final String ELASTICSEARCH_HOST_KEY = "elasticsearch.host";
    private static final String ELASTICSEARCH_PORT_KEY = "elasticsearch.port";

    private static String host;
    private static Integer port;

    static {
        host = ElasticSearchUtils.getProperty(ELASTICSEARCH_HOST_KEY);
        port = Integer.parseInt(ElasticSearchUtils.getProperty(ELASTICSEARCH_PORT_KEY));
    }



    public static SearchResponse search(SearchRequest request, RequestOptions options){
        // 创建客户端对象
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(host, port, "http"))
        );
        try {
            return client.search(request, options);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("ElasticSearchUtils query failed");
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException("ElasticSearchUtils client close failed");
            }
        }
    }

    private static String getProperty(String key) {
        ApplicationContext applicationContext = SpringBeanUtils.getApplicationContext();
        Environment environment = applicationContext.getEnvironment();
        return environment.getProperty(key);
    }


}
