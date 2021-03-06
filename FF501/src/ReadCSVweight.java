import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReadCSVweight {
public static String csvSplit = ";";
	
	public static void main(String[] args) {
		readFile(Paths.get("/Users/mortenjaeger/Dropbox/Uni/Datalogi/FF501/Data/Data/ACP/Falcon 7x/weightlimits.csv"), 
				Paths.get("/Users/mortenjaeger/Dropbox/Uni/Datalogi/FF501/Data/Data/ACP/Falcon 7x/weightlimits.ser"));

	}
	public static Scanner sc = null;
	
	public static void readFile(Path path, Path dest){
		FileInputStream ip = null;
		
		FileOutputStream op = null;
		
		try {
			ip = new FileInputStream(path.toString());
			sc = new Scanner(ip);
			op = new FileOutputStream(dest.toString());
			ObjectOutputStream out = new ObjectOutputStream(op);
			
			weightLimits[] weight = new weightLimits[50];
			int j = 0;
			/**
			 * Weight:
			 * 0 = 33000
			 * 1 = 37000
			 * 2 = 41000
			 * 3 = 45000
			 * 4 = 49000
			 * 5 = 53000
			 * 6 = 57000
			 * 7 = 61000
			 * 8 = 65000
			 * 9 = 69000
			 * 10 = 73000
			 * Formula: 4000*x+29000
			 */
			
			/**
			 * FL = index *10
			 */
			while (sc.hasNext()){
				String[] line = sc.nextLine().split(";");
				if (line[1].equals("0")){
					weightLimits we = new weightLimits();
					we.weight = Integer.parseInt(line[2]);
					weight[(Integer.parseInt(line[0])/10)] = we;
				}
			}
			out.writeObject(weight);
			out.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void test(){
		Map<String, Double> map = new HashMap<>();
		while(sc.hasNext()){
			String line = sc.nextLine();
			String[] words = line.split(csvSplit);
			if(!words[0].equals("")){
				map.compute(words[2], (k,v)->{
					if( v == null){
						return 1.0;
					}
					else{
						return v+1;
					}
				});
			}
		}
		//out.writeObject(d);
		System.out.println(map.size());
	}
	
	public static int countLines(String path) throws FileNotFoundException{
		InputStream is = new BufferedInputStream(new FileInputStream(path));
		try {
			byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

}
