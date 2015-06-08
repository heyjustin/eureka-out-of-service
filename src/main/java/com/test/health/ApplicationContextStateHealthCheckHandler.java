package com.test.health;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * A {@link HealthCheckHandler} which consults a {@link HealthIndicator} to determine it's status.
 */
@Component
public class ApplicationContextStateHealthCheckHandler implements HealthCheckHandler {

    private static final Log log = LogFactory.getLog(ApplicationContextStateHealthCheckHandler.class);

    @Autowired
    private ApplicationContextStateHealthIndicator indicator;

    @Override
    public InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus currentStatus) {

        if (InstanceInfo.InstanceStatus.STARTING == currentStatus) {
            return currentStatus;
        }

        final Status status = indicator.health().getStatus();
        final InstanceInfo.InstanceStatus mappedStatus;


        if (Status.UP.equals(status)) {
            mappedStatus = InstanceInfo.InstanceStatus.UP;
        } else if (Status.DOWN.equals(status)) {
            mappedStatus = InstanceInfo.InstanceStatus.DOWN;
        } else if (Status.OUT_OF_SERVICE.equals(status)) {
            mappedStatus = InstanceInfo.InstanceStatus.OUT_OF_SERVICE;
        } else {
            mappedStatus = InstanceInfo.InstanceStatus.UNKNOWN;
        }

        if (log.isDebugEnabled()) {
            log.debug(
                    "Mapped status from: " +
                            status.getClass().getSimpleName() + "." + status +
                            " to: " +
                            mappedStatus.getClass().getSimpleName() + "." + mappedStatus);
        }
        return mappedStatus;
    }
}
