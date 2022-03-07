package Duplicate;
import java.util.*;

class registers{
    // creating 32 registers 
    String[] register= {"zero","ra","sp","gp","tp","t0","t1","t2","s0","s1","a0","a1","a2","a3","a4","a5","a6","a7","s2","s3","s4","s5","s6","s7","s8","s9","s10","s11","t3","t4","t5","t6"};
    //assigning values to the registers
    int regvalue[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    int memory[]=new int[1024];
    int index=0;
    String label[]=new String[50];
    int labelIndex=0;

    boolean data=true;
    String Jump = "false"; 

    // function to get the index of register
    public int getindex(String[] register ,String tempo){
        int i = 0;
        
        if(tempo.charAt(0) == 'x' ){
            if(tempo.length()== 2){
                i = Character.getNumericValue(tempo.charAt(1));
            }
            else{
                String dum = "" + tempo.charAt(1) + tempo.charAt(2);
                i = Integer.parseInt(dum);
            }

        }

        else{
            for(i = 0; i < 32; i++){
                if(register[i].equals(tempo)){
                    break;   
                }
            }
        }   
        return i;
    }

    // Function to show the register elements
    public void Showregister(){
        System.out.println("******* Showing Register Element ********");
        System.out.println(" X"+0+"["+register[0]+"]"+"   "+regvalue[0]+"     "+"X"+(0+16)+"["+register[0+16]+"]"+"        "+regvalue[0+16]);
        for(int i=1;i<10;i++){
            System.out.println(" X"+i+"["+register[i]+"]"+"     "+regvalue[i]+"     "+"X"+(i+16)+"["+register[i+16]+"]"+"        "+regvalue[i+16]);
        }
        for(int i=10;i<16;i++){
            System.out.println("X"+i+"["+register[i]+"]"+"     "+regvalue[i]+"     "+"X"+(i+16)+"["+register[i+16]+"]"+"        "+regvalue[i+16]);
        }

        System.out.println("\n");
    }

    // function to show the memory element
    public void ShowMemory(){
        System.out.println("******* Showing Memory Element ********");
        System.out.println(" address                  value    ");
        for(int x=0;x<index;x++){
            System.out.println("  "+"0x"+10010000+x*4+"              "+ memory[x]);
        }
    }

    // Function to read the lines in .data section
    public void ReadData(String S){
        if(S.equals(".data")){
            return;
        }
        int n=S.length();
        S=S.trim();
        
        String temp="";
        String t="";
        String arg="";
        int i=0;
        while(S.charAt(i) != ':' && i<n && S.charAt(i)!='#'){
            t=t+S.charAt(i);
            i++;
        }
        label[labelIndex]=t;
        labelIndex++;
        
        while(S.charAt(i)!='.'){
            i++;
        }
        i++;
        while(i<n && S.charAt(i)!='#' && S.charAt(i) != ' '){
            arg=arg+S.charAt(i);
            i++;
        }
        
        while(i<n && S.charAt(i)!='#'){
            temp=temp+S.charAt(i);
            i++;
        }
        temp=temp.trim();
        
        if(arg.equals("word")){
            int value=Integer.parseInt(temp);
            memory[index]=value;
            index++;
        }
    }

    // function to read lines in .text section
    public void ReadLine(String S,int linePointer){
        int n=S.length();
        S=S.trim();
        
        String temp="";
        String temp1="";
        String temp2="";
        String temp3="";
        String t="";
        String arg="";
        int i=0;
        while(i<n && S.charAt(i)!='#' && S.charAt(i) != ' '){
            t=t+S.charAt(i);
            i++;
        }
        while(i<n && S.charAt(i)!='#'){
            arg=arg+S.charAt(i);
            i++;
        }
        String[] argdone = arg. split(",");
        
        // add function
        if(t.equals("add")){
            System.out.println("enters add");
            temp1 = argdone[0].trim();            
            temp2 = argdone[1].trim();                
            temp3 = argdone[2].trim();
                       
            regvalue[getindex(register,temp1)]=(regvalue[getindex(register,temp2)] + regvalue[getindex(register,temp3)]);
        }
        // addi function 
        else if(t.equals("addi")){

            temp1 = argdone[0].trim();            
            temp2 = argdone[1].trim();                
                               
            regvalue[getindex(register,temp1)]=  regvalue[getindex(register,temp2)] +  Integer.parseInt(argdone[2].trim());
        }
        // sub function
        else if(t.equals("sub")){

            temp1 = argdone[0].trim();            
            temp2 = argdone[1].trim();                
            temp3 = argdone[2].trim();
                               
            regvalue[getindex(register,temp1)]=(regvalue[getindex(register,temp2)] - regvalue[getindex(register,temp3)]);
        }
        // subi function
        else if(t.equals("subi")){

            temp1 = argdone[0].trim();            
            temp2 = argdone[1].trim();                
                               
            regvalue[getindex(register,temp1)]= regvalue[getindex(register,temp2)] -  Integer.parseInt(argdone[2].trim());
        }
        // move function
        else if(t.equals("mv") || t.equals("move")){

            temp1 = argdone[0].trim();
            temp2 = argdone[1].trim();
                
            regvalue[getindex(register,temp1)] = regvalue[getindex(register,temp2)];
            
        }
        // beq,bne,bgt,bge,blt,ble function
        else if(t.equals("beq") || t.equals("bne") || t.equals("bgt") || t.equals("bge") || t.equals("blt") || t.equals("ble") ){
            System.out.println("enters comparision loop");

            temp1 = argdone[0].trim();
            temp2 = argdone[1].trim();
            temp = argdone[2].trim();

            int dum1 = regvalue[getindex(register,temp1)];
            int dum2 = regvalue[getindex(register,temp2)];

            if (t.equals("beq") && dum1 == dum2){
                Jump = temp + ":";
            }
            else if (t.equals("bne") && dum1 != dum2){
                Jump = temp + ":";
            }
            else if (t.equals("bgt") && dum1 > dum2){
                Jump = temp + ":";
            }
            else if (t.equals("bge") && dum1 >= dum2){
                System.out.println("enters bge");
                Jump = temp + ":";
            }
            else if (t.equals("blt") && dum1 < dum2){
                Jump = temp + ":";
            }
            else if (t.equals("ble") && dum1 <= dum2){
                Jump = temp + ":";
            }


        }

        else if(t.equals("lw") || t.equals("lb")){
            
            temp1 = argdone[0].trim();
            temp2 = argdone[1].trim();

            int k=0;
            String D="";
            while(temp2.charAt(k)!='('){
                D=D+temp2.charAt(k);
                k++;
            }
           
            k++;
            String E="";
            while(temp2.charAt(k)!=')'){
                E=E+temp2.charAt(k);
                k++;
            }
            
            int dup=Integer.parseInt(D);
            dup=dup/4;
            int pos=index;
            
            boolean status=false;
            for(int y=0;y<32;y++){
                if(register[y].equals(E)){
                    status=true;
                    break;
                }
            }

            if(status){
                for(int x=0;x<index;x++){
                    if(memory[x]==(regvalue[getindex(register,E)])){
                        pos=x;
                        break;
                    }
                }
            }
            else{
                for(int x=0;x<labelIndex;x++){
                    if(label[x].equals(E)){
                        pos=x;
                        break;
                    }
                }
            }
            regvalue[getindex(register,temp1)]=memory[pos+dup];
        }
        
        else if(t.equals("sw") || t.equals("sb")){

            temp1 = argdone[0].trim();
            temp2 = argdone[1].trim();

            int k=0;
            String D="";
            while(temp2.charAt(k)!='('){
                D=D+temp2.charAt(k);
                k++;
            }
            k++;
            String E="";
            while(temp2.charAt(k)!=')'){
                E=E+temp2.charAt(k);
                k++;
            }
            int dup=Integer.parseInt(D);
            dup=dup/4;
            int pos=index;
            for(int x=0;x<1024;x++){
                if(memory[x]==(regvalue[getindex(register,E)])){
                    pos=x;
                    break;
                }
            }
            index=pos+dup;
            memory[index]=regvalue[getindex(register,temp1)];
            index++;
            
        }else if(t.equals("li")){

            temp1 = argdone[0].trim();
            temp2 = argdone[1].trim();

            int dup=Integer.parseInt(temp2);
            regvalue[getindex(register,temp1)]= dup; 

        }

        else if(t.equals("jal") || t.equals("j")){
            System.out.println("enters j");
            temp = arg.trim();
            Jump = temp + ":" ;
        }

    }
    
}

