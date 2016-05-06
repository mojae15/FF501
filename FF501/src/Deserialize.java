import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Scanner;

/**
 * Created by Morten Jaeger on 03/05/2016
 * Email: mojae15@student.sdu.dk
 */
public class Deserialize {

	public static void main(String[] args) {
		Climb d = null;
		
		FileInputStream ip;
		try {
			ip = new FileInputStream("/Users/mortenjaeger/Dropbox/Uni/Datalogi/FF501/Data/Data/ACP/Falcon 7x/climb.ser");
			ObjectInputStream in = new ObjectInputStream(ip);
			d = (Climb)in.readObject();
			in.close();
			ip.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
