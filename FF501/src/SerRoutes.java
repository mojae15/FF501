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
	
	public static void main(String[] args){
		nodes = new LinkedList<>();
		edges = new LinkedList<>();
		
		Path dir = Paths.get("/Users/mortenjaeger/Dropbox/Uni/Datalogi/Java/FF501/Routes/");
		deserialize();
		searchDir(dir);
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
						System.out.println("Checking file "+path);
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
		System.out.println(file);
		String[] fName = file.split("txt");
		String dest = "/Users/mortenjaeger/Dropbox/Uni/Datalogi/Java/FF501/"+fName[0]+"ser";
		System.out.println("Destination file: "+dest);
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
			for(int j = 0; j < 50; j++){
				if(i != 0 && j == 0){
					j = 1;
				}
				for(int n = 0; n < 50; n++){
					if(i != count-2 && n == 0){
						n = 1;
					}
					if(n == j){
						double time = distance/cruise[j][pWeight].speed;
						double cost = (time*1000)+((cruise[j][pWeight].fuelFlow*time)*3);
						addEdge("", points[i][j], points[i+1][n], cost);
					}
					else if(n < j){
						Descent d = descent[j][pWeight];
						if(d.distance < distance){
							double time = d.time*1000;
							double cost = time+(d.fuel*3);
							addEdge("", points[i][j], points[i+1][n], cost);
							
						}
					}
					else if(n > j){
						Climb c = climb[j][pWeight];
						if(c.distance < distance){
							double time = c.time*1000;
							double cost = time+(c.fuel*3);
							addEdge("", points[i][j], points[i+1][n], cost);
							
						}
					}
					
				}
			}			
		}
		graph.setCount(count);
		graph.setPoints(points);
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
