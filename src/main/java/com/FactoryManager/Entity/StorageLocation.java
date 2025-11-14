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
public class StorageLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storageAreaName;
    private String rowLabel;
    private int columnNumber;
    private String shelfName;
    private String bucketCode;

    @Column(unique = true, updatable = false)
    private String locationCode;

    private int capacity = 5;
    private int occupiedCount = 0;

    @ManyToOne
    @JoinColumn(name = "storage_area_id")
    private StorageArea storageArea;


    @OneToMany(mappedBy = "storageLocation")
    private List<Tool> tools = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void generateCode() {
        this.locationCode = storageAreaName + "-"
                + rowLabel + "-"
                + String.format("%02d", columnNumber) + "-"
                + shelfName + "-"
                + bucketCode;
    }
}
