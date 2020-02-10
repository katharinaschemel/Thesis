import java.io.BufferedWriter;
import java.io.IOException;

public class Standort {
	public String code, name, plz, anschrift;

	public Standort(String code, String name, String plz, String anschrift) {
		super();
		this.code = code;
		this.name = name;
		this.plz = plz;
		this.anschrift = anschrift;
	}

	public void writeNode(BufferedWriter writer) {

		String a = "MERGE (:Standort {Code: '" + this.code + "', Name: '" + this.name + "', PLZ: '" + this.plz + "', Anschrift: '" + this.anschrift + "'});\n";

		try {
			writer.write(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
