package Duplicate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.*;


public class main extends registers {
 
     public static void main(String[] args )throws Exception{

        int linePointer=1;

        registers obj=new registers();//creating object of type registers

        // give the file input with "\\" in b/w files
        try{
            File file = new File("C:\\Users\\DELL\\Downloads\\sample1.txt");

            // Creating an object of BufferedReader class
        BufferedReader br= new BufferedReader(new FileReader(file));

        String st;
        
        br.mark(200);
        
        System.out.println("Reading the program");
        while ((st = br.readLine()) != null){
        
            if(st.equals(".text")){
                obj.data=false;
            }
            System.out.println(st);// Print the string

            if(obj.Jump.equals("false")){
                if(obj.data){
                    obj.ReadData(st);//this will read data in .data section
                }else{
                    obj.ReadLine(st,linePointer);//this will read data in .text class
                }
            }
            else{
                br.reset();
                while ((st = br.readLine()) != null){
                    
                    if(st.equals(obj.Jump)){
                        obj.Jump = "false";
                        break;
                    }
                }
            }
        }   
        }catch( FileNotFoundException e){
            System.out.println("Path enterd is wrong or the file dosent't exist");
        }

        System.out.println("Program is finished");
        obj.Showregister(); // this will show the elements in register
        obj.ShowMemory();   // this will show the memory elements 
    }
}

