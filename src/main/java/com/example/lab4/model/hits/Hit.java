package com.example.lab4.model.hits;

import com.example.lab4.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Hit {
    @Id
    @GeneratedValue
    private Long id;
    @CreationTimestamp
    private Date created;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;
    @Embedded
    private Point point;
    @Column(name = "inside")
    private Boolean isInside;
}
