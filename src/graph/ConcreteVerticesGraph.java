package graph;

import java.util.*;

/**
 * @author Super-Tang
 */
public class ConcreteVerticesGraph<L> implements graph.Graph<L> {

    private final HashMap<L, Vertex<L>>vertices = new HashMap<>();

    @Override
    public int hashCode() {
        return Objects.hash(vertices);
    }

    @Override
    public boolean equals(Object o) {
        return (o == this) || ((o instanceof ConcreteVerticesGraph) && vertices.equals(((ConcreteVerticesGraph) o).vertices));
    }


    @Override
    public boolean add(L vertex) {
        if(vertices.keySet().contains(vertex)){
            return false;
        }
        Vertex<L> newVertex = new Vertex<>(vertex);
        vertices.put(vertex,newVertex);
        return true;
    }


    @Override
    public void set(L source, L target, int num, double weight) {
        if(vertices.keySet().contains(source)) {
            Vertex<L> vertex = vertices.get(source);
            Map<L, Double> vertexLinkedVertex = vertex.getLinkedVertex();
            vertexLinkedVertex.put(target, weight);
            vertex.setLinkedVertex(vertexLinkedVertex);
        }
        else {
            Vertex<L> newVertex = new Vertex<>(source, target, num, weight);
            vertices.put(source, newVertex);
        }
    }

    @Override
    public Set<L> vertices() {
        return vertices.keySet();
    }


    @Override
    public Map<L, Double> targets(L source) {
        return vertices.get(source) == null ? new HashMap<>() : vertices.get(source).getLinkedVertex();
    }

    @Override
    public int getNum(L source){
        return vertices.get(source) == null ? 0 : vertices.get(source).getNum();
    }

    @Override
    public boolean isEmpty(){
        return vertices.size() == 0;
    }


    /**
     * An implementation of vertex
     * Mutable.
     * This class is internal to the rep of ConcreteVerticesGraph.
     * <p>
     * <p>PS2 instructions: the specification and implementation of this class is
     * up to you.
     */
    static class Vertex<L> {

        private L vertex;
        private int num;
        private Map<L, Double> linkedVertex = new HashMap<>();
        // Abstraction function:
        //   represents the vertex and its adjacency list in the graph
        // Representation invariant:
        //   linkedVertex is the adjacency list including the weights of edges
        //   the weight between the linked vertices must no less than 0
        //   vertex != null, the Integer in the linkedVertex must no less than 0
        // Safety from rep exposure:
        //  All fields are private;
        //  the vertex is Strings so are guaranteed immutable;

        public int getNum(){
            return num;
        }

        Vertex(L source) {
            this.vertex = source;
        }

        Vertex(L source, L target,int num, double weight) {
            this.vertex = source;
            this.num = num;
            this.linkedVertex.put(target, weight);
        }

        L getVertex() {
            return vertex;
        }

        void setLinkedVertex(Map<L, Double> linkedVertex) {
            this.linkedVertex = linkedVertex;
        }

        Map<L, Double> getLinkedVertex() {
            return linkedVertex;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("which points to " + linkedVertex.size() + " vertice(s) ");
            Set<L> set = linkedVertex.keySet();
            for (L string : set) {
                s.append(" ").append(string);
            }
            return s.toString();
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertex,linkedVertex);
        }

        @Override
        public boolean equals(Object o) {
            return o == this || o instanceof Vertex && vertex.equals(((Vertex) o).vertex) && linkedVertex.equals(((Vertex) o).linkedVertex);
        }
    }
}
