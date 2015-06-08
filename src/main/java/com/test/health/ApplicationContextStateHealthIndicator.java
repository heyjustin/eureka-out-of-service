package com.test.health;

import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * A {@link HealthIndicator} that reports the health as {@link Status#OUT_OF_SERVICE} if the {@link ApplicationContext}
 * is not running.
 */
@Component
public class ApplicationContextStateHealthIndicator extends AbstractHealthIndicator implements ApplicationContextAware {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // TODO not sure about this cast - is there a better way?
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        if (applicationContext.isRunning()) {
            builder.up();
        } else {
            builder.outOfService();
        }
    }
}
