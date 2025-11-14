package com.FactoryManager.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorageArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
    private int totalTools = 0;
    private int maxCapacity = 500;

    @OneToOne
    @JoinColumn(name = "factory_id", unique = true)
    private Factory factory;

    @OneToMany(mappedBy = "storageArea")
    private List<StorageLocation> storageLocations = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
