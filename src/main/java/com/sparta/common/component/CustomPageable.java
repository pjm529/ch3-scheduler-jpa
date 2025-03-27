package com.sparta.common.component;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
public class CustomPageable {
    @Schema(description = "페이지 번호 (1부터 시작)", defaultValue = "1")
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    private int page = 1;

    @Schema(description = "한 페이지에 표시할 데이터 개수", defaultValue = "10")
    @Min(value = 1, message = "표시 데이터 개수는 1 이상이어야 합니다.")
    private int size = 10;

    @Hidden
    private String sort = "modifiedDate";

    @Hidden
    private Sort.Direction direction = Sort.Direction.DESC;

    public PageRequest getPageable() {
        return createPageRequest(sort, direction);
    }

    public PageRequest getPageable(String sort, Sort.Direction direction) {
        String resolvedSort = (StringUtils.isNotEmpty(sort)) ? sort : this.sort;
        Sort.Direction resolvedDirection = (direction != null) ? direction : this.direction;

        return createPageRequest(resolvedSort, resolvedDirection);
    }

    private PageRequest createPageRequest(String sort, Sort.Direction direction) {
        return PageRequest.of(page - 1, size, direction, sort.split(","));
    }
}
