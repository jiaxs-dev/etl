package com.dongao.dio.etl.common.elastic;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author jiaxiansun@dongao.com
 * @date 2019/12/2 11:00
 * @description es rest client初始化
 */
@Component
public class ElasticHttpClientConfiguration {
    @Value("${elasticsearch.username}")
    String username;
    @Value("${elasticsearch.password}")
    String password;
    @Value("${elasticsearch.security}")
    boolean security;
    @Value("${elasticsearch.ssl}")
    boolean ssl;

    @Value("${elasticsearch.http.uris}")
    String httpUris;

    @Bean("restHighLevelClient")
    public RestHighLevelClient initRestHighLevelClient() throws Exception {
        if (security) {
            if (ssl) {
                return initSecuritySslClient();
            }
            return initBasicSecurityClient();
        }
        return initBasicClient();

    }

    /**
     * 初始化非认证客户端
     *
     * @return
     */
    public RestHighLevelClient initBasicClient() {
        String[] hostStr = httpUris.split(",");
        HttpHost[] httpHosts = new HttpHost[hostStr.length];
        for (int i = 0; i < hostStr.length; i++) {
            String[] host = hostStr[i].split(":");
            httpHosts[i] = new HttpHost(host[0], Integer.parseInt(host[1]), Const.HTTP);
        }
        RestClientBuilder clientBuilder = RestClient.builder(httpHosts).setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
        RestHighLevelClient client = new RestHighLevelClient(clientBuilder);
        return client;
    }

    /**
     * 初始化简单认证客户端
     *
     * @return
     */
    public RestHighLevelClient initBasicSecurityClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        String[] hostStr = httpUris.split(",");
        HttpHost[] httpHosts = new HttpHost[hostStr.length];
        for (int i = 0; i < hostStr.length; i++) {
            String[] host = hostStr[i].split(":");
            httpHosts[i] = new HttpHost(host[0], Integer.parseInt(host[1]), Const.HTTP);
        }
        RestClientBuilder clientBuilder = RestClient.builder(httpHosts).setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        });
        RestHighLevelClient client = new RestHighLevelClient(clientBuilder);
        return client;
    }

    /**
     * 初始化简单认证客户端
     *
     * @return
     */
    public static RestHighLevelClient initBasicSecurityClient(String uris, String username, String password) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        RestClientBuilder clientBuilder = RestClient.builder(urisChange(uris)).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        });
        RestHighLevelClient client = new RestHighLevelClient(clientBuilder);
        return client;
    }

    public static HttpHost[] urisChange(String uris) {
        String[] hostStr = uris.split(Const.URI_SEPERATOR);
        HttpHost[] httpHosts = new HttpHost[hostStr.length];
        for (int i = 0; i < hostStr.length; i++) {
            String[] host = hostStr[i].split(Const.URI_LINK);
            httpHosts[i] = new HttpHost(host[0], Integer.parseInt(host[1]), Const.HTTP);
        }
        return httpHosts;
    }

    /**
     * 初始化ssl加密客户端
     *
     * @return
     * @throws Exception
     */
    public RestHighLevelClient initSecuritySslClient() throws Exception {
        String[] hostStr = httpUris.split(",");
        HttpHost[] httpHosts = new HttpHost[hostStr.length];
        for (int i = 0; i < hostStr.length; i++) {
            String[] host = hostStr[i].split(":");
            httpHosts[i] = new HttpHost(host[0], Integer.parseInt(host[1]), Const.HTTPS);
        }
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance(Const.SSL);
            sc.init(null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLIOSessionStrategy sessionStrategy = new SSLIOSessionStrategy(sc, new NullHostNameVerifier());
        SecuredHttpClientConfigCallback httpClientConfigCallback = new SecuredHttpClientConfigCallback(sessionStrategy, credentialsProvider);
        RestClientBuilder builder = RestClient.builder(httpHosts).setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS).setHttpClientConfigCallback(httpClientConfigCallback);
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }

    static TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }};

    public static class NullHostNameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            return true;
        }
    }

}
