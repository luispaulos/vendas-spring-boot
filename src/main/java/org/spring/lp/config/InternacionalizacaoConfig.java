package org.spring.lp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

/**
 * Classe responsável pelo carregamento e configuração do arquivo de mensagens 'messages.properties'
 * utilizado para internacionalização de mensagens.
 */
@Configuration
public class InternacionalizacaoConfig {

    /**
     * Bean responsável por carregar o arquivo messages.propergies
     *
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("ISO-8859-1");
        messageSource.setDefaultLocale(Locale.getDefault());
        return messageSource;
    }

    /**
     * Bean responsável por obter um ResourceBundle e colocá-lo disponível no contexto do spring
     *
     * @return
     */
    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource());
        return validatorFactoryBean;
    }
}
