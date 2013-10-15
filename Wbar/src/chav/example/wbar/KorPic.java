package chav.example.wbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class KorPic extends SherlockActivity {
	private static final String LOG_TAG = "MY_LOG";
	// имена атрибутов для Map
	  final String ATTR_IDZP = "idzp";	//index zapisi v baze
	  final String ATTR_IMAGE = "image";
		final String ATTR_TEXT1 = "text1";
		final String ATTR_KRTXT = "krtxt";
		final String ATTR_NOWA = "nowa";
	
	final String ATTR_ISNOT="isnot"; // Kartinka for itis	
	final String ATTR_ISEE="isee"; // itis	= 1 I see; itis	= 0 I ne wiju;
	final String ATTR_MOLCHA="molcha";
	  final static String ATTR_LL = "Linia";
	//  final int DIALOG_EXIT = 1;

	  int i_tag=-1;
	  ListView lvcorS;
	  SimpleAdapter sAdapter;
	  static ArrayList<Map<String, Object>> data;
	  Map<String, Object> m;
	  //ToggleButton toogleButton;
	  //Button btriger;
	  //Button bnew;
	  LinearLayout ll2;
	  LinearLayout ll3;
	  //static int rez_dia;	//=0-ничего =1-изменили  =2-новая  =3-удалить итем 4-удалили историю
//int nclick=-1;		//nom stroki klirnuli  
	  int idzap;  // ид zapisi v base
	  int idpick;  // ид картинки 
   	  static String txtpic;	//техт картинки
   	  int isornot;	//видимая-1 невидимая-0
   	  static int deafor;	//molcha=1(удалить нельзя) строки заданные по умолчанию; =0 строки введенные пользователем
   	  
	   final DbTrafic tf=new DbTrafic(this, 1);
		
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
		   @Override
   protected void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.kor_pic);
		         final com.actionbarsherlock.app.ActionBar ab = getSupportActionBar(); 
		         ab.setHomeButtonEnabled(true);
		         ab.setDisplayHomeAsUpEnabled(true); 
						ab.setDisplayShowHomeEnabled(true);
						ab.setDisplayShowTitleEnabled(true);
/*
		         ab.setHomeButtonEnabled(false);
		           ab.setDisplayHomeAsUpEnabled(false); 

		        				ab.setDisplayShowHomeEnabled(false);
		         				ab.setDisplayShowTitleEnabled(false);
*/		  
	  
		    // упаковываем данные в понятную для адаптера структуру
						data = new ArrayList<Map<String, Object>>();		
			fada();
		 	// массив имен атрибутов, из которых будут читаться данные
	 	    String[] from = {ATTR_LL, ATTR_IMAGE, ATTR_TEXT1, ATTR_KRTXT, ATTR_NOWA, ATTR_ISNOT, ATTR_MOLCHA};	
	 	      int[] to = {R.id.Lin_3, R.id.imagk, R.id.textk, R.id.bnew, R.id.newtekst,R.id.btriger, R.id.udalim};	
		 	    // создаем адаптер
sAdapter = new SimpleAdapter(this, data, R.layout.kor_pic_item,from, to);

	   sAdapter.setViewBinder(new MyViewBinder());
	 	    // определяем список и присваиваем ему адаптер  
	 	   lvcorS = (ListView) findViewById(R.id.lvcorS);
	 	  lvcorS.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

	 	  lvcorS.setAdapter(sAdapter);
		    } 
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
 public void fada()	   
		   {
		   int chzp=0;
//		   nclick=-1;
		    SQLiteDatabase db = tf.getWritableDatabase();
	 		String stk = "select id_drawable_pic, text_pic, itis, molcha, i_pics from t_pics order by id_drawable_pic" ;
	 		Cursor curs = db.rawQuery(stk, null);
	 		if(!curs.moveToFirst())System.exit(0);
			chzp=curs.getCount();
			data.clear();
		    //data = new ArrayList<Map<String, Object>>(chzp);
		    int i=0;
	 	  do {
		    	   m = new HashMap<String, Object>();
					//Log.d(LOG_TAG, "Номер в списке="+id_in_list);

			 	  m.put(ATTR_IDZP, curs.getInt(4));  // key zapisi autoincriment
			 	  

 			 	//Log.d(LOG_TAG, "Номер в списке="+stk);			//***************************************
		 	      m.put(ATTR_IMAGE, curs.getInt(0));  // картинка
		 	      
		 	      m.put(ATTR_TEXT1, curs.getString(1));	//text k kartinki
		 	      if(curs.getInt(2)==1)m.put(ATTR_ISNOT,true); 
		 	      else m.put(ATTR_ISNOT,false);
		 	 
		 	      
		 	      m.put(ATTR_ISEE, curs.getInt(2));
		 	      m.put(ATTR_MOLCHA, curs.getInt(3));
				  m.put(ATTR_LL, i); 	    		
		    		i++;
	 	      

		 	    	data.add(m);  
		 	      }while(curs.moveToNext());
		 	  		curs.close();
		 	  		db.close();
}
 
 
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW		   
		 	// создание обработчика
	   
 //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
 //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
