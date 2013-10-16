package com.ads2.android;

import android.app.Activity;
import android.os.Bundle;
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
		
	//Comparo primero que no haya campos vacios.
		if(!nombre.getText().toString().trim().equals("") && !apellido.getText().toString().trim().equals("")
		&& !usuario.getText().toString().trim().equals("") && !contrasena1.getText().toString().trim().equals("")
		&& !contrasena2.getText().toString().trim().equals("")){
			
		//No hay campos vacios, ahora verifiquemos que el password se confirme correctamente.
			if(contrasena1.getText().toString().trim().equals(contrasena2.getText().toString().trim())){
				//Se crea el xml para ser enviado al servidor de BD.
				//:::::::::::::::::::::::::::::::::::::::::::::::::::
				//:::::::::::::::::::::::::::::::::::::::::::::::::::
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
		
		
	}













} //fin de la clase.
