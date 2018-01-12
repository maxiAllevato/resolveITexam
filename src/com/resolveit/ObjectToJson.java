package com.resolveit;


import java.util.Set;

public class ObjectToJson {
	
	

	private String word;
	private Set<Integer> sentence;
	private int totalOcurrences;
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Set<Integer> getSentence() {
		return sentence;
	}
	public void setSentence(Set<Integer> set) {
		this.sentence = set;
	}
	public int getTotalOcurreces() {
		return totalOcurrences;
	}
	public void setTotalOcurrences(int totalOcurrences) {
		this.totalOcurrences = totalOcurrences;
	}
	
	

}
