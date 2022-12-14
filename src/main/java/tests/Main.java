package tests;

import classes.MyGraph;
import classes.Vertex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import br.com.davesmartins.api.Graph;

public class Main {
    static MyGraph createSimpleGraph() {
        MyGraph graph = new MyGraph(false);
        Vertex v1 = graph.addVertex("v1");
        Vertex v2 = graph.addVertex("v2");
        Vertex v3 = graph.addVertex("v3");
        Vertex v4 = graph.addVertex("v4");
        Vertex v5 = graph.addVertex("v5");
        Vertex v6 = graph.addVertex("v6");
        graph.addEdge(v1, v3, 0);
        graph.addEdge(v1, v2, 0);
        graph.addEdge(v1, v4, 0);
        graph.addEdge(v3, v2, 0);
        graph.addEdge(v4, v2, 0);
        graph.addEdge(v2, v6, 0);
        graph.addEdge(v5, v4, 0);
        graph.addEdge(v6, v5, 0);
        return graph;
    }

    static MyGraph createMultiGraph() {
        MyGraph graph = new MyGraph(true);
        Vertex v1 = graph.addVertex("v1");
        Vertex v2 = graph.addVertex("v2");
        Vertex v3 = graph.addVertex("v3");
        Vertex v4 = graph.addVertex("v4");
        Vertex v5 = graph.addVertex("v5");
        Vertex v6 = graph.addVertex("v6");
        graph.addEdge(v1, v3, 100);
        graph.addEdge(v1, v3, 1);
        graph.addEdge(v1, v2, 2);
        graph.addEdge(v1, v2, 3);
        graph.addEdge(v1, v4, 4);
        graph.addEdge(v1, v1, 5);
        graph.addEdge(v3, v2, 6);
        graph.addEdge(v2, v2, 7);
        graph.addEdge(v4, v2, 8);
        graph.addEdge(v2, v6, 9);
        graph.addEdge(v5, v4, 10);
        graph.addEdge(v6, v5, 11);
        return graph;
    }

    public static void main(String[] args) throws IOException {
        MyGraph graph = createMultiGraph();
        System.out.println(graph.getVertexList());

        System.out.println("Lista de Adjacências");
        System.out.println(graph.getAdjList());

        System.out.println("Lista de Adjacências do vértice V1");
        Vertex v1 = graph.getVertex("v1");
        List<Vertex> adjListVertexV1 = graph.getAdjListVertex(v1);
        System.out.println(adjListVertexV1);

        System.out.println("\nMatriz de Adjacências");
        int[][] adjMatrix = graph.getAdjMatrix();
        for (int[] matrix : adjMatrix) {
            IntStream.range(0, adjMatrix.length).forEach(j -> System.out.printf("%4d", matrix[j]));
            System.out.println();
        }

        int[][] incidenceMatrix = graph.getIncidenceMatrix();
        System.out.println("\nMatriz de incidência");
        if (incidenceMatrix == null){
            System.out.println("Grafo não direcionado");
        }
        else {
            for (int[] matrix : incidenceMatrix) {
                IntStream.range(0, incidenceMatrix[0].length).forEach(j -> System.out.printf("%4d", matrix[j]));
                System.out.println();
            }
        }

        int graphOrder = graph.getGraphOrder();
        System.out.println("\nOrdem do grafo");
        System.out.println(graphOrder);

        System.out.println("\nGrau do vértice");
        System.out.println(graph.getVertexDegree(v1));

        System.out.println("\nGrafo é simples?");
        System.out.println(graph.isSimple());

        Vertex v6 = graph.getVertex("v6");

        System.out.println("\nO caminho existe?");
        System.out.println(graph.hasPath(v1, v6));

        System.out.println("\nGrafo é direcionado?");
        System.out.println(graph.isDirected());

        graph.saveGraphFile("grafo1");

        float[][] graphMatrix = graph.getGraphMatrix();
        for (float[] matrix : graphMatrix) {
            IntStream.range(0, graphMatrix[0].length).forEach(j -> System.out.printf("  %.2f", matrix[j]));
            System.out.println();
        }
        graph.dijkstra(0);

        graph.primMST();

        Graph.createFileDotToPng("./src/main/files/graphFiles/grafo1.dot", "./src/main/files/graphImages/grafo1.png");

//        MyGraph novoGrafo = MyGraph.readGraphFile("./src/main/files/graphFiles/graph_1.dot");
//        System.out.println("Lista de Adjacências");
//        System.out.println(novoGrafo.getAdjList());
//        novoGrafo.saveGraphFile("grafo2");
//
//        Graph.createFileDotToPng("./src/main/files/graphFiles/grafo2.dot", "./src/main/files/graphImages/grafo2.png");
    }
}
