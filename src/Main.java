import io.github.xeyez.stationgraph.AbstractGraph;
import io.github.xeyez.stationgraph.StationGraph;
import io.github.xeyez.stationgraph.StationGraphVO;

public class Main {

	public static void main(String[] args) {
		StationGraph graph = StationGraph.getInstance();
		
		for(AbstractGraph<StationGraphVO>.Edge obj : graph.getEdges("신도림")) {
			System.out.println(obj);
		}
	}
}