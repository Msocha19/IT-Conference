package sii.task.conference.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lecture")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Lecture extends AbstractEntity {

    @NotNull
    @Basic(optional = false)
    @Column(name = "lecture_date", nullable = false)
    private LocalDateTime lectureDate;

    @NotNull
    @Basic(optional = false)
    @Column(name = "duration_time", updatable = false, nullable = false)
    private int durationTime = 105;

    @NotNull
    @Basic(optional = false)
    @Column(name = "topic_path", updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private TopicPath topicPath;

    @NotNull
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "lecture_participant",
        joinColumns = @JoinColumn(name = "lecture_id"),
        inverseJoinColumns = @JoinColumn(name = "participant_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"lecture_id", "participant_id"}))
    private Set<Participant> lectures = new HashSet<>();
}
