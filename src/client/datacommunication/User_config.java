package client.datacommunication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class User_config {
	
	public static String IP; // = "127.0.0.1";
    public static Integer port; // = 9999;
    
    public static String getIP() {//IP�� return 
    	return IP;
    }
    public static Integer getport() {//port ��ȣ return
    	return port;
    }

    public static void result() throws IOException {//���� �б�
    	File file = new File("test.txt");//���ϸ� : Test.txt

        if (file.exists()) {//Test.txt ������ ���� ��
            BufferedReader inFile = new BufferedReader(new FileReader(file));

            String[] arr = new String[3];
            String sLine = null;
            int i = 0;

            while ((sLine = inFile.readLine()) != null) {
                arr[i] = sLine;//arr�迭�� ���� �� �پ� �ֱ�
                i++;
            }

            IP = arr[0];
            port = Integer.valueOf(arr[1]);
            
        } else {//Test.txt ������ ���� ��
            System.out.println("file x");
            IP = "localhost";
            port = 5000;
        }
        
        getIP();
        getport();
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