//"select id_drawable_pic, text_pic, itis, molcha, i_pics from t_pics order by id_drawable_pic" ;	       
 //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
	       public void   oclBt(View vv){
	    	   Dia.kakoi_dia=0;
	    	   i_tag=(Integer) vv.getTag();
		    	 m = new HashMap<String, Object>(); 
		 	     m=data.get(i_tag);
		 	     Dia.txt_pic = (String) m.get(ATTR_TEXT1); 
		 	     idzap = (Integer)m.get(ATTR_IDZP);
		 	    idpick = (Integer)m.get(ATTR_IMAGE);
	    	   Log.d(LOG_TAG, "(i_tag1="+i_tag+" text="+Dia.txt_pic); 
	    	   switch (vv.getId()){
//wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
			      case R.id.bnew:
			 	    	Dia.kakoi_buton="bnew";
		            	 Intent intent_new = new Intent(getApplicationContext(), Dia.class);
		      		    startActivityForResult(intent_new, 1);	//urok 29  
		      		  break; 
//wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww		      		  
			      case R.id.newtekst:
				 	     Dia.kakoi_buton="newtekst";
			 	    	 
		            	 Intent intent_textn = new Intent(getApplicationContext(), Dia.class);
		      		    startActivityForResult(intent_textn, 1);	//urok 29  
		      		  break; 
  		    	   
	
			      case R.id.btriger:
			 Log.d(LOG_TAG, "((CheckBox)vv).getTag()="+((CheckBox)vv).getTag());			//***************************************
			 Log.d(LOG_TAG, "((CheckBox)vv).isChecked()="+((CheckBox)vv).isChecked()); 
			   //((CheckBox)vv).setChecked(true);
			 if( ((CheckBox)vv).isChecked() ) isornot=1; else isornot=0; 
		   		SQLiteDatabase db = tf.getWritableDatabase();
		   		String st = "update t_pics set itis="+isornot+ " where i_pics="+idzap;
		   		db.execSQL(st);
		   		db.close();
	    		fada(); 
	 	      	sAdapter.notifyDataSetChanged();

	 	      	break; 
	 	      	
			      case R.id.udalim:
				 	     Dia.kakoi_buton="udalim";
			 	    	 
		            	 Intent intent_udalim = new Intent(getApplicationContext(), Dia.class);
		      		    startActivityForResult(intent_udalim, 1);	//urok 29  
		      		  break; 
   	    default:
   	      break;  	 	    	    
   	  }
	  
  };

//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW		   

//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
   	   
	    	   
	  //	     Log.d(LOG_TAG,"oclBt="+"Nowaia"); 
	
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
	       @Override
	       protected void onActivityResult(int requestCode, int resultCode, Intent data) {	//urok 29
	    	   String st;
	    	   
	    	   if(Dia.rez_dia==1){	// изменить text
		   		SQLiteDatabase db = tf.getWritableDatabase();
		   		st = "update t_pics set text_pic='"+Dia.txt_pic+ "' where i_pics="+idzap;
		   		db.execSQL(st);
		   		db.close();

	    		fada(); 
	 	      	sAdapter.notifyDataSetChanged();
	    	 }
	    	 else if(Dia.rez_dia==2){	// новпая
		   		SQLiteDatabase db = tf.getWritableDatabase();
				  st="insert into t_pics (text_pic, id_drawable_pic,itis, molcha) values('";	
				  st=st+Dia.txt_pic+"', ";	//текст картинки
				  st=st+idpick;				  
				  st=st+", 1, 0);";		// 1 картинка задействована, 0 не задействована
		          	//Log.d(LOG_TAG,stk);		//+++++++++++++++++++++++++++++++++++++++  
				  db.execSQL(st);
			   	  db.close();
	    		fada(); 
	    		sAdapter.notifyDataSetChanged(); 
	    	 }
	    	 else if(Dia.rez_dia==3){	// удалить
		   		SQLiteDatabase db = tf.getWritableDatabase();
		   	    st="Delete from t_pics where i_pics="+idzap;  
			    db.execSQL(st);
				db.execSQL(st);
			   	db.close();
	    		fada(); 
	    		sAdapter.notifyDataSetChanged(); 

	    		 
	    	 }


	    	   
	      	 	//System.exit(0);
	       }    

