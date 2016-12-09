package me.nickt.summarizers;

import com.google.common.base.Functions;
import com.google.common.base.Supplier;

import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.algorithms.scoring.PageRankWithPriors;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;



import me.nickt.BagOfWords;
import me.nickt.Document;
import me.nickt.Sentence;

import java.util.*;

/**
 * Created by John on 12/6/2016.
 * Has the ability to take a set of sentences and order them from most important to least important
 */
public class BestGraphSummarizerEver implements Summarizer{

  private DirectedSparseGraph<Integer, Integer> graph = new DirectedSparseGraph<>();
  private Map<Integer, Double> edgeWeights = new HashMap<>();
  private Supplier<Integer> edgeFactory = new Supplier<Integer>() {
                                                int i=0;
                                                public Integer get() {
                                                  return i++;
                                                }};

  private void addEdge(Graph<Integer,Integer> G, Integer v1, Integer v2, double weight) {
    Integer edge = edgeFactory.get();
    graph.addEdge(edge, v1, v2);
    edgeWeights.put(edge, weight);
  }

  public List<Integer> orderSentences(Document doc) {

    for(int i = 0; i < doc.sentenceCount(); i++){
      graph.addVertex(i);
    }

    List<Sentence> listOfSentences = doc.getSentenceList();

    for(int i = 0; i < doc.sentenceCount(); i++){

      int totalPossibleOuts = 0;

      for(int j = 0; j < doc.sentenceCount(); j++){

        if(i != j){

          BagOfWords a = new BagOfWords(listOfSentences.get(i));
          BagOfWords b = new BagOfWords(listOfSentences.get(j));

          int edgeValue = a.similarity(b);

          totalPossibleOuts += edgeValue;

        }

      }

      double factor = 1.0 / (double)totalPossibleOuts;

      for (int j = 0; j < doc.sentenceCount(); j++){

        if( i != j){

          BagOfWords a = new BagOfWords(listOfSentences.get(i));
          BagOfWords b = new BagOfWords(listOfSentences.get(j));

          int edgeValue = a.similarity(b);
          addEdge(graph, i, j, (edgeValue * factor));

          //System.out.println("The current edge value is " + edgeValue * factor);

        }

      }

    }

    PageRankWithPriors<Integer, Integer> pr = new PageRank<Integer, Integer>(graph, Functions.forMap(edgeWeights), 0.1);
    pr.evaluate();

    Map<Integer, Double> vertices = new HashMap<>();
    for (int i = 0; i < doc.sentenceCount(); i++){
      vertices.put(i, pr.getVertexScore(i));
    }

    Map<Integer, Double> orderedVertices = sortByValue(vertices);

    List<Integer> result = new LinkedList<>();
    for(int i = 0; i < orderedVertices.keySet().size(); i++) {
      result.add((int)orderedVertices.keySet().toArray()[i]);
    }
    return result;
  }

  public static <K, V extends Comparable<? super V>> Map<K, V>
  sortByValue( Map<K, V> map )
  {
    List<Map.Entry<K, V>> list =
      new LinkedList<>( map.entrySet() );
    Collections.sort( list, new Comparator<Map.Entry<K, V>>()
    {
      @Override
      public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
      {
        return ( o1.getValue() ).compareTo( o2.getValue() );
      }
    } );

    Map<K, V> result = new LinkedHashMap<>();
    for (Map.Entry<K, V> entry : list)
    {
      result.put( entry.getKey(), entry.getValue() );
    }
    return result;
  }

}
