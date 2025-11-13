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
public class Redemption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User distributor;

    @Column(name = "requested_at")
    private LocalDateTime requested_at;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @OneToMany(mappedBy = "redemption", cascade = CascadeType.ALL)
    private List<Merchandise> merchandises;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

