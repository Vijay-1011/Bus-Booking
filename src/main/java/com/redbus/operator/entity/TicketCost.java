package com.redbus.operator.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket_cost")
public class TicketCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketId;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "bus_id", referencedColumnName = "busId") // Update this line
    private BusOperator busOperator;
    @Column(name = "ticket_price")
    private double ticketPrice;
    private String code;
    @Column(name = "discount_price")
    private double discountPrice;
}
