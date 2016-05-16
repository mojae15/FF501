/**
 * Created by Morten Jaeger on 09/05/2016
 * Email: mojae15@student.sdu.dk
 */
public class Edge {

	public String id;
	public Vertex source;
	public Vertex destination;
	public double cost;
	
	public Edge(String id, Vertex source, Vertex destination, double cost){
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.cost = cost;
	}

	public String getId() {
		return id;
	}

	public Vertex getSource() {
		return source;
	}

	public Vertex getDestination() {
		return destination;
	}

	public double getCost() {
		return cost;
	}

}
