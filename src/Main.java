import io.github.xeyez.stationgraph.StationGraph;

public class Main {

	public static void main(String[] args) {
		StationGraph.getInstance().find("신도림").forEach(System.out::println);
		System.out.println();
		StationGraph.getInstance().find("신도림", "1").forEach(System.out::println);
		//StationGraph.getInstance().find("망포", "B").forEach(System.out::println);
	}
}