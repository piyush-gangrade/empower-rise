package com.empower.controller.donation;

import java.util.Date;

import com.empower.controller.category.Category;
import com.empower.controller.user.User;
import com.empower.enums.StatusEnum;

import jakarta.persistence.Column;
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

    @NotNull(message = "Donation title is required")
    private String title;

    @NotNull(message = "Amount is required")
    private Long amount;

    @NotNull(message = "Donation description is required")
    private String description;

    @NotNull(message = "user is required")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @NotNull(message = "Category is required")
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @NotNull(message = "Day left field is required")
    private Date dayLeft;

    @NotNull(message = "Location field is required")
    private String location;

    @NotNull(message = "Images field is required")
    @Column(name = "images", columnDefinition = "text[]")
    private String[] images;

    private Date date;
    private StatusEnum status = StatusEnum.PENDING;
    private int donatedPeople = 0;
    private int collectedAmount = 0;
    private boolean isAccepted = false;

}
