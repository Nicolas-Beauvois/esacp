package fr.delpharm.esacp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, fr.delpharm.esacp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, fr.delpharm.esacp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, fr.delpharm.esacp.domain.User.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Authority.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.User.class.getName() + ".authorities");
            createCache(cm, fr.delpharm.esacp.domain.UserExtra.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Sexe.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Sexe.class.getName() + ".userExtras");
            createCache(cm, fr.delpharm.esacp.domain.CorrectPrevent.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.CorrectPrevent.class.getName() + ".actions");
            createCache(cm, fr.delpharm.esacp.domain.Csp.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Csp.class.getName() + ".userExtras");
            createCache(cm, fr.delpharm.esacp.domain.FicheSuiviSante.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.FicheSuiviSante.class.getName() + ".rapports");
            createCache(cm, fr.delpharm.esacp.domain.Actions.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Categorie.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Categorie.class.getName() + ".rapports");
            createCache(cm, fr.delpharm.esacp.domain.Contrat.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Contrat.class.getName() + ".userExtras");
            createCache(cm, fr.delpharm.esacp.domain.Criticite.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Criticite.class.getName() + ".actions");
            createCache(cm, fr.delpharm.esacp.domain.Equipement.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Equipement.class.getName() + ".rapports");
            createCache(cm, fr.delpharm.esacp.domain.EtapeValidation.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.EtapeValidation.class.getName() + ".actions");
            createCache(cm, fr.delpharm.esacp.domain.ListingMail.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Mail.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.NatureAccident.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.NatureAccident.class.getName() + ".rapports");
            createCache(cm, fr.delpharm.esacp.domain.OrigineLesions.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.OrigineLesions.class.getName() + ".rapports");
            createCache(cm, fr.delpharm.esacp.domain.PieceJointes.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.PieceJointes.class.getName() + ".actions");
            createCache(cm, fr.delpharm.esacp.domain.Priorite.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Priorite.class.getName() + ".actions");
            createCache(cm, fr.delpharm.esacp.domain.Rapport.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Rapport.class.getName() + ".actions");
            createCache(cm, fr.delpharm.esacp.domain.Departement.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Departement.class.getName() + ".userExtras");
            createCache(cm, fr.delpharm.esacp.domain.SiegeLesions.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.SiegeLesions.class.getName() + ".rapports");
            createCache(cm, fr.delpharm.esacp.domain.Site.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Site.class.getName() + ".userExtras");
            createCache(cm, fr.delpharm.esacp.domain.Statut.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Statut.class.getName() + ".userExtras");
            createCache(cm, fr.delpharm.esacp.domain.Type.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Type.class.getName() + ".rapports");
            createCache(cm, fr.delpharm.esacp.domain.Repartition.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.Repartition.class.getName() + ".types");
            createCache(cm, fr.delpharm.esacp.domain.TypeAt.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.TypeAt.class.getName() + ".ficheSuiviSantes");
            createCache(cm, fr.delpharm.esacp.domain.TypeAction.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.TypeAction.class.getName() + ".actions");
            createCache(cm, fr.delpharm.esacp.domain.TypeRapport.class.getName());
            createCache(cm, fr.delpharm.esacp.domain.TypeRapport.class.getName() + ".rapports");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
