package com.beecoders.ras.model.entity;

import com.beecoders.ras.model.entity.auth.Credential;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Table(name = "restaurant_tables")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_credential_id", referencedColumnName = "id")
    private Credential credential;

    @Column(name = "last_update", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Timestamp lastUpdate;

    @ManyToOne
    @JoinColumn(name = "fk_status_id")
    private TableStatus status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "table")
    private List<Order> orders;
}