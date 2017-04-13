package com.example.zemtsovaanna.economic.layuouts;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zemtsovaanna.economic.R;
import com.example.zemtsovaanna.economic.object.ProductObject;
import com.example.zemtsovaanna.economic.workWithXML.DOMParser;

import java.io.File;
import java.util.ArrayList;

public class ListLayout extends AppCompatActivity {
    private static int amount =0;
    private static String year;
    private static String month;
    private Double generalPrice;
    private LinearLayout relativeLayout;
    private int width;
    private int height;
    private ListView listView;
    private  String lastPath;
    private Button total;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        amount=0;
        ArrayList<String> ars = getData("/sdcard/all/");
        int size = ars.size();
        final String[] years = new String[size];
        for(int i=0;i<size;i++){
            years[i]=ars.get(i);
        }
        super.onCreate(savedInstanceState);
        relativeLayout = new LinearLayout(this);
        relativeLayout.setOrientation(LinearLayout.VERTICAL);
        myScreenSize();
        LinearLayout.LayoutParams viewGroupLayoutParams = new LinearLayout.LayoutParams(width,height);
        setContentView(relativeLayout,viewGroupLayoutParams);
        final BitmapDrawable d = (BitmapDrawable)
                getResources().getDrawable(R.drawable.fon5);
        relativeLayout.setBackgroundDrawable(d);

        viewGroupLayoutParams = new LinearLayout.LayoutParams(width,height/3+height/4+height/9);
        viewGroupLayoutParams.gravity = Gravity.TOP|Gravity.CENTER;
        listView = new ListView(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,years);
        listView.setAdapter(adapter);
        relativeLayout.addView(listView,viewGroupLayoutParams);
        viewGroupLayoutParams = new LinearLayout.LayoutParams(width,height/10);
        viewGroupLayoutParams.gravity = Gravity.BOTTOM|Gravity.RIGHT;
        viewGroupLayoutParams.topMargin = width/12;
        viewGroupLayoutParams.bottomMargin = height/10;
        total = new Button(this);
        total.setText("               общая сумма: ");
        viewGroupLayoutParams.setLayoutDirection(R.drawable.rounded_button);
        relativeLayout.addView(total,viewGroupLayoutParams);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                String data = ((TextView) itemClicked).getText().toString();
                if (amount==0) {
                   lastPath= "/sdcard/all/"+data;
                    setList("/sdcard/all/" + data);
                    year =data;
                }
                if (amount==1){
                    if(data.equals("январь")) month="01";
                    if(data.equals("февраль")) month="02";
                    if(data.equals("март")) month="03";
                    if(data.equals("апрель")) month="04";
                    if(data.equals("май")) month="05";
                    if(data.equals("июнь")) month="06";
                    if(data.equals("июль")) month="07";
                    if(data.equals("август")) month="08";
                    if(data.equals("сентябрь")) month="09";
                    if(data.equals("октябрь")) month="10";
                    if(data.equals("ноябрь")) month="11";
                    if(data.equals("декабрь")) month="12 ";
                    lastPath = "/sdcard/all/"+year+"/"+month;
                    setDataList("/sdcard/all/"+year+"/"+month);
                }
                if(amount==2){
                    lastPath = "/sdcard/all/"+year+"/"+month+"/"+data+".xml";
                    setProductList("/sdcard/all/"+year+"/"+month+"/"+data+".xml");
                    total.setText("               общая сумма: "+generalPrice);
                }
                if(amount<3) amount++;
            }

        });
    }

    @Override
    public void onBackPressed() {
        File file = new File(lastPath);
        File fileParent=file.getParentFile();
        if(amount==3) setDataList(fileParent.getPath());
        if(amount==2)  setList(fileParent.getPath());
        if(amount==1) {
            ArrayList<String> ars = getData("/sdcard/all/");
            int size = ars.size();
            final String[] years = new String[size];
            for(int i=0;i<size;i++){
                years[i]=ars.get(i);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,years);
            listView.setAdapter(adapter);
        }
        if(amount==0){
            Intent intent = new Intent(ListLayout.this, MainLayout.class);
            startActivity(intent);
        }
        if(amount>0) amount--;
        lastPath = fileParent.getPath();
    }


    public void setProductList(String path){
        DOMParser domParser = new DOMParser();
        domParser.getList(path);
        ArrayList<ProductObject> productObjects = DOMParser.getProductObjects();
        String [] products = new String[productObjects.size()];
        for(int i=0;i<products.length;i++){
            products[i]=productObjects.get(i).getProductName();
            for(int j=productObjects.get(i).getProductName().length();j<50;j++){
                products[i]+=" ";
            }
            products[i]+=productObjects.get(i).getPrice();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,products);
        listView.setAdapter(adapter);
        generalPrice = DOMParser.getGeneralPrice();
    }


    public void setDataList(String path){
        ArrayList<String> ars = getDataDate(path);
        int size = ars.size();
        String[] month = new String[size];
        for(int i=0;i<size;i++){
            month[i]=ars.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,month);
        listView.setAdapter(adapter);
    }
    public void setList(String path){
        ArrayList<String> ars = getData(path);
        int size = ars.size();
        String[] month = new String[size];
        for(int i=0;i<size;i++){
            if(ars.get(i).equals("01")) month[i]="январь";
            if(ars.get(i).equals("02")) month[i]="февраль";
            if(ars.get(i).equals("03")) month[i]="март";
            if(ars.get(i).equals("04")) month[i]="апрель";
            if(ars.get(i).equals("05")) month[i]="май";
            if(ars.get(i).equals("06")) month[i]="июнь";
            if(ars.get(i).equals("07")) month[i]="июль";
            if(ars.get(i).equals("08")) month[i]="август";
            if(ars.get(i).equals("09")) month[i]="сентябрь";
            if(ars.get(i).equals("10")) month[i]="октябрь";
            if(ars.get(i).equals("11")) month[i]="ноябрь";
            if(ars.get(i).equals("12")) month[i]="декабрь";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,month);
        listView.setAdapter(adapter);
    }
    public void myScreenSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
    }

    public  ArrayList<String> getData(String path) {
        ArrayList<String> res = new ArrayList<>();
        File dir = new File(path);

        for (File file : dir.listFiles()) {
            if (file.isDirectory()){
                res.add(file.getName());
            }
        }
        return res;
    }

    public  ArrayList<String> getDataDate(String path) {
        ArrayList<String> res = new ArrayList<>();
        File dir = new File(path);

        for (File file : dir.listFiles()) {
            if (file.isFile()){
                String s = new String(file.getName());
                CharSequence a= s.subSequence(0,2);
                res.add(a.toString());
            }
        }
        return res;
    }


}
