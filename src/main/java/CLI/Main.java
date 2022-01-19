package CLI;

import br.com.davesmartins.api.Graph;
import classes.MyGraph;
import classes.Vertex;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner read = new Scanner(System.in);

        MyGraph graph = null;

        while (true) {

            System.out.println("MENU");
            System.out.println("## 1) Ler grafo manual");
            System.out.println("## 2) Mostrar dados de grafo");
            System.out.println("## 3) Usar grafo de exemplo");
            System.out.println("## 4) Dijkstra");
            System.out.println("## 5) Pesquisar caminho entre 2 vértices");
            System.out.println("## 6) Salvar grafo em arquivo .dot");
            System.out.println("## 7) Salvar grafo em png");
            System.out.println("## 8) Ler grafo de arquivo .dot");
            System.out.println("## 9) Sair");
            int op = read.nextInt();

            if (op == 1) {
                System.out.println("\nO Grafo é direcionado?");
                boolean directed = read.nextBoolean();
                graph = new MyGraph(directed);
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
            }
            else if (op == 2) {

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

            }
            else if (op == 3) {
                System.out.println("## 3.1) Grafo Simples direcionado");
                System.out.println("## 3.2) Grafo Simples não direcionado");
                System.out.println("## 3.3) Multigrafo direcionado");
                System.out.println("## 3.4) Multigrafo não direcionado");

                String opExampleGraph = read.next();

                if (Objects.equals(opExampleGraph, "3.1")){
                    graph = new MyGraph(true);
                    Vertex v1 = graph.addVertex("v1");
                    Vertex v2 = graph.addVertex("v2");
                    Vertex v3 = graph.addVertex("v3");
                    Vertex v4 = graph.addVertex("v4");
                    Vertex v5 = graph.addVertex("v5");
                    Vertex v6 = graph.addVertex("v6");
                    graph.addEdge(v1, v3, 0);
                    graph.addEdge(v1, v2, 10);
                    graph.addEdge(v1, v4, 20);
                    graph.addEdge(v3, v2, 30);
                    graph.addEdge(v4, v2, 40);
                    graph.addEdge(v2, v6, 50);
                    graph.addEdge(v5, v4, 60);
                    graph.addEdge(v6, v5, 70);

                } else if (Objects.equals(opExampleGraph, "3.2")){
                    graph = new MyGraph(false);
                    Vertex v1 = graph.addVertex("v1");
                    Vertex v2 = graph.addVertex("v2");
                    Vertex v3 = graph.addVertex("v3");
                    Vertex v4 = graph.addVertex("v4");
                    Vertex v5 = graph.addVertex("v5");
                    Vertex v6 = graph.addVertex("v6");
                    graph.addEdge(v1, v3, 0);
                    graph.addEdge(v1, v2, 10);
                    graph.addEdge(v1, v4, 20);
                    graph.addEdge(v3, v2, 30);
                    graph.addEdge(v4, v2, 40);
                    graph.addEdge(v2, v6, 50);
                    graph.addEdge(v5, v4, 60);
                    graph.addEdge(v6, v5, 70);

                }else if (Objects.equals(opExampleGraph, "3.3")){
                    graph = new MyGraph(true);
                    Vertex v1 = graph.addVertex("v1");
                    Vertex v2 = graph.addVertex("v2");
                    Vertex v3 = graph.addVertex("v3");
                    Vertex v4 = graph.addVertex("v4");
                    Vertex v5 = graph.addVertex("v5");
                    Vertex v6 = graph.addVertex("v6");
                    graph.addEdge(v1, v3, 0);
                    graph.addEdge(v1, v2, 10);
                    graph.addEdge(v1, v2, 20);
                    graph.addEdge(v1, v4, 30);
                    graph.addEdge(v1, v1, 40);
                    graph.addEdge(v3, v2, 50);
                    graph.addEdge(v2, v2, 60);
                    graph.addEdge(v4, v2, 70);
                    graph.addEdge(v2, v6, 80);
                    graph.addEdge(v5, v4, 90);
                    graph.addEdge(v6, v5, 100);
                    graph.addEdge(v6, v5, 110);

                }else if (Objects.equals(opExampleGraph, "3.4")) {
                    graph = new MyGraph(false);
                    Vertex v1 = graph.addVertex("v1");
                    Vertex v2 = graph.addVertex("v2");
                    Vertex v3 = graph.addVertex("v3");
                    Vertex v4 = graph.addVertex("v4");
                    Vertex v5 = graph.addVertex("v5");
                    Vertex v6 = graph.addVertex("v6");
                    graph.addEdge(v1, v3, 0);
                    graph.addEdge(v1, v2, 10);
                    graph.addEdge(v1, v2, 20);
                    graph.addEdge(v1, v4, 30);
                    graph.addEdge(v1, v1, 40);
                    graph.addEdge(v3, v2, 50);
                    graph.addEdge(v2, v2, 60);
                    graph.addEdge(v4, v2, 70);
                    graph.addEdge(v2, v6, 80);
                    graph.addEdge(v5, v4, 90);
                    graph.addEdge(v6, v5, 100);
                    graph.addEdge(v6, v5, 110);
                }
                assert graph != null;
                System.out.println("Lista de Adjacências");
                System.out.println(graph.getAdjList());
            }
            else if (op == 4) {
                System.out.println("Qual o vértice inicial?\n");
                String originVertex = read.next();
                int originVertexIndex = graph.getVertexList().indexOf(originVertex);
                graph.dijkstra(originVertexIndex);
            }
            else if (op == 5) {
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
            else if (op == 6) {
                System.out.println("Nome do arquivo");
                String filename = read.next();
                assert graph != null;
                graph.saveGraphFile(filename);
            }
            else if (op == 7) {
                System.out.println("Caminho do arquivo:");
                String filepath = read.next();
                Graph.createFileDotToPng(filepath, "./src/main/files/graphFiles/graph.dot");
            }
            else if (op == 8) {
                System.out.println("Qual o caminho para o arquivo?");
                String filepath = read.next();
                graph = MyGraph.readGraphFile(filepath);
            }
            else if (op == 9) {
                break;
            }
        }

    }
    
}
