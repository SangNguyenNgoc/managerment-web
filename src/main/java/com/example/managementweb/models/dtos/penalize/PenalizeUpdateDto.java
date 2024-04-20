package com.example.managementweb.models.dtos.penalize;


import com.example.managementweb.models.entities.PenalizeEntity;
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
    @NotBlank(message = "Mã xử lí không được bỏ trống")
    @Pattern(regexp = "\\d", message = "Mã xử lí chỉ được chứa ký tự số")
    String id;
    @NotBlank(message = "Mã thành viên không được bỏ trống")
    @Pattern(regexp = "\\d", message = "Mã thành viên chỉ được chứa ký tự số")
    String person;
    @NotBlank(message = "Hình thức xử lí không được để trống")
    String type;
    @Pattern(regexp = "\\d", message = "Tiền chỉ được chứa ký tự số")
    String payment;
    //    @NotBlank(message = "Ngày xử lí không được để trống")
//    String date;
//    @NotBlank(message = "Trạng thái xử lí không được để trống")
//    String status;
}


