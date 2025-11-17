package com.FactoryManager.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolRequestItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each request item belongs to a request
    @ManyToOne
    @JoinColumn(name = "tool_request_id")
    private Tool_Request toolRequest;

    // Which tool is requested
    @ManyToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;

    private int quantity;

    // When issued to worker
    private LocalDate issuedDate;

    // When returned
    private LocalDate returnDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
