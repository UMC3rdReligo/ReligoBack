package com.umcreligo.umcback.domain.event.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.umcreligo.umcback.domain.church.domain.Church;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Table(name = "event")
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id; // pk

    private String eventName;

    private LocalDateTime eventDate;

    private String eventIntroduction;

    private String participation; // 참여대상

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "churchId")
    @ToString.Exclude
    private Church church;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Event(String eventName, LocalDateTime eventDate, String eventIntroduction, String participation, String location, Church church) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventIntroduction = eventIntroduction;
        this.participation = participation;
        this.location = location;
        this.church = church;
    }
}
