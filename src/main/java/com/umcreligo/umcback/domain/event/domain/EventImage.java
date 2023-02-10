package com.umcreligo.umcback.domain.event.domain;


import com.umcreligo.umcback.domain.church.domain.Church;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class EventImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id; // pk


    @Column
    private String imageURL;

    @Column
    @Enumerated(value = STRING)
    private ImageType imageType;

    public enum ImageType{
        MAIN
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId")
    @ToString.Exclude
    private Event event;

}
