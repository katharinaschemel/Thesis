import java.io.BufferedWriter;
import java.io.IOException;

public class Standards {
	public String code, art, name;

	public Standards(String code, String art, String name) {
		super();
		this.code = code;
		this.art = art;
		this.name = name;
	}

	public void writeNode(BufferedWriter writer) {

		String a = "MERGE (:Standards { Art: '" + this.art + "', Name: '" + this.name + "'});\n";

		try {
			writer.write(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
