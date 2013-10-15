package chav.example.wbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class Akbar extends SherlockActivity {
	// имена атрибутов для Map
		  final static String ATTR_IMAGE = "image";
		final static String ATTR_TEXT1 = "text1";
		  final static String ATTR_LL = "Linia";
		//	final static long nedela=(24*3600000)*7;	// милисекунд в 1 неделе
		//	final static long nedela5=nedela*5;	// милисекунд в 5 неделях	
		//static int minzwet;
		final static String LOG_TAG = "myLogs";		//+++++++++++++++++++++++++++++++++++++++++
				
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); 
		Map<String, Object> m;
		ListView lvS;
		  static SimpleAdapter sAdapter;
		  //static LinearLayout lout;
		  static int nclick=-1;		//nom stroki klirnuli
		  Button btnOk;
		  int idpick;  //для записи в историю ид картинки 
		  String stext;		// для записи в историю текста
		  //static TextView my_text;
		  //static ArrayList<Map<String, Object>> data;
		  //Map<String, Object> m;
		  static int  k[];			//порядковые номера выбранной картинки в общем списке картин
		  DbTrafic tf=new DbTrafic(this, 1);

		

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akbar);
         final com.actionbarsherlock.app.ActionBar ab = getSupportActionBar(); 
         /*
         ab.setHomeButtonEnabled(true);
         ab.setDisplayHomeAsUpEnabled(true); 
				ab.setDisplayShowHomeEnabled(true);
				ab.setDisplayShowTitleEnabled(true);
		*/

         ab.setHomeButtonEnabled(false);
           ab.setDisplayHomeAsUpEnabled(false); 

        				ab.setDisplayShowHomeEnabled(false);
         				ab.setDisplayShowTitleEnabled(false);
		    AllPics.setdat();

		    // упаковываем данные в понятную для адаптера структуру
		    fada();		        
		 	// массив имен атрибутов, из которых будут читаться данные
		 	    String[] from = { ATTR_IMAGE, ATTR_TEXT1, ATTR_LL};
		 	    // массив ID View-компонентов, в которые будут вставлять данные
		 	    int[] to = {  R.id.imag, R.id.texta, R.id.Linia};

		 	    // создаем адаптер
		 	sAdapter = new SimpleAdapter(this, data, R.layout.item,
		 	        from, to);
		 	  sAdapter.setViewBinder(new MyViewBinder());
			  btnOk = (Button) findViewById(R.id.dial);
			  btnOk.setOnClickListener(oclBtnOk);
			  btnOk.setEnabled(false);
 	      //my_text= (TextView) findViewById(R.id.my_text);
		 	    // определяем список и присваиваем ему адаптер
		 	    lvS = (ListView) findViewById(R.id.lvS);
		 	   lvS.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		 	    lvS.setAdapter(sAdapter);
		 	  

//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
		 	   lvS.setOnItemClickListener(new OnItemClickListener() {
		 	      public void onItemClick(AdapterView<?> parent, View view,
		 	          int position, long id) {
		 	    	 nclick=position;
		 	    	 m = new HashMap<String, Object>(); 
		 	    	 m=data.get(nclick);
			 	    idpick=(Integer) m.get(ATTR_IMAGE);
		 	    	String stk=(String) m.get(ATTR_TEXT1);
		 	    	stext=stk;
		 	    	stk=stk+"(OK?)";
		 	    	btnOk.setText(stk);
		 	    	btnOk.setBackgroundColor(Color.WHITE);
		 	    	btnOk.setEnabled(true);
		 	    	//Log.d(LOG_TAG,"n__k---___ick = "+nclick);
		 	      	sAdapter.notifyDataSetChanged();
		 	      }
		 	    });
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW

		 	    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.akbar, menu);
       // setHomeButtonEnabled;
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
           switch (item.getItemId()) {
            case R.id.pirog:
            	Log.d(LOG_TAG,"pirog="+item.getItemId());
            	
            	 Intent intent = new Intent(getApplicationContext(), Seehist.class);
   		      //startActivity(intent);
	 		       startActivityForResult(intent, 1);	//urok 29  

            	
                return true;
            case R.id.karandash:
              	 Intent intentk = new Intent(getApplicationContext(), KorPic.class);
      		      //startActivity(intent);
   	 		       startActivityForResult(intentk, 1);	//urok 29  
                return true;
            case android.R.id.home:
                  return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
   //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {	//urok 29
    	
		fada(); 
		sAdapter.notifyDataSetChanged(); 
   	 	//System.exit(0);
    }    
    
   //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW 
    
    OnClickListener oclBtnOk = new OnClickListener() {
        @Override
        public void onClick(View v) {
        	btnOk.setText("Принято!");
        	btnOk.setBackgroundColor(Color.rgb(99, 250, 90));
 	    	btnOk.setEnabled(false);
        	tf.myInsert(idpick,stext);
        	//showDialog(idpick,stext);
           	//Log.d(LOG_TAG,"P_R_I_N_I_A_I");
          }
      };
 
 //WWWWWWWWWWWWWWWWWWWWWWWWWWW Inner class WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
 //wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
      class MyViewBinder implements SimpleAdapter.ViewBinder {
        
        int red = getResources().getColor(R.color.Red);
        int orange = getResources().getColor(R.color.Orange);
        int green = getResources().getColor(R.color.Green);
        int Bgol = getResources().getColor(R.color.Bgol);
        int Dor = getResources().getColor(R.color.Dor);
        
        
        @Override
        public boolean setViewValue(View view, Object data,
            String textRepresentation) {
        
		int i;
          switch (view.getId()) {
         case R.id.Linia:
        	 i = ((Integer) data).intValue();
       	  if(i==nclick){
        	 //Log.d(LOG_TAG,"Ravenstvo   sclik ="+nclick+"  nint ="+i);
       		//view.setBackgroundColor(Bgol);
       		view.setBackgroundColor(Color.rgb(174, 232, 232));
       		} 
       	  else{
       	        view.setBackgroundColor(Dor);
    		 }
      return true;        	  
          }

          return false;
          
      }
    }      

 //WWWWWWWWWWWWWWWWWWWWWWWWWWW End inner class WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW  
 //wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
    public void fada(){
    nclick=-1;	
    data.clear();
  	Log.d(LOG_TAG, "Array list_1");		//+++++++++++++++++++++++++++++++++++++++
	SQLiteDatabase db = tf.getWritableDatabase();
	String stk = "select id_drawable_pic,text_pic from t_pics where itis=1 order by id_drawable_pic";
  	Log.d(LOG_TAG,stk);		//+++++++++++++++++++++++++++++++++++++++        		
	Cursor curs = db.rawQuery(stk, null);
	if(curs.moveToFirst()){
	int i = 0;
  	do {
		m = new HashMap<String, Object>();
		m.put(Akbar.ATTR_IMAGE, curs.getInt(0));
  		m.put(Akbar.ATTR_TEXT1, curs.getString(1)); 
		m.put(Akbar.ATTR_LL, i); 	    		
		i++;
		data.add(m);
    }while(curs.moveToNext());
	}
	curs.close();
	db.close();
  	  }    

}
   
