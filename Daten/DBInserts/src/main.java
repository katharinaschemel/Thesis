import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f1 = new File("Informationssystem.csv");
		File w = new File("InformationssystemInserts.txt");
		
		File f2 = new File("Organisationseinheit.csv");
		File f3 = new File("Technologie.csv");
		try {
			FileReader reader = new FileReader(f1);
			//FileInputStream utf = new FileInputStream(f), "UTF8");
			BufferedReader buffReader = new BufferedReader(reader);
			FileWriter writer = new FileWriter(w);
			BufferedWriter buffWriter = new BufferedWriter(writer);
			String s;
			while ((s = buffReader.readLine()) != null) {
				String[] t = s.split(";");
				String a = "MERGE (:Informationssystem { Code: '" + t[0] +"', Name: '" + t[1] +"', Beschreibung: '" + t[2] +"', Anzahl_Installationen: '" + t[4] +"', Subsysteme: '" + t[6] + "', Investitionsgröße: '" + t[7] +"', Eingesetzt_seit: '" + t[9] +"' })\n";
				buffWriter.write(a);
				String b = "MERGE (:Person { Name: '" + t[3] + "'})\n";
				buffWriter.write(b);
				String c = "MERGE (:Anzahl_Anwender { Klasse: '" + t[5] +"'})\n";
				buffWriter.write(c);
				
				String d = "Match (a:Informationssystem),(b:Person) "
						+ "WHERE a.Code = '" + t[0] + "' AND a.Name = '" + t[1] + "' AND a.Beschreibung = '" + t[2] + "' AND a.Anzahl_Installationen = '" + t[4] + "' AND a.Subsysteme = '" + t[6] + "' AND a.Investitionsgröße = '" + t[7] + "' AND a.Eingesetzt_seit = '" + t[9] + "' "
						+ "AND b.Name = '" + t[3] +  "'"
						+ "CREATE (a)<-[r:verantwortlich]-(b)\n";
				buffWriter.write(d);
				
				String e = "Match (a:Informationssystem),(b:Anzahl_Anwender) "
						+ "WHERE a.Code = '" + t[0] + "' AND a.Name = '" + t[1] + "' AND a.Beschreibung = '" + t[2] + "' AND a.Anzahl_Installationen = '" + t[4] + "' AND a.Subsysteme = '" + t[6] + "' AND a.Investitionsgröße = '" + t[7] + "' AND a.Eingesetzt_seit = '" + t[9] + "' "
						+ "AND b.Klasse = '" + t[5] +  "'"
						+ "CREATE (a)-[r:besitzt]->(b)\n";
				buffWriter.write(e);
			}		
			buffReader.close();
			buffWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
