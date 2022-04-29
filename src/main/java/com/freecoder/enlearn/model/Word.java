package com.freecoder.enlearn.model;

import javafx.util.Pair;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Frank
 * @date 2022/4/28 13:38
 */
@Data
public class Word {
    String word;
    String phonetic;
    List<String> explains;
    List<String> wordGroup;
    List<Sentence> sentences;

    public Word(String word, String phonetic, List<String> explains, List<String> wordGroup, List<Pair<String, String>> sentences) {
        this.word = word;
        this.phonetic = phonetic;
        this.explains = explains;
        this.wordGroup = wordGroup;
        this.sentences = sentences.stream().map(p -> new Sentence(p.getKey(), p.getValue())).collect(Collectors.toList());
    }
}
