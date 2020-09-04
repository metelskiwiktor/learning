package pl.wiktor.learning.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.time.Clock;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

@Configuration
public class ProjectConfiguration {

    @Bean
    public Clock defaultClock(){
        return Clock.systemDefaultZone();
    }

    @Bean
    public DateTimeFormatter dateTimeFormatter(){
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                .withLocale(new Locale("pl"))
                .withZone(ZoneId.systemDefault());
    }

    @Bean
    public ConversionService conversionService(Set<Converter<?,?>> converters){
        ConversionServiceFactoryBean csfb = new ConversionServiceFactoryBean();
        csfb.setConverters(converters);
        csfb.afterPropertiesSet();
        return csfb.getObject();
    }
}
