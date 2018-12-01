package io.github.devwillee.koreametrograph.city.seoul;

import java.util.ArrayList;
import java.util.TreeMap;

public class Model {
	private String line1 = "서동탄 병점 "
			+ "광명 금천구청 "
			+ "신창 온양온천 배방 아산 쌍용(나사렛대) 봉명 천안 두정 직산 성환 평택 지제 서정리 송탄 진위 오산 오산대 세마 병점 세류 수원 화서 성균관대 의왕 당정 군포 금정 명학 안양 관악 석수 금천구청 독산 가산디지털단지 구로 "
			+ "인천 동인천 도원 제물포 도화 주안 간석 동암 백운 부평 부개 송내 중동 부천 소사 역곡 온수 오류동 개봉 구일 "
			+ "구로 신도림 영등포 신길 대방 노량진 용산 남영 서울 시청 종각 종로3가 종로5가 동대문 동묘앞 신설동 제기동 청량리 회기 외대앞 신이문 석계 광운대 월계 녹천 창동 방학 도봉 도봉산 망월사 회룡 의정부 가능 녹양 양주 덕계 덕정 지행 동두천중앙 보산 동두천 소요산";

	private String line2 = "까치산 신정네거리 양천구청 도림천 신도림 신설동 용두 신답 용답 성수 시청 충정로 아현 이대 신촌 홍대입구 합정 당산 영등포구청 문래 신도림 대림 구로디지털단지 신대방 신림 봉천 서울대입구 낙성대 사당 방배 서초 교대 " +
			"강남 역삼 선릉 삼성 종합운동장 잠실새내 잠실 잠실나루 강변 구의 건대입구 성수 뚝섬 한양대 왕십리 상왕십리 신당 동대문역사문화공원 을지로4가 을지로3가 을지로입구 시청";

	// 원흥역 없음
	private String line3 = "오금 경찰병원 가락시장 수서 일원 대청 학여울 대치 도곡 매봉 양재 남부터미널 교대 고속터미널 잠원 신사 압구정 옥수 금호 약수 동대입구 충무로 을지로3가 종로3가 안국 경복궁 독립문 무악재 홍제 녹번 불광 연신내 구파발 지축 삼송 원흥 원당 화정 대곡 백석 마두 정발산 주엽 대화";

	private String line4 = "오이도 정왕 신길온천 안산 초지 고잔 중앙 한대앞 상록수 반월 대야미 수리산 산본 금정 범계 평촌 인덕원 정부과천청사 과천 대공원 경마공원 선바위 남태령 사당 총신대입구(이수) 동작 이촌 신용산 삼각지 숙대입구 서울 회현 명동 충무로 동대문역사문화공원 동대문 혜화 한성대입구 성신여대입구 길음 미아사거리 미아 수유 쌍문 창동 노원 상계 당고개";

	private String line5 = "마천 거여 개롱 오금 방이 올림픽공원 둔촌동 강동 상일동 고덕 명일 굽은다리 길동 강동 천호 광나루 아차산 군자 장한평 답십리 마장 왕십리 행당 신금호 청구 동대문역사문화공원 을지로4가 종로3가 광화문 서대문 충정로 애오개 공덕 마포 여의나루 여의도 신길 영등포시장 영등포구청 양평 오목교 목동 신정 까치산 화곡 우장산 발산 마곡 송정 김포공항 개화산 방화";

	private String line6 = "봉화산 화랑대 태릉입구 석계 돌곶이 상월곡 월곡 고려대 안암 보문 창신 동묘앞 신당 청구 약수 버티고개 한강진 이태원 녹사평 삼각지 효창공원앞 공덕 대흥 광흥창 상수 합정 망원 마포구청 월드컵경기장 디지털미디어시티 증산 새절 응암 "
			+ "역촌 불광 독바위 연신내 구산";

	private String line7 = "부평구청 굴포천 삼산체육관 상동 부천시청 신중동 춘의 부천종합운동장 까치울 온수 천왕 광명사거리 철산 가산디지털단지 남구로 대림 신풍 보라매 신대방삼거리 장승배기 상도 숭실대입구 남성 총신대입구(이수) 내방 고속터미널 반포 논현 학동 강남구청 청담 뚝섬유원지 건대입구 어린이대공원 군자 중곡 용마산 사가정 면목 상봉 중화 먹골 태릉입구 공릉 하계 중계 노원 마들 수락산 도봉산 장암";

	private String line8 = "모란 수진 신흥 단대오거리 남한산성입구 산성 복정 장지 문정 가락시장 송파 석촌 잠실 몽촌토성 강동구청 천호 암사";

	private String line9 = "종합운동장 봉은사 삼성중앙 선정릉 언주 신논현 사평 고속터미널 신반포 구반포 동작 흑석 노들 노량진 샛강 여의도 국회의사당 당산 선유도 신목동 염창 등촌 증미 가양 양천향교 마곡나루 신방화 공항시장 김포공항 개화";

