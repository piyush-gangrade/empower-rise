package com.empower.controller.reply;

import java.util.Date;

import com.empower.controller.comment.Comment;
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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reply")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Comment id is required")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @NotNull(message = "Reply content is required")
    private String content;

    @NotNull(message = "User id is required")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Date createdAt = new Date();

}
