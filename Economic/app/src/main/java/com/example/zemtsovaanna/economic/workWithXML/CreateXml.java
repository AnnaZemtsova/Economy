package com.example.zemtsovaanna.economic.workWithXML;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by zemtsovaanna on 04.09.16.
 */
public class CreateXml {
    ArrayList<String> days = new ArrayList<>(Arrays.asList("Понедельник","Вторник","Среда","Четверг","Пятница","Суббота","Воскресенье"));
    private double generalPrice;

    public void saveDataSAX(File xml,String productName,Double price) throws ParserConfigurationException, TransformerException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        generalPrice = price;
        DocumentBuilderFactory factoryBuilder = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc =factoryBuilder.newDocumentBuilder().newDocument();
        Element root = doc.createElement("products");
        doc.appendChild(root);
        Attr attr = doc.createAttribute("generalPrice");
        attr.setValue(String.valueOf(generalPrice));
        root.setAttributeNode(attr);

        Element item1 = doc.createElement("product");
        root.appendChild(item1);

        attr = doc.createAttribute("productName");
        attr.setValue(productName);
        item1.setAttributeNode(attr);

        attr = doc.createAttribute("productPrice");
        attr.setValue(String.valueOf(price));
        item1.setAttributeNode(attr);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(xml));
    }

    public void writeToXML(String productName,Double price,File xml) throws TransformerException, ParserConfigurationException {
        saveDataSAX(xml,productName,price);
    }
}

