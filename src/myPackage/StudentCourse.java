package myPackage;

import java.io.File;
import static java.lang.Integer.parseInt;
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


public class StudentCourse {
    private String Student_id;
    private ArrayList<String> Course_id = new ArrayList<>();

    public StudentCourse(String id, ArrayList<String> Course_id) {
        this.Student_id = id;
        this.Course_id = Course_id;
    }

    public String getId() {
        return Student_id;
    }

    public void setId(String id) {
        this.Student_id = id;
    }

    public ArrayList<String> getCourse_id() {
        return Course_id;
    }

    public void setCourse_id(ArrayList<String> Course_id) {
        this.Course_id = Course_id;
    }
    
    public void save(){
//        for(int i = 0; i<this.Course_id.size(); i++){
//            System.out.println(this.Course_id.get(i));
//        }
        try{
            DeleteElement(this.Student_id);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File("D:\\Backend\\13\\AppProject\\src\\xml\\StudentCourse.xml");
            Document doc = db.parse(f);
            doc.normalize();
            Element root = doc.getDocumentElement();
            Element nodeElement = doc.createElement("Student");
            root.appendChild(nodeElement);
            //Element p1 = doc.createElement("Student_Id");
            ArrayList<Element> p = new ArrayList<Element>();
            for (int i = 0; i<this.Course_id.size(); i++){
                p.add(doc.createElement("Course_id" + Integer.toString(i+1)));
            }
            //p1.appendChild(doc.createTextNode(this.Student_id));
            for (int i = 0; i<this.Course_id.size(); i++){
                p.get(i).appendChild(doc.createTextNode(this.Course_id.get(i)));
            }
            //nodeElement.appendChild(p1);
            for (int i = 0; i<this.Course_id.size(); i++){
               nodeElement.appendChild(p.get(i));
            }
            Attr at = doc.createAttribute("id");
            at.setValue(this.Student_id);
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
    
    public static List<Cource> getStudentCourse(String id) {
        List<Cource> objects = new ArrayList<>();
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("D:\\Backend\\13\\AppProject\\src\\xml\\StudentCourse.xml");
            doc.normalize();
            NodeList list = doc.getElementsByTagName("Student");
            for(int i=0; i<list.getLength(); i++){
                Node n = list.item(i);
                Element e = (Element) n;
                if (e.getAttribute("id").equals(id)){
                    Cource c = new Cource();
                    int j =0;
                    while (!(e.getElementsByTagName("Course_id" + Integer.toString(j+1)).item(0)==null)) {
                        c = Cource.getCourse(e.getElementsByTagName("Course_id" + Integer.toString(j+1)).item(0).getTextContent());
                        c.setId(parseInt(e.getElementsByTagName("Course_id" + Integer.toString(j+1)).item(0).getTextContent()));
                        objects.add(c);
                        j++;
                    }
                }
            }
            return objects;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return objects;
        }
    }
    

    public static boolean validStudentCourse(String StudendtId, String CourseID){
        boolean srch = false;
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("D:\\Backend\\13\\AppProject\\src\\xml\\StudentCourse.xml");
            doc.normalize();
            NodeList list = doc.getElementsByTagName("Student");
            for(int i=0; i<list.getLength(); i++){
                Node n = list.item(i);
                Element e = (Element) n;
                if (e.getAttribute("id").equals(StudendtId)){
                    int j =0;
                    while (!(e.getElementsByTagName("Course_id" + Integer.toString(j+1)).item(0)==null)) {
                        srch = (e.getElementsByTagName("Course_id" + Integer.toString(j+1)).item(0).getTextContent().equals(CourseID));
                        if (srch){
                            break;
                        }
                        j++;
                    }
                }
            }
            return srch;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return srch;
        }
    }
    
    public static void DeleteElement(String StudentId){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File("D:\\Backend\\13\\AppProject\\src\\xml\\StudentCourse.xml");
            Document doc = db.parse(f);
            doc.normalize();
            NodeList nodes = doc.getElementsByTagName("Student");
            for(int i=0; i < nodes.getLength(); i++){
                Element person  = (Element)nodes.item(i);
                if (person.getAttribute("id").equals(StudentId)){
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
