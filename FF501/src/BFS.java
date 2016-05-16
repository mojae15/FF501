import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BFS {
	private final List<Vertex> nodes;
	private final List<Edge> edges;
	private Map<Vertex, Vertex> parent;
	private Queue<Vertex> queue;
	private Set<Vertex> doneNodes;
	
	public BFS(Graph graph){
		this.nodes = graph.vertexes;
		this.edges = graph.edges;
	}
	
	//TODO - Make it work
	
	public void run(Vertex start){
		queue = new LinkedList<>();
		
		for(Vertex v: nodes){
			v.setCost(-1);
			parent.put(v, null);
		}
		
		start.setCost(0);
		queue.add(start);
		
		while(!queue.isEmpty()){
			
			Vertex current = queue.poll();
			List<Vertex> neighbor = getNeighbor(current);
			for(Vertex n: neighbor){
				if(n.getCost() > 0){
					double distance = getDistance(current, n);
					if(distance > 0)
						n.setCost(current.getCost()+distance);
						parent.put(n, current);
						queue.add(n);
				}
			}
			
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
	
	public double getDistance(Vertex source, Vertex target){
		for(Edge edge : edges){
			if((edge.getSource().equals(source) && edge.getDestination().equals(target))
					|| (edge.getSource().equals(target) && edge.getDestination().equals(target))){
				return edge.getCost();
			}
		}
		return -1;
	}
}
