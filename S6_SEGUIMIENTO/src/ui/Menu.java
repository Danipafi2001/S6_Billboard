package ui;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import model.InfrastructureDepartment;

public class Menu {
	
	private InfrastructureDepartment infraDept;
	private Scanner in;

	public Menu() {
		in = new Scanner(System.in);
		try {
			infraDept = new InfrastructureDepartment();
		} catch (IOException ioException) {
			System.out.println("\n couldn load the saved data");
		} catch (ClassNotFoundException classNotFoundException) {
			System.out.println("\n couldn load the saved data");
		}
	
	}

	public void startProgram() {
		boolean exit = false;
		int option = 0;
		System.out.println("WELCOME");

		do {
			showMenu();

			try {
				option = Integer.parseInt(in.nextLine());

			} catch (NumberFormatException numberFormatException) {
				option = 0;
				
			}
			
			exit = runOption(option);

		} while (!exit);
		
		in.close();
	}

	private void showMenu() {
		System.out.println("MENU");
		System.out.println("1) Add billboard");
		System.out.println("2) Show billboard");
		System.out.println("3) Import billboard");
		System.out.println("4) Export hazard report");
		System.out.println("5) Leave");

	}

	private boolean runOption(int option) {
		boolean exit = false;
		switch (option) {
		case 1:
			boolean optionExit;
			do {
				optionExit = runOptionOne();
				
			} while (!optionExit);
			
			break;

		case 2:
			runOptionTwo();
			break;

		case 3:
			runOptionTree();
			break;
		case 4:
			runOptionFour();
			break;

		case 5:
			exit = true;
			break;

		default:
			System.out.println(
					"Not a valid option. \nPlease only type the number next to the option (1 to 4), or 5 to exit the program\n-");
			break;
		}
		return exit;
	}

	private boolean runOptionOne() {
		boolean exit = false;
		double width = 0;
		double heigth = 0;
		boolean inUse  = false;
		String brand = "";
		
		try {
			System.out.println("Enter the width of the billboard");
			width = Double.parseDouble(in.nextLine());
			if (width <= 0) {
				throw new NumberFormatException();
			}
			
			System.out.println("Enter the heigth of the billboard");
			heigth = Double.parseDouble(in.nextLine());
			if (heigth <= 0) {
				throw new NumberFormatException();
			}
			
			System.out.println("Billboard in use ?\n" + "1) YES\n" + "2) NO");
			int option = Integer.parseInt(in.nextLine());
			if (option == 1 || option == 2) {
				inUse = (option == 1) ? true : false;

			} else {
				System.out.println("Remember to only type");
				throw new NumberFormatException();
			}
			
			System.out.println("Enter the brand of the billboard");
			brand = in.nextLine();
			
			infraDept.addBilboard(width, heigth, inUse, brand);
			exit = true;
			
		} catch (NumberFormatException numberFormatException) {
			System.out.println("\nInvalid option format entered.\n please only enter valid values / options.");
		} catch (IOException ioException) {
			System.out.println("\nError, new bilboard was not saved.");
		}
		
		return exit;
	}

	private void runOptionTwo() {
		System.out.println("Billboard list:");
		System.out.println(infraDept.toString());

	}

	private void runOptionTree() {
		try {
			System.out.println("Enter the file name to import. ex: data/BillboardData.csv ");
			String fileName = in.nextLine(); 
			infraDept.importData(fileName);
			System.out.println("Data succesfully imported, check on the 2nd option in the menu ");
		} catch (IOException ioException) {
			System.out.println("The data couldnt be imported, please try again.");
		}

	}

	private void runOptionFour() {
		try {
			System.out.println("- Enter then file name.\n ex: data/DangerousBillboardReport.txt");
			String fileName = in.nextLine();
			infraDept.ExportDangerousBillboardReport(fileName);
			System.out.println("Data succesfully exported, you can find the file here: " + fileName);
		} catch (FileNotFoundException fileNotFoundException) {
			System.out.println("The data couldnt be exported, please try again.");
			
		}

	}

	public static void cls() {
		try {
			new ProcessBuilder("cmd","/c","csl").inheritIO().start().waitFor();
		}catch (Exception E) {
			System.out.println(E);
		}
			
	}	

}