package chav.example.wbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

//import android.content.Intent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;


//public class Seehist extends Activity{
public class Seehist extends SherlockActivity {	
	private static final String LOG_TAG = "MY_LOG";
	 DbTrafic tf=new DbTrafic(this, 1);
	// имена атрибутов для Map
		  final String ATTR_IMAGE = "image";
		final String ATTR_TEXT1 = "text1";
		  final String ATTR_TEXT2 = "text2";
		  int delhist;
		  int delhist_rez;	//=1 elfkztv =0 ytn
 

	ListView lvS2;
	SimpleAdapter sAdapter;
	static ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	Map<String, Object> m;
	String stk = "";
	private String dmy;  
	long progres_max;
		  Button btn1d;
		  Button btn1w;
		  Button btn1m;		  
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
		   @Override
		    protected void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.activity2);
		        Log.d(LOG_TAG, "Log_0");	
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
//         ___________________________________		  

						Log.d(LOG_TAG, "Log_1");	
		    // упаковываем данные в понятную для адаптера структуру
		   
		    int chzp=0;
	 	     Date dt=new Date();
	 	     //----начало дня-------
	 	     		Date dt_nach=new Date(dt.getYear(),dt.getMonth(), dt.getDate());
	 	     		long Wremia_nach=dt_nach.getTime();
	 		 //----начало дня-------

		    SQLiteDatabase db = tf.getWritableDatabase();
	//table ttrec (i_time integer primary key, id_pic integer, dlt_date integer, text_pic text);";	    
		 // sum данных 
		    long max_data;
	 		stk="select max(i_time) from ttrec" ;
	 		Cursor curs = db.rawQuery(stk, null);
	 		if(!curs.moveToFirst()){
	 			
	 			max_data=0;
	 		} else{		//begin ****************** else
//begin ******************************************************************* else	 		 		
	 		
	 		max_data = curs.getLong(0);
	 	     long last_delta = dt.getTime()-max_data;	//время для последней
			stk="UPDATE ttrec SET dlt_date="+last_delta;
			stk=stk+" WHERE i_time="+ max_data;
			db.execSQL(stk);

	 		//progres_max=curs.getLong(0)/1000+last_delta/1000;
	 		
			curs.close();
		    
		    
		    
			stk="select i_time, dlt_date, id_pic, text_pic from ttrec ";
			stk=stk+"where i_time >= "+Wremia_nach+" order by i_time";
		 	Log.d(LOG_TAG, stk);
			curs = db.rawQuery(stk, null);
			chzp=curs.getCount();
			if(!curs.moveToFirst()){
				//System.exit(0)
			}else
			{
				//begin 2mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm  else
				

						
	 	     String stk="";
	 	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy kk.mm\n");
	 	    // delta, dch, dmin, dsec;	// long dateTime and ihtervals
	 	   long ldt;
	 	  data.clear();
	 	  do {
		    	   m = new HashMap<String, Object>();
					//Log.d(LOG_TAG, "Номер в списке="+id_in_list);
		 	      m.put(ATTR_IMAGE, (int) curs.getLong(2));  // картинка
		 	      m.put(ATTR_TEXT1, curs.getString(3));	//text k kartinki
		 	     ldt=curs.getLong(0);	//long dateTime
		 	Log.d(LOG_TAG, "Text_Pikis="+curs.getString(3));
		 	    dt.setTime(ldt);	//установили дату
		 		stk=""+sdf.format(dt);
		 		long dltinmili= curs.getLong(1);
		 		long delta = dltinmili/1000;	//wsego sekond
		 		//m.put(ATTR_PBLOAD,  delta);
			 	   long dch = delta/3600;	//wsego chasov
			 	   if(dch==0)stk=stk+"(";else stk=stk+" ("+dch+"h ";
			 	  delta=delta%3600;
		 	   long dmin = delta/60;	//wsego minut
		 	   		if(!(dmin==0))stk=stk+dmin+"m ";
			 	 long dsec = delta%60;  //cecond w chase
			 	 	if(!(dsec==0))stk=stk+dsec+"c";
			 	 	stk=stk.trim()+")";
			 	 //stk=stk+" ("+dch+"-"+dmin+"-"+dsec+")";
		 	      m.put(ATTR_TEXT2, stk);
		 	      data.add(m);
		 	      }while(curs.moveToNext());
	 	  		Dia.hist_chto_udaliem=4;	//=4 удалить всю историю
  	     		Seetotal.Wremia_nach=Wremia_nach;

		 	    
		 	    
		 	// массив имен атрибутов, из которых будут читаться данные
  	     	}	//End 2  mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm  else
		   }		//END ****************** else	
