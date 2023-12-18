package com.example.lab4.dto;

import com.example.lab4.model.hits.Point;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class HitResponse {
    private Point point;
    private Boolean isInside;
    private Date creationDate;
}
