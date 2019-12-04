import java.io.BufferedWriter;
import java.io.IOException;

public class Orga {

	public String code, name, uebergeordneteEinheit, standort;

	public Orga(String code, String name, String uebergeordneteEinheit, String standort) {
		super();
		this.code = code;
		this.name = name;
		this.uebergeordneteEinheit = uebergeordneteEinheit;
		this.standort = standort;
	}
	
	public Orga(String code, String name, String standort) {
		super();
		this.code = code;
		this.name = name;
		this.uebergeordneteEinheit = "unbekannt";
		this.standort = standort;
	}
	
	public void writeNode(BufferedWriter writer) {
		
		String a = "MERGE (:Organisationseinheit { Name: '" + this.name + "', Uebergeordnete_Einheit: '" + this.uebergeordneteEinheit + "', Standort: '" + this.standort + "'});\n";
		
		try {
			writer.write(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
