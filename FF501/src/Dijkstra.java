import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Dijkstra {
	
	private final List<Vertex> nodes;
	private final List<Edge> edges;
	private FileInputStream fp;
	private Vertex[][] test; 
	private Set<Vertex> doneNodes;
	private PQHeap pq;
	private Map<Vertex, Vertex> predecessor;
	
	public Dijkstra(Graph graph){
		this.nodes = new LinkedList<>(graph.vertexes);
		this.edges = new LinkedList<>(graph.edges);
	}
	
	public void run(Vertex start){
		predecessor = new HashMap<>();
		doneNodes = new HashSet<>();
		pq = new PQHeap(nodes.size());
		start.setCost(0);
		pq.insert(start);
		
		
		while(pq.getSize() > 0){
			Vertex vertex = pq.extractMin();
			doneNodes.add(vertex);
			expand(vertex);
		}
		
		/**
		for(Vertex v: nodes){
			if(v != start){
				v.setCost(Double.MAX_VALUE);
				predecessor.put(v, null);
			}
			pq.insert(v);
		}	
		while(amountNodes > 0){
			Vertex u = pq.extractMin();
			System.out.println("Extracted "+u.getId());
			doneNodes.add(u);
			amountNodes--;
			List<Vertex> neighbors = getNeighbor(u);
			for(Vertex v: neighbors){
				System.out.println(getDistance(u, v));
				double alt = u.getCost() + getDistance(u, v);
				if(alt < v.getCost() && v.getCost() > 0){
					v.setCost(alt);
					predecessor.put(v, u);
				}
			}
		}
		*/
		
	}
	
	public void expand(Vertex v){
		List<Vertex> neighbors = getNeighbor(v);
		for(Vertex u: neighbors){
			double alt = getShortest(v) + getDistance(v, u);
			if(getShortest(u) > alt){
				u.setCost(alt);
				predecessor.put(u, v);
				pq.insert(u);
			}
		}
	}
	
	public double getShortest(Vertex v){
		double d = v.getCost();
		if(d < 0){
			return Double.MAX_VALUE;
		}
		else{
			return d;	
		}
	}
	
	public List<Vertex> getNeighbor(Vertex v){
		List<Vertex> neighbors = new LinkedList<>();
		for(Edge e: edges){
			if (e.getSource().equals(v) && !isDone(e.getDestination())) {
				neighbors.add(e.getDestination());
			}
		}
		return neighbors;
	}
	
	public boolean isDone(Vertex v){
		return doneNodes.contains(v);
	}
	
	public List<Vertex> getPath(Vertex end){
		List<Vertex> path = new LinkedList<>();
		Vertex step = end;
		if(predecessor.get(step) == null){
			return null;
		}
		path.add(step);
		while(predecessor.get(step) != null){
			step = predecessor.get(step);
			path.add(step);
		}
		Collections.reverse(path);
		return path;
	}
	

	public double getDistance(Vertex source, Vertex target){
		for(Edge edge : edges){
			if(edge.getSource().equals(source) && edge.getDestination().equals(target)){
				return edge.getCost();
			}
		}
		return -1;
	}
	
	public void initVertex(File f){
		int wayCounter = 0;
		try {
			fp = new FileInputStream(f);
			Scanner sc = new Scanner(fp);
			while(sc.hasNext()){
				String[] line = sc.nextLine().split(";");
				if(line[0].equals("Waypoint")){
					wayCounter++;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test = new Vertex[50][wayCounter];
		
		
	}
}
