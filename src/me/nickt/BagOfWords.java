package me.nickt;

import java.util.*;

public class BagOfWords {

    private List<String> orderedUniqueWords = new ArrayList<String>();

    public BagOfWords(Sentence sentence) {
        Set<String> bag = new HashSet<String>();
        for (String word : sentence.wordArray()) {
            bag.add(word.toLowerCase());
        }
        orderedUniqueWords.addAll(bag);
        Collections.sort(orderedUniqueWords);
    }

    public int similarity(BagOfWords other) {
        int similarity = 0;
        Iterator<String> aIterator = orderedUniqueWords.iterator();
        Iterator<String> bIterator = other.orderedUniqueWords.iterator();
        String a = aIterator.next();
        String b = bIterator.next();
        while (aIterator.hasNext() && bIterator.hasNext()) {
            if (a.equals(b)) {
                similarity++;
                a = aIterator.next();
                b = bIterator.next();
            } else if (a.compareTo(b) > 0) {
                b = bIterator.next();
            } else {
                a = aIterator.next();
            }
        }
        return similarity;
    }

    public String toString() {
        String s = "";
        for (String word : orderedUniqueWords) {
            s += word + " ";
        }
        return s;
    }

}
