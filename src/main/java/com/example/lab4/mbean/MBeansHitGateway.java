package com.example.lab4.mbean;

import com.example.lab4.model.hits.Hit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MBeansHitGateway {
    private final HitCounter hitCounter;
    private final FigureSquareCalculator figureSquareCalculator;

    public void processHitEvent(Hit hit) {
        hitCounter.incrementHitCount(hit.getIsInside());
        figureSquareCalculator.addHit(hit);
    }
}
