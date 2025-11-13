package com.FactoryManager.Entity;

import com.FactoryManager.Constatnts.Role;
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
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @Column(name = "phone_no")
    private String phoneNo;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String photo;
    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Bay bay;

    @ManyToOne
    @JoinColumn(name = "factory_id")
    private Factory factory;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private DistributorDetails distributorDetails;

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tool> assignedTools;

    // Relationships
    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "distributor", cascade =CascadeType.ALL )
    private List<Order> orders;

    @OneToMany(mappedBy = "worker",cascade =CascadeType.ALL )
    private List<Tool_Request> toolRequests;

    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL)
    private List<Redemption> redemptions;

    @OneToMany(mappedBy = "requestedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CentralOfficeRequest> raisedRequests;

    @OneToMany(mappedBy = "approvedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CentralOfficeRequest> approvedRequests;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

