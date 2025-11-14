package com.FactoryManager.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Audited
public class Bay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bay_id;

    private String bay_name;

    @ManyToOne
    @JoinColumn(name = "factory_id")
    private Factory factory;

    @OneToMany(mappedBy = "bay")
    private List<User> workers;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
