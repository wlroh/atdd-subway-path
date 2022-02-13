package nextstep.subway.unit;

import nextstep.subway.domain.Line;
import nextstep.subway.domain.Section;
import nextstep.subway.domain.Sections;
import nextstep.subway.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LineTest {
    final Line line = new Line();
    final Station 상행역 = new Station("상행역");
    final Station 하행역 = new Station("하행역");
    final Station 마지막역 = new Station("마지막역");
    final Station 새로운역 = new Station("새로운역");
    final Station 또다른역 = new Station("또다른역");

    @DisplayName("구간 목록 마지막에 새로운 구간을 추가할 경우")
    @Test
    void addSection() {
        line.addSection(상행역, 하행역, 1);

        final Station 새로운역 = new Station("새로운역");
        line.addSection(하행역, 새로운역, 1);

        assertThat(line.sections().size()).isEqualTo(2);
    }

    @DisplayName("노선에 속해있는 역 목록 조회")
    @Test
    void getStations() {
        // given
        line.addSection(상행역, 하행역, 1);
        line.addSection(하행역, new Station("새로운역"), 1);

        // when
        List<Station> stations = line.sections().getAllStations();

        // then
        assertThat(stations).hasSize(3);
    }

    @DisplayName("구간이 목록에서 마지막 역 삭제")
    @Test
    void removeSection() {
        // given
        line.addSection(상행역, 하행역, 1);
        line.addSection(하행역, 마지막역, 1);

        // when
        line.sections().deleteSection(마지막역);

        // then
        assertThat(line.sections().getAllStations().size()).isEqualTo(2);
    }

    @DisplayName("노선 생성시 구간 1개 자동 생성")
    @Test
    void createLine() {
        line.addSection(상행역, 하행역, 3);

        assertThat(line.sections().size()).isEqualTo(1);
    }

    @DisplayName("노선 생성시 상행 종점, 하행 종점 지정 확인")
    @Test
    void createLine2() {
        final Section section = new Section(line, 상행역, 하행역, 3);
        line.addSection(상행역, 하행역, 3);

        assertThat(line.sections().isFirstStationFrom(section)).isTrue();
        assertThat(line.sections().isLastStationFrom(section)).isTrue();
    }

    @DisplayName("노선 생성 후, 상행종점역 기준으로 상행역 구간 추가 (종점 추가)")
    @Test
    void addSection2() {
        line.addSection(상행역, 하행역, 3);

        final Station 새로운역 = new Station("새로운역");

        // distance 상관 없음
        final Section newSection = new Section(line, 새로운역, 상행역, 1);
        line.addSection(newSection);

        assertThat(line.sections().isFirstStationFrom(newSection)).isTrue();
    }

    @DisplayName("노선 생성 후, 상행역 기준으로 상행역 구간 추가 (중간 추가)")
    @Test
    void addSection3() {
        line.addSection(상행역, 하행역, 3);

        final Station 새로운역 = new Station("새로운역");

        // distance 상관 없음
        final Section firstSection = new Section(line, 새로운역, 상행역, 5);
        line.addSection(firstSection);

        // 새로운역-(5)-상행역-(3)-하행역
        final Station 또다른역 = new Station("또다른역");
        line.addSection(또다른역, 상행역, 2);

        // 새로운역-(3)-또다른역-(2)-상행역-(3)-하행역
        assertThat(line.sections().isFirstStationFrom(firstSection)).isTrue();
    }

    @DisplayName("노선 생성 후, 하행종점 역 기준으로 하행역 구간 추가(종점 추가)")
    @Test
    void addSection4() {
        line.addSection(상행역, 하행역, 3);

        // distance 상관 없음
        final Section newSection = new Section(line, 하행역, 새로운역, 5);
        line.addSection(newSection);

        // 상행역-(3)-하행역-(5)-새로운역
        // distance 체크 필요
        final Section lastSection = new Section(line, 새로운역, 또다른역, 2);
        line.addSection(lastSection);

        // 상행역-(3)-하행역-(5)-새로운역-(2)-또다른역
        assertThat(line.sections().isLastStationFrom(lastSection)).isTrue();
    }

    @DisplayName("노선 생성 후, 하행 역 기준으로 하행역 구간 추가(중간 추가)")
    @Test
    void addSection5() {
        line.addSection(상행역, 하행역, 3);

        // distance 상관 없음
        final Section newSection = new Section(line, 하행역, 새로운역, 5);
        line.addSection(newSection);

        // 상행역-(3)-하행역-(5)-새로운역
        final Section lastSection = new Section(line, 또다른역, 새로운역, 2);
        line.addSection(lastSection);

        // 상행역-(3)-하행역-(3)-또다른역(2)-새로운역-(2)
        assertThat(line.sections().isFirstStationFrom(lastSection)).isFalse();
        assertThat(line.sections().isLastStationFrom(lastSection)).isTrue();
    }

    @DisplayName("노선 생성 후, 중간 추가 시 길이가 부족하면 에러 발생 확인")
    @Test
    void addSection6() {
        line.addSection(상행역, 하행역, 3);

        // distance 상관 없음
        final Section newSection = new Section(line, 하행역, 새로운역, 1);
        line.addSection(newSection);

        // 상행역-(3)-하행역-(1)-새로운역
        final Section lastSection = new Section(line, 또다른역, 새로운역, 2);

        // distance 체크
        assertThrows(IllegalArgumentException.class, () -> {
            line.addSection(lastSection);
        });
    }


    @DisplayName("노선의 모든 역 순서대로 가져오기")
    @Test
    void getAllStations() {
        final Line line = new Line();
        // 상행역-(3)-하행역-(1)-새로운역
        final Sections sections = new Sections(Arrays.asList(
                new Section(line, 상행역, 하행역, 3),
                new Section(line, 하행역, 새로운역, 1)
        ));

        final List<Station> allStations = sections.getAllStations();
        assertThat(allStations).hasSize(3);
        assertThat(allStations.get(0)).isEqualTo(상행역);
    }
}
