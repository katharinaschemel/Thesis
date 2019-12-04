import java.io.BufferedWriter;
import java.io.IOException;

public class FachProz {
	public String code, name, verantwortlich, standardkonformit�t;

	public FachProz(String code, String name, String verantwortlich, String standardkonformit�t) {
		super();
		this.code = code;
		this.name = name;
		this.verantwortlich = verantwortlich;
		this.standardkonformit�t = standardkonformit�t;
	}

	public void writeNode(BufferedWriter writer) {

		String a = "MERGE (:FachProz { Name: '" + this.name + "', Verantwortlich: '" + this.verantwortlich + "', Standardkonformit�t: '" + this.standardkonformit�t + "'});\n";

		try {
			writer.write(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
