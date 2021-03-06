package com.ads2.android;

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

public class inicio extends Activity implements OnTouchListener {

//Botones y demas controles para el manejo de la GUI.
	Button subir;
	Button visualizar;
	Button salir;
	Button archivos;
	
	String usuario;
	
	int cont=0;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inicio);
		cont = 0;
		//Paso los valores de la GUI.
		subir=(Button)findViewById(R.id.btn_subir_imagen);
		visualizar=(Button)findViewById(R.id.btn_compartir_imagen);
		salir=(Button)findViewById(R.id.btn_salir);
		archivos=(Button)findViewById(R.id.btn_misarchivos);
		
		//Ahora le paso los eventos del ontouch a los botones.
		subir.setOnTouchListener(this);
		visualizar.setOnTouchListener(this);
		salir.setOnTouchListener(this);
		archivos.setOnTouchListener(this);
	
		Bundle b1 = getIntent().getExtras();
		usuario = b1.getString("usuario");
		
	}
	
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
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		if(cont==0){
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
			Intent intentar_compartir = new Intent("com.ads2.android.compartir");
			intentar_compartir.putExtra("usuario", usuario);
			startActivity(intentar_compartir);
			
		}
		
		//Cuando se oprime salir.
		else if (arg0.getId()==R.id.btn_salir){
			//Llama al metodo para salir de la aplicacion.
			Intent intent = new Intent(Intent.ACTION_MAIN); finish();
			System.exit(0);
			
		}
		
		//@+id/btn_misarchivos
		else if(arg0.getId()==R.id.btn_misarchivos){
			//Llama al metodo para listar archivos
			Intent intentar_subir=new Intent("com.ads2.android.Misarchivos");
			intentar_subir.putExtra("usuario", usuario);
			startActivity(intentar_subir);
		
		}
		
		cont++;
		}
		
		return false;
	}

}
