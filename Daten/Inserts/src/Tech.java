import java.io.BufferedWriter;
import java.io.IOException;

public class Tech {

	public String code, name, beschreibung, endOfLife;

	public Tech(String code, String name, String beschreibung, String endOfLife) {
		super();
		this.code = code;
		this.name = name;
		this.beschreibung = beschreibung;
		this.endOfLife = endOfLife;
	}

	public void writeNode(BufferedWriter writer) {

		String a = "MERGE (:Technologie {Code: '" + this.code + "', Name: '" + this.name + "', Beschreibung: '" + this.beschreibung + "', EndOfLife: '" + this.endOfLife + "'});\n";

		try {
			writer.write(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
