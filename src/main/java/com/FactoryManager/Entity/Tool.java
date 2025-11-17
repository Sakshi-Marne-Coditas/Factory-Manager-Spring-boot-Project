package com.FactoryManager.Entity;

import com.FactoryManager.Constatnts.ToolType;
import com.FactoryManager.Constatnts.UseCase;
import com.FactoryManager.Entity.FactoryTool;
import com.FactoryManager.Entity.StorageLocation;
import com.FactoryManager.Entity.ToolRequestItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Audited
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String toolName;
    private String toolDescription;
    private String toolImage;

    @Enumerated(EnumType.STRING)
    private UseCase useCase;

    @Enumerated(EnumType.STRING)
    private ToolType toolType;

    private String storage_area;
    private int thresholdQty;
    private int totalStock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "storage_location_id")
    private StorageLocation storageLocation;

    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FactoryTool> factoryTools = new ArrayList<>();

    @OneToMany(mappedBy = "tool")
    private List<ToolRequestItem> requestItems;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Transient
    public String getUniqueLocationCode() {
        return storageLocation != null ? storageLocation.getLocationCode() : "NOT_IN_STORAGE";
    }
}
