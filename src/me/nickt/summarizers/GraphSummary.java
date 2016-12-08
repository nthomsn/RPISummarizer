package me.nickt.summarizers;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import me.nickt.BagOfWords;
import me.nickt.Document;
import me.nickt.Sentence;

import java.util.List;

/**
 * Created by John on 12/6/2016.
 */
public class GraphSummary implements Summarizer {
  public List<Integer> orderSentences(Document doc) {

    DirectedSparseGraph<Integer, Double> graph = new DirectedSparseGraph<Integer, Double>();
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
          graph.addEdge( (edgeValue * factor), i, j, EdgeType.DIRECTED);

          System.out.println("The current edge value is " + edgeValue);

        }

      }

    }



    return null;
  }
}
