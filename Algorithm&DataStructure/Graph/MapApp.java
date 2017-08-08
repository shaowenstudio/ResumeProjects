/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Spring 2016 
//PROJECT:          Navigation App using Graphs (p5)
//FILE:             MapApp.java
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
//Mainly provided by professor, createNavigationGraphFromMapFile() is 
// write by our group
//
////////////////////////////80 columns wide //////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Driver class that reads/parses the input file and creates NavigationGraph
 * object.
 * 
 * @author CS367
 *
 */
public class MapApp {

	private NavigationGraph graphObject;

	/**
	 * Constructs a MapApp object
	 * 
	 * @param graph
	 *            NaviagtionGraph object
	 */
	public MapApp(NavigationGraph graph) {
		this.graphObject = graph;
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java MapApp <pathToGraphFile>");
			System.exit(1);
		}

		// read the filename from command line argument
		String locationFileName = args[0];
		try {
			NavigationGraph graph = createNavigationGraphFromMapFile(
					locationFileName);
			MapApp appInstance = new MapApp(graph);
			appInstance.startService();

		} catch (FileNotFoundException e) {
			System.out.println("GRAPH FILE: " + locationFileName + 
					" was not found.");
			System.exit(1);
		} catch (InvalidFileException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}

	}

	/**
	 *Displays options to user about the various operations on the loaded graph
	 */
	public void startService() {

		System.out.println("Navigation App");
		Scanner sc = new Scanner(System.in);

		int choice = 0;
		do {
			System.out.println();
			System.out.println("1. List all locations");
			System.out.println("2. Display Graph");
			System.out.println("3. Display Outgoing Edges");
			System.out.println("4. Display Shortest Route");
			System.out.println("5. Quit");
			System.out.print("Enter your choice: ");

			while (!sc.hasNextInt()) {
				sc.next();
				System.out.println("Please select a valid option: ");
			}
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.println(graphObject.getVertices());
				break;
			case 2:
				System.out.println(graphObject.toString());
				break;
			case 3: {
				System.out.println("Enter source location name: ");
				String srcName = sc.next();
				Location src = graphObject.getLocationByName(srcName);

				if (src == null) {
					System.out.println(srcName + " is not a valid Location");
					break;
				}

				List<Path> outEdges = graphObject.getOutEdges(src);
				System.out.println("Outgoing edges for " + src + ": ");
				for (Path path : outEdges) {
					System.out.println(path);
				}
			}
				break;

			case 4:
				System.out.println("Enter source location name: ");
				String srcName = sc.next();
				Location src = graphObject.getLocationByName(srcName);

				System.out.println("Enter destination location name: ");
				String destName = sc.next();
				Location dest = graphObject.getLocationByName(destName);

				if (src == null || dest == null) {
					System.out.println(srcName + " and/or " + destName + 
							" are not valid Locations in the graph");
					break;
				}

				if (src == dest) {
					System.out.println(srcName + " and " + destName + 
							" correspond to the same Location");
					break;
				}

				System.out.println("Edge properties: ");
				// List Edge Property Names
				String[] propertyNames = graphObject.getEdgePropertyNames();
				for (int i = 0; i < propertyNames.length; i++) {
					System.out.println("\t" + (i + 1) + ": " + 
											propertyNames[i]);
				}
				System.out.println(
						"Select property to compute shortest route on: ");
				int selectedPropertyIndex = sc.nextInt() - 1;

				if (selectedPropertyIndex >= propertyNames.length) {
					System.out.println("Invalid option chosen: " + 
									(selectedPropertyIndex + 1));
					break;
				}

				String selectedPropertyName = 
						propertyNames[selectedPropertyIndex];
				List<Path> shortestRoute = 
						graphObject.getShortestRoute(
								src, dest, selectedPropertyName);
				for(Path path : shortestRoute) {
					System.out.print(path.displayPathWithProperty(
											selectedPropertyIndex)+", ");
				}
				if(shortestRoute.size()==0) {
					System.out.print("No route exists");
				}
				System.out.println();

				break;

			case 5:
				break;

			default:
				System.out.println("Please select a valid option: ");
				break;

			}
		} while (choice != 5);
		sc.close();
	}

	/**
	 * Reads and parses the input file passed as argument create a
	 * NavigationGraph object. The edge property names required for
	 * the constructor can be got from the first line of the file
	 * by ignoring the first 2 columns - source, destination. 
	 * Use the graph object to add vertices and edges as
	 * you read the input file.
	 * 
	 * @param graphFilepath
	 *            path to the input file
	 * @return NavigationGraph object
	 * @throws FileNotFoundException
	 *             if graphFilepath is not found
	 * @throws InvalidFileException
	 *             if header line in the file has < 3 columns or 
	 *             if any line that describes an edge has different 
	 *              number of properties 
	 *             	than as described in the header or 
	 *             if any property value is not numeric 
	 */

	public static NavigationGraph createNavigationGraphFromMapFile(
			String graphFilepath) 
					throws FileNotFoundException, InvalidFileException{
			// TO: read/parse the input file graphFilepath and create
			// NavigationGraph with vertices and edges
//		read source from file
		List<String> lines = new ArrayList<String>(); // hold the raw files
		List<String> sours = new ArrayList<String>(); // hold precise elements
		List<String> dests = new ArrayList<String>();
		List<Double> time = new ArrayList<Double>();
		List<Double> costs = new ArrayList<Double>();
		// may generate exception
		Scanner scnr = new Scanner(new File(graphFilepath));
		
		String firstLine = scnr.nextLine(); // title line, useless
		while(scnr.hasNextLine()){
			lines.add(scnr.nextLine());
		}
		scnr.close();
		
		for(String s : lines){
			String[] tokens = s.split(" ");
			sours.add(tokens[0].toLowerCase());
			dests.add(tokens[1].toLowerCase());
			time.add(Double.parseDouble(tokens[2]));
			costs.add(Double.parseDouble(tokens[3]));
		}
		
//		create the name of proprity names
		String [] proprities = firstLine.split(" ");
		String[] edgePropertyNames = new String[proprities.length - 2];
		for (int i = 2; i < proprities.length; i++) {
			edgePropertyNames[i-2] = proprities[i];
		}
				
// 		create a null graph
		NavigationGraph graph = new NavigationGraph(edgePropertyNames);
		
//		add all the location to graph without duplication, option 1
		for (int i = 0; i < sours.size(); i++) {
			Location curSrc = graph.getLocationByName(sours.get(i));
			Location curDest = graph.getLocationByName(dests.get(i));
			if(curSrc == null) // no such vertex in graph
				graph.addVertex(new Location(sours.get(i)));
			if(curDest == null) // no such vertex in graph
				graph.addVertex(new Location(dests.get(i)));
		}
		
//		add edges to the graph, connect vertex
//		loop through file, add every path to the src location node
		for (int i = 0; i < sours.size(); i++) {
			Location curSrc = graph.getLocationByName(sours.get(i));
			Location curDest = graph.getLocationByName(dests.get(i));
			// create new path
			List<Double> l = new ArrayList<Double>();
			l.add(time.get(i));
			l.add(costs.get(i));
			Path newPath = new Path(curSrc, curDest, l); 
			graph.addEdge(curSrc, curDest, newPath);
		}
		
		return graph;
	}

}
