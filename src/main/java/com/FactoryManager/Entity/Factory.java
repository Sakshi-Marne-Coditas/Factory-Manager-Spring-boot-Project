package com.FactoryManager.Entity;

import jakarta.persistence.*;
import lombok.*;
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
@ToString
@Audited
public class Factory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    @OneToMany(mappedBy = "factory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FactoryProduct> factoryProducts = new ArrayList<>();

    @OneToMany(mappedBy = "factory", cascade = CascadeType.ALL)
    private List<Tool_Request> toolRequest;

    @OneToMany(mappedBy = "factory",cascade = CascadeType.ALL)
    private List<Bay> bays;

    @OneToMany(mappedBy = "factory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FactoryTool> factoryTools = new ArrayList<>();


    @OneToMany(mappedBy = "factory", cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "factory",cascade = CascadeType.ALL)
    private List<CentralOfficeRequest> centralOfficeRequests;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;



}

