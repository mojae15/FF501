/**
 * Created by Morten Jaeger on 09/05/2016
 * Email: mojae15@student.sdu.dk
 */
public class Vertex {
	public int row;
	public int col;
	public double cost;
	
	public Vertex(int row, int col){
		this.row = row;
		this.col = col;
		this.cost = -1;
	}

	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public void setCost(double cost){
		this.cost = cost;
	}
	
	public double getCost(){
		return cost;
	}
}

