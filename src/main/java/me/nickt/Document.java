package me.nickt;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import me.nickt.summarizers.Summarizer;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Document {
    /**
     * An immutable collection of text to be summarized.
     * It is an ordered list of sentences.
     */

    private List<Sentence> sentenceList;

    /**
     * Create a document from some text
     * @param rawText - basis of the document
     */
    public Document(String rawText) {
        this.sentenceList = textToSentenceList(rawText);
    }

    public void printSummary(Summarizer strategy, int numSentences) {
        if (numSentences > sentenceList.size()) {
            numSentences = sentenceList.size();
        }

        List<Integer> topIndexes = strategy.orderSentences(this).subList(0, numSentences);
        Collections.sort(topIndexes);

        for (int i : topIndexes) {
//            System.out.print(i);
//            System.out.print("\t");
            System.out.println(sentenceList.get(i));
        }
    }

    public int sentenceCount() {
        return sentenceList.size();
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < sentenceList.size(); i++) {
//            System.out.print(i);
//            System.out.print("\t");
            System.out.println(sentenceList.get(i));
        }
        return s;
    }

    /**
     * Convert a string into a list of sentences by using the coreNLP preprocessor.
     */
    private List<Sentence> textToSentenceList(String text) {
        List<Sentence> sentenceList = new ArrayList<>();
        Reader reader = new StringReader(text);
        DocumentPreprocessor dp = new DocumentPreprocessor(reader);
        for (List<HasWord> coreNLPSentence : dp) {
            sentenceList.add(new Sentence(coreNLPSentence));
        }
        return sentenceList;
    }
}
