package com.redhat.developergames.config;

import org.infinispan.client.hotrod.DefaultTemplate;
import org.infinispan.client.hotrod.configuration.TransactionMode;
import org.infinispan.client.hotrod.transaction.lookup.RemoteTransactionManagerLookup;
import org.infinispan.commons.marshall.JavaSerializationMarshaller;
import org.infinispan.commons.marshall.ProtoStreamMarshaller;
import org.infinispan.spring.starter.remote.InfinispanRemoteCacheCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Configuration
public class InfinispanConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public InfinispanRemoteCacheCustomizer infinispanRemoteCacheCustomizer() {
        return b -> {
            b.transaction();
            b.marshaller(new ProtoStreamMarshaller());
            b.marshaller(new JavaSerializationMarshaller());
            b.addJavaSerialWhiteList(".*");
            b.transaction().transactionTimeout(1, TimeUnit.MINUTES);
            b.remoteCache("weather")
                    .templateName(DefaultTemplate.DIST_SYNC);
        };
    }
}
