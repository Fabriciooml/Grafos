package CLI;

import classes.Graph;
import classes.Vertex;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        Scanner read = new Scanner(System.in);

        Graph graph = null;

        while (true) {

            System.out.println("MENU");
            System.out.println("## 1) Ler grafo manual");
            System.out.println("## 2) Mostrar dados de grafo");
            System.out.println("## 3) Usar grafo de exemplo");
            System.out.println("## 4) Pesquisar caminho entre 2 vértices");
            System.out.println("## 5) sair");
            int op = read.nextInt();

            if (op == 1) {
                System.out.println("\nO Grafo é direcionado?");
                boolean directed = read.nextBoolean();
                graph = new Graph(directed);
                System.out.println("Número de vértices:");
                int nVertexes = read.nextInt();
                for (int i = 0; i < nVertexes; i++) {
                    System.out.println("Nome da vértice:");
                    String vertexName = read.next();
                    graph.addVertex(vertexName);
                }
                System.out.println("Número de arestas:");
                int nEdges = read.nextInt();
                for (int i = 0; i < nEdges; i++) {
                    System.out.println("Nome da primeira vértice");
                    String v1 = read.next();
                    System.out.println("Nome da segunda vértice");
                    String v2 = read.next();
                    System.out.println("Valor da aresta");
                    int value = read.nextInt();
                    Vertex vertex1 = graph.getVertex(v1);
                    Vertex vertex2 = graph.getVertex(v2);
                    graph.addEdge(vertex1, vertex2, value);
                }
            } else if (op == 2) {

                assert graph != null;
                System.out.println("Lista de Adjacências");
                System.out.println(graph.getAdjList());

                for (Vertex v: graph.getVertexList()) {
                    System.out.println("\nGrau do vértice " + v);
                    System.out.println(graph.getVertexDegree(v));
                    System.out.println("Lista de Adjacências do vértice " + v);
                    List<Vertex> adjListVertex = graph.getAdjListVertex(v);
                    System.out.println(adjListVertex);
                }

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

                System.out.println("\nGrafo é simples?");
                System.out.println(graph.isSimple());

                System.out.println("\nGrafo é direcionado?");
                System.out.println(graph.isDirected());

            } else if(op == 3){
                System.out.println("opção 3");
            }
            else if (op == 4) {
                assert graph != null;
                System.out.println("Vértice de origem");
                String origin = read.next();
                Vertex originVertex = graph.getVertex(origin);
                System.out.println("Vértice de destino");
                String destiny = read.next();
                Vertex destinyVertex = graph.getVertex(destiny);
                System.out.println("\nO caminho existe?");
                System.out.println(graph.hasPath(originVertex, destinyVertex));

            }
            else if (op == 5) {
                break;
            }
        }

    }
    
}