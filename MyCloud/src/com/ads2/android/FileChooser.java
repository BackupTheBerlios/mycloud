package com.ads2.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class FileChooser extends ListActivity {
	
	
	
	//Conatantes para invocacion de WebServices
	private static final String NAMESPACE = "http://Consultas/";
	private static final String URL = "http://192.168.1.101:8080/WebService/ConsultasService?xsd=1";
	private static final String METHOD_NAME = "subir";
	private static final String SOAP_ACTION = "http://Consultas/subir";
	
	//Declaracion de variables para consumir el web service
	private SoapObject request = null;
	private SoapSerializationEnvelope envelope = null;
	private SoapPrimitive resultRequestSOAP = null;
	int cont=0;
	String usuario="";
	
	
	
	
	
    private File currentDir;
    private FileArrayAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b1 = getIntent().getExtras();
		usuario = b1.getString("usuario");
        
        
        currentDir = new File("/sdcard/");
        fill(currentDir);
    }
    private void fill(File f)
    {
    	File[]dirs = f.listFiles();
		 this.setTitle("Current Dir: "+f.getName());
		 List<Option>dir = new ArrayList<Option>();
		 List<Option>fls = new ArrayList<Option>();
		 try{
			 for(File ff: dirs)
			 {
				if(ff.isDirectory())
					dir.add(new Option(ff.getName(),"Folder",ff.getAbsolutePath()));
				else
				{
					fls.add(new Option(ff.getName(),"File Size: "+ff.length(),ff.getAbsolutePath()));
				}
			 }
		 }catch(Exception e)
		 {
			 
		 }
		 Collections.sort(dir);
		 Collections.sort(fls);
		 dir.addAll(fls);
		 if(!f.getName().equalsIgnoreCase("sdcard"))
			 dir.add(0,new Option("..","Parent Directory",f.getParent()));
		 adapter = new FileArrayAdapter(FileChooser.this,R.layout.file_chooser,dir);
		 this.setListAdapter(adapter);
    }
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Option o = adapter.getItem(position);
		if(o.getData().equalsIgnoreCase("folder")||o.getData().equalsIgnoreCase("parent directory")){
				currentDir = new File(o.getPath());
				fill(currentDir);
		}
		else
		{
			onFileClick(o);
		}
	}
    
    
    
    //Cuando se sube el archivo.
    private void onFileClick(Option o)
    {
    	cont=0;
    	
    	
    			
    			
    			
    	
    	Toast.makeText(this, "File Clicked: "+o.getName()+" ; "+o.getPath(), Toast.LENGTH_SHORT).show();
    	
    	
    	
    	if(cont==0){
    		cont=cont+1;
    		//Metodo que subira el archivo.
    		subirarchivo(o);
    		
    		
    		
    	}
    	
    	
    	
    }
    
    
    
    
    
    //METODO PARA CARGAR LOS DATOS Y ARCHIVOS.
	   public void subirarchivo(Option o){
					
			File file = new File(o.getPath());
			
			
			try{
			       byte[] b = new byte[(int) file.length()];
			         try {
			               FileInputStream fileInputStream = new FileInputStream(file);
			               fileInputStream.read(b);
			               
			          } catch (Exception e) {
			        	Toast t=Toast.makeText(this,"Error al preparar la carga", 3000);
			  			t.show();
			          }
			         
			         
			         
			         String datosimage=Base64.encodeToString(b,Base64.DEFAULT);
			         
			         
			         
			         //Proceso puro de carga.
			         
						request = new SoapObject(NAMESPACE,METHOD_NAME);
						request.addProperty("arg0", usuario);
						request.addProperty("arg1", datosimage);
						request.addProperty("arg2", "Imagen");
						request.addProperty("arg3", o.getName());
						request.addProperty("arg4", (int) file.length());
						envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.setOutputSoapObject(request);
						HttpTransportSE transporte = new HttpTransportSE(URL);
					
						
						try {
							transporte.call(SOAP_ACTION, envelope);
							resultRequestSOAP = (SoapPrimitive) envelope.getResponse();
						} catch (HttpResponseException e) {
							Toast t=Toast.makeText(this,"Resultado: "+e.getMessage(), 3000);
						} catch (IOException e) {
							Toast t=Toast.makeText(this,"Resultado: "+e.getMessage(), 3000);
						} catch (XmlPullParserException e) {
							Toast t=Toast.makeText(this,"Resultado: "+e.getMessage(), 3000);
						}
					
					String resultado ="Vacio";
					
					if(resultRequestSOAP!=null){
						resultado = resultRequestSOAP.toString();
					}
					
					
					 if(resultado.equals("OK")){
						 Toast t=Toast.makeText(this,"Archivo: "+o.getName()+" Subido exitosamente", 3000);
						 t.show();
					 }
					 else{
						Toast t=Toast.makeText(this,"Archivo: "+o.getName()+" No pudo cargarse: "+resultado, 3000);
						 t.show();
					 }
				
			         	         
			         //fin proceso puro de carga.
			         
			         
			         
				
			}
			catch(Exception e){
				Toast t=Toast.makeText(this,"Error: "+e, 3000);
				t.show();
				
			}
						
			
		}
	   
    
    
    
    
    
    
    
    
    
    
}