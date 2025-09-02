import org.junit.Assert;
import org.junit.Test;

import static edu.gvsu.dlunit.DLUnit.*;

public class ComparatorTwoBitStrong {
@Test
public void zero_zero() {
	for(int a=0, a <=3, a++){
		for(int b=0, b<=3, b++){	
		  
			  
	  
    	setPinUnsigned("InputA", a);
   		setPinUnsigned("InputB", b);
		
		run();
		int expected;

		if (a < b){
			expected = 1;
		}
		else{
			expected = 0;

		}

    		Assert.assertEquals(expected, readPinUnsigned("Output"));
  }

}
