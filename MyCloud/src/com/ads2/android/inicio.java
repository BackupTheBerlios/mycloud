package com.ads2.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class inicio extends Activity implements OnTouchListener {

//Botones y demas controles para el manejo de la GUI.
	Button subir;
	Button visualizar;
	Button salir;
	
	String usuario;
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inicio);
		
		//Paso los valores de la GUI.
		subir=(Button)findViewById(R.id.btn_subir_imagen);
		visualizar=(Button)findViewById(R.id.btn_compartir_imagen);
		salir=(Button)findViewById(R.id.btn_salir);
		
		//Ahora le paso los eventos del ontouch a los botones.
		subir.setOnTouchListener(this);
		visualizar.setOnTouchListener(this);
		salir.setOnTouchListener(this);
	
		Bundle b1 = getIntent().getExtras();
		usuario = b1.getString("usuario");
		
	}
	
	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		
		//Cuando se oprima ingresar.
		if(arg0.getId()==R.id.btn_subir_imagen){
			//Llama al metodo para subir una foto.
			Intent intentar_subir=new Intent("com.ads2.android.FileChooser");
			intentar_subir.putExtra("usuario", usuario);
			startActivity(intentar_subir);
			
		}
			
		//Cuando se oprima visualizar foto
		else if (arg0.getId()==R.id.btn_compartir_imagen){
			//Llama al metodo para visualizar una foto.
		}
		
		//Cuando se oprime salir.
		else if (arg0.getId()==R.id.btn_salir){
			//Llama al metodo para salir de la aplicacion.
			Intent intent = new Intent(Intent.ACTION_MAIN); finish();
			System.exit(0);
			
		}
		
		
		
		
		return false;
	}

}
