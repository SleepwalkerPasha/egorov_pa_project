package ru.tinkoff.edu.java.scrapper.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "link")
@Data
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_id_seq")
    @SequenceGenerator(name = "link_id_seq", sequenceName = "link_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "url")
    private String url;

    @JoinColumn(name = "tg_id")
    @ManyToOne(targetEntity = ChatEntity.class)
    private ChatEntity tgId;

    @Column(name = "checked_at")
    private OffsetDateTime checkedAt;

    @Column(name = "update_at")
    private OffsetDateTime updateAt;

    @Column(name = "open_issues_count")
    @Nullable
    private Integer openIssuesCount;

    @Column(name = "forks_count")
    @Nullable
    private Integer forksCount;

    @Column(name = "answer_count")
    @Nullable
    private Integer answerCount;

    @Column(name = "comment_count")
    @Nullable
    private Integer commentCount;
}
