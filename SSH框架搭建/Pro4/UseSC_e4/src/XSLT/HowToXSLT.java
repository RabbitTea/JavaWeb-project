package XSLT;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

public class HowToXSLT {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		try {
			TransformerFactory transFactory = TransformerFactory.newInstance();
			
			Transformer transformer = 
					transFactory.newTransformer
					(new javax.xml.transform.stream.StreamSource
							("WebContent/pages/success_view.xsl"));
			
			transformer.transform
			(new javax.xml.transform.stream.StreamSource
					("WebContent/pages/success_view.xml"), 
			 new javax.xml.transform.stream.StreamResult
			        (new FileOutputStream("WebContent/success_view.html")));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		System.out.println("transform successly");
	}

}
