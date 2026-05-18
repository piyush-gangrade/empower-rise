package com.empower.controller.fund_apply;

import com.empower.controller.fund.Fund;
import com.empower.controller.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "fund_apply")
public class FundApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String userImages;

    @NotNull(message = "Fund is is required")
    @ManyToOne(fetch = FetchType.LAZY)
    private Fund fund;

    private String userEmail;

    @NotNull(message = "Document link is required")
    private String document;

}
