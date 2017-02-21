# StationGraph
수도권 지하철 이전/다음역 및 노선번호 정보를 그래프를 이용하여 구현. (JDK 1.8+ 이상 필요!)

## 왜 만들었고, 왜 공개하는가?
**지하철 타이머**에서 사용하기 위해 작성. 딱히 역들의 연결정보(이전/다음역)에  공개된 소스도 없고, 내 애플리케이션에 적용하려는 최적의 구조를 갖춘 소스가 필요했기에 직접 삽질.
그리고 지하철 모바일 애플리케이션은 많이 만들어보는 앱이기에 다른이들이 같은 삽질은 반복 안하도록 의도하여 공개.
- Android : https://play.google.com/store/apps/details?id=com.pongdang.jita
- Web : http://xeyez.mooo.com:8888 (임시. 직접 서버 운영하는거라 좀 불안정해서 간헐적으로 접속이 안될수도 있음.)

## 간단한 사용 방법
```java
StationGraph.getInstance().get("신도림").forEach(System.out::println);
System.out.println();
StationGraph.getInstance().get("신도림", "1").forEach(System.out::println);
```

### 결과
```
StationGraphVO(stationName=구로, lineNum=1, identifier=PREVIOUS, isMainLine=true)
StationGraphVO(stationName=영등포, lineNum=1, identifier=NEXT, isMainLine=true)
StationGraphVO(stationName=도림천, lineNum=2, identifier=PREVIOUS, isMainLine=false)
StationGraphVO(stationName=문래, lineNum=2, identifier=PREVIOUS, isMainLine=true)
StationGraphVO(stationName=대림, lineNum=2, identifier=NEXT, isMainLine=true)

StationGraphVO(stationName=구로, lineNum=1, identifier=PREVIOUS, isMainLine=true)
StationGraphVO(stationName=영등포, lineNum=1, identifier=NEXT, isMainLine=true)
```

- stationName : 역 이름
- lineNum : 노선 이름
 - 1~9 : 1~9호선
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
- identifier : 이전역, 현재역, 다음역 구분
- isMainLine : 이전/다음역이 여러개인 경우 주노선의 역인 지 구분
