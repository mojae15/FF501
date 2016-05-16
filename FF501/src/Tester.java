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
	private static int pWeight = 1;
	private static List<Double> distances = new LinkedList<>();
	
	public static void main(String[] args) {
		
		nodes = new LinkedList<>();
		edges = new LinkedList<>();
		File file = new File("EBBR-ESSA-138.txt");
		initAll(file);

		Graph graph = new Graph(nodes, edges);
		Dijkstra dijkstra = new Dijkstra(graph);
		Vertex start = points[0][0];
		Vertex end = points[count-1][0];
		dijkstra.run(start);
		List<Vertex> path = dijkstra.getPath(end);
		
		if (path != null && path.size() > 0) {
			for(Vertex v: path){
				System.out.println("Point "+v.row+" FL "+v.col*100);
			}
		}
	}

	public static void addVertex(String id, String name){
		Vertex vertex = new Vertex(Integer.parseInt(id), Integer.parseInt(name));
		nodes.add(vertex);
	}
	
	public static void addEdge(String id, Vertex source, Vertex dest, double cost){
		Edge e = new Edge(id, source, dest, cost);
		edges.add(e);
	}
	
	public static void initAll(File file){
		deserialize();
		distances(file);
		initVertex();
		createEdges();
	}
	
	public static void initVertex(){
		for(int i = 0; i < count; i++){
			for (int j = 0; j < 50; j++) {
				points[i][j] = new Vertex(i, j);
				nodes.add(points[i][j]);
			}
		}
	}
	
	public static void createEdges(){
		for(int i = 0; i < count-1; i++){
			double distance = distances.get(i);
			for(int j = 0; j < 50; j++){
				for(int n = 0; n < 50; n++){
					if(n == j){
						double cost = (cruise[j][pWeight].fuelFlow*distance)*3;
						addEdge("", points[i][j], points[i+1][n], cost);
					}
					else if(n < j){
						Descent d = descent[j][pWeight];
						if(d.distance < distance){
							double cost = d.time*1000;
							//double cost = (d.fuel*distance)*3;
							addEdge("", points[i][j], points[i+1][n], cost);
							
						}
					}
					else if(n > j){
						Climb c = climb[j][pWeight];
						if(c.distance < distance){
							double cost = c.time*1000;
							//double cost = (c.fuel*distance)*3;
							addEdge("", points[i][j], points[i+1][n], cost);
							
						}
					}
					
				}
			}			
		}
	}
	
	public static void distances(File file){
		count = 1;
		try {
			fp = new FileInputStream(file);
			Scanner sc = new Scanner(fp);
			while(sc.hasNext()){
				String[] line = sc.nextLine().split(" ");
				double d = Double.parseDouble(line[0]);
				distances.add(d);
				count++;
				
			}
			
			fp.close();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		points = new Vertex[count][50];
	}
	
	public static void deserialize(){
		deserializeClimb();
		deserializeCruise();
		deserializeDescent();
		deserializeWeight();
	}
	
	public static void deserializeClimb(){
		try {
			fp = new FileInputStream("climb.ser");
			in = new ObjectInputStream(fp);
			climb = (Climb[][])in.readObject();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			if (fp != null){
				try {
					fp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void deserializeWeight(){
		try {
			fp = new FileInputStream("weightlimits.ser");
			in = new ObjectInputStream(fp);
			weight = (weightLimits[])in.readObject();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			if (fp != null){
				try {
					fp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void deserializeCruise(){
		try {
			fp = new FileInputStream("cruise.ser");
			in = new ObjectInputStream(fp);
			cruise = (Cruise[][])in.readObject();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			if (fp != null){
				try {
					fp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void deserializeDescent(){
		try {
			fp = new FileInputStream("descent.ser");
			in = new ObjectInputStream(fp);
			descent = (Descent[][])in.readObject();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			if (fp != null){
				try {
					fp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
