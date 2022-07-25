# 지하철 노선도 미션
[ATDD 강의](https://edu.nextstep.camp/c/R89PYi5H) 실습을 위한 지하철 노선도 애플리케이션

---

### 요구사항

- [X] 지하철 구간 관련 단위 테스트를 완성하세요.
  - [X] 구간 단위 테스트 (LineTest)
  - [X] 구간 서비스 단위 테스트 with Mock (LineServiceMockTest)
  - [X] 구간 서비스 단위 테스트 without Mock (LineServiceTest)
  - [X] 구간 등록 
    - [X] 신규 구간의 상행역과 하행역이 둘중 하나만 노선에 등록되어 있어야한다.
    - [X] 구간 사이 등록 기능
      - [X] 구간 사이에 등록 시 신규 구간의 길이가 구간 사이의 길이보다 작아야한다.
      - [X] 기존 구간의 길이가 신규 구간의 길이만큼 줄어든다.
      - [X] 신규 구간이 상행 구간일 경우
        - [X] 기존 구간의 상행역이 신규구간의 하행역으로 변경된다.
      - [X] 신규 구간이 하행 구간일 경우
        - [X] 기존 구간의 하행역이 신규구간의 상행역으로 변경된다.
    - [X] 신규 구간이 상행 종점 구간 등록 기능
      - [X] 신규 구간이 등록된다.
    - [X] 신규 구간이 하행 종점 구간 등록 기능
      - [X] 신규 구간이 등록된다.
  - [X] 노선의 지하철 역 조회 시 연결된 순서에 맞게 제공한다.
  - [ ] 구간 삭제
    - [X] 삭제하려는 지하철 역을 가진 구간이 있어야만 한다.
    - [ ] 구간을 삭제 시 최소 두개 이상의 구간이 존재해야 한다.
    - [ ] 상행 종점 구간
      - [ ] 구간이 삭제된다.
    - [ ] 하행 종점 구간
      - [ ] 구간이 삭제된다.
    - [ ] 중간 구간
      - [ ] 구간이 삭제되면 해당 구간의 후행 구간(상행역이 삭제된 구간의 하행역과 같은 구간)의 상행역이 삭제된 상행역으로 변경된다.
      - [ ] 구간이 삭제된면 해당 구간의 후행 구간(상행역이 삭제된 구간의 하행역과 같은 구간)의 길이는 삭제된 구간의 길이를 합친다.
- [X] 단위 테스트를 기반으로 비즈니스 로직을 리팩터링 하세요.
- [X] 인수조건을 검증하는 인수 테스트를 작성하세요.
  - [X] 구간 사이에 새로운 구간 등록 인수 테스트
  - [X] 상행 종점에 새로운 구간 등록 인수 테스트
  - [X] 하행 종점에 새로운 구간 등록 인수 테스트
  - [X] 첫번째 구간 삭제 인수 테스트
  - [ ] 중간 구간 삭제 인수 테스트
  - [ ] 마지막 구간 삭제 인수 테스트
  
### 인수 조건

- 구간 사이에 새로운 구간을 등록하면 구간이 추가된다.
  > `when` 노선의 구간 사이에 새로운 구간을 추가하면   
  > `then` 구간 사이에 새로운 구간이 추가된다. 
- 상행 종점에 새로운 구간을 등록하면 구간이 추가된다.
  > `when` 노선의 상행 종점역에 새로운 구간을 추가하면   
  > `then` 구간 상행에 새로운 구간이 추가된다. 
- 하행 종점에 새로운 구간을 등록하면 구간이 추가된다.
  > `when` 노선의 하행 종점역에 새로운 구간을 추가하면   
  > `then` 구간 하행에 새로운 구간이 추가된다.
- 첫번째 구간을 삭제하면 해당 구간이 삭제된다.
  > `given` 지하철 노선에 새로운 구간 추가를 요청 하고   
  > `when` 지하철 노선의 첫번째 구간 제거를 요청 하면   
  > `then` 노선에 구간이 제거된다.
- 중간 구간을 삭제하면 해당 구간이 삭제된다.
  > `given` 지하철 노선에 새로운 구간 추가를 요청 하고   
  > `when` 지하철 노선의 중간 구간 제거를 요청 하면   
  > `then` 노선에 구간이 제거된다.
- 마지막 구간을 삭제하면 해당 구간이 삭제된다.
  > `given` 지하철 노선에 새로운 구간 추가를 요청 하고   
  > `when` 지하철 노선의 마지막 구간 제거를 요청 하면   
  > `then` 노선에 구간이 제거된다.