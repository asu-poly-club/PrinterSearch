/** @author Jake Leonard
 * 
 */

import java.util.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;


public class Driver {

	public static void main(String[] args) {
		
		PrinterList printerList = generatePrinterList();
		
		/* The following is simply a rudimentary test of search functionality
		 * 
		 */
		Scanner scanner = new Scanner(System.in);
		boolean newInput = true;
		do {
			
			System.out.println("Enter your specifications: ");

			System.out.println("Enter 'ADD' to add a new printer");

            // Branch of user adding a new printer to the list
            if (scanner.nextLine().equals("ADD")) {
                addPrinter();
                continue;
            }
			
			System.out.println("Tension: ");
			double tension = scanner.nextDouble();
			
			System.out.println("Compression: ");
			double compression = scanner.nextDouble();
			
			System.out.println("Impact: ");
			double impact = scanner.nextDouble();
			
			System.out.println("Complexity: ");
			double complexity = scanner.nextDouble();
			
			System.out.println("Lead Time: ");
			double leadTime = scanner.nextDouble();
			
			System.out.println("Ease of Customizing (true/false): "); // Will need to better validate this input.
			boolean eoc = scanner.nextBoolean();
			
			System.out.println("Range of Materials: ");
			scanner.nextLine(); // Clear buffer
			String rom = scanner.nextLine();
			
			System.out.println("Tolerance: ");
			double tolerance = scanner.nextDouble();
			
			System.out.println("Desired Finish Type: ");
			scanner.nextLine();
			String finish = scanner.nextLine();
			
			printerList.setMatches(tension, compression, impact, complexity, leadTime, eoc, storeROM(rom), tolerance, finish);
			
			outputSearchedList(printerList);
			
			
			// Continue entering new input?
			System.out.println("Perform New Search? (y/n)");
			String continueSearch = scanner.nextLine();
			if(continueSearch.equalsIgnoreCase("n")){
				newInput = false;
				System.out.println("Program ending...");
			} else if(continueSearch.equalsIgnoreCase("no")) {
				newInput = false;
				System.out.println("Program ending...");
			} else if ((continueSearch.equalsIgnoreCase("yes")) || (continueSearch.equalsIgnoreCase("y"))) {
				newInput = true;
			} else {
				newInput = false;
				System.out.println("Program ending...");
			}
				
		} while(newInput == true);


	}
	
