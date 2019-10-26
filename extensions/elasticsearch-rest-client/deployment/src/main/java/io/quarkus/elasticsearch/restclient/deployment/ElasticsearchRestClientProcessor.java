package io.quarkus.elasticsearch.restclient.deployment;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.BeanContainerBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.ExtensionSslNativeSupportBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.elasticsearch.restclient.runtime.ElasticsearchRestClientConfiguration;
import io.quarkus.elasticsearch.restclient.runtime.ElasticsearchRestClientProducer;
import io.quarkus.elasticsearch.restclient.runtime.ElasticsearchRestClientRecorder;

class ElasticsearchRestClientProcessor {

    @BuildStep
    public FeatureBuildItem feature() {
        return new FeatureBuildItem(FeatureBuildItem.ELASTICSEARCH_REST_CLIENT);
    }

    @BuildStep
    public void build(BuildProducer<ExtensionSslNativeSupportBuildItem> extensionSslNativeSupport) {
        // Indicates that this extension would like the SSL support to be enabled
        extensionSslNativeSupport.produce(new ExtensionSslNativeSupportBuildItem(FeatureBuildItem.ELASTICSEARCH_REST_CLIENT));
    }

    @BuildStep
    AdditionalBeanBuildItem createRestClientProducer() {
        return AdditionalBeanBuildItem.unremovableOf(ElasticsearchRestClientProducer.class);
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    void configureRestClientProducer(ElasticsearchRestClientRecorder recorder, BeanContainerBuildItem beanContainerBuildItem,
            ElasticsearchRestClientConfiguration configuration) {
        // TODO Use shutdownContext to close RestClient ?
        recorder.configureRestClientProducer(beanContainerBuildItem.getValue(), configuration);
    }

}
