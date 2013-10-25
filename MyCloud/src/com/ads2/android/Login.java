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
	private Toast mensajito;
	
	//Conatantes para invocacion de WebServices
	private static final String NAMESPACE = "";
	private static final String URL = "";
	private static final String METHOD_NAME = "";
	private static final String SOAP_ACTION = "";
	
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
		
		request = new SoapObject(NAMESPACE,METHOD_NAME);
		request.addProperty("", usuario);
		request.addProperty("", passwd);
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		try {
			transporte.call(SOAP_ACTION, envelope);
			resultRequestSOAP = (SoapPrimitive) envelope.getResponse();
		} catch (HttpResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String resultado = resultRequestSOAP.toString();
		
		Toast t=Toast.makeText(this,resultado, 3000);
		
		t.show();
		
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
}
