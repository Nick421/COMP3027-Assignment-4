import java.util.ArrayList;
import java.util.Scanner;

import org.jgrapht.graph.*;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;

class Main
{
	public static void main(String[] args)
	{
		// read the input data (this is some REALLY ugly code...)
		Scanner reader = new Scanner(System.in);
		// number of projects
		int l = reader.nextInt();
		// max students per project
		int[] p = new int[l];
		for(int i = 0; i < l; ++i)
		{
			p[i] = reader.nextInt();
		}
		// number of teachers
		int m = reader.nextInt();
		// max students per teacher
		int[] s = new int[m];
		for(int i = 0; i < m; ++i)
		{
			s[i] = reader.nextInt();
		}
		// teacher preferences (which projects they are willing to supervise)
		reader.nextLine();
		ArrayList<ArrayList<Integer>> t = new ArrayList<ArrayList<Integer>>(m);
		for(int i = 0; i < m; ++i)
		{
			ArrayList<Integer> row = new ArrayList<Integer>();
			String line = reader.nextLine();
			Scanner lineReader = new Scanner(line);
			while(lineReader.hasNextInt())
			{
				row.add(lineReader.nextInt());
			}
			t.add(row);
		}
		// number of students
		int n = reader.nextInt();
		// student preferences (which projects they are willing to study)
		reader.nextLine();
		ArrayList<ArrayList<Integer>> w = new ArrayList<ArrayList<Integer>>(n);
		for(int i = 0; i < n; ++i)
		{
			ArrayList<Integer> row = new ArrayList<Integer>();
			String line = reader.nextLine();
			Scanner lineReader = new Scanner(line);
			while(lineReader.hasNextInt())
			{
				row.add(lineReader.nextInt());
			}
			w.add(row);
		}
		reader.close();

		// TODO: implement your algorithm (i.e. by building a suitable flow network)

		/* example of building a graph and running ford-fulkerson, using jgrapht
		 * see http://jgrapht.org and http://jgrapht.org/javadoc/
		 * you could use a different library (or implement your own graph etc.)
		 * ... but make sure you appropriately cite any 3rd party libraries you use
		 */
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		graph.addVertex("source");
		graph.addVertex("sink");
		// set source -> students
		for (int i =0; i< n; i++){
			graph.addVertex("s"+i);
			graph.addEdge("source", "s"+i);
			graph.setEdgeWeight(graph.getEdge("source", "s"+i), 1);
		}
		// set p in -> p out
		for (int i =0; i< l; i++){
			graph.addVertex("pin"+i);
			graph.addVertex("pout"+i);
			graph.addEdge("pin"+i, "pout"+i);
			graph.setEdgeWeight(graph.getEdge("pin"+i, "pout"+i), p[i]);
		}
		// set students -> p in 
		for (int i =0; i< w.size(); i++){
			for(int j = 0; j < w.get(i).size(); j++){
				graph.addEdge("s"+i, "pin"+w.get[i].get[j]);
				graph.setEdgeWeight(graph.getEdge("s"+i, "pin"+w.get[i].get[j]), 1);
			}
		}
		
		//set teachers -> sink
		for(int i = 0; i < m; i++){
			graph.addVertex("t"+i);
			graph.addEdge("t"+i, "sink");
			graph.setEdgeWeight(graph.getEdge("t"+i, "sink"), s[i]);
		}
		
		//set p out -> teacher
		for(int i = 0; i < t.size; i++){
			for(int j = 0; j < t.get[i].size; i++){
				graph.addEdge("pout"+t.get[i].get[j], "t"+i);
				graph.setEdgeWeight(graph.getEdge("pout"+t.get[i].get[j], "t"+i), p[t.get[i].get[j]]);
			}
			
		}
		
		EdmondsKarpMFImpl<String, DefaultWeightedEdge> ek = new EdmondsKarpMFImpl<String, DefaultWeightedEdge>(graph);
		double max_flow = ek.calculateMaximumFlow("source", "sink");
		// the variable max_flow is now the value of the maximum flow on this graph,
		// from vertex "a" to vertex "c"

		// TODO: deduce the answer to the original problem, and print it
		String answer = "YES"; // or "NO"
		System.out.println(answer);
	}

}

