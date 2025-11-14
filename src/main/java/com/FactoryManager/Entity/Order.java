package com.FactoryManager.Entity;


import com.FactoryManager.Constatnts.PaymentMethod;
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
@Audited
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double total_amount;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalDateTime order_date;

    private LocalDateTime delivery_date;

    @Enumerated(EnumType.STRING)
    private PaymentMethod payment_method;

    // who placed the order
    @ManyToOne
    @JoinColumn(name = "distributor_id")
    private User distributor;

    @ManyToOne
    @JoinColumn(name = "approvedBy_id")
    private User approvedBy;

    // items in this order
    @OneToMany(mappedBy = "order")
    private List<CartItem> items;

    @OneToMany(mappedBy = "order")
    private List<OrderBatch> batches;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}

