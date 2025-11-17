package com.FactoryManager.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Audited
public class OrderBatchItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantityDispatched;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private OrderBatch batch;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
