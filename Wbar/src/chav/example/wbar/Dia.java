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
	static int kakoi_dia;	// =1-��������  =2-������� 
	static String kakoi_buton;	// ="bnew" "btriger" "newtekst" ""
	static String txt_pic;
	static int hist_chto_udaliem;
			// 3-ostawim za mesiaz; 2-ostawim za nedelu; 1-ostawim tekushchi den; 4-udalim wcu
	static int rez_dia;	//=0-������ =1-��������  =2-�����  =3-������� ���� 4-������� �������
	//private static final int DIALOG_EXIT = 1;
	String stk;
String LOG_T = "myLo";	
	@Override
	protected  void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    AlertDialog.Builder adb = new AlertDialog.Builder(this);		
		
if(kakoi_dia==1){	//��������(������� �������)

    // ���������
	
    adb.setTitle("�������� �������");
    

	  switch (hist_chto_udaliem) {
	   	case 1:
	   		stk="������� ��� ������� ����� �������� ���?";		    	   		
        	break;
	   	case 2:
	   		stk="������� ��� ������� ����� ������� ��� � ������?";
	   		break;
	   	case 3:
	   		stk="������� ��� ������� ����� ������� ���, ������ � ������?";		    	   		
		   break;
	   	case 4:
	   		stk="������� ��� �������?";		    	   		
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
        	 rez_dia=0;	//	�� ���� �� ������
          finish();
           // �������� �������� ��������� ������!
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
    // ���������
	   Log.d(LOG_T, "NOWAIA="); 
    adb.setTitle("H����");
    // ���������
    adb.setMessage("������� ����� � �������");
    // ������
    adb.setIcon(android.R.drawable.ic_dialog_info);
    // ������� ���� �����
    final EditText input = new EditText(this);
    input.setText(txt_pic);
    adb.setView(input);
 

    // ������ �������������� ������
   // adb.setPositiveButton("yes", myClickListener);
    
          // ������ �������������� ������
         // adb.setPositiveButton("yes", myClickListener);
          
    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int whichButton) {
        	      Editable value = input.getText();
        	      txt_pic=value.toString();
        	      rez_dia=2;
                 	finish();
        	       // �������� �������� ��������� ������!
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
    // ���������
    adb.setTitle("�������� �����");
    // ���������
    adb.setMessage("�������� ����� � �������");
    // ������
    adb.setIcon(android.R.drawable.ic_dialog_info);
    // ������� ���� �����
    final EditText input = new EditText(this);
    input.setText(txt_pic);
    adb.setView(input);
 //   j = lvcorS.getCheckedItemPosition();
  

    // ������ �������������� ������
   // adb.setPositiveButton("yes", myClickListener);
    
          // ������ �������������� ������
         // adb.setPositiveButton("yes", myClickListener);
          
    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int whichButton) {
        	      Editable value = input.getText();
        	      txt_pic=value.toString();
        	      rez_dia=1;
                 	finish();
        	       // �������� �������� ��������� ������!
         	      }
        	    }
        	     );
      
          
          
    adb.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
      	    public void onClick(DialogInterface dialog, int whichButton) {
      	      rez_dia=0;
               	finish();
      	       // �������� �������� ��������� ������!
       	      }
      	    }
      	     );       

          adb.show();        
	
}

//adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {

//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
else if(kakoi_buton=="udalim"){
    // ���������
    adb.setTitle("������� ������");
    // ���������
    //adb.setMessage("�������� ����� � �������");
    // ������
    adb.setIcon(android.R.drawable.ic_dialog_info);
    // ������� ���� �����
 
    // ������ �������������� ������
   // adb.setPositiveButton("yes", myClickListener);
    
          // ������ �������������� ������
         // adb.setPositiveButton("yes", myClickListener);
          
    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int whichButton) {
        	      rez_dia=3;
                 	finish();
        	       // �������� �������� ��������� ������!
         	      }
        	    }
        	     );
          
          
    adb.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
      	    public void onClick(DialogInterface dialog, int whichButton) {
      	      rez_dia=0;
               	finish();
      	       // �������� �������� ��������� ������!
       	      }
      	    }
      	     );       

          adb.show();        
	
}

//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
}      // konez onCreate
            
 
  }