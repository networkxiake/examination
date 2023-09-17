package com.xiaoyao.examination.controller.form.admin;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class DeleteForm {
    @NotEmpty
    private List<Long> ids;
}
