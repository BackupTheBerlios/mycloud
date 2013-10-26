package com.ads2.android;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Misarchivos extends Activity implements OnTouchListener {

	TextView etiqueta;
	String usuario;
	
	
	//Conatantes para invocacion de WebServices
	private static final String NAMESPACE = "http://Consultas/";
	private static final String URL = "http://192.168.1.101:8080/WebService/ConsultasService?xsd=1";
	private static final String METHOD_NAME = "Listado";
	private static final String SOAP_ACTION = "http://Consultas/Listado";
	
	//Declaracion de variables para consumir el web service
	private SoapObject request = null;
	private SoapSerializationEnvelope envelope = null;
	private SoapPrimitive resultRequestSOAP = null;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_archivos);
			
	//Emparejo los valores de la GUI con los valores de las variables declaradas.
	etiqueta=(TextView) findViewById(R.id.myarchivos);
	
	//obtengo el usuario.
	Bundle b1 = getIntent().getExtras();
	usuario = b1.getString("usuario");
	
	//se crea el listado.
	getarchivos(usuario);
	}
	
	
	
	public void getarchivos(String user){

		Toast t;
		
		
			request = new SoapObject(NAMESPACE,METHOD_NAME);
			request.addProperty("arg0", user);
			envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			HttpTransportSE transporte = new HttpTransportSE(URL);
		
			
			try {
				transporte.call(SOAP_ACTION, envelope);
				resultRequestSOAP = (SoapPrimitive) envelope.getResponse();
			} catch (HttpResponseException e) {
				t=Toast.makeText(this,"Resultado: "+e.getMessage(), 3000);
			} catch (IOException e) {
				t=Toast.makeText(this,"Resultado: "+e.getMessage(), 3000);
			} catch (XmlPullParserException e) {
				 t=Toast.makeText(this,"Resultado: "+e.getMessage(), 3000);
			}
		
		String resultado ="Vacio";
		
		if(resultRequestSOAP!=null){
			resultado = resultRequestSOAP.toString();
		}
		
		
		 if(!resultado.equals("")){
			 etiqueta.setText(resultado);
		 }
		 else{
			 etiqueta.setText("No tiene archivos");
		 }
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		
		
		
		return false;
	}

}
