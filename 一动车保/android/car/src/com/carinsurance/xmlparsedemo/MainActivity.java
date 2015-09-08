package com.carinsurance.xmlparsedemo;
//package com.carinsurance.xmlparse;
//
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.util.List;
//
//import com.carinsurancer.car.R;
//
//import android.support.v7.app.ActionBarActivity;
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//
//
//public class MainActivity extends Activity {  
//    
//  private static final String TAG = "XML";  
//    
//  private BookParser parser;  
//  private List<Book> books;  
//    
//  @Override  
//  public void onCreate(Bundle savedInstanceState) {  
//      super.onCreate(savedInstanceState);  
//      setContentView(R.layout.activity_main);  
//        
//      Button readBtn = (Button) findViewById(R.id.readBtn);  
//      Button writeBtn = (Button) findViewById(R.id.writeBtn);  
//        
//      readBtn.setOnClickListener(new View.OnClickListener() {  
//          @Override  
//          public void onClick(View v) {  
//              try {  
//                  InputStream is = getAssets().open("books.xml");  
//                  parser = new SaxBookParser();  //创建SaxBookParser实例  
//                  books = parser.parse(is);  //解析输入流  
//                  for (Book book : books) {  
//                      Log.i(TAG, book.toString());  
//                  }  
//              } catch (Exception e) {  
//                  Log.e(TAG, e.getMessage());  
//              }  
//          }  
//      });  
//      writeBtn.setOnClickListener(new View.OnClickListener() {  
//          @Override  
//          public void onClick(View v) {  
//              try {  
//                  String xml = parser.serialize(books);  //序列化  
//                  FileOutputStream fos = openFileOutput("books.xml", Context.MODE_PRIVATE);  
//                  fos.write(xml.getBytes("UTF-8"));  
//              } catch (Exception e) {  
//                  Log.e(TAG, e.getMessage());  
//              }  
//          }  
//      });  
//  }  
//}  