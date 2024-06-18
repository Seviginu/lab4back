package com.example.lab4.mbean;

import org.springframework.jmx.export.annotation.ManagedAttribute;

public interface HitCounterMBean {
    int getHitCount();
    void resetHitCount();
    int getOutsideHitCount();
    int getInsideHitCount();
}
