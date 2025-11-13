    package com.FactoryManager.Entity;

    import jakarta.persistence.*;
    import lombok.*;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;
    import org.hibernate.envers.Audited;

    import java.time.LocalDateTime;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Audited
    public class FactoryProduct {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "factory_id")
        private Factory factory;

        @ManyToOne
        @JoinColumn(name = "product_id")
        private Product product;

        private int quantity;

        private String storageLocation;


        @CreationTimestamp
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;
    }

