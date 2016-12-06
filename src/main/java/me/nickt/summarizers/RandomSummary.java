package me.nickt.summarizers;

import me.nickt.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomSummary implements Summarizer {

    @Override
    public List<Integer> orderSentences(Document doc) {
        List<Integer> ordering = new ArrayList<>();
        for (int i = 0; i < doc.sentenceCount(); i++) {
            ordering.add(i);
        }
        Collections.shuffle(ordering);
        return ordering;
    }

}
