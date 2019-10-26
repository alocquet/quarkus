package io.quarkus.elasticsearch.restclient.runtime;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

@ApplicationScoped
public class ElasticsearchRestClientProducer {

    private static final Logger log = Logger.getLogger("io.quarkus.elasticsearch-rest-client");

    private ElasticsearchRestClientConfiguration configuration;

    void initialize(ElasticsearchRestClientConfiguration configuration) {
        this.configuration = configuration;
    }

    @Produces
    public RestClient restClient() {
        if (!configuration.enable) {
            throw new IllegalStateException(
                    "ElasticSearch Rest client is not enabled : quarkus.elasticsearch-restclient=false");
        }

        final RestClientBuilder builder = RestClient
                .builder((HttpHost[]) configuration.hosts.stream()
                        .map(HttpHost::create)
                        .toArray(HttpHost[]::new));

        configuration.pathPrefix.ifPresent(builder::setPathPrefix);
        configuration.username.ifPresent(username -> {
            if (configuration.password.isPresent()) {
                builder.setHttpClientConfigCallback(createAuthenticationConfigCallback(username, configuration.password.get()));
            } else {
                log.warning("Password used for authentication is not defined while the username is");
            }
        });

        return builder.build();
    }

    private RestClientBuilder.HttpClientConfigCallback createAuthenticationConfigCallback(String username, String password) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));
        return httpClientBuilder -> httpClientBuilder
                .setDefaultCredentialsProvider(credentialsProvider);
    }
}
