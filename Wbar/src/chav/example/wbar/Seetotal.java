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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;


//public class Seehist extends Activity{
public class Seetotal extends SherlockActivity {	
	private static final String LOG_TAG = "MY_LOG";
	//static int k; //otladka
	static int total_1_2_3;	//1-day 2-week 3-month  day, week, month 
	//0-удалить все; 1-оставить день; 2-Оставить неделю; 3-оставить месяц
	//
	// имена атрибутов для Map
		final String ATTR_TEXT1 = "text1";
		  final String ATTR_PBAL = "PBAL";
		  final String ATTR_ITOT = "ITOT";		  
		  int max_PBAL;
		  static long Wremia_nach;
		  //final String ATTR_PBLOAD = "pbLoad";	  
	 //ProgressBar pgb;	  
	ListView lvtot;
	SimpleAdapter sAdapter;
	ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); 
	Map<String, Object> m;
	String stk = "";
		TextView txtwhot;
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
		   @Override
		    protected void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.total);
		        txtwhot = (TextView) findViewById(R.id.txtwhot); 
		        txtwhot.setBackgroundColor(Color.rgb(111, 255, 255));
		        txtwhot.setTextColor(Color.rgb(119, 0, 0));
		         final com.actionbarsherlock.app.ActionBar ab = getSupportActionBar(); 
		         ab.setHomeButtonEnabled(true);
		         ab.setDisplayHomeAsUpEnabled(true); 
						ab.setDisplayShowHomeEnabled(true);
						//ab.setDisplayShowTitleEnabled(true);