//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
 //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
	       public void   sel_it(View vv){
	    	   
	    	   
	    	   
//	 	    	 nclick=(Integer) vv.getTag();
//	 	    	sAdapter.notifyDataSetChanged();
	       }
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW	       
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW	       
		   @Override
		    public boolean onCreateOptionsMenu(Menu menu) {
		        // Inflate the menu; this adds items to the action bar if it is present.
		        getSupportMenuInflater().inflate(R.menu.korpic, menu);
		       // setHomeButtonEnabled;
		        return true;
		    }	       
		    @Override
		    public boolean onOptionsItemSelected(MenuItem item) {
		        // Handle presses on the action bar items
		           switch (item.getItemId()) {
		            case android.R.id.home:
		              Log.d(LOG_TAG,"id.home2="+item.getItemId());
		              System.exit(0);
		                return true;
		                
		                
		            default:
		                return super.onOptionsItemSelected(item);
		                
		        }
		   
				   //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW	
		 		  //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
  }	


		    
//*********************************************************************
			  class MyViewBinder implements SimpleAdapter.ViewBinder {
				    
				    int red = getResources().getColor(R.color.Red);
				    int orange = getResources().getColor(R.color.Orange);
				    int green = getResources().getColor(R.color.Green);
			        int Dor = getResources().getColor(R.color.Dor);
// in all tag=""+i+" "+	R.id.Lin_1+","+R.id.Lin_2+";"+R.id.Lin_3+":"+R.id.Lin_4		    
				    @Override
				    public boolean setViewValue(View view, Object data,
				        String textRepresentation) {
				  
				      int i = 0;
				      int ind_list=-1;
				      boolean bol=false;
				      switch (view.getId()) {
				      //wwwwwwwwwwwwwwwwwwwwwwwwwww
				      //wwwwwwwwwwwwwwwwwwwwwwwwwww
				      case R.id.Lin_3:
				    
				    	  ind_list = ((Integer) data).intValue();
				       	 //if( i==nclick)view.setBackgroundColor(Color.rgb(128, 255, 255));
				       	 view.setBackgroundColor(Dor);
				       	 i_tag=ind_list;
				       	 //view.setTag(i);				    	  
			              Log.d(LOG_TAG,"Lin_3=");				    	  
				      return true; 
				      //wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww	
				    //wwwwwwwwwwwwwwwwwwwwwwwwwww
				      case R.id.imagk:
				    	  i = ((Integer) data).intValue();
			    	  	((ImageView) view).setImageResource(i);
				    	  view.setTag(i_tag);
				    	  
				      return true; 
				      //wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww					      
				    //wwwwwwwwwwwwwwwwwwwwwwwwwww
				      case R.id.textk:
			              //Log.d(LOG_TAG,"textk=");				    	  
				      return false; 
				      //wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww	
				    //wwwwwwwwwwwwwwwwwwwwwwwwwww
				      case R.id.bnew:
				    	  view.setTag(i_tag);
				          ((Button)view).setText("Новая");
	      	 //if( ind_list==nclick)view.setEnabled(true);else view.setEnabled(false);
			
	      	return true;
				          //wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww					      
				          //wwwwwwwwwwwwwwwwwwwwwwwwwww
				      case R.id.newtekst:
				    	  view.setTag(i_tag);
				          ((Button)view).setText("Сменить текст");
			              Log.d(LOG_TAG,"Сменить текст=");
					      	 //if( ind_list==nclick)view.setEnabled(true);else view.setEnabled(false);
				          return true;
				          //wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww					      
				      case R.id.btriger:
				    	  bol = ((Boolean) data).booleanValue();
				    	  if(bol){
				    		  ((CheckBox)view).setChecked(true);
				    		  ((CheckBox)view).setText("Выключить");
				    	  }else{
				    		  ((CheckBox)view).setChecked(false);
				    		  ((CheckBox)view).setText("Включить");
				    	  };
				
				    	  view.setTag(i_tag);
				      return true; 
//wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
				      case R.id.udalim:
				    	  i = ((Integer) data).intValue();
		      	 //if( ind_list==nclick)view.setEnabled(true);else view.setEnabled(false);
				    	  if(i==1){
				    		  ((Button)view).setText("Удалить нельзя");
				    		  view.setEnabled(false);
				    	  }
				    	  else {
				    		  ((Button)view).setText("Удалить");
				    		  view.setEnabled(true);
				    	  }
				    	  view.setTag(i_tag);
				          
			              Log.d(LOG_TAG,"Сменить текст=");
				          return true;
//wwwwwwwwwwwwwwwwwwwwwwwwwwwwww
			      
				          }
    
				      return false;
				    }
				  }
							
			  //*********************************************************************
			  
		    
}		    

//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW		    



