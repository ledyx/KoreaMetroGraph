import io.github.xeyez.stationgraph.StationGraph;

public class Main {

	public static void main(String[] args) {
		StationGraph.getInstance().get("신도림").forEach(System.out::println);
		System.out.println();
		StationGraph.getInstance().get("신도림", "1").forEach(System.out::println);
	}
}