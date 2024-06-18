package com.example.lab4.mbean;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

@ManagedResource(objectName = "com.example.lab4.mbean:type=HitCounter")
@Component
public class HitCounter extends NotificationBroadcasterSupport implements HitCounterMBean{
    private int insideHitCount = 0;
    private int outsideHitCount = 0;
    private long sequenceNumber = 1;
    private final String notificationMessageTemplate = "Hit count is a multiple of 15: hits=%d, insideHits=%d, outsideHits=%d";

    @ManagedAttribute
    @Override
    public int getHitCount() {
        return insideHitCount + outsideHitCount;
    }

    @ManagedAttribute
    @Override
    public int getInsideHitCount() {
        return insideHitCount;
    }

    @ManagedAttribute
    @Override
    public int getOutsideHitCount() {
        return outsideHitCount;
    }

    public void incrementHitCount(boolean isInside) {

        if(isInside)
            insideHitCount++;
        else
            outsideHitCount++;

        if (getHitCount() % 15 == 0) {
                Notification notification = new Notification(
                    "hitCountNotification",
                    this,
                    sequenceNumber++,
                    System.currentTimeMillis(),
                    String.format(notificationMessageTemplate, getHitCount(), insideHitCount, outsideHitCount)
            );
            sendNotification(notification);
        }
    }

    @ManagedAttribute
    @Override
    public void resetHitCount() {
        outsideHitCount = 0;
        insideHitCount = 0;
    }
}
