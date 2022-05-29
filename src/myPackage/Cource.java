package myPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Cource {
    private int Id;
    private String name;
    private String teacher;
    private int duration;
    //public  String XMLPath = "D:\\Backend\\13\\AppProject\\src\\xml\\Course.xml";
    
    public Cource(String Name, String Teache, int Duration){
        
        this.name = Name;
        this.teacher = Teache;
        this.duration = Duration;
    }
    
    public void setId(int Id) {
        this.Id = Id;
    }

    public int getId() {
        return Id;
    }
    public Cource(){
    }
    
    public static int getLastId(){ 
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("D:\\Backend\\13\\AppProject\\src\\xml\\Course.xml");
            doc.normalize();
            NodeList list = doc.getElementsByTagName("Cource");
            if (list == null){
//                JOptionPane.showMessageDialog(null, "null");
                return 1;
            }
            else
            {
                if (list.getLength()== 0){
//                    JOptionPane.showMessageDialog(null, "Lentg 0");
                    return 1;
                }
                else
                {
                    return list.getLength()+1;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return 0;
        }
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Cource{" + "Id=" + Id + ", name=" + name + ", teacher=" + teacher + ", duration=" + duration + '}';
    }
    
    public static void edit(String Id){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File("D:\\Backend\\13\\AppProject\\src\\xml\\Course.xml");
            Document doc = db.parse(f);
            doc.normalize();
            NodeList list = doc.getElementsByTagName("Cource");
            Node n = list.item(4).getParentNode();
            doc.getDocumentElement().removeChild(n);
            

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(f);
            t.transform(source, result);
            
            
        }
        catch(Exception ex){
        } 
    }
    
    public void save(int id){
        try{
            String MyId = String.valueOf(this.Id);
            DeleteElement(MyId);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File("D:\\Backend\\13\\AppProject\\src\\xml\\Course.xml");
            Document doc = db.parse(f);
            doc.normalize();
            
            Element root = doc.getDocumentElement();
            
            Element nodeElement = doc.createElement("Cource");
            root.appendChild(nodeElement);
            
            Element p1 = doc.createElement("Name");
            Element p2 = doc.createElement("Teacher");
            Element p3 = doc.createElement("Duration");
            
            
            
            p1.appendChild(doc.createTextNode(this.name));
            p2.appendChild(doc.createTextNode(this.teacher));
            p3.appendChild(doc.createTextNode(Integer.toString(this.duration)));
            
            nodeElement.appendChild(p1);
            nodeElement.appendChild(p2);
            nodeElement.appendChild(p3);
            
            Attr at = doc.createAttribute("id");
            at.setValue(Integer.toString(id));
        //connect attribute to notebook object
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
    
    public static List<Cource> getCourse() {
        List<Cource> objects = new ArrayList<>();
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("D:\\Backend\\13\\AppProject\\src\\xml\\Course.xml");
            doc.normalize();
            NodeList list = doc.getElementsByTagName("Cource");
            for(int i=0; i<list.getLength(); i++){
                Node n = list.item(i);
                
                Element e = (Element) n;
//                System.out.println("Name: " +e.getElementsByTagName("Name").item(0).getTextContent());
//                System.out.println("Teacher: " +e.getElementsByTagName("Teacher").item(0).getTextContent());
//                System.out.println("Duration: " +Integer.parseInt(e.getElementsByTagName("Duration").item(0).getTextContent()));
                Cource c = new Cource(e.getElementsByTagName("Name").item(0).getTextContent(), 
                                      e.getElementsByTagName("Teacher").item(0).getTextContent(),
                                      Integer.parseInt(e.getElementsByTagName("Duration").item(0).getTextContent()));
                c.setId(Integer.parseInt(e.getAttribute("id")));
                objects.add(c);
            }
            return objects;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return objects;
        }
    }
    
    public static Cource getCourse(String Id) {
        Cource tc = new Cource();
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("D:\\Backend\\13\\AppProject\\src\\xml\\Course.xml");
            doc.normalize();
            NodeList list = doc.getElementsByTagName("Cource");
            for(int i=0; i<list.getLength(); i++){
                Node n = list.item(i);
                Element e = (Element) n;
                if (e.getAttribute("id").equals(Id)){
                    tc.setName(e.getElementsByTagName("Name").item(0).getTextContent());
                    tc.setTeacher(e.getElementsByTagName("Teacher").item(0).getTextContent());
                    tc.setDuration(Integer.parseInt(e.getElementsByTagName("Duration").item(0).getTextContent()));
                    return tc;
                }
            }
            return tc;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return tc;
        }
    }


    public static Cource Search(String Id){
        
        Cource tmp = new Cource();
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("D:\\Backend\\13\\AppProject\\src\\xml\\Course.xml");
            doc.normalize();
            NodeList list = doc.getElementsByTagName("Cource");
            for(int i=0; i<list.getLength(); i++){
                Node n = list.item(i);
                Element e = (Element) n;
                if (e.getAttribute("id").equals(Id)){
                    Cource c = new Cource(e.getElementsByTagName("Name").item(0).getTextContent(), 
                                         e.getElementsByTagName("Teacher").item(0).getTextContent(),
                                          Integer.parseInt(e.getElementsByTagName("Duration").item(0).getTextContent()));
                    c.setId(Integer.parseInt(e.getAttribute("id")));
                
                    tmp = c;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
        return tmp;
    }

    public static void DeleteElement(String CourceId){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File("D:\\Backend\\13\\AppProject\\src\\xml\\Course.xml");
            Document doc = db.parse(f);
            doc.normalize();
            NodeList nodes = doc.getElementsByTagName("Cource");
            for(int i=0; i < nodes.getLength(); i++){
                Element person  = (Element)nodes.item(i);
                if (person.getAttribute("id").equals(CourceId)){
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
