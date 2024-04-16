    package com.example.managementweb.models.entities;

    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDateTime;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "usage_info")
    public class UsageInfoEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;

        @Column(name = "checkin_time", nullable = true)
        private LocalDateTime checkinTime;

        @Column(name = "booking_time", nullable = true)
        private LocalDateTime bookingTime;

        @Column(name = "borrow_time", nullable = true)
        private LocalDateTime borrowTime;

        @Column(name = "return_time", nullable = true)
        private LocalDateTime returnTime;

        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
        @JoinColumn(
                name = "person_id",
                referencedColumnName = "id",
                nullable = false
        )
        private PersonEntity person;

        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
        @JoinColumn(
                name = "device_id",
                referencedColumnName = "id",
                nullable = true
        )
        private DeviceEntity device;

        @Override
        public String toString() {
            return "UsageInfoEntity{" +
                    "id=" + id +
                    ", checkinTime=" + checkinTime +
                    ", borrowTime=" + borrowTime +
                    ", returnTime=" + returnTime +
                    '}';
        }
    }
