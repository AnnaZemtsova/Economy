package com.example.zemtsovaanna.economic.workWithXML;

import com.example.zemtsovaanna.economic.object.ProductObject;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class DOMParser {
    private static Double generalPrice;
    private static ArrayList<ProductObject> productObjects = new ArrayList<>();


    public static ArrayList<ProductObject> getProductObjects() {
        return productObjects;
    }

    public static Double getGeneralPrice() {
        return generalPrice;
    }

    private static void addNode(ProductObject productObject, Document doc,String path) throws TransformerException {
        int flag=0;
        Element rootEl = doc.getDocumentElement();
        int i = rootEl.getElementsByTagName("product").getLength();
        Double price = productObject.getPrice();
        Double tmp =Double.parseDouble(rootEl.getAttributes().item(0).getNodeValue());
        String result = String.valueOf(price+tmp);
        rootEl.getAttributes().item(0).setNodeValue(result);
        NodeList nodeList = rootEl.getElementsByTagName("product");
        if (i != 0) {
            Node nodeParent;
            Element item1 = doc.createElement("product");
            Attr attr = doc.createAttribute("productName");
            attr.setValue(productObject.getProductName());
            item1.setAttributeNode(attr);

            attr = doc.createAttribute("productPrice");
            attr.setValue(String.valueOf(productObject.getPrice()));
            item1.setAttributeNode(attr);
            for (int j = 0; j < i; j++) {
                NamedNodeMap node = rootEl.getElementsByTagName("product").item(j).getAttributes();
                if (productObject.getPrice() > Double.valueOf(String.valueOf(node.item(1).getNodeValue()))) {

                    flag=1;
                    if(j!=0) {
                        nodeParent = nodeList.item(j - 1);
                        nodeParent.appendChild(item1);
                    }else{
                        nodeParent = nodeList.item(j);
                        rootEl.insertBefore(item1,nodeParent);
                    }

                    break;
                }

            }
            if(flag==0){
                nodeParent = nodeList.item(rootEl.getElementsByTagName("product").getLength() - 1);
                nodeParent.appendChild(item1);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(path));
        }
    }

    private static void getList(Document doc) {
        productObjects = new ArrayList<>();
        Element rootEl = doc.getDocumentElement();
        generalPrice = Double.parseDouble(rootEl.getAttribute("generalPrice"));
        int i = rootEl.getElementsByTagName("product").getLength();
        for (int j = 0; j < i; j++) {
            NamedNodeMap namedNodeMap = rootEl.getElementsByTagName("product").item(j).getAttributes();
            ProductObject productObject = new ProductObject();
            productObject.setProductName(String.valueOf(namedNodeMap.item(0).getNodeValue()));
            productObject.setPrice(Double.parseDouble(String.valueOf(namedNodeMap.item(1).getNodeValue())));
            productObjects.add(productObject);
        }
    }

    public void getList(String path) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        Document doc = null;
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            DOMParser dom = new DOMParser();
            File file =new File(path);
            //if(fileParent==null) file = new File(path);
           // else file = fileParent;
            doc = db.parse(file);
            dom.getList(doc);
        } catch (ParserConfigurationException p) {
            p.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException s) {
            s.printStackTrace();
        }
    }

    public void addNode(ProductObject productObject,String path){
        DOMParser domParser = new DOMParser();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        Document doc = null;
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            System.out.println(path);
            File file = new File(path);
            doc = db.parse(file);
            domParser.addNode(productObject, doc,path);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
         catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
