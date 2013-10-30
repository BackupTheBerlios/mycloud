package com.ads2.android;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.view.View.OnTouchListener;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class compartir extends Activity implements OnTouchListener {
	
	public String archivos[] = new String[10];
	private TextView tv1;
	private ListView lv1;
	
	EditText userotro;
	String arch;
	
	
	Button comparte;
	
	String usuario;
	int cont=0;
	

	//Conatantes para invocacion de WebServices
	private static final String NAMESPACE = "http://Consultas/";
	private static final String URL = "http://192.168.1.101:8080/WebService/ConsultasService?xsd=1";
	private static final String METHOD_NAME = "Compartir";
	private static final String SOAP_ACTION = "http://Consultas/Compartir";
	
	private static final String METHOD_NAME2 = "Muestra";
	private static final String SOAP_ACTION2 = "http://Consultas/Muestra";
	
	//Declaracion de variables para consumir el web service
	private SoapObject request = null;
	private SoapSerializationEnvelope envelope = null;
	private SoapPrimitive resultRequestSOAP = null;
	
	private SoapObject request2 = null;
	private SoapPrimitive resultRequestSOAP2 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compartor2);
		
		Bundle b1 = getIntent().getExtras();
		usuario = b1.getString("usuario");
		
		tv1=(TextView)findViewById(R.id.tv1);
        lv1=(ListView)findViewById(R.id.listView1);
        userotro=(EditText) findViewById(R.id.editText1);
        
        comparte=(Button)findViewById(R.id.button1);
        
        agregarArchivo(usuario);
        
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, archivos);  
        lv1.setAdapter(adapter);
        
        lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                tv1.setText("Archivo a compartir es " + archivos[posicion]);
                arch = archivos[posicion];
            }
        });
       
        comparte.setOnTouchListener(this);
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
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		// Cuando se oprime registrar.
		if(v.getId()==R.id.button1){
			if (cont==0){
				comparte ();
				cont++;
			}
		}
		
		//cont=0;
		return false;
	}
	
	
	
	public void agregarArchivo(String user) {
		Toast t;
		
		
		request2 = new SoapObject(NAMESPACE,METHOD_NAME2);
		request2.addProperty("arg0", user);
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request2);
		HttpTransportSE transporte = new HttpTransportSE(URL);
	
		
		try {
			transporte.call(SOAP_ACTION2, envelope);
			resultRequestSOAP2 = (SoapPrimitive) envelope.getResponse();
		} catch (HttpResponseException e) {
			t=Toast.makeText(this,"Resultado: "+ e.getMessage(), 3000);
		} catch (IOException e) {
			t=Toast.makeText(this,"Resultado: "+e.getMessage(), 3000);
		} catch (XmlPullParserException e) {
			 t=Toast.makeText(this,"Resultado: "+e.getMessage(), 3000);
		}catch(Exception e){
			archivos[0] = "No hay archivos";
		}
	
	String resultado ="Vacio";
	
		if(resultRequestSOAP2!=null){
			resultado = resultRequestSOAP2.toString();
		}
	
	
		if(!resultado.equals("")){
			archivos = resultado.split(";");
			
		}
		else{
			archivos[0] = "No hay archivos";
		
		}
		
		
		
	}
	
	
	
	
	public void comparte() {
		
		String usercomp = userotro.getText().toString().trim();
		String propio = usuario;
		String archivoCompartir = arch;
		if(!usercomp.equals("") && !archivoCompartir.equals("")){
			
			compartirWS(usercomp,propio,archivoCompartir);
		}
		
		else {
			Toast t=Toast.makeText(this,"Seleccione un archivo", 3000);
			t.show();
		}
		
	}
	
	
	public void compartirWS(String ussc, String ussp, String file) {
		
		Toast t;
		
		
		request = new SoapObject(NAMESPACE,METHOD_NAME);
		request.addProperty("arg0", ussp);
		request.addProperty("arg1", ussc);
		request.addProperty("arg2", file);
		envelope = null;
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
	
		
		try {
			transporte.call(SOAP_ACTION, envelope);
			if (envelope.getResponse()!=null){
				resultRequestSOAP = (SoapPrimitive) envelope.getResponse();	
			}
		} catch (HttpResponseException e) {
			t=Toast.makeText(this,"Resultado: "+e.getMessage(), 3000);
		} catch (IOException e) {
			t=Toast.makeText(this,"Resultado: "+e.getMessage(), 3000);
		} catch (XmlPullParserException e) {
			 t=Toast.makeText(this,"Resultado: "+e.getMessage(), 3000);
		}catch(Exception e){
			//archivos[0] = "No hay archivos";
		}
	
	String resultado ="Vacio";
	
	if(resultRequestSOAP!=null){
		resultado = resultRequestSOAP.toString();
	}
	
	
	 if(resultado.equals("OK")){
		 t=Toast.makeText(this,"Archivo Compartido "+cont, 3000);
		 t.show();
		 
			Intent intentar_compartir = new Intent("com.ads2.android.compartir");
			intentar_compartir.putExtra("usuario", usuario);
			startActivity(intentar_compartir);
		 Intent intent = new Intent(Intent.ACTION_MAIN); finish();
			System.exit(0);

			
			
			
		 
		 
	 }
	 else{
		 t=Toast.makeText(this,"No se pudo Compartir\n\nIntente de Nuevo "+cont+" "+resultado, 3000);
		 t.show();
	 }
	}

}
