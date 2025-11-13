package com.FactoryManager.Entity;

import com.FactoryManager.Constatnts.RequestStatus;
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
@ToString
@Audited
public class CentralOfficeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int qty;
    @ManyToOne
    @JoinColumn(name = "centralOffice_id")
    private CentralOffice centralOffice;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "requested_by", nullable = false)
    private User requestedBy;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    private String reason;

    @ManyToOne
    @JoinColumn(name = "factory_id")
    private Factory factory;

    @Enumerated(EnumType.STRING)
    RequestStatus requestStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