//END *********************************************************************** else	
	 		
	 		
	 	    String[] from = { ATTR_IMAGE, ATTR_TEXT1, ATTR_TEXT2};
		 	    // массив ID View-компонентов, в которые будут вставлять данные
	 	    int[] to = {  R.id.imag2, R.id.text1, R.id.text2};

		 	    // создаем адаптер
	 	    sAdapter = new SimpleAdapter(this, data, R.layout.item2,from, to);


	 	    // определяем список и присваиваем ему адаптер  
	 	  lvS2 = (ListView) findViewById(R.id.lvS2);
	 	 lvS2.setAdapter(sAdapter);
		 	   btn1d = (Button) findViewById(R.id.d1);   
		 	   btn1w = (Button) findViewById(R.id.w1);  
		 	   btn1m = (Button) findViewById(R.id.m1);  
		 	   btn1d.setOnClickListener(oclBtn);
		 	  btn1w.setOnClickListener(oclBtn);
		 	 btn1m.setOnClickListener(oclBtn);
		 	// создание обработчика
		    }    
	  //__________________________________________________________
	 	     OnClickListener oclBtn = new OnClickListener() {
		 	       @Override
		 	       public void onClick(View v) {
		 	    	  switch (v.getId()) {
			    	   	case R.id.d1:
				 	  		Dia.hist_chto_udaliem=1;	// удалить всю историю кроме текущего дня			    	   		
			    	   		Seetotal.total_1_2_3=1;
		 		         	break;
			    	   	case R.id.w1:
				 	  		Dia.hist_chto_udaliem=2;	// удалить всю историю кроме текущих дня и недели
			    	   		Seetotal.total_1_2_3=2;
			    	   		break;
			    	   	case R.id.m1:
				 	  		Dia.hist_chto_udaliem=3;	// удалить всю историю кроме текущих дня, недели и месяца			    	   		
			    	   		Seetotal.total_1_2_3=3;
			    		   break;
				       }
		    	   		Intent intent = new Intent(getApplicationContext(), Seetotal.class);
	 		         	startActivityForResult(intent, 1);	//urok 29  
				   }
				};	
	//___________________________________________________________________________
	   
   //*********************************************************************
		   @Override
		    public boolean onCreateOptionsMenu(Menu menu) {
		        // Inflate the menu; this adds items to the action bar if it is present.
		        getSupportMenuInflater().inflate(R.menu.seehist, menu);
		       // setHomeButtonEnabled;
		        return true;
		    }
		    
		    @Override
		    public boolean onOptionsItemSelected(MenuItem item) {
		        // Handle presses on the action bar items
		           switch (item.getItemId()) {
		            case R.id.karhist:
		            	Dia.kakoi_dia=1;			//Удаление истории(короткий диа)
			            	 Intent intentj = new Intent(getApplicationContext(), Dia.class);	
			      		      //startActivity(intentj);
			      		    startActivityForResult(intentj, 1);	//urok 29  

			      		    Log.d(LOG_TAG,"W Karzinu="+item.getItemId());
		            	return true;
		            case android.R.id.home:
		              Log.d(LOG_TAG,"id.home2="+item.getItemId());
		              System.exit(0);
		                return true;
		            default:
		                return super.onOptionsItemSelected(item);
		        }
		    }
		  //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
		  //(i_time integer primary key, id_pic integer, dlt_date integer, text_pic text)       
		  	       @Override
		  	       protected void onActivityResult(int requestCode, int resultCode, Intent data) {	//urok 29
		  	    	   String st;
		  	    	   if(Dia.rez_dia==0)return;	// ничего не удалять
		  	    	   DbTrafic tf=new DbTrafic(this, 1);
		  	    	   SQLiteDatabase db = tf.getWritableDatabase();
		  	    	   stk="delete from ttrec";
		  	    	   db.execSQL(stk);
		  	    	 Seehist.data.clear();
		  	    	 sAdapter.notifyDataSetChanged();

		  	        }
		      	   
		  //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW		    
		   //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW		   
	}