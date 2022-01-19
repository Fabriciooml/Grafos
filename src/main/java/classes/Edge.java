package classes;

public class Edge {
    protected Vertex origin;
    protected Vertex destiny;
    protected float value;

    Edge(Vertex origin, Vertex destiny, float value) {
        this.origin = origin;
        this.destiny = destiny;
        this.value = value;
    }
}
