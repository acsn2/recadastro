package br.mppe.mp.recadastro.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(br.mppe.mp.recadastro.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.Servidor.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.Servidor.class.getName() + ".serMatriculas", jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.Dependente.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.ParenteServidor.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.EstadoCivil.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.Orgao.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.ServidorTipo.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.Pais.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.Cidade.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.RacaCor.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.Escolaridade.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.CategoriaESocial.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.RegimeTrabalho.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.RegimePrevidenciario.class.getName(), jcacheConfiguration);
            cm.createCache(br.mppe.mp.recadastro.domain.AnelViario.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
