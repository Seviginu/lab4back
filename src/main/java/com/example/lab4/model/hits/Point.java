package com.example.lab4.model.hits;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Point {
    Double x;
    Double y;
    Double r;
}
