package com.ads2.android;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnTouchListener {

	EditText user;
	EditText pass;
	Button ingresar;
	Button registrar;
	int cont=0;
	
	private Toast mensajito;
	
	//Conatantes para invocacion de WebServices
	private static final String NAMESPACE = "http://Consultas/";
	private static final String URL = "http://192.168.1.101:8080/WebService/ConsultasService?xsd=1";
	private static final String METHOD_NAME = "Permiso";
	private static final String SOAP_ACTION = "http://Consultas/Permiso";
	
	//Declaracion de variables para consumir el web service
	private SoapObject request = null;
	private SoapSerializationEnvelope envelope = null;
	private SoapPrimitive resultRequestSOAP = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//Paso los valores de user y password al crearse la aplicacion.
		user=(EditText) findViewById(R.id.txtuser);
		pass=(EditText) findViewById(R.id.txtpassword);
		ingresar=(Button)findViewById(R.id.btningresar);
		registrar=(Button)findViewById(R.id.btnregistro);
		
		//Ahora le paso los eventos del ontouch a los botones.
		ingresar.setOnTouchListener(this);
		registrar.setOnTouchListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	
	//Cuando se oprima algo dentro del activity
	public boolean onTouch(View arg0, MotionEvent arg1) {
		
		//Cuando se oprima ingresar.
		if(arg0.getId()==R.id.btningresar){
			//Llama al metodo para ingresar.
			cont=0;
			ingresar();
		}
			
		//Cuando se oprima registrarme.
		else if (arg0.getId()==R.id.btnregistro){
			registrar();
		}
			
		
		return false;
	}
		
	//Metodo para manejar el ingreso de los usuarios al sistema.
	public void ingresar(){
		String usuario = user.getText().toString().trim();
		String passwd = pass.getText().toString().trim();
		//Si no estan vacios los cuadros de texto entonces...
		if(!usuario.equals("") && !passwd.equals("")){
			
			this.loginWS(usuario, passwd);

//			Toast t=Toast.makeText(this,"Usuario: "+usuario+"\n"+
//									    "Contraseña: "+passwd, 3000);
//			t.show();
		}
		//Else si estan vacios entonces.....
		else {
			Toast t=Toast.makeText(this,"Ingrese sus credenciales", 3000);
			t.show();
		}
				
	}
	
	//Metodo para registrar al usaurio.
	public void registrar(){
		Intent intentar_registrar=new Intent("com.example.login.Reg");
		startActivity(intentar_registrar);
		
	}
	
	public void loginWS(String usuario, String passwd){
		if(cont==0){
		Toast t;
		
		
			request = new SoapObject(NAMESPACE,METHOD_NAME);
			request.addProperty("arg0", usuario);
			request.addProperty("arg1", passwd);
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
		
		
		 if(resultado.equals("ACEPTADO")){
				Intent intentar_Inicio=new Intent("com.ads2.android.inicio");
				intentar_Inicio.putExtra("usuario", usuario);
				startActivity(intentar_Inicio);
		 }
		 else{
			 t=Toast.makeText(this,"Credenciales Incorrectas\nIntente de Nuevo", 3000);
			 t.show();
		 }
		cont++;
		}
		
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
}
