package com.example.managementweb.models.dtos.person;

import com.example.managementweb.models.entities.Role;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.example.managementweb.models.entities.PersonEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonAndPenaltiesDto implements Serializable {
    Long id;
    String name;
    String department;
    String email;
    String profession;
    String phoneNumber;
    Boolean status;
    Role role;
    List<PenalizeDto> penalties;

    /**
     * DTO for {@link com.example.managementweb.models.entities.PenalizeEntity}
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PenalizeDto implements Serializable {
        Long id;
        String type;
        Integer payment;
        String date;
        Boolean status;
    }
}