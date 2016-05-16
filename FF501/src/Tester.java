import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Morten Jaeger on 13/05/2016
 * Email: mojae15@student.sdu.dk
 */
public class Tester {

	private static List<Vertex> nodes;
	private static List<Edge> edges;
	private static Climb[][] climb;
	private static Descent[][] descent;
	private static Cruise[][] cruise;
	private static weightLimits[] weight;
	private static FileInputStream fp;
	private static ObjectInputStream in;
	private static Vertex[][] points;
	private static int count;
	private static Graph graph;
	private static int pWeight = 1;
	private static List<Double> distances = new LinkedList<>();
	
	public static void main(String[] args) {
		
		nodes = new LinkedList<>();
		edges = new LinkedList<>();
		File file = new File("EBBR-VABB-40.ser");
		//initAll(file);
		
		//graph = new Graph(nodes, edges);
		
		try {
			fp = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fp);
			graph = (Graph) in.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		Dijkstra dijkstra = new Dijkstra(graph);
		points = graph.getPoints();
		count = graph.getCount();
		Vertex start = points[0][0];
		Vertex end = points[count-1][0];
		dijkstra.run(start);
		List<Vertex> path = dijkstra.getPath(end);
		
		if (path != null && path.size() > 0) {
			for(Vertex v: path){
				System.out.println("Point "+v.row+" FL "+v.col*10);
			}
		}
	}

}