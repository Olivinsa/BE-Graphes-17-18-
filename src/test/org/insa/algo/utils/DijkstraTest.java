package org.insa.algo.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.insa.algo.ArcInspector;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.GraphStatistics;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraTest {
	
	//tableau des resultats de Bellman
	private static float[][] resultB = new float[6][6];
	
	//tableau des resultats de Dijkstra
	private static float[][] resultD= new float[6][6];
	
	 // Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc x1_x2, x1_x3, x2_x4, x2_x5, x2_x6, x3_x1, x3_x2, x3_x6, x5_x4, x5_x3, x5_x6, x6_x5;

    
    @BeforeClass
    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, "");

        // Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        x1_x2 = Node.linkNodes(nodes[0], nodes[1], 7, speed10, null);
        x1_x3 = Node.linkNodes(nodes[0], nodes[2], 8, speed10, null);
        x2_x4 = Node.linkNodes(nodes[1], nodes[3], 4, speed10, null);
        x2_x5 = Node.linkNodes(nodes[1], nodes[4], 1, speed10, null);
        x2_x6 = Node.linkNodes(nodes[1], nodes[5], 5, speed10, null);
        x3_x1 = Node.linkNodes(nodes[2], nodes[0], 7, speed10, null);
        x3_x2 = Node.linkNodes(nodes[2], nodes[1], 2, speed10, null);
        x3_x6 = Node.linkNodes(nodes[2], nodes[5], 2, speed10, null);
        x5_x4 = Node.linkNodes(nodes[4], nodes[3], 2, speed10, null);
        x5_x3 = Node.linkNodes(nodes[4], nodes[2], 2, speed10, null);
        x5_x6 = Node.linkNodes(nodes[4], nodes[5], 3, speed10, null);
        x6_x5 = Node.linkNodes(nodes[5], nodes[4], 3, speed10, null);



        graph = new Graph("ID", "", Arrays.asList(nodes), null);
                


    }
	@SuppressWarnings("deprecation")
	@Test
	public void test()  throws IOException {
		
		for (int i = 0; i < nodes.length; ++i) {		
	        for (int j = 0; j < nodes.length; ++j) {
	        	
	        	ArcInspector AS = new ArcInspector() {
	                @Override
	                public boolean isAllowed(Arc arc) {
	                    return true;
	                }

	                @Override
	                public double getCost(Arc arc) {
	                    return arc.getLength();
	                }

	                @Override
	                public int getMaximumSpeed() {
	                    return GraphStatistics.NO_MAXIMUM_SPEED;
	                }

	                @Override
	                public Mode getMode() {
	                    return Mode.LENGTH;
	                }

	                @Override
	                public String toString() {
	                    return "Shortest path, all roads allowed";
	                }
	            };
	        	ShortestPathData data = new ShortestPathData(graph, nodes[i], nodes[j], AS );
	        	
	        	BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
	        	ShortestPathSolution s_B = B.run();
	        	try {
	        		Path p_B = s_B.getPath();
	        		resultB[i][j] = p_B.getLength();
	        	}catch (Exception e){
	        		resultB[i][j] = -1;
	        	}
	        	
	        	DijkstraAlgorithm D = new DijkstraAlgorithm(data);
	        	ShortestPathSolution s_D = D.run();
	        	try {
	        		Path p_D = s_D.getPath();
	        		resultD[i][j] = p_D.getLength();
	        	}catch (Exception e){
	        		resultD[i][j] = -1;
	        	}

	        }
		}
		assertEquals(resultB,resultD);
	}

}
