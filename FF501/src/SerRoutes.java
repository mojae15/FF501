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
	private static int pWeight = 1;
	private static Graph graph;
	private static List<Double> distances = new LinkedList<>();
	private static List<Double> test = new ArrayList<>();
	
	public static void main(String[] args){
		nodes = new LinkedList<>();
		edges = new LinkedList<>();
		
		Path dir = Paths.get("/Users/mortenjaeger/Dropbox/Uni/Datalogi/Java/FF501/Routes/");
		deserialize();
		searchDir(dir);
		System.out.println(test.size());
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
		
		Climb a = climb[1][pWeight];
		double cTime = a.time;
		
		Descent b = descent[49][pWeight];
		double dTime = b.time;
		
		for(int i = 0; i < count-1; i++){
			double distance = distances.get(i);
			for(int j = 0; j < 50; j++){
				if(i != 0 && j == 0){
					j = 1;
				}
				
				Climb prevC = climb[j][pWeight];
				double prevCtime = (prevC.time/60)*1000;
				double fuelC = (prevC.fuel/6.2)*3;
				double prevCcost = prevCtime+fuelC;
				
				if(prevC.distance < distance){
					double tempDist = distance - prevC.distance; 
					prevCcost = prevCcost + cruise(tempDist, j);
				}
				
				Descent prevD = descent[j][pWeight];
				double prevDtime = (prevD.time/60)*1000;
				double fuelD = (prevD.fuel/6.2)*3;
				double prevDcost = prevDtime+fuelD;
				
				if(prevD.distance < distance){
					double tempDist = distance - prevD.distance; 
					prevDcost = prevDcost + cruise(tempDist, j);
				}
				
				for(int n = 0; n < 50; n++){
					if(i != count-2 && n == 0){
						n = 1;
					}
					
					if(n == j){
						Cruise c = cruise[j][pWeight];
						if(c.fuelFlow > 0){
							double time = distance/c.speed;
							double cost = (time*1000)+(((c.fuelFlow*time)/6.2)*3);
							addEdge("", points[i][j], points[i+1][n], cost);
						}
					}
					else if(n < j){
						Descent d = descent[n][pWeight];
						//if(d.fuel > 0){
							if(d.distance < distance){
								double time = (dTime/60)*1000;
								double fuel = (d.fuel/6.2)*3;
								double cost = time+fuel;
								double restDist = distance - d.distance;
								cost = (cost + cruise(restDist, j))-prevDcost;
								addEdge("", points[i][j], points[i+1][n], cost);
							}
						//}
					}
					else if(n > j){
						Climb c = climb[n][pWeight];
						//if( c.fuel > 0){
							if(c.distance < distance){
								double time = (cTime/60)*1000;
								double fuel = (c.fuel/6.2)*3;
								double cost = time+fuel;
								double restDist = distance - c.distance;
								cost = prevCcost - (cost + cruise(restDist, n));
								addEdge("", points[i][j], points[i+1][n], cost);
							}
						//}
					}
					
				}
			}			
		}
		graph.setCount(count);
		graph.setPoints(points);
	}
	
	public static double cruise(double dist, int FL){
		Cruise c = cruise[FL][pWeight];
		if(c.fuelFlow > 0){
			double time = dist/c.speed;
			double cost = (time*1000)+(((c.fuelFlow*time)/6.2)*3);
			return cost;
		}
		return 0;
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
