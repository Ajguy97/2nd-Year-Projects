package PathFinding2;

public class aNode {
	
	public boolean isWall;
	public int x = 0;
	public int y = 0;
	
	public int gcost;
	public int hcost;
	
	
	public aNode parent;
	
	public aNode(boolean map[][], int x , int y){
		this.x = x;
		this.y = y;
		if(map[x][y]){
			isWall = true;
		}
	}
	
	public int fcost(){
		int fcost = gcost + hcost;
		
		return fcost;
	}
}
