package com.empower.controller.comment;

import java.util.Date;

import com.empower.controller.donation.Donation;
import com.empower.controller.fund.Fund;
import com.empower.controller.user.User;

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
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Donation donation;

    @ManyToOne(fetch = FetchType.LAZY)
    private Fund fund;

    @Column(name = "images", columnDefinition = "text[]")
    private String[] images;

    @NotNull(message = "User id is required")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NotNull(message = "Comment text is required")
    private String text;

    private Date createdAt = new Date();

}
