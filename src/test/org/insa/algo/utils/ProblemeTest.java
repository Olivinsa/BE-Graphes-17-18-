package org.insa.algo.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.algo.shortestpath.Probleme;
import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProblemeTest {
	
	private static Graph graph;

    @BeforeClass
    public static void initAll() throws IOException {
    	String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bordeaux.mapgr";
    	GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        graph = reader.read();
    }
    
    @Test
    public void test() throws IOException {
    	initAll();
    	Probleme P = new Probleme(this.graph, this.graph.get(12243),this.graph.get(3426),this.graph.get(3874),this.graph.get(2672));
    	P.run();
    }
    
}
