package me.nickt;

import edu.stanford.nlp.ling.HasWord;

import java.util.Arrays;
import java.util.List;

public class Sentence {

    private String[] words;

    public Sentence(List<HasWord> sentence) {
        words = new String[sentence.size()];
        for (int i = 0; i < sentence.size(); i++) {
            words[i] = sentence.get(i).word();
        }
    }

    public Sentence(String[] words) {
        this.words = words;
    }

    public String[] wordArray() {
        return words.clone();
    }

    public String toString() {
        String s = "";
        for (String word : words) {
            s += word + " ";
        }
        return s;
    }
}
