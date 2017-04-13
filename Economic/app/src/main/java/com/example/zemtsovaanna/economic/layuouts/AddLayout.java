package com.example.zemtsovaanna.economic.layuouts;

import android.annotation.TargetApi;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.zemtsovaanna.economic.R;
import com.example.zemtsovaanna.economic.object.ProductObject;
import com.example.zemtsovaanna.economic.workWithXML.CreateXml;
import com.example.zemtsovaanna.economic.workWithXML.DOMParser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class AddLayout extends AppCompatActivity {
    private LinearLayout relativeLayout;
    private int width;
    private int height;
    private String productNameET;
    private String priceET;
    private EditText productName;
    private EditText price;
    private Button buttonOk;
    private  String[] date;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        relativeLayout = new LinearLayout(this);
        relativeLayout.setOrientation(LinearLayout.VERTICAL);
        myScreenSize();
        LinearLayout.LayoutParams viewGroupLayoutParams = new LinearLayout.LayoutParams(width,height);
        setContentView(relativeLayout,viewGroupLayoutParams);
        BitmapDrawable d = (BitmapDrawable)
                getResources().getDrawable(R.drawable.fon5);
        relativeLayout.setBackgroundDrawable(d);
        viewGroupLayoutParams = new LinearLayout.LayoutParams(width/2+width/6,height/10);
        viewGroupLayoutParams.gravity = Gravity.CENTER;
        viewGroupLayoutParams.topMargin = width/6;
        productName = new EditText(this);
        productName.setHint("Товар или услуга");
        relativeLayout.addView(productName,viewGroupLayoutParams);

        viewGroupLayoutParams = new LinearLayout.LayoutParams(width/2+width/6,height/10);
        viewGroupLayoutParams.gravity = Gravity.CENTER;
        viewGroupLayoutParams.topMargin = width/6-width/8;
        price = new EditText(this);
        price.setHint("Цена");
        relativeLayout.addView(price,viewGroupLayoutParams);

        viewGroupLayoutParams = new LinearLayout.LayoutParams(width/2+width/6,height/10);
        viewGroupLayoutParams.gravity = Gravity.BOTTOM|Gravity.RIGHT;
        viewGroupLayoutParams.topMargin = width/2+width/6;
        viewGroupLayoutParams.rightMargin =width/10;
        viewGroupLayoutParams.bottomMargin = height/10;
        buttonOk = new Button(this);
        buttonOk.setText("OK");
        viewGroupLayoutParams.setLayoutDirection(R.drawable.rounded_button);
        relativeLayout.addView(buttonOk,viewGroupLayoutParams);
        listener();
    }


    public void myScreenSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
    }

    public void listener(){
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(productName.getText()!=null&price.getText()!=null){
                     productNameET = String.valueOf(productName.getText());
                     priceET = String.valueOf(price.getText().toString());
                    productName.setText("");
                    price.setText("");
                    productName.setHint("Товар или услуга");
                    price.setHint("Цена");
                    getDate();
                    File checkDir = new File("/sdcard/all/"+date[1]);
                    if (checkDir.exists()) {
                         checkDir = new File("/sdcard/all/"+date[1]+"/"+date[2]);
                        if (checkDir.exists()) {
                            checkDir = new File("/sdcard/all/"+date[1]+"/"+date[2]+"/"+date[3]+".xml");
                            if(checkDir.exists()){
                                addFieldToXML("/sdcard/all/"+date[1]+"/"+date[2]+"/"+date[3]+".xml");
                        }else{
                                try {
                                    createFileXML();
                                } catch (TransformerException e) {
                                    e.printStackTrace();
                                } catch (ParserConfigurationException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            checkDir.mkdirs();
                            try {
                                createFileXML();
                            } catch (TransformerException e) {
                                e.printStackTrace();
                            } catch (ParserConfigurationException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        checkDir.mkdirs();
                        checkDir = new File("/sdcard/all/"+date[1]+"/"+date[2]);
                        checkDir.mkdirs();
                        try {
                            createFileXML();
                        } catch (TransformerException e) {
                            e.printStackTrace();
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }

        });

    }




    public void addFieldToXML(String path){
        ProductObject productObject= new ProductObject();
        productObject.setProductName(productNameET);
        productObject.setPrice(Double.valueOf(priceET));
        DOMParser domParser = new DOMParser();
           domParser.addNode(productObject, path);
    }

    public String[] getDate(){
        Date d = new Date();
        date = new String[4];
        SimpleDateFormat format2 = new SimpleDateFormat("День dd Месяц MM Год yyyy Время hh:mm");
        String s= format2.format(d);
        date[3]=s.substring(5,7);
        date[2]=s.substring(14,16);
        date[1]=s.substring(21,25);
        return date;
    }
    public void createFileXML() throws TransformerException, ParserConfigurationException {
        File xml = new File("/sdcard/all/"+date[1]+"/"+date[2]+"/"+date[3]+".xml");
        try {
               xml.createNewFile();//????НЕ ФАКТ ЧТО НУЖНО
               CreateXml createXml = new CreateXml();
               createXml.writeToXML(productNameET,Double.valueOf(priceET),xml);
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
