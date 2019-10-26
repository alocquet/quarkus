package io.quarkus.elasticsearch.restclient.runtime;

import org.jboss.logging.Logger;

import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class ElasticsearchRestClientRecorder {

    private static final Logger log = Logger.getLogger(ElasticsearchRestClientRecorder.class);

    public void configureRestClientProducer(BeanContainer beanContainer, ElasticsearchRestClientConfiguration configuration) {
        ElasticsearchRestClientProducer clientProducer = beanContainer.instance(ElasticsearchRestClientProducer.class);
        clientProducer.initialize(configuration);
    }

}
