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
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Reg extends Activity implements OnTouchListener {

//Variables para poder manejar la GUI de android.	
EditText nombre;
EditText apellido;
EditText usuario;
EditText contrasena1;
EditText contrasena2;
Button registrar;

String name="";
String last="";
String user="";
String pass="";
String pass2="";

//Conatantes para invocacion de WebServices
private static final String NAMESPACE = "http://Consultas/";
private static final String URL = "http://192.168.1.101:8080/WebService/ConsultasService?xsd=1";
private static final String METHOD_NAME = "Registro";
private static final String SOAP_ACTION = "http://Consultas/Registro";

//Declaracion de variables para consumir el web service
private SoapObject request = null;
private SoapSerializationEnvelope envelope = null;
private SoapPrimitive resultRequestSOAP = null;

int cont=0;
	
	
@Override
public boolean onKeyDown(int keyCode, KeyEvent event){
	
	switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			Intent intent = new Intent(Intent.ACTION_MAIN); finish();
			System.exit(0);
			return true;
	}
	
	return false;
}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg);
		
	//Emparejo los valores de la GUI con los valores de las variables declaradas.
	nombre=(EditText) findViewById(R.id.txtnombre_reg);
	apellido=(EditText) findViewById(R.id.txtapellido_reg);
	usuario=(EditText) findViewById(R.id.txtusuario_reg);
	contrasena1=(EditText) findViewById(R.id.txtpass_reg);
	contrasena2=(EditText) findViewById(R.id.txtpass2_reg);
	registrar=(Button) findViewById(R.id.btnreg_reg);
	
	//agrego la accion al boton de registro.
	registrar.setOnTouchListener(this);
	}



	
	//Metodo para activar los controles tactiles en el dispositivo.
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		// Cuando se oprime registrar.
		if(v.getId()==R.id.btnreg_reg){
			//Llama al metodo para registrar.
				registro();
				
		}
		
		return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Metodo que verifica que todo los campos del registro sean congruentes.
	public void registro(){
		if(cont==0){
		name = nombre.getText().toString();
		last = apellido.getText().toString();
		user = usuario.getText().toString();
		pass = contrasena1.getText().toString();
		pass2 = contrasena2.getText().toString();
		
		
	//Comparo primero que no haya campos vacios.
		if(!name.equals("") && !last.equals("")
		&& !user.equals("") && !pass.equals("")
		&& !pass2.equals("")){
			
		//No hay campos vacios, ahora verifiquemos que el password se confirme correctamente.
			if(pass.equals(pass2)){
				this.registroWS(name, last, user, pass);
			}
			
			//Caso contrario passwords no coinciden entonces........
			else {
				//Mensaje informativo al usuario.
				Toast M=Toast.makeText(this,"La verificacion de contraseñas es incorrecta", 3000);
				M.show();
			}
			//Fin del if interno (verificacion de passwords)
			
			
			
		}
		
		//En caso contrario si algun campo esta vacio entonces se muestra el mensaje al usuario.
		else {
			Toast Mensaje=Toast.makeText(this,"Faltan datos, rellene todos los campos",3000);
			Mensaje.show();			
		}
		

			cont++;
		}
	}


	public void registroWS(String nombre, String apellido, String usuario, String passwd){
		
		Toast t;
		
		
		request = new SoapObject(NAMESPACE,METHOD_NAME);
		request.addProperty("arg0", nombre);
		request.addProperty("arg1", apellido);
		request.addProperty("arg2", usuario);
		request.addProperty("arg3", passwd);
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
	
	
	 if(resultado.equals("OK")){
		 t=Toast.makeText(this,"Registro Existoso", 3000);
		 t.show();
	 }
	 else{
		 t=Toast.makeText(this,"Registro Incorrecto\n\nIntente de Nuevo", 3000);
		 t.show();
	 }
		
	}


} //fin de la clase.
