import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;


public class ReadCsv {
	private String splitBy;
	
	public ReadCsv(String splitBy) {
		this.splitBy=splitBy;
	}
 
  public static void main(String[] args) {
 
	if (args.length > 1) {
		ReadCsv obj = new ReadCsv(",");
		obj.load(new File(args[1]));
	}
 
  }
 
  public List<String[]> load(File file) {
	BufferedReader br = null;
	String line = "";
	List<String[]> list = new ArrayList<>();
	try {
 
		br = new BufferedReader(new FileReader(file));
		while ((line = br.readLine()) != null) {
 
		        // use comma as separator
			String[] fields = line.split(this.splitBy);
			list.add(fields);
		}
 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	return list;
  }
 
}