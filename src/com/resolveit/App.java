/*
 * 
 */
package com.resolveit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;



// TODO: Auto-generated Javadoc
/**
 * 
 *
 */
public class App 
{
	
	public ArrayList<TokenWord> tokenList = new ArrayList<TokenWord>();
	
	
	public static SortedMap<String, TokenWord> map = new TreeMap<String, TokenWord>(new Comparator<String>() {
	    public int compare(String o1, String o2) {
	        return o1.toLowerCase().compareTo(o2.toLowerCase());
	    }
	});
	

	public static List<TokenWord> listword = new ArrayList<TokenWord>();

	
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main( String[] args )
    {
       
        
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // read some text in the text variable
        String text = "Take this paragraph of text and return an alphabetized list of ALL unique words.  A unique word is any form of a word often communicated with essentially the same meaning. For example, fish and fishes could be defined as a unique word by using their stem fish. For each unique word found in this entire paragraph, determine the how many times the word appears in total. Also, provide an analysis of what unique sentence index position or positions the word is found. The following words should not be included in your analysis or result set: \"a\", \"the\", \"and\", \"of\", \"in\", \"be\", \"also\" and \"as\".  Your final result MUST be displayed in a readable console output in the same format as the JSON sample object shown below.";//"book books";//"For example, fish and fishes could be defined as a unique word by using their stem fish. you buy a book and then you book a room in an hotel.";
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        
     
        
     // these are all the sentences in this document
     // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
     List<CoreMap> sentences = document.get(SentencesAnnotation.class);
    
     int numSent= 0;
     
     for(CoreMap sentence: sentences) {
       // traversing the words in the current sentence
       // a CoreLabel is a CoreMap with additional token-specific methods
       for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
         // this is the text of the token
         String word = token.get(TextAnnotation.class);
         // this is the POS tag of the token
         String pos = token.get(PartOfSpeechAnnotation.class);
       
         String lem = token.get(LemmaAnnotation.class);
         
        // System.out.println("Word " + word);
         
        
         TokenWord tokenWord =  new TokenWord();
         tokenWord.setLem(lem);
         tokenWord.setPos(pos);
         tokenWord.setWord(word);
         tokenWord.setTotalOccurrences(1);

   
	 	if(!isInExclusionList(tokenWord.getWord())){
	 		evaluateWord(listword, tokenWord, numSent);
        }
	 	
  
       }
       numSent++;
      
     }
  
 
     //sort the list
     Collections.sort(listword, new Comparator<TokenWord>() {

 		public int compare(TokenWord t1, TokenWord t2) {
 		   String tokW1 = t1.getWord().toUpperCase();
 		   String tokw2 = t2.getWord().toUpperCase();

 		   //ascending order
 		   return tokW1.compareTo(tokw2);

 		  
 	    }});
     
     formatListAndShow(listword);
        
    }
    
   

	/**
	 * Format list and show.
	 *
	 * @param listword the listword
	 */
	private static void formatListAndShow(List<TokenWord> listword) {
    		
    		ArrayList<ObjectToJson> formatedList = new ArrayList();
    		
    	
    		Iterator <TokenWord> iterTW = listword.iterator();
		while(iterTW.hasNext()) {
			TokenWord tw = iterTW.next();
			
			ObjectToJson obj = new ObjectToJson();
			obj.setWord(tw.getWord());
			obj.setTotalOcurreces(tw.getTotalOccurrences());
			obj.setSentence(tw.getSentence());
			
			formatedList.add(obj);
		}
    	
    	
		Result result = new Result();
	     result.setResults(formatedList);
    	
	     
	     Gson gsonPrint = new GsonBuilder().setPrettyPrinting().create();
	     String show = gsonPrint.toJson(result);
	     
	     System.out.println(show);
		
	}
    
    
    /**
     * Checks if word is in exclusion list.
     *
     * @param value the value
     * @return true, if is in exclusion list
     */
    public static boolean isInExclusionList(String value) {
    	
    		return ExclusionList.checkIfExist(value);
    	
    }

	/**
	 * Evaluate each word to make update or add to the list
	 *
	 * @param listword the listword
	 * @param tokenWord the token word
	 * @param numSent the num sent
	 */
	public static void evaluateWord(List<TokenWord> listword, TokenWord tokenWord, int numSent) {
    	
		if(!makeUpdate(listword, tokenWord, numSent)) {
			 Set <Integer> sentList1 = new HashSet<Integer>();
			        	 
			 sentList1.add(numSent);
			 tokenWord.setSentence(sentList1);
			listword.add(tokenWord);
						
		}
		
	}


	/**
	 * Make update if its necessary
	 *
	 * @param listword the listword
	 * @param tokenWord the token word
	 * @param numSent the num sent
	 * @return true, if successful
	 */
	private static boolean makeUpdate(List<TokenWord> listword, TokenWord tokenWord, int numSent) {
		
		Iterator <TokenWord> iterTW = listword.iterator();
		while(listword != null && iterTW.hasNext()) {
			
			TokenWord tw = iterTW.next();
			
			if(tw.getLem().equalsIgnoreCase(tokenWord.getLem()) && sameMeaning(tokenWord, tw)) {
				
				tw.setTotalOccurrences(tw.getTotalOccurrences() + 1);
				tw.getSentence().add(numSent);
				
				return true;
			}
		}
	
		return false;
	}	
    

	/**
	 * Evaluate if two words have Same meaning.
	 * 
	 *
	 * @param tokenWord the token word
	 * @param tw the tw
	 * @return true, if successful
	 */
	private static boolean sameMeaning(TokenWord tokenWord, TokenWord tw) {
		
		boolean samePos = tw.getPos().indexOf(tokenWord.getPos()) != -1;
		boolean samePoss = tokenWord.getPos().indexOf(tw.getPos()) != -1;
		
		return (samePoss || samePos)?true:false;
	}
    	
    
    
}
