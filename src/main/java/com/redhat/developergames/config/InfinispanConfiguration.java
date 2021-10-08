package com.redhat.developergames.config;

//import org.infinispan.spring.starter.remote.InfinispanRemoteCacheCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.net.URI;
import java.net.URISyntaxException;

import org.infinispan.spring.starter.embedded.InfinispanCacheConfigurer;
import org.infinispan.spring.starter.embedded.InfinispanGlobalConfigurationCustomizer;
import org.infinispan.spring.starter.embedded.InfinispanConfigurationCustomizer;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.eviction.EvictionType;

@Configuration
public class InfinispanConfiguration {

   /*@Bean
   @Order(Ordered.HIGHEST_PRECEDENCE)
   public InfinispanRemoteCacheCustomizer caches() {
      return b -> {
         // Configure the weather cache to be created if it does not exist in the first call
         URI weatherCacheConfigUri = cacheConfigURI("weatherCache.xml");

         b.remoteCache("weather")
                 .configurationURI(weatherCacheConfigUri);
      };
   }

   private URI cacheConfigURI(String cacheConfigFile) {
      URI cacheConfigUri;
      try {
         cacheConfigUri = this.getClass().getClassLoader().getResource(cacheConfigFile).toURI();
      } catch (URISyntaxException e) {
         throw new RuntimeException(e);
      }
      return cacheConfigUri;
   }*/

   // Configuración para usar datagrid en local a continuación
   @Bean
    public InfinispanCacheConfigurer cacheConfigurer() {
        return manager -> {
            final org.infinispan.configuration.cache.Configuration ispnConfig = new ConfigurationBuilder()
                            .clustering()
                            .cacheMode(CacheMode.LOCAL)
                            .build();

            manager.defineConfiguration("local-sync-config", ispnConfig);
        };
    }

    @Bean(name = "testCache")
    public org.infinispan.configuration.cache.Configuration testCache() {
        return new ConfigurationBuilder()
            //.read(baseCache)
            //.memory().size(1000L)
            //.memory().evictionType(EvictionType.COUNT)
            .build(); // MUY IMPORTANTE, EL PARÁMETRO EVICTION ES EL QUE ESTABLECE CUÁNTO TIEMPO PUEDO PERMANECER ALMACENADO UN VALOR EN LA MEMORIA
    }
}
