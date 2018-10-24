package com.bactoria.toy1.configuration;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@Component
@EnableJpaAuditing //JPA Auditing 활성화
public class JpaAuditConfig {
}
