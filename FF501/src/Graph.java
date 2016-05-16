import java.util.List;

/**
 * Created by Morten Jaeger on 09/05/2016
 * Email: mojae15@student.sdu.dk
 */
public class Graph {

	public List<Vertex> vertexes;
	public List<Edge> edges;
	
	public Graph(List<Vertex> vertexes, List<Edge> edges){
		this.vertexes = vertexes;
		this.edges = edges;
	}
	
	public List<Vertex> getVertexes(){
		return vertexes;
	}
	
	public List<Edge> getEdges(){
		return edges;
	}
}
