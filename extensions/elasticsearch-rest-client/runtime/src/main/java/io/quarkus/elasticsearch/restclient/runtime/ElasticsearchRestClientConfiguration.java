package io.quarkus.elasticsearch.restclient.runtime;

import java.util.List;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(phase = ConfigPhase.RUN_TIME, name = "elasticsearch-restclient")
public class ElasticsearchRestClientConfiguration {

    static final String DEFAULT_ELASTIC_SEARCH_HOST = "localhost:9200";

    /**
     * Determine whether to enable the Rest Client.
     */
    @ConfigItem(name = ConfigItem.PARENT)
    boolean enable;

    /**
     * Elasticsearch hosts list.
     */
    @ConfigItem(defaultValue = DEFAULT_ELASTIC_SEARCH_HOST)
    List<String> hosts;

    /**
     * A path's prefix for every request used by the rest client.
     */
    @ConfigItem
    Optional<String> pathPrefix;

    /**
     * Username used for authentication
     */
    @ConfigItem
    Optional<String> username;

    /**
     * Password used for authentication
     */
    @ConfigItem
    Optional<String> password;

}
