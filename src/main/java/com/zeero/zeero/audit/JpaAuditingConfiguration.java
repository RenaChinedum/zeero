package com.zeero.zeero.audit;

import com.zeero.zeero.model.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfiguration {


    @Bean
    public AuditorAware<String> auditorAware(){
        return new JpaAuditingImpl();
    }

    private static class JpaAuditingImpl implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof Users) {
                Users loggedInPatron = (Users) authentication.getPrincipal();
                return Optional.ofNullable(loggedInPatron.getFirstName() + " " + loggedInPatron.getLastName());
            } else {
                return Optional.empty();
            }
        }
    }
}
