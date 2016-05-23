import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SerRoutes {


	private static List<Vertex> nodes;
	private static List<Edge> edges;
	private static Climb[][] climb;
	private static Descent[][] descent;
	private static Cruise[][] cruise;
	private static weightLimits[] weight;
	private static FileInputStream fp;
	private static ObjectInputStream in;
	private static ObjectOutputStream out;
	private static FileOutputStream fo;
	private static int pWeight = 3;
	private static Graph graph;
	private static List<Double> distances = new LinkedList<>();
	private static List<Double> test = new ArrayList<>();
	private static List<Double> test2 = new ArrayList<>();
	
	public static void main(String[] args){
		nodes = new LinkedList<>();
		edges = new LinkedList<>();
		
		Path dir = Paths.get("/Users/mortenjaeger/Dropbox/Uni/Datalogi/Java/FF501/Routes/");
		deserialize();
		searchDir(dir);
		System.out.println(test.size());
		System.out.println(test2.size());
	}
	
	public static void searchDir(Path dir){
		
		try {
			DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir);
			for(Path path: dirStream){
				
				if(Files.isDirectory(path)){
					
					searchDir(dir);
				}
				else{
					if(path.toString().endsWith(".txt")){
						readFile(path);
						clearAll();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void clearAll(){
		nodes.clear();
		edges.clear();
		distances.clear();
	}
	
	public static void readFile(Path path){
		String[] name = path.toString().split("/");
		String file = name[name.length-1];
		String[] fName = file.split("txt");
		String dest = "/Users/mortenjaeger/Dropbox/Uni/Datalogi/Java/FF501/"+fName[0]+"ser";
		graph = new Graph(null, null);
		distances(path.toFile());
		graph.setVertexes(nodes);
		graph.setEdges(edges);
		try {
			fo = new FileOutputStream(dest);
			out = new ObjectOutputStream(fo);
			out.writeObject(graph);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fo != null){
				try {
					fo.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void initAll(File file){
		deserialize();
		distances(file);
		
	}
	
	public static void addVertex(String id, String name){
		Vertex vertex = new Vertex(Integer.parseInt(id), Integer.parseInt(name));
		nodes.add(vertex);
	}
	
	public static void addEdge(String id, Vertex source, Vertex dest, double cost){
		Edge e = new Edge(id, source, dest, cost);
		edges.add(e);
	}
	
	public static void distances(File file){
		int count = 1;
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
		Vertex[][] points = new Vertex[count][50];
		
		for(int i = 0; i < count; i++){
			for (int j = 0; j < 50; j++) {
				points[i][j] = new Vertex(i, j);
				nodes.add(points[i][j]);
			}
		}
		
		for(int i = 0; i < count-1; i++){
			double distance = distances.get(i);
			if(i == 0){
				for(int n = 1; n < 50; n++){
					Climb c = climb[n][pWeight];
					if(c.distance < distance){
						double time = (c.time/60)*1000;
						double fuel = (c.fuel/6.2)*3;
						double cost = time+fuel;
						double restDist = distance - c.distance;
						cost = cost + cruise(restDist, n);
						addEdge("", points[i][0], points[i+1][n], cost);
					}
				}
			}
			else{
				for(int j = 1; j < 50; j++){
					if(checkWeight(pWeight, j)){
						Climb prevClimb = climb[j][pWeight];
						double prevCtime = 0;
						double prevCfuel = 0;
						double prevCcost = 0;
						prevCtime = (prevClimb.time/60)*1000;
						prevCfuel = (prevClimb.fuel/6.2)*3;
						prevCcost = prevCtime+prevCfuel;
					
						Descent prevDescent = descent[j][pWeight];
						double prevDtime = 0;
						double prevDfuel = 0;
						double prevDcost = 0;
						prevDtime = (prevDescent.time/60)*1000;
						prevDfuel = (prevDescent.fuel/6.2)*3;
						prevDcost = prevDtime+prevDfuel;
					
						for(int n = 0; n < 50; n++){
							if(i != count-2 && n == 0){
								n = 1;
							}
							if(checkWeight(pWeight, n)){
								if(j == n){
									double cost = cruise(distance, j);
									addEdge("", points[i][j], points[i+1][n], cost);
								}
								else if(j < n){
									Climb c = climb[n][pWeight];
									if(c.distance < distance){
										double time = (c.time/60)*1000;
										double fuel = (c.fuel/6.2)*3;
										double restDist = distance - c.distance;
										double cost = time+fuel;
										cost = (cost - prevCcost) + cruise(restDist, n);
										if (cost < 0) {
											test.add(cost);
										}
										addEdge("", points[i][j], points[i+1][n], cost);
									}
								}
								else if(j > n){
									Descent d = descent[n][pWeight];
									if(d.distance < distance){
										double time = (d.time/60)*1000;
										double fuel = (d.fuel/6.2)*3;
										double cost = time+fuel;
										double restDist = distance - d.distance;
										double temp = cost;
										cost = (prevDcost - cost) + cruise(restDist, j);
										if(cost < 0){
											//System.out.println(cruise(restDist, j));
											test2.add(cost);
										}
										addEdge("", points[i][j], points[i+1][n], cost);
									}
								}
							}
						}
					
					}
				}
			}
		}
		
		graph.setCount(count);
		graph.setPoints(points);
	}
	
	public static double cruise(double dist, int FL){
		Cruise c = cruise[FL][pWeight];
		double time = (dist/c.speed);
		double cost = (time*1000)+(((c.fuelFlow*time)/6.2)*3);
		return cost;
	}
	
	public static boolean checkWeight(double w, int FL){
		weightLimits maxWeight = weight[FL];
		w = 4000*(w+1)+29000;
		if(w <= maxWeight.weight){
			return true;
		}
		else {
			return false;
		}
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
