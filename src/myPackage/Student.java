package myPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Student {
    private String id;
    private String name;
    private String family;
    private String email;

    public Student(String name, String family, String email) {
        this.name = name;
        this.family = family;
        this.email = email;
    }
    
    public Student(){
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void save(int id){
        try{
            String MyId = this.id;
            DeleteElement(MyId);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File("D:\\Backend\\13\\AppProject\\src\\xml\\Student.xml");
            Document doc = db.parse(f);
            doc.normalize();
            
            Element root = doc.getDocumentElement();
            
            Element nodeElement = doc.createElement("Student");
            root.appendChild(nodeElement);
            
            Element p1 = doc.createElement("Name");
            Element p2 = doc.createElement("Family");
            Element p3 = doc.createElement("Email");
            
            
            
            p1.appendChild(doc.createTextNode(this.name));
            p2.appendChild(doc.createTextNode(this.family));
            p3.appendChild(doc.createTextNode(this.email));
            
            
            nodeElement.appendChild(p1);
            nodeElement.appendChild(p2);
            nodeElement.appendChild(p3);
            
            Attr at = doc.createAttribute("id");
            at.setValue(Integer.toString(id));
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
    
    
        public static List<Student> getStudent() {
        List<Student> objects = new ArrayList<>();
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("D:\\Backend\\13\\AppProject\\src\\xml\\Student.xml");
            doc.normalize();
            NodeList list = doc.getElementsByTagName("Student");
            for(int i=0; i<list.getLength(); i++){
                Node n = list.item(i);
                Element e = (Element) n;
                Student c = new Student(e.getElementsByTagName("Name").item(0).getTextContent(), 
                                        e.getElementsByTagName("Family").item(0).getTextContent(),
                                        e.getElementsByTagName("Email").item(0).getTextContent());
                c.setId(e.getAttribute("id"));
                objects.add(c);
            }
            return objects;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return objects;
        }
    }

    public static int getLastId(){ 
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("D:\\Backend\\13\\AppProject\\src\\xml\\Student.xml");
            doc.normalize();
            NodeList list = doc.getElementsByTagName("Student");
            if (list == null){
                return 1;
            }
            else
            {
                if (list.getLength()== 0){
                    return 1;
                }
                else
                {
                    Node n = list.item(list.getLength()-1);
                    Element e = (Element)n;
                    return Integer.parseInt(e.getAttribute("id"))+1;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return 0;
        }
    }
    
    public static Student getStudentbyId(String id){
        Student ts = new Student();
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("D:\\Backend\\13\\AppProject\\src\\xml\\Student.xml");
            doc.normalize();
            NodeList list = doc.getElementsByTagName("Student");
            for(int i=0; i<list.getLength(); i++){
                Node n = list.item(i);
                Element e = (Element) n;
                if (e.getAttribute("id").equals(id)){
                    ts.setName(e.getElementsByTagName("Name").item(0).getTextContent());
                    ts.setFamily(e.getElementsByTagName("Family").item(0).getTextContent());
                    ts.setEmail(e.getElementsByTagName("Email").item(0).getTextContent());
                    return ts;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return ts;
    }
    
    public static void DeleteElement(String StudentId){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File("D:\\Backend\\13\\AppProject\\src\\xml\\Student.xml");
            Document doc = db.parse(f);
            doc.normalize();
            NodeList nodes = doc.getElementsByTagName("Student");
            for(int i=0; i < nodes.getLength(); i++){
                Element person  = (Element)nodes.item(i);
                if (person.getAttribute("id").equals(StudentId)){
                    System.out.println("ID: " + person.getAttribute("id"));
                    System.out.println("InputID: " + StudentId);
                    person.getParentNode().removeChild(person);
                }
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(f);
            t.transform(source, result);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    
}
