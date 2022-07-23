package nextstep.subway.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nextstep.subway.domain.exception.NotExistSectionException;
import nextstep.subway.domain.exception.SectionDeleteException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Sections {

    private static final int EMPTY_VALUE = 0;
    private static final int ONE = 1;

    @OneToMany(mappedBy = "line", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Section> values = new ArrayList<>();

    public List<Station> getStations() {
        Section firstSection = findFirstSection();
        List<Section> sections = new ArrayList<>();
        sections.add(firstSection);

        while (sections.size() != values.size()) {
            Section lastStation = sections.get(sections.size() - ONE);
            Section connectableSection = findConnectableSection(lastStation);
            sections.add(connectableSection);
        }
        return exportStations(sections);
    }

    private List<Station> exportStations(List<Section> sections) {
        return sections.stream()
                .map(Section::getStations)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

    private Section findFirstSection() {
        return values.stream()
                .filter(section -> isMissMatchDownStation(section.getUpStation()))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    private boolean isMissMatchDownStation(Station station) {
        return values.stream()
                .allMatch(section -> section.isMissMatchDownStation(station));
    }

    public void add2(Section section) {
        if (values.isEmpty()) {
            values.add(section);
            return;
        }
        if (this.hasNotOnlyOneStation(section)) {
            throw new IllegalArgumentException();
        }
        Section connectableSection = findConnectableSection(section);
        if (connectableSection.isConnectInSide(section)) {
            values.remove(connectableSection);
            connectableSection.connectInside(section);
            values.add(connectableSection);
        }
        values.add(section);
    }

    private Section findConnectableSection(Section section) {
        return values.stream()
                .filter(section::isConnectable)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private boolean hasNotOnlyOneStation(Section section) {
        return !hasOnlyOneStation(section);
    }

    private boolean hasOnlyOneStation(Section section) {
        boolean hasUpStation = this.hasStation(section.getUpStation());
        boolean hasDownStation = this.hasStation(section.getDownStation());

        if (hasUpStation && hasDownStation) {
            return false;
        }
        return hasUpStation || hasDownStation;
    }

    private boolean hasStation(Station station) {
        return values.stream().anyMatch(section -> section.hasStation(station));
    }

    public void delete(Station station) {
        Section lastSection = findLastSection();
        if (lastSection.isMissMatchDownStation(station)) {
            throw new SectionDeleteException(station.getId());
        }
        values.remove(lastSection);
    }

    private Section findLastSection() {
        if (lastIndex() < EMPTY_VALUE) {
            throw new NotExistSectionException();
        }
        return values.get(lastIndex());
    }

    private int lastIndex() {
        return values.size() - ONE;
    }
}
