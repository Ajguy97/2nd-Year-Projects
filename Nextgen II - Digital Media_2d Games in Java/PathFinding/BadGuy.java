package PathFinding2;


import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Stack;

public class BadGuy {
	public ArrayList<aNode> openList1 = new ArrayList<aNode>();
	public ArrayList<aNode> closedList1 = new ArrayList<aNode>();

	public Stack<aNode> path = new Stack<aNode>();
	Image myImage;
	int x=0,y=0;
	boolean hasPath=false;

	public BadGuy( Image i ) {
		myImage=i;
		x = 30;
		y = 10;
	}
	
	public void reCalcPath(boolean map[][],int targx, int targy) {
		
		ArrayList<aNode> openList = new ArrayList<aNode>();
		ArrayList<aNode> closedList = new ArrayList<aNode>();
		
		aNode startNode = new aNode(map,x,y);
		aNode targetNode = new aNode(map,targx,targy);
		
		
		openList.add(startNode);
		openList1.add(startNode);
		
		
		while(!openList.isEmpty()){
			aNode currentNode = openList.get(0);
			System.out.println("current node : "+ currentNode.x + ","+currentNode.y);
			for(int i = 1 ; i < openList.size();i++){
			//	System.out.println("Open List : F Cost of " + "Node" + i + " = "  + openList.get(i).fcost());
				if(openList.get(i).fcost() < currentNode.fcost() || openList.get(i).fcost() == currentNode.fcost() && openList.get(i).hcost < currentNode.hcost){
					currentNode = openList.get(i);
					System.out.println("current node is now : "+ currentNode.x + ","+currentNode.y + " with f cost of " + currentNode.fcost() + " and parent = Node " + currentNode.parent.x +","+currentNode.parent.y);
					//current = node in open with the lowest f cost 
					
				}
			}
			// remove current from open 
			// add current to close
			openList.remove(currentNode);
			closedList.add(currentNode);
			closedList1.add(currentNode);
			/*for(int i = 1 ; i < closedList.size();i++){
			System.out.println("Closed list :  Node  " + closedList.get(i).x +","+closedList.get(i).y);
			}*/
			
			//if current is target node path found
			if((currentNode.x == targetNode.x)&&(currentNode.y == targetNode.y)){
				System.out.println("Path found");
				setPath(startNode,closedList.get(closedList.size()-1));
				return;
			}
			
			// for each neighbour for current node
			for(aNode neighbour : getNeighbours(map,currentNode) ){
				//if neighbour is a wall or neighbour is in closed skip to next neighbour
				if(neighbour.isWall == true || closedList.contains(neighbour)){
					
					continue;
				}
				
				System.out.println("Checking neighbour : " + neighbour.x + "," + neighbour.y +  " of current node : "+ currentNode.x + ","+currentNode.y);
				
				
				//if new path to neighbour is shorter or neighbour is not in open
				
				//set f cost of neighbour
				//set parent of neighbour to current
				
				// if neighbour is not in open
				//add neighbour to open
				int newMoveCostToNeighbour = currentNode.gcost + getDistance(currentNode, neighbour);
				if(newMoveCostToNeighbour < neighbour.gcost || !openList.contains(neighbour)){
					neighbour.gcost = newMoveCostToNeighbour;
					neighbour.hcost = getDistance(neighbour,targetNode);
					neighbour.parent = currentNode;
					
					if(!openList.contains(neighbour)){
						openList.add(neighbour);
						openList1.add(neighbour);
					}
				}
			}
			
		}
		
		
	}
	
	
	public void setPath( aNode startNode, aNode endNode){
		
		System.out.println("start Node = " + startNode.x + "," + startNode.y);
		System.out.println("end Node = " + endNode.x + "," + endNode.y + "Parent = " + endNode.parent);
		
		aNode currentNode = endNode;
		
		while (currentNode != startNode){
			path.push(currentNode);
			currentNode = currentNode.parent;
		}	
		hasPath = true;
	}
	
	
	public int getDistance(aNode a, aNode b){
		int dstX = Math.abs(a.x - b.x);
		int dstY = Math.abs(a.y - b.y);
		
		if ( dstX > dstY ){
			return 14*dstY + 10*(dstX - dstY);
		}
			else{
				return 14*dstX + 10*(dstY - dstX);
			}
		
	}
	
	public ArrayList<aNode> getNeighbours(boolean map[][], aNode node){
		ArrayList<aNode> neighbours = new ArrayList<aNode>();
		
		for(int x = -1; x<=1;x++){
			for(int y = -1; y<=1;y++){
				if(x == 0 && y ==0 ){
					continue;
				}
				
				int checkX = node.x+x;
				int checkY = node.y+y;
				
				if(checkX >= 0 && checkX < 40 && checkY >= 0 && checkY < 40 ){
					neighbours.add(new aNode(map,checkX,checkY));
				}
				
				
				
			}
		}
		
		return neighbours;
	}
	
	public void move(boolean map[][]) {
		if (hasPath) {
			// TO DO: follow A* path, if we have one defined
			aNode next = (aNode)path.pop();
			int nextX = next.x;
			int nextY = next.y;
			
			if(path.isEmpty()){
				hasPath = false;
			}
			
			x = nextX;
			y = nextY;
		}
		
		else{
			System.out.println("I have no path.");
		}
		/*
		int newx=x, newy=y;
		if (targx<x)
			newx--;
		else if (targx>x)
			newx++;
		if (targy<y)
			newy--;
		else if (targy>y)
			newy++;
		if (!map[newx][newy]) {
			x=newx;
			y=newy;
		}*/
	}
	
	public void paint(Graphics g) {
		g.drawImage(myImage, x*20, y*20, null);
	}
	
}

