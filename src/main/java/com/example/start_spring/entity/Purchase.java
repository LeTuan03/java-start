package com.example.start_spring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "purchase")
public class Purchase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne
    @JoinColumn(name = "buyerId")
    Account buyer;

    @OneToOne
    @JoinColumn(name = "sellerId")
    Account seller;

    @OneToOne
    @JoinColumn(name = "comicId")
    Comic comic;

    @OneToOne
    @JoinColumn(name = "shoppingFormId")
    ShoppingForm shoppingForm;

    @Column(name = "price", nullable = false)
    Double price;

    @Column(name = "quantity", nullable = false)
    Integer quantity;

    @Column(name = "purchase_date", nullable = false)
    LocalDateTime purchaseDate = LocalDateTime.now();

    @Column(name = "purchaseNumber")
    String purchaseNumber;

    @Column(name = "status")
    String status;

    @Column(name = "note")
    String note;

    @PrePersist
    void generateId() {
        if (this.purchaseNumber == null) {
            this.purchaseNumber = generateUniqueId();
        }
    }

    String generateUniqueId() {
        String prefix = "PUR-";
        int nextId = getNextIdFromDatabase();
        return prefix + String.format("%06d", nextId);
    }

    int getNextIdFromDatabase() {
        return 1;
    }

}
