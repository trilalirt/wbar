package chav.example.wbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.SimpleAdapter;

public class DbTrafic extends SQLiteOpenHelper {
	
	  static final String D_NAME = "dtrec";
	  private static final String T_CREATE = "create table ttrec (i_time integer primary key, id_pic integer, dlt_date integer, text_pic text);";
	  //id_pics ключи равные уникальному(i_pics) из таблицы T_pics
	  private static final String T_pics = "create table t_pics (i_pics integer primary key autoincrement,id_drawable_pic integer, text_pic text, itis integer, molcha integer);";
	  //По таблице T_pics со значением itis=1 строится лист виев в головном активити
	  //itis меняется в третьем активити там же вводится новая строка в таблицу или удаляется
	  //molcha=1 строки заданные по умолчанию; =0 строки введенные пользователем 
	  String stk="";
	final String LOG_TAG = "MY_LOG";    //********************************************
	static ArrayList<Map<String, Object>> data;
	private Map<String, Object> m;
	final static long nedela=(24*3600000)*7;	// милисекунд в 1 неделе
	final static long nedela5=nedela*5;	// милисекунд в 5 неделях		
	DbTrafic(Context context,int dbVer) {
		  super(context, D_NAME, null, dbVer);
		 
//		SQLiteDatabase db = DbTrafic.getWritableDatabase();	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	  //mContext = context;//нужно ли?
	}
	
	//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW	
	@Override
	public void onCreate(SQLiteDatabase db) {
		  db.execSQL(T_CREATE);
		  db.execSQL(T_pics);
		  for(int i=0; i<AllPics.k.length; i++){
			  stk="insert into t_pics (text_pic, id_drawable_pic,itis, molcha) values('";	
			  stk=stk+AllPics.k[i].texts1;	//текст картинки
			  stk=stk+"',"+AllPics.k[i].img;				  
			  stk=stk+", "+AllPics.k[i].itis+", 1);";		// 1 картинка задействована, 0 не задействована
	          	//Log.d(LOG_TAG,stk);		//+++++++++++++++++++++++++++++++++++++++  
			  db.execSQL(stk);
		  }
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	  db.execSQL("DROP TABLE IF EXISTS ttrec");
	  db.execSQL("DROP TABLE IF EXISTS T_pics");
	  onCreate(db);
	}
	//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW	
	
	public void read_pics1(){		//чтение из базы задействованных картинок (itis==1) для главного активити
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor curs = db.rawQuery("select id_drawable_pic, text_pic  from t_allpics where itis=1", null);
		int with1=0;		//количество задействованных картинок
		curs.moveToFirst();
		do {
			int ldt = (int) curs.getLong(0);
			AllPics.k[ldt].texts1=curs.getString(1);
			if(curs.getInt(2)==1){with1++;}
			AllPics.k[ldt].itis=curs.getInt(2);
	      }while(curs.moveToNext());
		curs.close();
		AllPics.wiju_pics=with1;
	}
	//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
//ttrec (i_time integer primary key, id_pic integer, dlt_date integer, text_pic text);";
	
	public void myInsert(int idpic, String stext) {
	if(idpic>=0){
		  Log.d(LOG_TAG, "тгьиук зшслсрш="+idpic);     	//******************************************
			Date dt=new Date();
			long tt=dt.getTime();  //текущее время в милисекундах от 1970 года
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor curs = db.rawQuery("select max(i_time), min(i_time) from ttrec", null);
			boolean nor1=curs.moveToFirst();
			long maxi=curs.getLong(0);
			long mini=curs.getLong(1);
			curs.close();
			
		if((maxi-mini>nedela5)){	//удаляем записи за неделю    //*************************************
				long dtdel=mini+nedela;	//дата до которой удаляем
				stk="Delete from ttrec where i_time<"+dtdel;
				db.execSQL(stk);
		}														//****************************************
			
			if(maxi>0){	//запись не первая
				//Log.d(LOG_TAG, "Делаем апдате "+maxi);	*******************************************************
				long ts=tt-maxi;
				stk="UPDATE ttrec SET dlt_date="+ts;
				stk=stk+" WHERE i_time="+ maxi;
				//Log.d(LOG_TAG, stk);					*****************************************************
				db.execSQL(stk);
			}

			stk="insert into ttrec (i_time, id_pic, dlt_date, text_pic) values("+tt+", ";
			stk=stk+idpic+", 0, '"+stext+"')";
			Log.d(LOG_TAG, stk);					//*****************************************
			db.execSQL(stk);
			db.close();
	}  
		}
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
	//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
	//db = new SQLiteDatabase getReadableDatabase();
	//imgView.setImageResource(int resId) — загружает изображение по идентификатору ресурса
	
	}

