package myPackage;

import java.io.File;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLActions {
    String filename;
    String parentName;
    private String[] fieldList;
    private String[] fieldValue;

    public XMLActions(String filename, String parentName, String[] fieldList, String[] fieldValue) {
        this.filename = filename;
        this.parentName = parentName;
        this.fieldList = fieldList;
        this.fieldValue = fieldValue;
        
    }
    
    
    public void save(String id){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File(this.filename);
            Document doc = db.parse(f);
            doc.normalize();
            
            Element root = doc.getDocumentElement();
            
            Element nodeElement = doc.createElement(this.parentName);
            root.appendChild(nodeElement);
            
            Element[] p = null;
            for (int i = 0; i<this.fieldList.length; i++){
                p[i] = doc.createElement(this.fieldList[i]);
            }
//            Element p1 = doc.createElement("Name");
//            Element p2 = doc.createElement("Family");
//            Element p3 = doc.createElement("Email");
            
            for (int i = 0; i<this.fieldValue.length; i++){
                p[i].appendChild(doc.createTextNode(this.fieldValue[i]));
            }
//            p1.appendChild(doc.createTextNode(this.name));
//            p2.appendChild(doc.createTextNode(this.family));
//            p3.appendChild(doc.createTextNode(this.email));
            
            for (int i = 0; i<this.fieldList.length; i++){
                nodeElement.appendChild(p[i]);
            }
            
//            nodeElement.appendChild(p1);
//            nodeElement.appendChild(p2);
//            nodeElement.appendChild(p3);
            
            Attr at = doc.createAttribute("id");
            at.setValue(id);
            nodeElement.setAttributeNode(at);
            

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(f);
            t.transform(source, result);
            JOptionPane.showMessageDialog(null, "Save has been done successfully");
            
            
        }
        catch(Exception ex){
        } 
    }
    
    
}
