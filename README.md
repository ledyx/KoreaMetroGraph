# KoreaMetroGraph
한국 도시 철도의 이전/다음역 및 노선번호 정보를 그래프 자료구조로 표현.

## 개발 환경
- JDK 1.8+
- [Lombok](https://projectlombok.org/)

## 이런걸 왜 만들었고, 왜 공개하나요?
2015년부터 만들고 있는 지하철 안드로이드 애플리케이션인 [**지하철 타이머**](https://play.google.com/store/apps/details?id=com.pongdang.jita)에 적용하기 위해 작성되었습니다.

보통 모바일 앱 제작에서 지하철은 흔한 접하는 주제입니다. 그 중 가장 중요한 정보가 **"이전/다음역"에 대한 연결정보**인 데 딱히 공개된 소스가 없었기에 직접 위키피디아를 크롤링해서 그래프 자료구조로 구현했습니다. ~~(없으면 만드는 개발자 본능!)~~

저와 같은 작업(=삽질)을 하는 누군가 수고를 덜었으면 하는 바람입니다.

## 간단한 사용 방법

```java
MetroGraph seoulMetro = MetroGraphFactory.create(SeoulMetroGraphFactory.class);

seoulMetro.find("신도림").forEach(System.out::println);
System.out.println();
seoulMetro.find("신도림", "1").forEach(System.out::println);
```


### 결과
```java
Station(stationName=구로, lineNum=1, identifier=PREVIOUS, isMainLine=true)
Station(stationName=영등포, lineNum=1, identifier=NEXT, isMainLine=true)
Station(stationName=도림천, lineNum=2, identifier=PREVIOUS, isMainLine=false)
Station(stationName=문래, lineNum=2, identifier=PREVIOUS, isMainLine=true)
Station(stationName=대림, lineNum=2, identifier=NEXT, isMainLine=true)

Station(stationName=구로, lineNum=1, identifier=PREVIOUS, isMainLine=true)
Station(stationName=영등포, lineNum=1, identifier=NEXT, isMainLine=true)
```

- stationName : 역 이름
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
  - W : 서해선
- identifier : 이전역, 현재역, 다음역 구분 (PREVIOUS, CURRENT, NEXT)
- isMainLine : 이전/다음역이 여러개인 경우 주노선의 역인 지 구분 (boolean)

현재는 서울만 구현되어있지만, 다른 도시 철도도 추가 예정입니다.


# MIT License
Copyright (c) 2017 Joohyuk Lee

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
