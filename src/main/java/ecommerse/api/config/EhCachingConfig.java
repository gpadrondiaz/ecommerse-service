package ecommerse.api.config;

import ecommerse.api.model.ECard;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import javax.cache.Caching;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EhCachingConfig {

  /**
   * Config EH cache manager.
   *
   * @return JCacheCacheManager cache manager
   */
  @Bean
  public JCacheCacheManager setupCacheManager() {

    CacheConfiguration<Long, ECard> eCardCacheConfig = CacheConfigurationBuilder
        .newCacheConfigurationBuilder(Long.class,
            ECard.class,
            ResourcePoolsBuilder.newResourcePoolsBuilder()
                .offheap(10, MemoryUnit.MB)
                .build())
        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(600)))
        .build();



    Map<String, CacheConfiguration<?, ?>> cacheMap = new HashMap<>();
    cacheMap.put("eCard", eCardCacheConfig);

    EhcacheCachingProvider ehcacheCachingProvider =
        (EhcacheCachingProvider) Caching.getCachingProvider(EhcacheCachingProvider.class.getName());

    DefaultConfiguration defaultConfiguration =
        new DefaultConfiguration(cacheMap, ehcacheCachingProvider.getDefaultClassLoader());

    javax.cache.CacheManager cacheManager =
        ehcacheCachingProvider.getCacheManager(ehcacheCachingProvider.getDefaultURI(),
            defaultConfiguration);
    JCacheCacheManager jCacheCacheManager = new JCacheCacheManager(cacheManager);
    return jCacheCacheManager;
  }
}

