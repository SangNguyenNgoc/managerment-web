package com.example.managementweb.models.dtos.penalize;


import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.entities.PenalizeEntity;
import com.example.managementweb.models.entities.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link PenalizeEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenalizeResponseDto  implements Serializable {
    Long id;
    String type;
    Integer payment;
    String date;
    Boolean status;
    PersonResponseDto person;

}
