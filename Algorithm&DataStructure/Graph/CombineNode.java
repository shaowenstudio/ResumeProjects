/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Spring 2016 
//PROJECT:          Navigation App using Graphs (p5)
//FILE:             CombineNode.java
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
//Persons: No
//
//Online sources: No
////////////////////////////80 columns wide //////////////////////////////////

/**
 * This class defines the PriorityQueue_P5 for the project
 * It will be used while constructing the graph.
 *
 * @author Liu, Wei, Cao, John
 */
class CombineNode {
	boolean visited;
	double weight;
	GraphNode<Location, Path> predecessor;
	GraphNode<Location, Path> currentNode;
	
	/**
	 * Getter method for the vertices
	 * 
	 * @param visited
	 *            	whether the node is visited
	 * @param weight
	 * 				weight of the node
	 * @param predecessor
	 * 				predecessor of the node
	 * @param currentNode
	 *            	current node
	 */
	CombineNode(boolean visited, double weight, 
			GraphNode<Location, Path> predecessor,
			GraphNode<Location, Path> currentNode) {
		this.visited = visited;
		this.weight = weight;
		this.predecessor = predecessor;
		this.currentNode = currentNode;
	}
	
	/**
	 * @return true is this node is visited
	 */
	boolean isVisited() {
		return visited;
	}

	/**
	 * Set this.visited to the parameter
	 * 
	 * @param visited
	 *            whether the node is visited
	 */
	void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * @return the weight of current node
	 */
	double getWeight() {
		return weight;
	}

	/**
	 * Modify the weight of current node
	 * 
	 * @param weight
	 *            the value to assign
	 */
	void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return predecessor of current node
	 */
	GraphNode<Location, Path> getPredecessor() {
		return predecessor;
	}

	/**
	 * Modify the predecessor of current node
	 * 
	 * @param predecessor
	 *            the predecessor need to be assigned
	 */
	void setPredecessor(GraphNode<Location, Path> predecessor) {
		this.predecessor = predecessor;
	}

	/**
	 * @return the current node
	 */
	GraphNode<Location, Path> getCurrentNode() {
		return currentNode;
	}

	/**
	 * Modify the current node 
	 * 
	 * @param currentNode
	 *            the currentNode need to be assigned
	 */
	void setCurrentNode(GraphNode<Location, Path> currentNode) {
		this.currentNode = currentNode;
	}
	
}
