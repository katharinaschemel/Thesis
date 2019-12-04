import java.io.BufferedWriter;
import java.io.IOException;

public class FachProz {
	public String code, name, verantwortlich, standardkonformitšt;

	public FachProz(String code, String name, String verantwortlich, String standardkonformitšt) {
		super();
		this.code = code;
		this.name = name;
		this.verantwortlich = verantwortlich;
		this.standardkonformitšt = standardkonformitšt;
	}

	public void writeNode(BufferedWriter writer) {

		String a = "MERGE (:FachProz { Name: '" + this.name + "', Verantwortlich: '" + this.verantwortlich + "', Standardkonformitšt: '" + this.standardkonformitšt + "'});\n";

		try {
			writer.write(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
