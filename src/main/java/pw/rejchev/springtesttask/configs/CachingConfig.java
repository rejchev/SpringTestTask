package pw.rejchev.springtesttask.configs;

import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@EnableCaching
@Configuration
@AllArgsConstructor
public class CachingConfig {

    static final List<Cache> cache = Arrays.asList(
            new ConcurrentMapCache("banks"),
            new ConcurrentMapCache("clients"),
            new ConcurrentMapCache("deposits")
    );

    @Bean
    public CacheManager getCacheManager() {

        SimpleCacheManager manager = new SimpleCacheManager();

        manager.setCaches(cache);

        return manager;
    }
}
