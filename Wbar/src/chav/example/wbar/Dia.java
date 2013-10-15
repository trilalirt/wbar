package chav.example.wbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
//import android.widget.Toast; 
//public class Dia  {
public class Dia extends Activity {
	static int kakoi_dia;	// =1-короткий  =2-длинный 
	static String kakoi_buton;	// ="bnew" "btriger" "newtekst" ""
	static String txt_pic;
	static int hist_chto_udaliem;
			// 3-ostawim za mesiaz; 2-ostawim za nedelu; 1-ostawim tekushchi den; 4-udalim wcu
	static int rez_dia;	//=0-ничего =1-изменили  =2-новая  =3-удалить итем 4-удалили историю
	//private static final int DIALOG_EXIT = 1;
	String stk;
String LOG_T = "myLo";	
	@Override
	protected  void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    AlertDialog.Builder adb = new AlertDialog.Builder(this);		
		
if(kakoi_dia==1){	//короткий(Удаляем историю)

    // заголовок
	
    adb.setTitle("Удаление истории");
    

	  switch (hist_chto_udaliem) {
	   	case 1:
	   		stk="удалить всю историю кроме текущего дня?";		    	   		
        	break;
	   	case 2:
	   		stk="удалить всю историю кроме текущих дня и недели?";
	   		break;
	   	case 3:
	   		stk="удалить всю историю кроме текущих дня, недели и месяца?";		    	   		
		   break;
	   	case 4:
	   		stk="удалить всю историю?";		    	   		
		   break;
     }
    
    
    adb.setMessage(stk);

    
	
    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog, int whichButton) {
	      rez_dia=4;
         	finish();
 	      }
	    }
	     );
  //_____________________________

    adb.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
        	 rez_dia=0;	//	ни чего не делать
          finish();
           // Получили значение введенных данных!
          }
    }
     );     
//_____________________________


adb.show(); 
}

//WWWWWWWWWWWWWWWWWWWWWW
//WWWWWWWWWWWWWWWWWWWWWW
else if(kakoi_buton=="bnew")
{
    // заголовок
	   Log.d(LOG_T, "NOWAIA="); 
    adb.setTitle("Hовая");
    // сообщение
    adb.setMessage("Введите текст к каринке");
    // иконка
    adb.setIcon(android.R.drawable.ic_dialog_info);
    // Добавим поле ввода
    final EditText input = new EditText(this);
    input.setText(txt_pic);
    adb.setView(input);
 

    // кнопка положительного ответа
   // adb.setPositiveButton("yes", myClickListener);
    
          // кнопка положительного ответа
         // adb.setPositiveButton("yes", myClickListener);
          
    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int whichButton) {
        	      Editable value = input.getText();
        	      txt_pic=value.toString();
        	      rez_dia=2;
                 	finish();
        	       // Получили значение введенных данных!
         	      }
        	    }
        	     );
      
          
          
    adb.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
      	    public void onClick(DialogInterface dialog, int whichButton) {
       	      rez_dia=0;
               	finish();
       	      }
      	    }
      	     );       

          adb.show();        
	}
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW 
else if(kakoi_buton=="newtekst"){
    // заголовок
    adb.setTitle("Изменить текст");
    // сообщение
    adb.setMessage("Измените текст к каринке");
    // иконка
    adb.setIcon(android.R.drawable.ic_dialog_info);
    // Добавим поле ввода
    final EditText input = new EditText(this);
    input.setText(txt_pic);
    adb.setView(input);
 //   j = lvcorS.getCheckedItemPosition();
  

    // кнопка положительного ответа
   // adb.setPositiveButton("yes", myClickListener);
    
          // кнопка положительного ответа
         // adb.setPositiveButton("yes", myClickListener);
          
    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int whichButton) {
        	      Editable value = input.getText();
        	      txt_pic=value.toString();
        	      rez_dia=1;
                 	finish();
        	       // Получили значение введенных данных!
         	      }
        	    }
        	     );
      
          
          
    adb.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
      	    public void onClick(DialogInterface dialog, int whichButton) {
      	      rez_dia=0;
               	finish();
      	       // Получили значение введенных данных!
       	      }
      	    }
      	     );       

          adb.show();        
	
}

//adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {

//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
else if(kakoi_buton=="udalim"){
    // заголовок
    adb.setTitle("Удалить запись");
    // сообщение
    //adb.setMessage("Измените текст к каринке");
    // иконка
    adb.setIcon(android.R.drawable.ic_dialog_info);
    // Добавим поле ввода
 
    // кнопка положительного ответа
   // adb.setPositiveButton("yes", myClickListener);
    
          // кнопка положительного ответа
         // adb.setPositiveButton("yes", myClickListener);
          
    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int whichButton) {
        	      rez_dia=3;
                 	finish();
        	       // Получили значение введенных данных!
         	      }
        	    }
        	     );
          
          
    adb.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
      	    public void onClick(DialogInterface dialog, int whichButton) {
      	      rez_dia=0;
               	finish();
      	       // Получили значение введенных данных!
       	      }
      	    }
      	     );       

          adb.show();        
	
}

//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
}      // konez onCreate
            
 
  }