package com.kims.community.board.model.form;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BoardArticleForm {

    /**
     * 제목
     */
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    /**
     * 내용
     */
    @NotBlank(message = "내용을 입력해주세요")
    private String contents;

}
