package com.pplbo.orderservice.common;

import java.util.Set;

import com.pplbo.orderservice.model.OrderItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {

    private Long customerId;

    private Double orderTotal;

    private String shippingAddress;
    
    // @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    // @JsonManagedReference
    // private Set<OrderItem> orderItems;
}
