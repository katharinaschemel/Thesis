import java.io.BufferedWriter;
import java.io.IOException;

public class Standard {
	public String code, art, name;

	public Standard(String code, String art, String name) {
		super();
		this.code = code;
		this.art = art;
		this.name = name;
	}

	public void writeNode(BufferedWriter writer) {

		String a = "MERGE (:Standard {Code: '" + this.code + "', Art: '" + this.art + "', Name: '" + this.name + "'});\n";

		try {
			writer.write(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
