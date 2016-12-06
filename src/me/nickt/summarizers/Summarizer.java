package me.nickt.summarizers;

import me.nickt.Document;

import java.util.List;

public interface Summarizer {
    public List<Integer> orderSentences(Document doc);
}
