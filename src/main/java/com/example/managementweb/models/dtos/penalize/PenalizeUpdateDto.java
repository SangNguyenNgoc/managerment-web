package com.example.managementweb.models.dtos.penalize;


import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.entities.PenalizeEntity;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link PenalizeEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenalizeUpdateDto implements Serializable {

    Long id;
    @NotBlank(message = "Hình thức xử lí không được để trống")
    String type;
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Tiền chỉ được chứa ký tự số")
    @Max(value = 99999999, message = "Tiền phải nhỏ hơn 999.999.999")
    Integer payment;
//    @NotBlank(message = "Trạng thái không được để trống")
    Boolean status;
    PersonResponseDto person;
    String date;
}


