package com.resolveit;

public enum ExclusionList {

	A("a"),
	THE("the"),
	AND("and"),
	OF("of"),
	IN("in"),
	BE("be"),
	ALSO("also"),
	AS("as"),
	COMMA(","),
	POINT("."),
	DPOINT(":"),
	QUOTES("``"),
	SPECIAL("\u0027\u0027");
	
	
	
	
	private String value;
	   private ExclusionList(String value)
	   {
	      this.value = value;
	   }

	   public String toString()
	   {
	      return this.value; 
	   }
	   
	   
	   public static boolean checkIfExist(String text) {
		    for (ExclusionList b : ExclusionList.values()) {
		      if (b.value.equalsIgnoreCase(text)) {
		        return true;
		      }
		    }
		    return false;
		  }
	
}
