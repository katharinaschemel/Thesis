import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;



public class Main {

	public static void main(String[] args) {
		
//		File out = new File("Output.txt");
//		FileWriter writer;
//		BufferedWriter buffWriter;
//		
//		ArrayList<Info> info = new ArrayList<>();
//		ArrayList<Orga> orga = new ArrayList<>();
//		ArrayList<Tech> tech = new ArrayList<>();
//		ArrayList<Standort> standort = new ArrayList<>();
//		ArrayList<Standard> standard = new ArrayList<>();
//		ArrayList<FachProz> fachProz = new ArrayList<>();
//		
//		readInfo(info);
//		readOrga(orga);
//		readTech(tech);
//		readStandort(standort);
//		readStandard(standard);
//		readFachProz(fachProz);
//		
//		try {
//			writer = new FileWriter(out);
//			buffWriter = new BufferedWriter(writer);
//			
//			writeInfo(info, buffWriter);
//			writeOrga(orga, buffWriter);
//			writeTech(tech, buffWriter);
//			writeStandort(standort, buffWriter);
//			writeStandard(standard, buffWriter);
//			writeFachProz(fachProz, buffWriter);
//			buffWriter.flush();
//			
//			writeRelation(info, orga, tech, standort, standard, fachProz, buffWriter);
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
			
			Driver driver = GraphDatabase.driver("bolt://localhost", AuthTokens.basic( "neo4j", "KaSc1905" ));
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
		File f = new File("info.csv");
		try {
			FileReader reader = new FileReader(f);
			BufferedReader buffReader = new BufferedReader(reader);
			String s;
			while ((s = buffReader.readLine()) != null) {
				String[] x = s.split(";");
				if(s.endsWith(";")) {
					Info i = new Info(x[0], x[1], x[2], x[3], x[4], x[5], x[6], x[7], x[8], x[9], x[10], "");
					info.add(i);
				} else {
					Info i = new Info(x[0], x[1], x[2], x[3], x[4], x[5], x[6], x[7], x[8], x[9], x[10], x[11]);
					info.add(i);
				}			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void readOrga(ArrayList<Orga> orga) {
		File f = new File("orga.csv");
		try {
			FileReader reader = new FileReader(f);
			BufferedReader buffReader = new BufferedReader(reader);
			String s;
			while ((s = buffReader.readLine()) != null) {
				String[] x = s.split(";");
				if(x[2].isEmpty()) {
					Orga o = new Orga(x[0], x[1], x[3]);
					orga.add(o);
				} else {
					Orga o = new Orga(x[0], x[1], x[2], x[3]);
					orga.add(o);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void readTech(ArrayList<Tech> tech) {
		File f = new File("tech.csv");
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
	
	public static void readStandort(ArrayList<Standort> standort) {
		File f = new File("standort.csv");
		try {
			FileReader reader = new FileReader(f);
			BufferedReader buffReader = new BufferedReader(reader);
			String s;
			while ((s = buffReader.readLine()) != null) {
				String[] x = s.split(";");
				Standort i = new Standort(x[0], x[1], x[2], x[3]);
				standort.add(i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void readStandard(ArrayList<Standard> standard) {
		File f = new File("test.csv");
		try {
			FileReader reader = new FileReader(f);
			BufferedReader buffReader = new BufferedReader(reader);
			String s;
			while ((s = buffReader.readLine()) != null) {
				String[] x = s.split(";");
				Standard i = new Standard(x[0], x[1], x[2]);
				standard.add(i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void readFachProz(ArrayList<FachProz> fachProz) {
		File f = new File("fachProz.csv");
		try {
			FileReader reader = new FileReader(f);
			BufferedReader buffReader = new BufferedReader(reader);
			String s;
			while ((s = buffReader.readLine()) != null) {
				String[] x = s.split(";");
				FachProz i = new FachProz(x[0], x[1], x[2], x[3]);
				fachProz.add(i);
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
	
	public static void writeStandort(ArrayList<Standort> standort, BufferedWriter buffWriter) {
		
		for(Standort t : standort){
			t.writeNode(buffWriter);		
		}
	}

	public static void writeStandard(ArrayList<Standard> standard, BufferedWriter buffWriter) {
	
		for(Standard t : standard){
			t.writeNode(buffWriter);		
		}
	}

	public static void writeFachProz(ArrayList<FachProz> fachProz, BufferedWriter buffWriter) {
	
		for(FachProz t : fachProz){
			t.writeNode(buffWriter);		
		}
	}
	
	public static void writeRelation(ArrayList<Info> info, ArrayList<Orga> orga, ArrayList<Tech> tech, ArrayList<Standort> standort, ArrayList<Standard> standard, ArrayList<FachProz> fachProz, BufferedWriter bufferedWriter) {
	
		for(Info i : info){
			ArrayList<String> temp1 = new ArrayList<>(Arrays.asList(i.eingesetztIn.split(",")));
			ArrayList<String> temp2 = new ArrayList<>(Arrays.asList(i.technologien.split(",")));
			ArrayList<String> temp3 = new ArrayList<>(Arrays.asList(i.standardkonformität.split(",")));
			
			for(String tmp : temp1) {
				//Orga o = getOrgaByCode(orga, tmp);
				String a = "Match (a:Informationssystem),(b:Organisationseinheit) "
						+ "WHERE a.Code = '" + i.code + "' "
						+ "AND b.Code = '" + tmp + "' "
						+ "CREATE (a)-[r:Eingesetzt_In]->(b);\n";
//				Orga o = getOrgaByCode(orga, tmp);
//				String a = "";
//				if(o.uebergeordneteEinheit != null){
//					a = "Match (a:Informationssystem),(b:Organisationseinheit) "
//							+ "WHERE a.Code = '" + i.code + "' AND a.Name = '" + i.name + "' AND a.Beschreibung = '" + i.beschreibung + "' AND a.Anzahl_Installationen = '" + i.anzInstallationen + "' AND a.Subsysteme = '" + i.subsysteme + "' AND a.Investitionsgroesse = '" + i.investitionsgroesse + "' AND a.Eingesetzt_seit = '" + i.eingesetztSeit + "' "
//							+ "AND b.Name = '" + o.name +  "' AND b.Uebergeordnete_Einheit = '" + o.uebergeordneteEinheit +  "' "
//							+ "CREATE (a)-[r:Eingesetzt_In {Code: '" + tmp + "'}]->(b);\n";
//				} else {
//					a = "Match (a:Informationssystem),(b:Organisationseinheit) "
//							+ "WHERE a.Code = '" + i.code + "' AND a.Name = '" + i.name + "' AND a.Beschreibung = '" + i.beschreibung + "' AND a.Anzahl_Installationen = '" + i.anzInstallationen + "' AND a.Subsysteme = '" + i.subsysteme + "' AND a.Investitionsgroesse = '" + i.investitionsgroesse + "' AND a.Eingesetzt_seit = '" + i.eingesetztSeit + "' "
//							+ "AND b.Name = '" + o.name 
//							+ "CREATE (a)-[r:Eingesetzt_In {Code: '" + tmp + "'}]->(b);\n";
//				}
				
				try {
					bufferedWriter.write(a);
					bufferedWriter.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(String tmp : temp2) {
				//Tech t = getTechByCode(tech, tmp);
				String a = "Match (a:Informationssystem),(b:Technologie) "
						+ "WHERE a.Code = '" + i.code + "' "
						+ "AND b.Code = '" + tmp + "' "
						+ "CREATE (a)-[r:verwendet_Technologie]->(b);\n";
//				String a = "Match (a:Informationssystem),(b:Technologie) "
//						+ "WHERE a.Code = '" + i.code + "' AND a.Name = '" + i.name + "' AND a.Beschreibung = '" + i.beschreibung + "' AND a.Anzahl_Installationen = '" + i.anzInstallationen + "' AND a.Subsysteme = '" + i.subsysteme + "' AND a.Investitionsgroesse = '" + i.investitionsgroesse + "' AND a.Eingesetzt_seit = '" + i.eingesetztSeit + "' "
//						+ "AND b.Name = '" + t.name +  "' AND b.Beschreibung = '" + t.beschreibung + "' AND b.EndOfLife = '" + t.endOfLife +  "' "
//						+ "CREATE (a)-[r:verwendet_Technologie {Code: '" + tmp + "'}]->(b);\n";
				
				try {
					bufferedWriter.write(a);
					bufferedWriter.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			for(String tmp : temp3) {
				Standard t = getStandardByCode(standard, tmp.split(":")[0]);
				if(t != null){
					String a = "Match (a:Informationssystem),(b:Standard) "
							+ "WHERE a.Code = '" + i.code + "' "
							+ "AND b.Code = '" + tmp.split(":")[0] +  "' "
							+ "CREATE (a)-[r:verwendet_Standard {Art: '" + tmp.split(":")[1] + "'}]->(b);\n";
					
					try {
						bufferedWriter.write(a);
						bufferedWriter.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		for(FachProz f : fachProz) {
			ArrayList<String> temp1 = new ArrayList<>(Arrays.asList(f.standardkonformität.split(",")));
			
			for(String tmp : temp1) {
				//Info i = getInfoByCode(info, tmp);
				
				String a = "Match (a:Fachprozess),(b:Informationssystem) "
						+ "WHERE a.Code = '" + f.code +  "' "
						+ "AND b.Code = '" + tmp + "' "
						+ "CREATE (a)-[r:integriert]->(b);\n";
				
				try {
					bufferedWriter.write(a);
					bufferedWriter.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		for(Orga o : orga) {
			
			//Standort i = getStandortByCode(standort, o.standort);
			
			String a = "Match (a:Organisationseinheit),(b:Standort) "
					+ "WHERE a.Code = '" + o.code +  "' "
					+ "AND b.Code = '" + o.standort + "' "
					+ "CREATE (a)-[r:liegt_am]->(b);\n";
			
			try {
				bufferedWriter.write(a);
				bufferedWriter.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			//Orga k = getOrgaByCode(orga, o.uebergeordneteEinheit);
			
			if(!o.uebergeordneteEinheit.equals("unbekannt")) {
				String b = "Match (a:Organisationseinheit),(b:Organisationseinheit) "
						+ "WHERE a.Code = '" + o.code +  "' "
						+ "AND b.Code = '" + o.uebergeordneteEinheit + "' "
						+ "CREATE (a)-[r:gehoert_zu]->(b);\n";
					
				try {
					bufferedWriter.write(a);
					bufferedWriter.write(b);
					bufferedWriter.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
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

	public static Standort getStandortByCode(ArrayList<Standort> standort, String code) {
		
		for(Standort t : standort) {
			if(t.code.equals(code))
				return t;
		}
		
		return null;
	}
	
	public static Standard getStandardByCode(ArrayList<Standard> standard, String code) {
		
		for(Standard t : standard) {
			if(t.code.equals(code))
				return t;
		}
		
		return null;
	}
	
	public static Info getInfoByCode(ArrayList<Info> info, String code) {
		
		for(Info t : info) {
			if(t.code.equals(code))
				return t;
		}
		
		return null;
	}


}
