# KoreaMetroGraph
한국 도시 철도의 이전/다음역 및 노선번호 정보를 그래프 자료구조로 표현.

## 개발 환경
- JDK 1.8+
- [Lombok](https://projectlombok.org/)

## 이런걸 왜 만들었고, 왜 공개하나요?
2015년부터 만들고 있는 지하철 안드로이드 애플리케이션인 ~~[**지하철 타이머**](https://play.google.com/store/apps/details?id=com.pongdang.jita)~~(잠정 휴식)에 적용하기 위해 작성되었습니다.

보통 모바일 앱 제작에서 지하철은 흔한 접하는 주제입니다. 그 중 가장 중요한 정보가 **"이전/다음역"에 대한 연결정보**인 데 딱히 공개된 소스가 없었기에 직접 위키피디아를 크롤링해서 그래프 자료구조로 구현했습니다. ~~(없으면 만드는 개발자 본능!)~~

저와 같은 작업 ~~(이라 쓰고 삽질 혹은 바퀴의 재발명이라 읽는 짓)~~ 을 하는 누군가 수고를 덜었으면 하는 바람입니다.

## 간단한 사용 방법

```java
MetroGraph seoulMetro = MetroGraphFactory.create(SeoulMetroGraphFactory.class);

seoulMetro.find("신도림").forEach(System.out::println);
System.out.println();
seoulMetro.find("신도림", "1").forEach(System.out::println);
```


### 결과
```json
{"identifier":"CURRENT","mainLine":true,"station_nm":"신도림","line_num":"1","fr_code":"140","station_cd":"1007","xpoint_wgs":37.508725,"ypoint_wgs":126.891295,"station_nm_han":"新道林","station_nm_eng":"Sindorim"}
{"identifier":"PREVIOUS","mainLine":true,"station_nm":"구로","line_num":"1","fr_code":"141","station_cd":"1701","xpoint_wgs":37.503039,"ypoint_wgs":126.881966,"station_nm_han":"九老","station_nm_eng":"Guro"}
{"identifier":"NEXT","mainLine":true,"station_nm":"영등포","line_num":"1","fr_code":"139","station_cd":"1006","xpoint_wgs":37.515504,"ypoint_wgs":126.907628,"station_nm_han":"永登浦","station_nm_eng":"Yeongdeungpo"}
{"identifier":"CURRENT","mainLine":true,"station_nm":"신도림","line_num":"2","fr_code":"234","station_cd":"0234","xpoint_wgs":37.508725,"ypoint_wgs":126.891295,"station_nm_han":"新道林","station_nm_eng":"Sindorim"}
{"identifier":"PREVIOUS","mainLine":false,"station_nm":"도림천","line_num":"2","fr_code":"234-1","station_cd":"0247","xpoint_wgs":37.514287,"ypoint_wgs":126.882768,"station_nm_han":"道林川","station_nm_eng":"Dorimcheon"}
{"identifier":"PREVIOUS","mainLine":true,"station_nm":"문래","line_num":"2","fr_code":"235","station_cd":"0235","xpoint_wgs":37.517933,"ypoint_wgs":126.89476,"station_nm_han":"文來","station_nm_eng":"Mullae"}
{"identifier":"NEXT","mainLine":true,"station_nm":"대림","line_num":"2","fr_code":"233","station_cd":"0233","xpoint_wgs":37.49297,"ypoint_wgs":126.895801,"station_nm_han":"大林","station_nm_eng":"Daerim"}

{"identifier":"CURRENT","mainLine":true,"station_nm":"신도림","line_num":"1","fr_code":"140","station_cd":"1007","xpoint_wgs":37.508725,"ypoint_wgs":126.891295,"station_nm_han":"新道林","station_nm_eng":"Sindorim"}
{"identifier":"PREVIOUS","mainLine":true,"station_nm":"구로","line_num":"1","fr_code":"141","station_cd":"1701","xpoint_wgs":37.503039,"ypoint_wgs":126.881966,"station_nm_han":"九老","station_nm_eng":"Guro"}
{"identifier":"NEXT","mainLine":true,"station_nm":"영등포","line_num":"1","fr_code":"139","station_cd":"1006","xpoint_wgs":37.515504,"ypoint_wgs":126.907628,"station_nm_han":"永登浦","station_nm_eng":"Yeongdeungpo"}
```

- identifier : 이전역, 현재역, 다음역 구분 (PREVIOUS, CURRENT, NEXT)
- mainLine : 이전/다음역이 여러개인 경우 주노선의 역인 지 구분 (boolean)
- station_nm : 역 이름
- lineNum : 노선 이름 (서울시 열린 데이터 광장의 [서울시 역코드로 지하철역 정보 검색](http://data.seoul.go.kr/dataList/datasetView.do?infId=OA-112&srvType=A&serviceKind=1) API의 LINE_NUM 필드를 기준으로 작성됐습니다. (자기부상선, 서해선 제외))
  - 1 ~ 9 : 1 ~ 9호선
  - A : 공항철도
  - B : 분당선
  - E : 에버라인 (용인경전철)
  - G : 경춘선
  - I : 인천1호선
  - I2 : 인천2호선
  - K : 경의중앙선
  - KK : 경강선
  - S : 신분당선
  - SU : 수인선
  - U : 의정부경전철
  - UI : 우이신설경전철
  - M : 자기부상선
  - W : 서해선
- fr_code : 외부코드
- station_cd : 역코드
- xpoint_wgs : 위도(Latitude)
- ypoint_wgs : 경도(Longitude)
- station_nm_han : 역 이름(한자)
- station_nm_eng : 역 이름(영문)

현재는 서울만 구현되어있지만, 다른 도시 철도도 추가 예정입니다.
