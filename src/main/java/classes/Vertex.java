package classes;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    String name;
    List<Edge> adj;

    Vertex(String name) {
        this.name = name;
        this.adj = new ArrayList<Edge>();
    }

    void addAdj(Edge e) {
        adj.add(e);
    }

    public String toString(){
        return name;
    }
}