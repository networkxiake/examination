package com.xiaoyao.examination.controller.form.admin;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class DeleteForm {
    @NotEmpty
    private List<Long> ids;
}
