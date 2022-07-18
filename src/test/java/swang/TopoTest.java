package swang;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TopoTest {

    @Test
    public void normal() {
        Graph<String, DefaultEdge> graph1 = createGraph1();
        Iterator<String> iterator = new TopologicalOrderIterator(graph1);
        List<String> list = new ArrayList<>();
        while (iterator.hasNext()) {
            String s = iterator.next();
            list.add(s);
        }
        Assert.assertEquals("[1, 2, 3, 4]", list.toString());
    }

    @Test
    public void testDAG() {
        try {
            Graph<String, DefaultEdge> graph1 = createGraph1();
            graph1.addEdge("4", "1");
            Iterator<String> iterator = new TopologicalOrderIterator(graph1);
            List<String> list = new ArrayList<>();
            while (iterator.hasNext()) {
                String s = iterator.next();
                list.add(s);
            }
            Assert.fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testForest() {
        Graph<String, DefaultEdge> graph1 = createGraph1();
        graph1.addVertex("5");
        graph1.addVertex("6");
        graph1.addEdge("5", "6");

        Iterator<String> iterator = new TopologicalOrderIterator(graph1);
        List<String> list = new ArrayList<>();

        int count = 0;
        while (iterator.hasNext()) {

            String s = iterator.next();
            int inDegree = graph1.inDegreeOf(s);
            if (inDegree == 0) {
                count++;
            }
            list.add(s);
        }
        Assert.assertEquals("[1, 5, 2, 3, 6, 4]", list.toString());
        Assert.assertEquals(2,count);
    }

    @Test
    public void testInDegreeMoreThan1(){
        Graph<String, DefaultEdge> graph1 = createGraph1();
        graph1.addVertex("5");
        graph1.addEdge("5","3");

        Iterator<String> iterator = new TopologicalOrderIterator(graph1);

        int count = 0;
        while (iterator.hasNext()) {
            String s = iterator.next();
            int inDegree = graph1.inDegreeOf(s);
            if (inDegree > 1) {
                count++;
            }
        }
        Assert.assertEquals(1,count);
    }

    private static Graph<String, DefaultEdge> createGraph1() {
        Graph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);

        g.addVertex("1");
        g.addVertex("2");
        g.addVertex("3");
        g.addVertex("4");

        g.addEdge("1", "2");
        g.addEdge("1", "3");
        g.addEdge("2", "4");

        return g;
    }


}
