package co.tula.tacc.tools;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class TaccDomHelper
{
	public static final String TAG = "TaccDomHelper";
	
	private TaccDomHelper() {
	}
	
	public static Document getDomElement(final String xml){
		final Document doc;
		final DocumentBuilderFactory dbf;
		final DocumentBuilder db;
		
		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is); 

		} catch (ParserConfigurationException e) {
			Log.e(TAG, "Error: " + e.getMessage());
			return null;
		} catch (SAXException e) {
			Log.e(TAG, "Error: " + e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e(TAG, "Error: " + e.getMessage());
			return null;
		}
		
		return doc;
	}
	
	final public static String getNodeValue(Node e)
	{
        Node child;
        if( e != null){
            if (e.hasChildNodes()){
                for(child = e.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        
        return "";
	}
}
