package Service;
import java.util.Base64;


public class CriptografiaService {
	  
		    public static String encode(String input) {
		        return Base64.getEncoder().encodeToString(input.getBytes());
		    }

		    
		    public static String decode(String input) {
		        byte[] decodedBytes = Base64.getDecoder().decode(input);
		        return new String(decodedBytes);
		    }

}