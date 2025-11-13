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

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "req_date")
    private LocalDateTime reqDate;

    @Column(name = "approval_date")

    private LocalDateTime approvalDate;

    @OneToMany(mappedBy = "toolRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tool> tools;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
   }

