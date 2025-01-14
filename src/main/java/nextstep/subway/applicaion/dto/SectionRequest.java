package nextstep.subway.applicaion.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SectionRequest {
    private Long upStationId;
    private Long downStationId;
    private int distance;
}
