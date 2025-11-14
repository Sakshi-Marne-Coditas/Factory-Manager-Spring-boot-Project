package com.FactoryManager.Entity;

import com.FactoryManager.Constatnts.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Audited
public class OrderBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantityDispatched;

    private LocalDateTime dispatchedAt;
    private LocalDateTime expectedDeliveryDate;


    @Enumerated(EnumType.STRING)
    private RequestStatus status; // DISPATCHED / IN_TRANSIT / DELIVERED etc.

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
