package com.dongao.dio.etl.common.elastic;

import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.elasticsearch.client.RestClientBuilder;

import java.util.Objects;

/**
 * @author jiaxiansun@dongao.com
 * @date 2019/12/2 10:40
 * @description
 */
public class SecuredHttpClientConfigCallback implements RestClientBuilder.HttpClientConfigCallback {

    private final CredentialsProvider credentialsProvider;

    /**
     * The {@link SSLIOSessionStrategy} for all requests to enable SSL / TLS encryption.
     */
    private final SSLIOSessionStrategy  sslStrategy;

    /**
     * Create a new {@link SecuredHttpClientConfigCallback}.
     *
     * @param credentialsProvider The credential provider, if a username/password have been supplied
     * @param sslStrategy         The SSL strategy, if SSL / TLS have been supplied
     * @throws NullPointerException if {@code sslStrategy} is {@code null}
     */

    SecuredHttpClientConfigCallback(final SSLIOSessionStrategy sslStrategy,

                                    final CredentialsProvider credentialsProvider) {

        this.sslStrategy = Objects.requireNonNull(sslStrategy);

        this.credentialsProvider = credentialsProvider;

    }


    /**
     * Get the {@link CredentialsProvider} that will be added to the HTTP client.
     *
     * @return Can be {@code null}.
     */
    CredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }


    /**
     * Get the {@link SSLIOSessionStrategy} that will be added to the HTTP client.
     *
     * @return Never {@code null}.
     */

    SSLIOSessionStrategy getSSLStrategy() {

        return sslStrategy;

    }


    /**
     * Sets the {@linkplain HttpAsyncClientBuilder#setDefaultCredentialsProvider(CredentialsProvider) credential provider},
     *
     * @param httpClientBuilder The client to configure.
     * @return Always {@code httpClientBuilder}.
     */
    @Override
    public HttpAsyncClientBuilder customizeHttpClient(final HttpAsyncClientBuilder httpClientBuilder) {

        // enable SSL / TLS
        httpClientBuilder.setSSLStrategy(sslStrategy);
        // enable user authentication
        if (credentialsProvider != null) {
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
        }
        return httpClientBuilder;
    }
}
