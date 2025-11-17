package com.FactoryManager.Entity;

import com.FactoryManager.Constatnts.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Tool_Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private User worker;

    @ManyToOne
    @JoinColumn(name = "factory_id")
    private Factory factory;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Column(name = "approved_by")
    private Long approvedBy;

    private String rejectionReason;

    private LocalDateTime reqDate;

    private LocalDateTime approvalDate;

    private LocalDateTime returnDate;

    @OneToMany(mappedBy = "toolRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToolRequestItem> items;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
