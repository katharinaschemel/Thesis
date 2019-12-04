import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;



public class main {

	public static void main(String[] args) {
		
//		File out = new File("Output.txt");
//		FileWriter writer;
//		BufferedWriter buffWriter;
//		
//		ArrayList<Info> info = new ArrayList<>();
//		ArrayList<Orga> orga = new ArrayList<>();
//		ArrayList<Tech> tech = new ArrayList<>();
//		
//		readInfo(info);
//		readOrga(orga);
//		readTech(tech);
//		
//		try {
//			writer = new FileWriter(out);
//			buffWriter = new BufferedWriter(writer);
//			
//			writeInfo(info, buffWriter);
//			writeOrga(orga, buffWriter);
//			writeTech(tech, buffWriter);
//			
//			writeRelationInfoOrgaTech(info, orga, tech, buffWriter);
//			
//			buffWriter.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		ImportStatements();
		
		
	}
	
	



	private static void ImportStatements() {
		File f = new File("Output.txt");
		
		try {
			FileReader reader = new FileReader(f);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String s;
			
			Driver driver = GraphDatabase.driver("bolt://localhost", AuthTokens.basic("neo4j", "KaSc1905"));
			Session t = driver.session();
			
			while((s = bufferedReader.readLine()) != null) {
				
				t.run(s);
				
			}
			
			driver.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}





	public static void readInfo(ArrayList<Info> info) {
		File f = new File("Informationssystem.csv");
		try {
			FileReader reader = new FileReader(f);
			BufferedReader buffReader = new BufferedReader(reader);
			String s;
			while ((s = buffReader.readLine()) != null) {
				String[] x = s.split(";");
				Info i = new Info(x[0], x[1], x[2], x[3], x[4], x[5], x[6], x[7], x[8], x[9], x[10], x[11]);
				info.add(i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void readOrga(ArrayList<Orga> orga) {
		File f = new File("Organisationseinheit.csv");
		try {
			FileReader reader = new FileReader(f);
			BufferedReader buffReader = new BufferedReader(reader);
			String s;
			while ((s = buffReader.readLine()) != null) {
				String[] x = s.split(";");
				Orga o = new Orga(x[0], x[1], x[2], x[3]);
				orga.add(o);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void readTech(ArrayList<Tech> tech) {
		File f = new File("Technologie.csv");
		try {
			FileReader reader = new FileReader(f);
			BufferedReader buffReader = new BufferedReader(reader);
			String s;
			while ((s = buffReader.readLine()) != null) {
				String[] x = s.split(";");
				Tech t = new Tech(x[0], x[1], x[2], x[3]);
				tech.add(t);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	public static void readFachProz(ArrayList<FachProz> FachProz) {
		File f = new File("Fachprozesse.csv");
		try {
			FileReader reader = new FileReader(f);
			BufferedReader buffReader = new BufferedReader(reader);
			String s;
			while ((s = buffReader.readLine()) != null) {
				String[] x = s.split(";");
				FachProz fp = new FachProz(x[0], x[1], x[2], x[3]);
				FachProz.add(fp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void readStandards(ArrayList<Standards> Standards) {
		File f = new File("Standards.csv");
		try {
			FileReader reader = new FileReader(f);
			BufferedReader buffReader = new BufferedReader(reader);
			String s;
			while ((s = buffReader.readLine()) != null) {
				String[] x = s.split(";");
				Standards sa = new Standards(x[0], x[1], x[2]);
				Standards.add(sa);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}public static void readStandorte(ArrayList<Standorte> Standorte) {
		File f = new File("Standorte.csv");
		try {
			FileReader reader = new FileReader(f);
			BufferedReader buffReader = new BufferedReader(reader);
			String s;
			while ((s = buffReader.readLine()) != null) {
				String[] x = s.split(";");
				Standorte so = new Standorte(x[0], x[1], x[2], x[3]);
				Standorte.add(so);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	public static void writeInfo(ArrayList<Info> info, BufferedWriter buffWriter) {
		
		for(Info i : info){
			i.writeNode(buffWriter);		
		}
	}
	
	public static void writeOrga(ArrayList<Orga> orga, BufferedWriter buffWriter) {
		
		for(Orga o : orga){
			o.writeNode(buffWriter);		
		}
	}
	
	public static void writeTech(ArrayList<Tech> tech, BufferedWriter buffWriter) {
		
		for(Tech t : tech){
			t.writeNode(buffWriter);		
		}
	}
	
	
public static void writeFachProz(ArrayList<FachProz> FachProz, BufferedWriter buffWriter) {
		
		for(FachProz fp : FachProz){
			fp.writeNode(buffWriter);		
		}
	}

public static void writeStandards(ArrayList<Standards> Standards, BufferedWriter buffWriter) {
	
	for(Standards sa : Standards){
		sa.writeNode(buffWriter);		
	}
}

public static void writeStandorte(ArrayList<Standorte> Standorte, BufferedWriter buffWriter) {
	
	for(Standorte so : Standorte){
		so.writeNode(buffWriter);		
	}
}






	public static void writeRelationInfoOrgaTechFachProzStandardsStandorte(ArrayList<Info> info, ArrayList<Orga> orga, ArrayList<Tech> tech, ArrayList<FachProz> FachProz, ArrayList<Standards> Standards, ArrayList<Standorte> Standorte, BufferedWriter bufferedWriter) {
	
		for(Info i : info){
			ArrayList<String> temp1 = new ArrayList<>(Arrays.asList(i.eingesetztIn.split(",")));
			ArrayList<String> temp2 = new ArrayList<>(Arrays.asList(i.technologien.split(",")));
			ArrayList<String> temp3 = new ArrayList<>(Arrays.asList(i.standardkonformität.split(",")));
	
	// Splitt notwendig???	FachProz - Standardkonformität ja & Standort - Anschrift auch ABER Standards eher nein	
	//		ArrayList<String> temp4 = new ArrayList<>(Arrays.asList(i.technologien.split(",")));       hier vlt nicht
	//		ArrayList<String> temp5 = new ArrayList<>(Arrays.asList(i.anschrift.split(",")));          hier vlt schon
			
			
			for(String tmp : temp1) {		
				Orga o = getOrgaByCode(orga, tmp);
				String a = "Match (a:Informationssystem),(b:Organisationseinheit) "
						+ "WHERE a.Code = '" + i.code + "' AND a.Name = '" + i.name + "' AND a.Beschreibung = '" + i.beschreibung + "' AND a.Anzahl_Installationen = '" + i.anzInstallationen + "' AND a.Subsysteme = '" + i.subsysteme + "' AND a.Investitionsgroesse = '" + i.investitionsgroesse + "' AND a.Eingesetzt_seit = '" + i.eingesetztSeit + "' "
						+ "AND b.Name = '" + o.name +  "' AND b.Uebergeordnete_Einheit = '" + o.uebergeordneteEinheit +  "' AND b.Standort = '" + o.standort +"' "
						+ "CREATE (a)-[r:Eingesetzt_In {Code: '" + tmp + "'}]->(b);\n";
				
				try {
					bufferedWriter.write(a);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(String tmp : temp2) {
				Tech t = getTechByCode(tech, tmp);
				String a = "Match (a:Informationssystem),(b:Technologie) "
						+ "WHERE a.Code = '" + i.code + "' AND a.Name = '" + i.name + "' AND a.Beschreibung = '" + i.beschreibung + "' AND a.Anzahl_Installationen = '" + i.anzInstallationen + "' AND a.Subsysteme = '" + i.subsysteme + "' AND a.Investitionsgroesse = '" + i.investitionsgroesse + "' AND a.Eingesetzt_seit = '" + i.eingesetztSeit + "' "
						+ "AND b.Name = '" + t.name +  "' AND b.Beschreibung = '" + t.beschreibung + "' AND b.EndOfLife = '" + t.endOfLife +  "' "
						+ "CREATE (a)-[r:verwendet {Code: '" + tmp + "'}]->(b);\n";
				
				try {
					bufferedWriter.write(a);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			for(String tmp : temp3) {		
				FachProz fp = getFachProzByCode(FachProz, tmp);
				String a = "Match (a:Informationssystem),(FachProz) "
						+ "WHERE a.Code = '" + i.code + "' AND a.Name = '" + i.name + "' AND a.Beschreibung = '" + i.beschreibung + "' AND a.Anzahl_Installationen = '" + i.anzInstallationen + "' AND a.Subsysteme = '" + i.subsysteme + "' AND a.Investitionsgroesse = '" + i.investitionsgroesse + "' AND a.Eingesetzt_seit = '" + i.eingesetztSeit + "' "
						+ "AND b.Name = '" + fp.name +  "' AND b.Verantwortlich = '" + fp.verantwortlich +  "' "
						+ "CREATE (a)-[r:Eingesetzt_In {Code: '" + tmp + "'}]->(b);\n";
				
				try {
					bufferedWriter.write(a);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
/*			for(String tmp : temp4) {		
				Standards sa = getStandardsByCode(Standards, tmp);
				String a = "Match (a:Informationssystem),(Standards) "
						+ "WHERE a.Code = '" + i.code + "' AND a.Name = '" + i.name + "' AND a.Beschreibung = '" + i.beschreibung + "' AND a.Anzahl_Installationen = '" + i.anzInstallationen + "' AND a.Subsysteme = '" + i.subsysteme + "' AND a.Investitionsgroesse = '" + i.investitionsgroesse + "' AND a.Eingesetzt_seit = '" + i.eingesetztSeit + "' "
						+ "AND b.Art = '" + sa.art +  "' AND b.Name = '" + sa.name +  "' "
						+ "CREATE (a)-[r:Eingesetzt_In {Code: '" + tmp + "'}]->(b);\n";
				
				try {
					bufferedWriter.write(a);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			for(String tmp : temp5) {		
				Standorte so = getStandorteByCode(Standorte, tmp);
				String a = "Match (a:Informationssystem),(Standorte) "
						+ "WHERE a.Code = '" + i.code + "' AND a.Name = '" + i.name + "' AND a.Beschreibung = '" + i.beschreibung + "' AND a.Anzahl_Installationen = '" + i.anzInstallationen + "' AND a.Subsysteme = '" + i.subsysteme + "' AND a.Investitionsgroesse = '" + i.investitionsgroesse + "' AND a.Eingesetzt_seit = '" + i.eingesetztSeit + "' "
						+ "AND b.Name = '" + so.name +  "' AND b.PLZ = '" + so.plz +  "' " + "AND b.Anschrift = '" + so.anschrift
						+ "CREATE (a)-[r:Eingesetzt_In {Code: '" + tmp + "'}]->(b);\n";
				
				try {
					bufferedWriter.write(a);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
		}
	}
	
	public static Orga getOrgaByCode(ArrayList<Orga> orga, String code) {
		
		for(Orga o : orga) {
			if(o.code.equals(code))
				return o;
		}
		
		return null;
	}
	
	public static Tech getTechByCode(ArrayList<Tech> tech, String code) {
		
		for(Tech t : tech) {
			if(t.code.equals(code))
				return t;
		}
		
		return null;
	}

	
	
public static FachProz getFachProzByCode(ArrayList<FachProz> FachProz, String code) {
		
		for(FachProz fp : FachProz) {
			if(fp.code.equals(code))
				return fp;
		}
		
		return null;
	}



public static Standards getStandardsByCode(ArrayList<Standards> Standards, String code) {
		
		for(Standards sa : Standards) {
			if(sa.code.equals(code))
				return sa;
		}
		
		return null;
	}



public static Standorte getStandorteByCode(ArrayList<Standorte> Standorte, String code) {
		
		for(Standorte so : Standorte) {
			if(so.code.equals(code))
				return so;
		}
		
		return null;
	}
}
