import java.io.BufferedWriter;
import java.io.IOException;

public class Info {

	public String code, name, beschreibung, verantwortlich, anzInstallationen, anzAnwedner, subsysteme,
			investitionsgroesse, technologien, eingesetztSeit, eingesetztIn, standardkonformität;

	public Info(String code, String name, String beschreibung, String verantwortlich, String anzInstallationen,
			String anzAnwedner, String subsysteme, String investitionsgroesse, String technologien,
			String eingesetztSeit, String eingesetztIn, String standardkonformität) {
		super();
		this.code = code;
		this.name = name;
		this.beschreibung = beschreibung;
		this.verantwortlich = verantwortlich;
		this.anzInstallationen = anzInstallationen;
		this.anzAnwedner = anzAnwedner;
		this.subsysteme = subsysteme;
		this.investitionsgroesse = investitionsgroesse;
		this.technologien = technologien;
		this.eingesetztSeit = eingesetztSeit;
		this.eingesetztIn = eingesetztIn;
		this.standardkonformität = standardkonformität;
	}

	// hier FachProz, Standards, Standorte?
	
	public void writeNode(BufferedWriter writer) {
		
		String a = "MERGE (:Informationssystem { Code: '" + this.code +"', Name: '" + this.name +"', Beschreibung: '" + this.beschreibung +"', Anzahl_Installationen: '" + this.anzInstallationen +"', Subsysteme: '" + this.subsysteme + "', Investitionsgroesse: '" + this.investitionsgroesse +"', Eingesetzt_seit: '" + this.eingesetztSeit +"', Standardkonformität: '" + this.standardkonformität +"' });\n";
		String b = "MERGE (:Person { Name: '" + this.verantwortlich + "'});\n";
		String c = "MERGE (:Anzahl_Anwender { Klasse: '" + this.anzAnwedner +"'});\n";
		String d = "Match (a:Informationssystem),(b:Person) "
				+ "WHERE a.Code = '" + this.code + "' AND a.Name = '" + this.name + "' AND a.Beschreibung = '" + this.beschreibung + "' AND a.Anzahl_Installationen = '" + this.anzInstallationen + "' AND a.Subsysteme = '" + this.subsysteme + "' AND a.Investitionsgroesse = '" + this.investitionsgroesse + "' AND a.Eingesetzt_seit = '" + this.eingesetztSeit + "' AND a.Standardkonformität = '" + this.standardkonformität + "' "
				+ "AND b.Name = '" + this.verantwortlich +  "' "
				+ "CREATE (a)<-[r:verantwortlich]-(b);\n";
		String e = "Match (a:Informationssystem),(b:Anzahl_Anwender) "
				+ "WHERE a.Code = '" + this.code + "' AND a.Name = '" + this.name + "' AND a.Beschreibung = '" + this.beschreibung + "' AND a.Anzahl_Installationen = '" + this.anzInstallationen + "' AND a.Subsysteme = '" + this.subsysteme + "' AND a.Investitionsgroesse = '" + this.investitionsgroesse + "' AND a.Eingesetzt_seit = '" + this.eingesetztSeit + "' AND a.Standardkonformität = '" + this.standardkonformität + "' "
				+ "AND b.Klasse = '" + this.anzAnwedner +  "' "
				+ "CREATE (a)-[r:besitzt]->(b);\n";
		try {
			writer.write(a);
			writer.write(b);
			writer.write(c);
			writer.write(d);
			writer.write(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}
}