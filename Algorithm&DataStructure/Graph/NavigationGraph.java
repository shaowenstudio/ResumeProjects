/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Spring 2016 
//PROJECT:          Navigation App using Graphs (p5)
//FILE:             NavigationGraph.java
//
//Authors:
//Author1: (Name: Shaowen Liu, 	Email: sliu455@wisc.edu, 	
//NetID: sliu455, 		Lecture number: 002)
//Author2: (Name: Yuqi Wei, Email: wei56@wisc.edu, 	
//NetID: wei56, 		Lecture number: 001)
//Author3: (Name: Shiwei Cao, Email: scao46@wisc.edu, 	
//NetID: scao46, 		Lecture number: 001)
//Author4: (Name: John Zhang ,	Email: hzhang473@wisc.edu, 	
//NetID: hzhang473, 	Lecture number: 003)
//
//---------------- OTHER ASSISTANCE CREDITS 
//Skeleton of methods are provided by professor.
//
////////////////////////////80 columns wide //////////////////////////////////

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines the PriorityQueue_P5 for the project
 * It will be used while constructing the graph.
 *
 * @author Liu, Wei, Cao, John
 */
public class NavigationGraph implements GraphADT<Location, Path> {

	// graph in the graph, which contain edges in itself
	private List<GraphNode<Location, Path>> graph 
							= new ArrayList<GraphNode<Location, Path>>();
	String[] edgePropertyNames;
	private int id;
	
	/**
	 * Constructor
	 * 
	 * @param edgePropertyNames
	 *            array of edge property names
	 */
	public NavigationGraph(String[] edgePropertyNames) {
		this.edgePropertyNames = edgePropertyNames;
		id = 0;
	}

	
	/**
	 * Returns a Location object given its name
	 * 
	 * @param name
	 *            name of the location
	 * @return Location object
	 */
	public Location getLocationByName(String name) {
		// if(name.equals( graph.getVertexData().getName() );
		if(name == null)
			throw new IllegalArgumentException();
		
		for(GraphNode<Location, Path> n : graph){
			if(name.equals(n.getVertexData().getName()))
				return n.getVertexData();
		}
		
		return null; //TO: implement correctly. 
	}

	/**
	 * Adds a vertex to the Graph
	 * 
	 * @param vertex
	 *            vertex to be added
	 */
	public void addVertex(Location vertex) {
		if(vertex == null)
			throw new IllegalArgumentException();
		// TO null path and add id in this way
		graph.add(new GraphNode<Location, Path>(vertex, id++)); // 0 means false
	}

	/**
	 * Creates a directed edge from src to dest
	 * 
	 * @param src
	 *            source vertex from where the edge is outgoing
	 * @param dest
	 *            destination vertex where the edge is incoming
	 * @param edge
	 *            edge between src and dest
	 */
	public void addEdge(Location src, Location dest, Path edge) {
		if(src == null || dest == null || edge == null)
			throw new IllegalArgumentException();
		// TO
		// find src location node by looping through the graph vertex
		for(GraphNode<Location, Path> srcLocNode : graph){
			if(src.equals(srcLocNode.getVertexData())){
				srcLocNode.addOutEdge(edge);
				return;
			}
		}
	}

	/**
	 * Getter method for the vertices
	 * 
	 * @return List of vertices of type V
	 */
	public List<Location> getVertices() {
		// TO return all the source vertices name as a list
		List<Location> vertices = new ArrayList<Location>();
		for(GraphNode<Location, Path> n : graph){
			vertices.add(n.getVertexData());
		}
		return vertices;
	}

	/**
	 * Returns edge if there is one from src to dest vertex else null
	 * 
	 * @param src
	 *            Source vertex
	 * @param dest
	 *            Destination vertex
	 * @return Edge of type E from src to dest
	 */
	public Path getEdgeIfExists(Location src, Location dest) {
		if(src == null || dest == null)
			throw new IllegalArgumentException();
		// TO 
		for(GraphNode<Location, Path> n : graph){
			for(Path p : n.getOutEdges()){
			   if(src.equals(p.getSource()) && dest.equals(p.getDestination()))
					return p; // path found, return it
			}
		}
		return null;
	}

	/**
	 * Returns the outgoing edges from a vertex
	 * 
	 * @param src
	 *           Source vertex for which the outgoing edges need to be obtained
	 * @return List of edges of type E
	 */
	public List<Path> getOutEdges(Location src) {
		if(src == null)
			throw new IllegalArgumentException();
		// TO
		// loop through graph, if find scr, return it's outEdges
		for(GraphNode<Location, Path> n : graph){
			if(src.equals(n.getVertexData()))
				return n.getOutEdges();
		}
		// if src not found return null, check when calling
		return new ArrayList<Path>(); // instead of path, or will get NPExcept
	}

