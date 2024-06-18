package com.example.lab4.mbean;

import com.example.lab4.model.hits.Hit;
import com.example.lab4.model.hits.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@ManagedResource(objectName = "com.example.lab4.mbean:type=FigureSquareCalculator")
public class FigureSquareCalculator implements FigureSquareCalculatorMBean {

    private final Figure figure = new Figure();

    @ManagedAttribute
    @Override
    public Double getSquare() {
        return figure.getSquare();
    }

    public void addHit(Hit hit) {
        figure.addPoint(hit.getPoint());
    }

    private static class Figure {
        private final List<Point> points = new ArrayList<>(3);
        private Double square;

        public void addPoint(Point point) {
            if (points.size() == 3) {
                points.remove(0);
            }
            points.add(point);
            onUpdate();
        }

        private void onUpdate() {
            square = null;
        }

        public double getSquare() {
            if(points.size() != 3) {
                throw new UnsupportedOperationException("Can't calculate square, because it's not a triangle");
            }
            if(square != null)
                return square;
            square = calculateSquare();
            return square;
        }

        private double calculateSquare() {
            double x1 = points.get(0).getX(), y1 = points.get(0).getY();
            double x2 = points.get(1).getX(), y2 = points.get(1).getY();
            double x3 = points.get(2).getX(), y3 = points.get(2).getY();

            double a = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
            double b = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
            double c = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));

            double s = (a + b + c) / 2;

            return Math.sqrt(s * (s - a) * (s - b) * (s - c));
        }
    }


}
