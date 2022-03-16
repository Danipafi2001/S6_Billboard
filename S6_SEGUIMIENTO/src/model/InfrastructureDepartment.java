package model;
import java.io.*;
import java.util.ArrayList;

public class InfrastructureDepartment {

	private final String SEPARATOR = "\\|";
	private final String BILLBOARD_FILE_NAME = "data/billboard.bbd";
	private ArrayList<Billboard> billboards;

	public InfrastructureDepartment() throws IOException, ClassNotFoundException {
		billboards = new ArrayList<Billboard>();
		loadBillboards();
	}

	public void addBilboard(double width, double height, boolean inUse, String brand) throws IOException{
		Billboard billboard = new Billboard(height, width, inUse, brand);
		billboards.add(billboard);
		saveBillboards();
	}
	
	private void saveBillboards() {
		try {
			ObjectOutputStream oStream = new ObjectOutputStream(new FileOutputStream(BILLBOARD_FILE_NAME));
			for(int i=0; i<billboards.size(); i++) {
				oStream.writeObject(billboards.get(i));
			}
			oStream.close();
		} catch(IOException e) {
			
		}
	}
	
	private void loadBillboards() {
		try {
			ObjectInputStream oInStream = new ObjectInputStream(new FileInputStream(BILLBOARD_FILE_NAME));
			try {
				while(true) {
					Billboard aux = (Billboard)oInStream.readObject();
					billboards.add(aux);
				}
			} catch(EOFException e) {
				
			} catch (ClassNotFoundException e) {
				
			} 
			oInStream.close();
		}catch(IOException e) {
		} 
	}

	public void ExportDangerousBillboardReport(String fileName) throws FileNotFoundException  {
		PrintWriter pWriter = new PrintWriter(fileName);
		int cent = 0;
		pWriter.println("===========================");
		pWriter.println("DANGEROUS BILLBOARD REPORT");
		pWriter.println("===========================");
		
		for (Billboard billboard : billboards) {
			if (billboard.calculateArea() >= 200000) {
				cent ++;
				pWriter.println(cent + ". Billboard " + billboard.getBrand() + " with area " + billboard.calculateArea());
			}
			
		}
		pWriter.close();
	}

	public void importData(String fileName) throws IOException{
		BufferedReader bReader = new BufferedReader(new FileReader(fileName));
		String line = bReader.readLine(); 
		int cent = 1;
		while (line != null ) {
			if (cent != 1) {
				String[] parts = line.split(SEPARATOR);
				double width = Double.parseDouble( parts[0] );
				double height = Double.parseDouble( parts[1] );
				boolean inUse = Boolean.parseBoolean( parts[2] );
				String brand = parts[3];
				Billboard billboard = new Billboard(height, width, inUse, brand);
				billboards.add(billboard);
			}
			cent ++;
			
			line = bReader.readLine();
		}
		saveBillboards();
		bReader.close();
	}

	@Override
	public String toString() {
		String message = "";
		for (Billboard billboard : billboards) {
			message += "\n++ " + billboard.getBrand() + " ++ Area: " + billboard.calculateArea() + " ++ in use: " + billboard.isInUse();
			
		}
		return message;
	}
}
