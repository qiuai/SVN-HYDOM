package com.hydom.util;


import it.sauronsoftware.jave.AudioAttributes;  
import it.sauronsoftware.jave.Encoder;  
import it.sauronsoftware.jave.EncoderException;  
import it.sauronsoftware.jave.EncodingAttributes;  
import it.sauronsoftware.jave.InputFormatException;  
import java.io.File;  
  
public class ChangeAudioFormat {  
    public static void main(String[] args) throws Exception {  
        String path1 = "E:\\11.amr";  
        String path2 = "E:\\1395047224460.mp3";  
      //  changeToMp3(path1, path2);  
    }  
  //sourceFile 源文件 
    public static void changeToMp3(File sourceFile, String targetPath) {  
       
        File target = new File(targetPath);  
        AudioAttributes audio = new AudioAttributes();  
        Encoder encoder = new Encoder();  
  
        audio.setCodec("libmp3lame");  
        EncodingAttributes attrs = new EncodingAttributes();  
        attrs.setFormat("mp3");  
        attrs.setAudioAttributes(audio);  
  
        try {  
            encoder.encode(sourceFile, target, attrs);  
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        } catch (InputFormatException e) {  
            e.printStackTrace();  
        } catch (EncoderException e) {  
            e.printStackTrace();  
        }  
    }  
}  