	/**
	 * Returns neighbors of a vertex
	 * 
	 * @param vertex
	 *            vertex for which the neighbors are required
	 * @return List of vertices(neighbors) of type V
	 */
	public List<Location> getNeighbors(Location vertex) {
		if(vertex == null)
			throw new IllegalArgumentException();
		// create a new array list to hold neighbors
		List<Location> neighbors = new ArrayList<Location>();
		
		// add all of its children to the list
		for(GraphNode<Location, Path> n : graph){
			// find the node whose location is vertex
			if(vertex.equals(n.getVertexData())){
				for(Path p : n.getOutEdges()){
					neighbors.add(p.getDestination());
				}
			}
		}
		
		// add all of its predecessor to the list
		for(GraphNode<Location, Path> n : graph){
			// find the node whose children contain vertex
			for(Path p : n.getOutEdges()){
				if(vertex.equals(p.getDestination()))
					if(!neighbors.contains(p.getSource())){
						neighbors.add(p.getSource());
						break;
					}
			}
		}
		
		return neighbors;
	}

	/**
	 * Calculate the shortest route from src to dest vertex using
	 * edgePropertyName
	 * 
	 * @param src
	 *            Source vertex from which the shortest route is desired
	 * @param dest
	 *            Destination vertex to which the shortest route is desired
	 * @param edgePropertyName
	 *            edge property by which shortest route has to be calculated
	 * @return List of edges that denote the shortest route by edgePropertyName
	 */
	public List<Path> getShortestRoute(Location src, Location dest, 
										String edgePropertyName) {
		if(src == null || dest == null || edgePropertyName == null ||
				src.equals(dest))
			throw new IllegalArgumentException();
		if(getLocationByName(src.getName()) == null ||
				getLocationByName(dest.getName()) == null)
			throw new IllegalArgumentException();
		// TO

		List<CombineNode> combineNodeList = new ArrayList<CombineNode>();
//		for each verticex v in graph, initilize and add to the graph
		for(GraphNode<Location, Path> curV : graph)
			combineNodeList.add(
					new CombineNode(false, Double.MAX_VALUE, null, curV));
		
		
//		find src in combineNodeList and set it's weight = 0
		for(CombineNode cbN : combineNodeList)
			if(src.equals(cbN.getCurrentNode().getVertexData()))
				cbN.setWeight(0);
		
		PriorityQueue_P5 pq = new PriorityQueue_P5(graph.size());
//		pq.insert(CombineNode(GraphNode cur, GraphNode pre, double weight))
		for(CombineNode cbN : combineNodeList)
			if(src.equals(cbN.getCurrentNode().getVertexData()))
				pq.insert(cbN);
		
		while(!pq.isEmpty()){
//			System.out.println(pq.arrayInfo());
			CombineNode curNode = pq.removeMin();
			curNode.setVisited(true);
//			System.out.println(curNode.getCurrentNode().getVertexData());
		
			// for each successor sucNode adjacent to curNode
			// find all the paths of current node -- curNode
			for(Path p : getOutEdges(curNode.getCurrentNode().getVertexData()))
				// extract its destination
				// then get the CombineNode correspond to it
				for(CombineNode sucNode : combineNodeList)
					if(p.getDestination().equals(
							sucNode.getCurrentNode().getVertexData()))
						if(!sucNode.isVisited()){
							double pathWeight = 0; // weight of that path
							for (int i = 0; i < edgePropertyNames.length; i++){
								if(edgePropertyName.equals(
										edgePropertyNames[i]))
									pathWeight = p.getProperties().get(i);
							}
							// if the total weight can be reduced
							if(sucNode.getWeight() > 
										curNode.getWeight() + pathWeight){
								sucNode.setWeight(
										curNode.getWeight() + pathWeight);
								sucNode.setPredecessor(
										curNode.getCurrentNode());
								if(!pq.contains(sucNode))
									pq.insert(sucNode);
							}
						}
		}

		// add the path to a list and return
		List<Path> retList = new ArrayList<Path>();
		for(CombineNode cNode : combineNodeList)
			if(dest.equals(cNode.getCurrentNode().getVertexData()))
				while(cNode.getPredecessor() != null){
					retList.add(getEdgeIfExists(
									cNode.getPredecessor().getVertexData(),
									cNode.getCurrentNode().getVertexData()) );
					// find the corresponding predecessor graphNode 
					// in CombineNode
					for(CombineNode  n : combineNodeList)
						if(cNode.getPredecessor().getVertexData().equals(
								n.getCurrentNode().getVertexData())){
							cNode = n;
							break;
						}
					
				}
		
		// get the correct order
		List<Path> retListReverse = new ArrayList<Path>(); 
		for (int i = retList.size() - 1; i >= 0; i--)
			retListReverse.add(retList.get(i));
		
		return retListReverse;
	}

	/**
	 * Getter method for edge property names
	 * 
	 * @return array of String that denotes the edge property names
	 */
	public String[] getEdgePropertyNames() {
		// TO
		return this.edgePropertyNames;
	}
	
	/**
	 * Return a string representation of the graph, Display Graph
	 * 
	 * @return String representation of the graph
	 */
	public String toString(){
		String out = "";
		
		for(GraphNode<Location, Path> n : graph){
			for(Path p : n.getOutEdges())
				out += p + ", ";
			if(n.getOutEdges().size() != 0){
				out = out.substring(0, out.length() - 2); // to delete ", "
				out += "\n";
			}
		}
		
		return out;
	}


}
