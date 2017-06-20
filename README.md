# StationGraph
수도권 지하철 이전/다음역 및 노선번호 정보를 그래프를 이용하여 구현.

## 개발 환경
- JDK 1.8+ (코드량 줄이기 위해서 stream API 사용. 안드로이드에 적용하시려면 Lightweight-Stream-API(https://github.com/aNNiMON/Lightweight-Stream-API) 로 변환하세요.)
- Lombok (Lombok 안쓰시면 그냥 getter/setter 만드세요.)

## 왜 만들었고, 왜 공개하는가?
**지하철 타이머**에서 사용하기 위해 작성. 딱히 역들의 연결정보(이전/다음역)에  공개된 소스도 없고, 내 애플리케이션에 적용하려는 최적의 구조를 갖춘 소스가 필요했기에 직접 삽질.
그리고 지하철 모바일 애플리케이션은 많이 만들어보는 앱이기에 다른이들이 같은 삽질은 반복 안하도록 의도하여 공개.
- Android : https://play.google.com/store/apps/details?id=com.pongdang.jita
- Web : http://jita.mooo.com (임시. 직접 서버 운영하는거라 좀 불안정해서 간헐적으로 접속이 안될수도 있음.)

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
- identifier : 이전역, 현재역, 다음역 구분 (PREVIOUS, CURRENT, NEXT)
- isMainLine : 이전/다음역이 여러개인 경우 주노선의 역인 지 구분 (true, false)


## Model 데이터 수정시 주의사항
이전/다음역 기준은 "상행/외선/노선도 기준 오른쪽방향"을 바탕으로 작성됐습니다. 그렇기때문에 역 추가시 인접 역명을 꼭 확인해야 합니다. 그리고 **필드명 수정은 피하세요.** 내부적으로 Reflection을 사용하여 필드명인 line'X'에서 'X'를 lineNum 구분자로 사용중입니다. 그리고 'X'의 기준은 서울시 열린 데이터 광장에서 제공하는 모든 지하철 관련 API의 공통 제공 필드를 기준으로 작성됐습니다.


# MIT License
Copyright (c) 2017 Joohyuk Lee

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