	/*
	 * Generates printer list from XML file
	 * Returns list to calling method
	 */
	public static PrinterList generatePrinterList(){
		
		PrinterList printerList = new PrinterList();
		
		// Build list of Printer objects from XML file;
		try{
			File file = new File("printers.xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			
			System.out.println("Root element:" + document.getDocumentElement().getNodeName());
			
			NodeList nList = document.getElementsByTagName("printer");
			
			/* The following iterates through the child-nodes of "printer" tags,
			 * retrieving the text within as String, converts, and then printers to console
			 * for evaluation.
			 */
			for(int i=0;i<nList.getLength();i++){
				
				String name;
				double tension;
				double compression;
				double impact;
				double complexity;
				double leadTime;
				boolean eoc;
				String [] romArrayEX;
				String rom = "";
				double tolerance;
				String finish = "";
				
				Node nNode = nList.item(i);
				
				System.out.println(nNode.getNodeName());
				
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					
					Element eElement = (Element)nNode;

					name = getString("NAME", eElement);
					//name = eElement.getElementsByTagName("NAME").item(0).getTextContent();
					System.out.println(name);


					tension = Double.parseDouble(getString("TENSION", eElement));
					//tension = Double.parseDouble(eElement.getElementsByTagName("TENSION").item(0).getTextContent());
					System.out.println(tension);
					
					compression = Double.parseDouble(getString("COMPRESSION", eElement));
					//compression = Double.parseDouble(eElement.getElementsByTagName("COMPRESSION").item(0).getTextContent());
					System.out.println(compression);
					
					impact = Double.parseDouble(getString("IMPACT", eElement));
					//impact = Double.parseDouble(eElement.getElementsByTagName("IMPACT").item(0).getTextContent());
					System.out.println(impact);
					
					complexity = Double.parseDouble(getString("PART_COMPLEXITY", eElement));
					//complexity = Double.parseDouble(eElement.getElementsByTagName("PART_COMPLEXITY").item(0).getTextContent());
					System.out.println(complexity);
					
					leadTime = Double.parseDouble(getString("LEAD_TIME", eElement));
					//leadTime = Double.parseDouble(eElement.getElementsByTagName("LEAD_TIME").item(0).getTextContent());
					System.out.println(leadTime);
					
					eoc = Boolean.valueOf((getString("EOC", eElement)));
					//eoc = Boolean.valueOf(eElement.getElementsByTagName("EOC").item(0).getTextContent());
					System.out.println(eoc);


					// SPECIFICALLY ROM SECTION
					NodeList listROM = eElement.getElementsByTagName("ROM");  // Now we create a new list specifically for just ROM.
					String lineToBeAdded = listROM.item(0).getTextContent();            // Retrieve single string inputted value, because input is considered one element within xml tag.
					romArrayEX = storeROM(lineToBeAdded);						// Call our method to update changes to the rom.
					for (int jojo = 0; jojo < romArrayEX.length; jojo++) {
						System.out.println(romArrayEX[jojo]);
					}
					
					tolerance = Double.parseDouble(getString("TOLERANCE", eElement));
					//tolerance = Double.parseDouble(eElement.getElementsByTagName("TOLERANCE").item(0).getTextContent());
					System.out.println(tolerance);
					
					finish = getString("FINISH", eElement);
					//finish = eElement.getElementsByTagName("FINISH").item(0).getTextContent();
					System.out.println(finish);
					
					//printerList.addPrinter(new Printer(name, tension, compression, impact, complexity,leadTime, eoc, rom, tolerance, finish));
					printerList.addPrinter(new Printer(name, tension, compression, impact, complexity,leadTime, eoc, romArrayEX, tolerance, finish));
					System.out.println("Added: " + printerList.getPrinter(0).getName());
				}
			}
				
			}catch(FileNotFoundException e){
				System.out.println("File Not Found" + e);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return printerList;
	}


	/** Adds a printer element to printers.xml
     * @author trevor forrey
     * @param printers
     */
    public static void addPrinter() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nYou Are Adding A New Printer\n");

        /**
         * Taking in printer parameters from user
         */
        System.out.println("Name of printer: ");
        String printerName = scanner.nextLine();

        System.out.println("Tension: ");
        double printerTension = scanner.nextDouble();

        System.out.println("Compression: ");
        double printerCompression = scanner.nextDouble();

        System.out.println("Impact: ");
        double printerImpact = scanner.nextDouble();

        System.out.println("Complexity: ");
        double printerComplexity = scanner.nextDouble();

        System.out.println("Lead Time: ");
        double printerLeadTime = scanner.nextDouble();

        System.out.println("Ease of Customizing (true/false): "); // Will need to better validate this input.
        boolean printerEoc = scanner.nextBoolean();

        System.out.println("Tolerance: ");
        double printerTolerance = scanner.nextDouble();

        System.out.println("Desired Finish Type: ");
        scanner.nextLine();
        String printerFinish = scanner.nextLine();


        try {
            /**
             * Creates link to xml file
             */
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse("printers.xml");
            Element root = document.getDocumentElement();

            /**
             * Creates printer root element
             */
            Element newPrinter = document.createElement("printer");


            /**
             * Inserts user given parameters into the new printer xml element in the following order:
             *
             * Creates element to represent attribute of printer
             * appends child element that holds value of the attribute
             * appends element to the new printer element
             */
            Element name = document.createElement("NAME");
            name.appendChild(document.createTextNode(printerName));
            newPrinter.appendChild(name);

            Element tension = document.createElement("TENSION");
            tension.appendChild(document.createTextNode(Double.toString(printerTension)));
            newPrinter.appendChild(tension);

            Element compression = document.createElement("COMPRESSION");
            compression.appendChild(document.createTextNode(Double.toString(printerCompression)));
            newPrinter.appendChild(compression);

            Element impact = document.createElement("IMPACT");
            impact.appendChild(document.createTextNode(Double.toString(printerImpact)));
            newPrinter.appendChild(impact);

            Element leadTime = document.createElement("LEADTIME");
            leadTime.appendChild(document.createTextNode(Double.toString(printerLeadTime)));
            newPrinter.appendChild(leadTime);

            Element easeOfChange = document.createElement("EASEOFCHANGE");
            easeOfChange.appendChild(document.createTextNode(Boolean.toString(printerEoc)));
            newPrinter.appendChild(easeOfChange);

            Element tolerance = document.createElement("TOLERANCE");
            tolerance.appendChild(document.createTextNode(Double.toString(printerTolerance)));
            newPrinter.appendChild(tolerance);

            Element finish = document.createElement("FINISH");
            finish.appendChild(document.createTextNode(printerFinish));
            newPrinter.appendChild(finish);


            root.appendChild(newPrinter);


            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("printers.xml");
            transformer.transform(source, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


	
	public static void outputSearchedList(PrinterList printers){
		
		ArrayList<Printer> list = printers.getPrinterList();
				
		ArrayList<Printer> outputList = new ArrayList<Printer>(); // Handle if no matches exist and is empty. 
		
		for(int i=0;i<list.size();i++){
		
			int matches = 0;
			
			for(Printer printer : list){
								
				int currentMatches = printer.getTotalMatches();
				
				if(currentMatches > 0 && !(outputList.contains(printer))){
					matches = printer.getTotalMatches();
					outputList.add(printer);
				}
			}
			
			// Basic Sorting of list
			int position, scan;
			Printer tempPrinter;

			for(position=list.size()-1;position >=0;position--){
				for(scan=0;scan<=position-1;scan++){
					if(list.get(scan).getTotalMatches() > list.get(scan+1).getTotalMatches()){
						
						tempPrinter = list.get(scan+1);
						list.set(scan+1, list.get(scan));
						list.set(scan, tempPrinter);

					}		
				}
			}
		}
		
		// Output to Console - Sorted by highest matches first.
		for(int i=list.size()-1;i>=0;i--){
			System.out.println("\n\n---------------------------------");
			System.out.println("Printer Name: " + outputList.get(i).getName());
			System.out.println("# Of Matches: " + outputList.get(i).getTotalMatches()); 
			System.out.println("\n----------------------------------");
		}
	}

	public static String getString(String tagName, Element element) {
		NodeList list = element.getElementsByTagName(tagName);
		if (list != null && list.getLength() > 0) {
			NodeList subList = list.item(0).getChildNodes();

			if (subList != null && subList.getLength() > 0) {
				return subList.item(0).getNodeValue();
			}
		}

		return null;
	}

	public static String[] storeROM(String romInput) {
		//String lineToBeAdded = listROM.item(0).getTextContent();            // Retrieve single string inputted value, because input is considered one element within xml tag.

		String [] romArray = romInput.split("\\s+");                   // Split our storing between whitespace.

		System.out.println("The length of this god damn fucking array is: " + romArray.length);

		for (int jojo = 0; jojo < romArray.length; jojo++) {
			System.out.println("The ROM attribute in this bitch is: " + romArray[jojo]);
		}
		return romArray;
	}
}