	// 공항
	// 검암 -> 운서 20000m 16~17분
	private String lineA = "서울 공덕 홍대입구 디지털미디어시티 김포공항 계양 검암 청라국제도시 영종 운서 공항화물청사 인천공항1터미널 인천공항2터미널";

	// 경의중앙
	private String lineK = "서울 신촌(경의중앙선) 가좌 "
			+ "지평 용문 원덕 양평(경의중앙선) 오빈 아신 국수 신원 양수 운길산 팔당 도심 덕소 양정 도농 구리 양원 망우 상봉 중랑 회기 "
			+ "청량리 왕십리 응봉 옥수 한남 서빙고 이촌 용산 효창공원앞 공덕 서강대 홍대입구 가좌 디지털미디어시티 수색 화전 강매 행신 능곡 대곡 곡산 백마 풍산 일산 탄현 야당 운정 금릉 금촌 월롱 파주 문산";

	// 경춘
	private String lineG = "춘천 남춘천 김유정 강촌 백양리 굴봉산 가평 상천 청평 대성리 마석 천마산 평내호평 금곡 사릉 퇴계원 별내 갈매 신내 망우 상봉 중랑 회기 청량리";

	// 경강
	private String lineKK = "판교 이매 삼동 경기광주 초월 곤지암 신둔도예촌 이천 부발 세종대왕릉 여주";

	// 분당
	private String lineB = "수원 매교 수원시청 매탄권선 망포 영통 청명 상갈 기흥 신갈 구성 보정 죽전 오리 미금 정자 수내 서현 이매 야탑 모란 태평 가천대 복정 수서 대모산입구 개포동 구룡 도곡 한티 선릉 선정릉 강남구청 압구정로데오 서울숲 왕십리";

	// 수인
	private String lineSU = "오이도 달월 월곶 소래포구 인천논현 호구포 남동인더스파크 원인재 연수 송도 "
			+ "인하대 숭의 신포 인천";

	// 신분당
	private String lineS = "광교 광교중앙 상현 성복 수지구청 동천 미금 정자 판교 청계산입구 양재시민의숲 양재 강남";

	// 인천1
	private String lineI = "국제업무지구 센트럴파크 인천대입구 지식정보단지 테크노파크 캠퍼스타운 동막 동춘 원인재 신연수 선학 문학경기장 인천터미널 예술회관 인천시청 간석오거리 부평삼거리 동수 부평 부평시장 부평구청 갈산 작전 경인교대입구 계산 임학 박촌 귤현 계양";

	// 인천2
	private String lineI2 = "검단오류 왕길 검단사거리 마전 완정 독정 검암 검바위 아시아드경기장 서구청 가정 가정중앙시장 석남 서부여성회관 인천가좌 가재울 주안국가산단 주안 시민공원 석바위시장 인천시청 석천사거리 모래내시장 만수 남동구청 인천대공원 운연";

	// 의정부
	private String lineU = "발곡 회룡 범골 경전철의정부 의정부시청 흥선 의정부중앙 동오 새말 경기도청북부청사 효자 곤제 어룡 송산 탑석";

	// 에버라인 (용인경전철)
	private String lineE = "기흥 강남대 지석 어정 동백 초당 삼가 시청·용인대 명지대 김량장 운동장·송담대 고진 보평 둔전 전대·에버랜드";

	// 우이신설경전철
	private String lineUI = "북한산우이 솔밭공원 4·19민주묘지 가오리 화계 삼양 삼양사거리 솔샘 북한산보국문 정릉 성신여대입구 보문 신설동";

	// 자기부상
	private String lineJ = "인천공항1터미널 장기주차장 합동청사 파라다이스시티 워터파크 용유";

	// 서해
	private String lineW = "소사 소새울 시흥대야 신천 신현 시흥시청 시흥능곡 달미 선부 초지 원곡 원시";

	private TreeMap<String, ArrayList<String>> stationNamesBylineNum = new TreeMap<>();

	private Model() {
		assemble("1", line1);
		assemble("2", line2);
		assemble("3", line3);
		assemble("4", line4);
		assemble("5", line5);
		assemble("6", line6);
		assemble("7", line7);
		assemble("8", line8);
		assemble("9", line9);

		assemble("A", lineA);
		assemble("K", lineK);
		assemble("G", lineG);
		assemble("KK", lineKK);
		assemble("B", lineB);
		assemble("SU", lineSU);
		assemble("S", lineS);
		assemble("I", lineI);
		assemble("I2", lineI2);
		assemble("U", lineU);
		assemble("E", lineE);
		assemble("UI", lineUI);
		assemble("J", lineJ);
		assemble("W", lineW);
	}

	private void assemble(String key, String values) {
		ArrayList<String> stations = new ArrayList<>();
		String[] arr = values.split(" ");
		for(String e : arr) {
			stations.add(e);
		}

		stationNamesBylineNum.put(key, stations);
	}

	public static TreeMap<String, ArrayList<String>> build() {
		return new Model().stationNamesBylineNum;
	}
}
