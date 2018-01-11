package com.resolveit;


import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class TokenWord implements Comparable<TokenWord>{
	
	private String word;
	private String pos;
	private String lem;
	private Set<Integer> sentence;
	private int totalOccurrences;
	
	
	public int getTotalOccurrences() {
		return totalOccurrences;
	}
	public void setTotalOccurrences(int totalOccurrences) {
		this.totalOccurrences = totalOccurrences;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getLem() {
		return lem;
	}
	public void setLem(String lem) {
		this.lem = lem;
	}
	
	public Set<Integer> getSentence() {
		return sentence;
	}
	public void setSentence(Set<Integer> sentenceList) {
		this.sentence = sentenceList;
	}
	public int compareTo(TokenWord o) {
		return this.word.compareTo(o.getWord());
		
	}
	
	
	
}
