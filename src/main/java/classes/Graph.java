package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Graph {
    List<Vertex> vertexList;
    List<Edge> edgeList;
    boolean directed;

    public Graph(boolean directed) {
        vertexList = new ArrayList<Vertex>();
        edgeList = new ArrayList<Edge>();
        this.directed = directed;
    }

    public Vertex addVertex(String name) {
        Vertex v = new Vertex(name);
        vertexList.add(v);
        return v;
    }

    public void addEdge(Vertex origin, Vertex destiny) {
        Edge e = new Edge(origin, destiny);
        origin.addAdj(e);
        edgeList.add(e);
        if (!this.directed){
            Edge e_inverted = new Edge(destiny, origin);
            destiny.addAdj(e_inverted);
            edgeList.add(e_inverted);
        }
    }

    public String getAdjList() {
        StringBuilder r = new StringBuilder();
        for (Vertex u : vertexList) {
            r.append(u.name).append(" -> ");
            for (Edge e : u.adj) {
                Vertex v = e.destiny;
                r.append(v.name).append(", ");
            }
            r.append("\n");
        }
        return r.toString();
    }

    public List<Vertex> getAdjListVertex(Vertex v){
        List<Vertex> listAdj = new ArrayList();
        for (Edge e: v.adj) {
            listAdj.add(e.destiny);
        }
        return listAdj;
    }

    public Vertex getVertex(String name){
        for (Vertex v: vertexList) {
            if (Objects.equals(v.name, name)){
                return v;
            }
        }
        return null;
    }

    public int[][] getAdjMatrix(){
        int n_vertex = vertexList.size();
        int[][] adjMatrix = new int[n_vertex][n_vertex];
        for (int i = 0; i < n_vertex; i++) {
            Vertex v = vertexList.get(i);
            List<Vertex> vList = getAdjListVertex(v);
            for (int j = 0; j < n_vertex; j++) {
                Vertex v2 = vertexList.get(j);
                if (vList.contains(v2)){
                    adjMatrix[i][j] = 1;
                }
                else {
                    adjMatrix[i][j] = 0;
                }
            }
        }
        return adjMatrix;
    }

    public int[][] getIncidenceMatrix(){
        if (this.directed) {
            int n_vertex = vertexList.size();
            int n_edges = edgeList.size();
            int[][] incidenceMatrix = new int[n_vertex][n_edges];
            for (int i = 0; i < n_edges; i++) {
                Edge e = edgeList.get(i);
                for (int j = 0; j < n_vertex; j++) {
                    incidenceMatrix[j][i] = 0;
                    Vertex v = vertexList.get(j);
                    if (e.destiny.equals(v)) {
                        incidenceMatrix[j][i] = -1;
                    } else if (e.origin.equals(v)) {
                        incidenceMatrix[j][i] = 1;
                    }
                }
            }
            return incidenceMatrix;
        }
        return null;
    }

    public int getGraphOrder(){
        return vertexList.size();
    }

    public int getVertexDegree(Vertex vertex){
        return getAdjListVertex(vertex).size();
    }
}