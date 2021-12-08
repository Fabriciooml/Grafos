package classes;

public class Edge {
    protected Vertex origin;
    protected Vertex destiny;
    protected int value;

    Edge(Vertex origin, Vertex destiny, int value) {
        this.origin = origin;
        this.destiny = destiny;
        this.value = value;
    }
}
