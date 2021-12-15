package classes;

public class Edge {
    protected Vertex origin;
    protected Vertex destiny;
    protected String value;

    Edge(Vertex origin, Vertex destiny, String value) {
        this.origin = origin;
        this.destiny = destiny;
        this.value = value;
    }
}
