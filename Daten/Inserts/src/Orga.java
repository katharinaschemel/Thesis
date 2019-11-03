import java.io.BufferedWriter;
import java.io.IOException;

public class Orga {

	public String code, name, uebergeordneteEinheit;

	public Orga(String code, String name, String uebergeordneteEinheit) {
		super();
		this.code = code;
		this.name = name;
		this.uebergeordneteEinheit = uebergeordneteEinheit;
	}
	
	public Orga(String code, String name) {
		super();
		this.code = code;
		this.name = name;
		this.uebergeordneteEinheit = "unbekannt";
	}
	
	public void writeNode(BufferedWriter writer) {
		
		String a = "MERGE (:Organisationseinheit { Name: '" + this.name + "', Uebergeordnete_Einheit: '" + this.uebergeordneteEinheit + "'});\n";
		
		try {
			writer.write(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
