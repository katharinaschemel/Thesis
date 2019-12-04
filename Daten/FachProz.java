import java.io.BufferedWriter;
import java.io.IOException;

public class FachProz {
	public String code, name, verantwortlich, standardkonformität;

	public FachProz(String code, String name, String verantwortlich, String standardkonformität) {
		super();
		this.code = code;
		this.name = name;
		this.verantwortlich = verantwortlich;
		this.standardkonformität = standardkonformität;
	}

	public void writeNode(BufferedWriter writer) {

		String a = "MERGE (:FachProz { Name: '" + this.name + "', Verantwortlich: '" + this.verantwortlich + "', Standardkonformität: '" + this.standardkonformität + "'});\n";

		try {
			writer.write(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
