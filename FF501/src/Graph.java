import java.io.Serializable;
import java.util.List;

/**
 * Created by Morten Jaeger on 09/05/2016
 * Email: mojae15@student.sdu.dk
 */
public class Graph implements Serializable{

	public List<Vertex> vertexes;
	public List<Edge> edges;
	public Vertex[][] points;
	public int count;
	
	public Graph(List<Vertex> vertexes, List<Edge> edges){
		this.vertexes = vertexes;
		this.edges = edges;
	}
	
	public List<Vertex> getVertexes(){
		return vertexes;
	}
	
	public void setVertexes(List<Vertex> vertexes){
		this.vertexes = vertexes;
	}
	
	public List<Edge> getEdges(){
		return edges;
	}
	
	public void setEdges(List<Edge> edges){
		this.edges = edges;
	}
	
	public Vertex[][] getPoints(){
		return points;
	}
	
	public void setPoints(Vertex[][] points){
		this.points = points;
	}
	
	public int getCount(){
		return count;
	}
	
	public void setCount(int count){
		this.count = count;
	}
}