/*
		         ab.setHomeButtonEnabled(false);
		           ab.setDisplayHomeAsUpEnabled(false); 

		        				ab.setDisplayShowHomeEnabled(false);
		         				ab.setDisplayShowTitleEnabled(false);
*/		  
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW		  

		    // упаковываем данные в понятную для адаптера структуру
		    DbTrafic tf=new DbTrafic(this, 1);
	 	     Date dt=new Date();
	 	     //----начало дня-------
	 	     		Date dt_nach=new Date(dt.getYear(),dt.getMonth(), dt.getDate());
	 	     		Wremia_nach=dt_nach.getTime();
	 	     		if(total_1_2_3==1){
	 	     			txtwhot.setText("Итог за день");
		 	     		Dia.hist_chto_udaliem=1;
	 	     		}

	 	     		else if(total_1_2_3==2){
	 	     			Wremia_nach=Wremia_nach-DbTrafic.nedela;
	 	     			txtwhot.setText("Итог за неделю");
	 	     		}
	 	     		else if(total_1_2_3==3){
	 	     			Wremia_nach=Wremia_nach-DbTrafic.nedela*4;
	 	     			txtwhot.setText("Итог за месяц");
	 	     		}
	 	     		
	 		 //----начало дня-------

		    SQLiteDatabase db = tf.getWritableDatabase();
	//table ttrec (i_time integer primary key, id_pic integer, dlt_date integer, text_pic text);";	    
		 // sum данных 
	 		stk="select max(i_time) from ttrec" ;
	 		Cursor curs = db.rawQuery(stk, null);
	 		if(!curs.moveToFirst())System.exit(0);
	 		long max_data = curs.getLong(0);
	 	     long last_delta = dt.getTime()-max_data;	//время для последней
			stk="UPDATE ttrec SET dlt_date="+last_delta;
			stk=stk+" WHERE i_time="+ max_data;
			//Log.d(LOG_TAG, stk);					//*****************************************************
			db.execSQL(stk);
			curs.close();
			db.close();
			db = tf.getWritableDatabase();
		    
			stk="select sum(dlt_date) from ttrec ";
			stk=stk+"where i_time >= "+Wremia_nach;
			curs = db.rawQuery(stk, null);
			if(!curs.moveToFirst())System.exit(0);
			Log.d(LOG_TAG, "Max_long="+curs.getLong(0));					//*****************************************************
			max_PBAL=(int) (curs.getLong(0)/1000);
			Log.d(LOG_TAG, "Max_int="+max_PBAL);					//*****************************************************			
		 	//pgb=(ProgressBar) findViewById(R.id.pbLoad);
			//  pgb.setMax((int) curs.getLong(0)/1000);
			curs.close();
		    
			stk="select sum(dlt_date), text_pic from ttrec ";
			stk=stk+"where i_time >= "+Wremia_nach+" group by text_pic order by 1 DESC";
			curs = db.rawQuery(stk, null);
			//chzp=curs.getCount();
			if(!curs.moveToFirst())System.exit(0);
						
		    data.clear();
	 	    //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy kk.mm");
	 	    // delta, dch, dmin, dsec;	// long dateTime and ihtervals
   int iz=0;
	 	  do {
	 		  	iz++;
	 		  	m = new HashMap<String, Object>();
					//Log.d(LOG_TAG, "Номер в списке="+id_in_list);
			 	long dltinmili= curs.getLong(0);
		 		int delta = (int) (dltinmili/1000);	//wsego sekond
			 	   m.put(ATTR_PBAL, delta);			    	   
		 	
		 	stk=curs.getString(1);
		 	   int dch = delta/3600;	//wsego chasov
		 	   if(dch==0)stk=stk+"(";else stk=stk+" ("+dch+"h ";
		 	  delta=delta%3600;
	 	   int dmin = delta/60;	//wsego minut
	 	   		if(!(dmin==0))stk=stk+dmin+"m ";
		 	 int dsec = delta%60;  //cecond w chase
		 	 	if(!(dsec==0))stk=stk+dsec+"c";
		 	 	stk=stk.trim()+")";
		 	      m.put(ATTR_TEXT1, stk);
				 	
				 	m.put(ATTR_ITOT,iz%2);
		 	      data.add(m);
		 	      }while(curs.moveToNext());
		 	  
		 	    
		 	    
		 	    
		 	// массив имен атрибутов, из которых будут читаться данные
	 	    String[] from = { ATTR_PBAL, ATTR_TEXT1, ATTR_ITOT};
		 	    // массив ID View-компонентов, в которые будут вставлять данные
	 	    int[] to = {R.id.pbLoad, R.id.textt, R.id.Ltot};

		 	    // создаем адаптер
	 	    SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.total_item,from, to);

	 	   sAdapter.setViewBinder(new MyViewBinder());

	 	    // определяем список и присваиваем ему адаптер 
	 	   lvtot = (ListView) findViewById(R.id.lvtot);
	 	  lvtot.setAdapter(sAdapter);
		 	// создание обработчика
		    }    
	  
					  //*********************************************************************
					  class MyViewBinder implements SimpleAdapter.ViewBinder {
						    
						    int red = getResources().getColor(R.color.Red);
						    int orange = getResources().getColor(R.color.Orange);
						    int green = getResources().getColor(R.color.Green);
					        int Dor = getResources().getColor(R.color.Dor);
					    
						    @Override
						    public boolean setViewValue(View view, Object data,
						        String textRepresentation) {
						      int i = 0;
						      switch (view.getId()) {
						         case R.id.Ltot:
						        	 i = ((Integer) data).intValue();
						       	  if(i==0){
						       		view.setBackgroundColor(Color.rgb(132, 60, 31));
						       		} 
						       	  else{
							       		view.setBackgroundColor(Color.rgb(0, 0, 159));
						    		 }
						        return true;        	  

						      case R.id.pbLoad:
						    
						    	 i = ((Integer) data).intValue();
						        ((ProgressBar)view).setProgress(i);
						        ((ProgressBar)view).setMax(max_PBAL);
								//Log.d(LOG_TAG, "Gress k="+k+"i="+i+"max_PBAL="+max_PBAL);					//*****************************************************
						        //k=k++;
						        return true;
						      }
						      return false;
						    }
						  }
							
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
		           switch (item.getItemId()) {
		            case R.id.kartot:
		            	Dia.kakoi_dia=1;			//Удаление истории(короткий диа)
		            	 Intent intentj = new Intent(getApplicationContext(), Dia.class);
		      		    startActivityForResult(intentj, 1);	//urok 29  

		                return true;
		            case R.id.karhist:
		            	Dia.kakoi_dia=1;			//Удаление истории(короткий диа)
		            	 Intent intentk = new Intent(getApplicationContext(), Dia.class);
		      		    startActivityForResult(intentk, 1);	//urok 29  

		                return true;
		                
		            case android.R.id.home:
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
	    	   stk="delete from ttrec where i_time < "+Wremia_nach;
	    	   db.execSQL(stk);

	        }
    	   
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW

 
	}