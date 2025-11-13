package com.FactoryManager.Entity;

import com.FactoryManager.Constatnts.ToolType;
import com.FactoryManager.Constatnts.UseCase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Audited
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tool_name;

    private String tool_description;

    private String tool_image;

    private LocalDate issued_date;

    private LocalDate return_date;

    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FactoryTool> factoryTools = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private UseCase use_case;

    @Enumerated(EnumType.STRING)
    private ToolType tool_type;

    private String storage_area;

    private int threshold_qty;

    private int total_stock;

    @ManyToOne
    @JoinColumn(name = "toolRequest_id")
    @JsonIgnore
    private Tool_Request toolRequest;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "storage_location_id")
    private StorageLocation storageLocation;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Transient
    public String getUniqueLocationCode() {
        return storageLocation != null ? storageLocation.getLocationCode() : "NOT_IN_STORAGE";
    }


}

