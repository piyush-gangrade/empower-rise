package com.empower.controller.donation;

import java.util.Date;

import com.empower.controller.fund.Fund; // Make sure this import is correct for your project
import com.empower.controller.user.User;
import com.empower.enums.StatusEnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donation")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount is required")
    private Double amount; // Changed to Double to support cents/decimals, standard for currency

    private String donorName;

    private String message;

    private boolean anonymous = false;

    // A donation MUST belong to a Fund
    @NotNull(message = "Fund is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Fund fund;

    // A donation MIGHT belong to a registered User (if they are logged in)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    private Date date;

    private StatusEnum status = StatusEnum.PENDING; // E.g., PENDING, SUCCESS, FAILED

    // Auto-set the date when the record is saved to the database
    @PrePersist
    protected void onCreate() {
        this.date = new Date();
    }
}