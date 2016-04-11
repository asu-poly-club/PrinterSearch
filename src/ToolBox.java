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

import javax.swing.JComboBox;
import javax.xml.parsers.*;

/**
 * A test of an implementation of the PrinterList class by
 * allowing the user to add printers to the printer list and searching
 * the subsequent list.
 *
 * @author  Jake Leonard, Trevor Forrey, (others on team), Marcinina Alvaran
 * @version (TODO: to be included by original programmer)
 * @see PrinterList
 * @see Printer
 */
public class ToolBox {
	/**
	 * Generates printer list from XML file and returns list to calling method.
	 *
	 * @return the printer list generated from XML file
	 */
	public static PrinterList generatePrinterList(){
		DocumentBuilderFactory documentBuilderFactory;
		DocumentBuilder documentBuilder;
		Document document;
		NodeList nList;
		Node nNode;
		Element eElement;
		PrinterList printerList;


		printerList = new PrinterList();

		// Build list of Printer objects from XML file;
		try{
			// This is a important line, for when using a mac directory must be switched, the directory has \\ because of eclipse.
			String stringSearch = System.getProperty("os.name");
			String keyword = "Mac";
			File file = new File("src\\printers.xml");
			Boolean found = Arrays.asList(stringSearch.split(" ")).contains(keyword);
			if(found){
				File fileChange = new File("src/printers.xml");	// Mac image path.
				file = fileChange;
			}
			//File file = new File("src/printers.xml");
			//File file = new File("src\\printers.xml");
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(file);

			// Display listed printers in console
			System.out.println(
					"Root element:" +
			        document.getDocumentElement().getNodeName());
			nList = document.getElementsByTagName("printer");

			for(int i=0;i<nList.getLength();i++){
				nNode = nList.item(i);
				System.out.println(nNode.getNodeName());
				displayPrinterNodes(nNode, printerList);
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

    /**
     * Iterates through the child-nodes of "printer" tag strings,
     * converts to appropriate data type, and then printer to console
     * for evaluation.
     */
	private static void displayPrinterNodes(Node node, PrinterList printers)
	{
		String name, vendor;
		double tension, compression, impact, tolerance, complexity, leadTime;
		boolean customizable;
		String finish = "", materialsString = "";
		String[] materialsArray;
		HashSet<String> materialsSet = new HashSet<String>();
		Element eElement;

		// Retrieve parameter info from listed printers
		if(node.getNodeType() == Node.ELEMENT_NODE)
		{
			eElement = (Element)node;
			name = getString("NAME", eElement);
			//System.out.println(name);

			vendor = getString("VENDOR", eElement);
			//System.out.println(vendor);

			tension = Double.parseDouble(getString("TENSION", eElement));
			//System.out.println(tension);

			compression = Double.parseDouble(
					getString("COMPRESSION", eElement));
			//System.out.println(compression);

			impact = Double.parseDouble(getString("IMPACT", eElement));
			//System.out.println(impact);

			complexity = Double.parseDouble(
					getString("PART_COMPLEXITY", eElement));
			//System.out.println(complexity);

			leadTime = Double.parseDouble(getString("LEAD_TIME", eElement));
			//System.out.println(leadTime);

			customizable = Boolean.valueOf((getString("EOC", eElement)));
			//System.out.println(customizable);

			materialsString = getString("ROM", eElement);
			//System.out.println(materialsString);
			materialsSet = stringToHashSet(materialsString);

			tolerance = Double.parseDouble(getString("TOLERANCE", eElement));
			//System.out.println(tolerance);

			finish = getString("FINISH", eElement);
			//System.out.println(finish);

			printers.addPrinter(new Printer(
					name, vendor, tension, compression, impact, complexity,
					leadTime, customizable, materialsSet, tolerance, finish));
			System.out.println(
					"Added: " +
			        printers.getPrinter(0).getPrinterName());
		}
	}


/**
 * Adds a printer element to printers.xml based on user input.
 *
 * @param printerName
 * @param printerTension
 * @param printerCompression
 * @param printerPartComplexity
 * @param printerROM
 * @param printerImpact
 * @param printerLeadTime
 * @param printerEaseOfChange
 * @param printerTolerance
 * @param printerFinish
 */
	public void addPrinter(String printerName, String vendor, String printerTension, String printerCompression, String printerPartComplexity, String printerROM, String printerImpact,
  		String printerLeadTime, String printerEaseOfChange, String printerTolerance, String printerFinish) {
        try {
            /*
             * Creates link to xml file
             */
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
					  String stringSearch = System.getProperty("os.name");
						String keyword = "Mac";
						Document document = documentBuilder.parse("src\\printers.xml");;
						StreamResult result = new StreamResult("src\\printers.xml");
						Boolean found = Arrays.asList(stringSearch.split(" ")).contains(keyword);
						if(found){
							Document documentChange = documentBuilder.parse("src/printers.xml");	// Mac image path.
							StreamResult resultChange = new StreamResult("src/printers.xml");	// Mac image path.
							document = documentChange;
							result = resultChange;
						}

			//Document document = documentBuilder.parse("src/printers.xml");		// Mac directory path.
			//Document document = documentBuilder.parse("src\\printers.xml");		// Windows directory path.
            Element root = document.getDocumentElement();

            /*
             * Creates printer root element
             */
            Element newPrinter = document.createElement("printer");


            /*
             * Inserts user given parameters into the new printer xml element in the following order:
             *
             * Creates element to represent attribute of printer,
             * appends child element that holds value of the attribute,
             * appends element to the new printer element
             */
            Element name = document.createElement("NAME");
            name.appendChild(document.createTextNode(printerName));
            newPrinter.appendChild(name);

			Element vendors = document.createElement("VENDOR");
			vendors.appendChild(document.createTextNode(vendor));
			newPrinter.appendChild(vendors);

            Element tension = document.createElement("TENSION");
            tension.appendChild(document.createTextNode(printerTension));
            newPrinter.appendChild(tension);

            Element compression = document.createElement("COMPRESSION");
            compression.appendChild(document.createTextNode(printerCompression));
            newPrinter.appendChild(compression);

            Element impact = document.createElement("IMPACT");
            impact.appendChild(document.createTextNode(printerImpact));
            newPrinter.appendChild(impact);

            Element partComplexity = document.createElement("PART_COMPLEXITY");
            partComplexity.appendChild(document.createTextNode(printerPartComplexity));
            newPrinter.appendChild(partComplexity);

            Element leadTime = document.createElement("LEAD_TIME");
            leadTime.appendChild(document.createTextNode(printerLeadTime));
            newPrinter.appendChild(leadTime);

            Element easeOfChange = document.createElement("EOC");
            easeOfChange.appendChild(document.createTextNode(printerEaseOfChange));
            newPrinter.appendChild(easeOfChange);

            Element ROM = document.createElement("ROM");
            ROM.appendChild(document.createTextNode(printerROM));
            newPrinter.appendChild(ROM);

            Element tolerance = document.createElement("TOLERANCE");
            tolerance.appendChild(document.createTextNode(printerTolerance));
            newPrinter.appendChild(tolerance);

            Element finish = document.createElement("FINISH");
            finish.appendChild(document.createTextNode(printerFinish));
            newPrinter.appendChild(finish);


            root.appendChild(newPrinter);


            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
						transformer.setOutputProperty(OutputKeys.INDENT, "yes");
  					transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			//StreamResult result = new StreamResult("src/printers.xml");	// Mac directory path.
			//StreamResult result = new StreamResult("src\\printers.xml");	// Windows directory path.
            transformer.transform(source, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
	 * Displays a list of printer matches sorted from highest number of matching
	 * attributes to lowest on the console.
	 *
	 * @param printers the ArrayList of printers
	 */
	public static ArrayList<Printer> outputSearchedList(PrinterList printers){
		// To-Do: Handle if no matches exist and is empty.

		ArrayList<Printer> list = printers.getPrinterList();
		ArrayList<Printer> outputList = new ArrayList<Printer>();

		for(Printer printer : list){
			int matches = 0;
			int currentMatches = printer.getTotalMatches();

			if(currentMatches > 0 && !(outputList.contains(printer))){
				matches = printer.getTotalMatches();
				outputList.add(printer);
			}
		}

		// Basic Sorting of list
		int position;
		boolean keepLooking = true;
		Printer tempPrinter;

		while(keepLooking){
			keepLooking = false;
			for(position=0;position < outputList.size()-1;position++){
				if(outputList.get(position).getTotalMatches() > outputList.get(position+1).getTotalMatches()){
					System.out.println(outputList.get(position).getTotalMatches() +  " is greater than " + outputList.get(position+1).getTotalMatches());
					tempPrinter = outputList.get(position+1);
					System.out.println("Swapping position " + position + ": " + outputList.get(position));
					outputList.set(position+1, outputList.get(position));
					outputList.set(position, tempPrinter);
					keepLooking = true;
				}
			}
		}


	// Output to Console - Sorted by highest matches first.
	/*for(int i=list.size()-1;i>=0;i--){
		System.out.println(
				"\n\n---------------------------------" +
		        "     Printer Name: " + outputList.get(i).getPrinterName() +
		        "Number Of Matches: " + outputList.get(i).getTotalMatches() +
		        "\n----------------------------------");
	}*/
	return outputList;
}

	/**
	 * Insert description here.
	 *
	 * @param tagName the String with a printer tag
	 * @param element the Element
	 * @return a String with...
	 */
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


	/* TODO: Commented out to try HashSet implementation of rom
	public static String[] storeROM(String romInput) {
		//String lineToBeAdded = listROM.item(0).getTextContent();            // Retrieve single string inputted value, because input is considered one element within xml tag.

		String [] romArray = romInput.split("\\s+");                   // Split our storing between whitespace.

		System.out.println("The length of this god damn fucking array is: " + romArray.length);

		for (int jojo = 0; jojo < romArray.length; jojo++) {
			System.out.println("The ROM attribute in this bitch is: " + romArray[jojo]);
		}
		return romArray;
	}
	*/

	/**
	 * Converts a String list delimited by ", " (without quotes) into a HashSet.
	 * @param list
	 * @return
	 */
	public static HashSet<String> stringToHashSet(String list) {
		String[] listArray;
		HashSet<String> hashSet = new HashSet<String>();

		listArray = list.split(", ");
		for (String element : listArray)
			hashSet.add(element);

		return hashSet;
		}
	/**
	 * 
	 */
	private static Object [] getVendor()
	{
		DocumentBuilderFactory documentBuilderFactory;
		DocumentBuilder documentBuilder;
		Document document;
		Node nNode;
		ArrayList<String> vendorList = new ArrayList<String>();
		

		// Build list of Printer objects from XML file;
		try{
			// This is a important line, for when using a mac directory must be switched, the directory has \\ because of eclipse.
			String stringSearch = System.getProperty("os.name");
			String keyword = "Mac";
			File file = new File("src\\vendors.xml");
			Boolean found = Arrays.asList(stringSearch.split(" ")).contains(keyword);
			if(found){
				File fileChange = new File("src/vendors.xml");	// Mac image path.
				file = fileChange;
			}
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(file);

			// Display listed printers in console
			Element root = document.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("vendor");

			for(int i=0;i<nodeList.getLength();i++){
				nNode = nodeList.item(i);
				Element element = (Element) nNode;
				vendorList.add(element.getAttribute("name"));
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
		return vendorList.toArray();
	}
	public static String [] getVendorList()
	{
		Object [] vendors= ToolBox.getVendor();
		String [] vendorList = new String[vendors.length];
		
		int i = 0;
		for(Object name:vendors)
		{
			vendorList[i] = (String) name;
			i++;
		}
		return vendorList;
	}
	/**
	 * getting the material list from xml file
	 */
	private static Object [] getMaterial()
	{
		DocumentBuilderFactory documentBuilderFactory;
		DocumentBuilder documentBuilder;
		Document document;
		Node nNode;
		ArrayList<String> vendorList = new ArrayList<String>();
		

		// Build list of Printer objects from XML file;
		try{
			// This is a important line, for when using a mac directory must be switched, the directory has \\ because of eclipse.
			String stringSearch = System.getProperty("os.name");
			String keyword = "Mac";
			File file = new File("src\\materials.xml");
			Boolean found = Arrays.asList(stringSearch.split(" ")).contains(keyword);
			if(found){
				File fileChange = new File("src/materials.xml");	// Mac image path.
				file = fileChange;
			}
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(file);

			// Display listed printers in console
			Element root = document.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("material");

			for(int i=0;i<nodeList.getLength();i++){
				nNode = nodeList.item(i);
				Element element = (Element) nNode;
				vendorList.add(element.getAttribute("name"));
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
		return vendorList.toArray();
	}
	/**
	 * creates a String array from the Object array generated from the xml file
	 * @return Material List
	 */
	public static String [] getMaterialList()
	{
		Object [] materials= ToolBox.getMaterial();
		String [] materialsStr = new String[materials.length];
		int i = 0;
		for(Object name:materials)
		{
			materialsStr[i] = (String) name;
			i++;
		}
		return materialsStr;
	}
	/**
	 * getting the Finish list from xml file
	 */
	private static Object [] getFinish()
	{
		DocumentBuilderFactory documentBuilderFactory;
		DocumentBuilder documentBuilder;
		Document document;
		Node nNode;
		ArrayList<String> vendorList = new ArrayList<String>();
		

		// Build list of Printer objects from XML file;
		try{
			// This is a important line, for when using a mac directory must be switched, the directory has \\ because of eclipse.
			String stringSearch = System.getProperty("os.name");
			String keyword = "Mac";
			File file = new File("src\\finish.xml");
			Boolean found = Arrays.asList(stringSearch.split(" ")).contains(keyword);
			if(found){
				File fileChange = new File("src/finish.xml");	// Mac image path.
				file = fileChange;
			}
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(file);

			// Display listed printers in console
			Element root = document.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("finish");

			for(int i=0;i<nodeList.getLength();i++){
				nNode = nodeList.item(i);
				Element element = (Element) nNode;
				vendorList.add(element.getAttribute("name"));
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
		return vendorList.toArray();
	}
	
	public static String [] getFinishList()
	{
		Object [] finish= ToolBox.getFinish();
		String [] finishList = new String[finish.length];
		
		int i = 0;
		for(Object name:finish)
		{
			finishList[i] = (String) name;
			i++;
		}
		return finishList;
	}
}
