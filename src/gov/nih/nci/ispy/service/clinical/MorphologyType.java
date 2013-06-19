package gov.nih.nci.ispy.service.clinical;






public enum MorphologyType{
	PATTERN_1{ public String toString(){return "1=Single uni-centric mass with well-defined margin";}},
    PATTERN_2{ public String toString(){return "2=Multi-lobulated mass with well-defined margin";}},
    PATTERN_3{ public String toString(){return "3=Area enhancement with irregular margins - with nodularity";}},
    PATTERN_4{ public String toString(){return "4=Area enhancement with irregular margins - without nodularity";}},
    PATTERN_5{ public String toString(){return "5=Septal spreading; streaming";}};
    
    public static MorphologyType getTypeForValue(int value) {   
  	  switch(value) {
  	  case 1: return MorphologyType.PATTERN_1;
  	  case 2: return MorphologyType.PATTERN_2;
  	  case 3: return MorphologyType.PATTERN_3;
  	  case 4: return MorphologyType.PATTERN_4;
  	  case 5: return MorphologyType.PATTERN_5;
  	 
  	  }
  	  return null;
    }
    
    
}

