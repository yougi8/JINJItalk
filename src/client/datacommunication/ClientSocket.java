package client.datacommunication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import client.frame.ChatWindowFrame;
import client.frame.ChatWindowPanel;
import client.frame.IndexPanel;
import server.datacommunication.Message;

public class ClientSocket extends User_config{

  Socket socket;

  public void startClient() {
    
    Thread thread = new Thread(()->{
      try {
    	User_config.result();
        System.out.println("IP: " + IP + "\tport : " + port);
        
        socket = new Socket(); // 소켓 생성
        //socket.connect(new InetSocketAddress("localhost", 5000)); // 연결 요청
        socket.connect(new InetSocketAddress(IP,port)); // 연결 요청
        System.out.println("연결 요청");
        // -> socket 생성 및 연결 요청
      } catch (IOException e) {
        System.out.println("서버 통신 안됨");
        e.printStackTrace();
      }
      receive();
    });
    
    thread.start();

  }

  public void stopClient() {

    try {
      if (socket != null && !socket.isClosed()) {
        socket.close();
      }
    } catch (IOException e) {
    }
  }

  // 서버에서 보낸 데이터를 받는 역할
  public void receive() {

    while (true) {
      // 버퍼 생성
      byte[] recvBuffer = new byte[1024];
      // 서버로부터 받기 위한 입력 스트림 뚫음
      InputStream inputStream;
      try {
        inputStream = socket.getInputStream();
        int readByteCount = inputStream.read(recvBuffer);
        if (readByteCount == -1) {
          throw new IOException();
        }
        Message message = toMessage(recvBuffer, Message.class);
        
// ===================================================================================================== //
        
        //JOptionPane.showConfirmDialog(null,  "일대일 채팅을 하시겠습니까?", "아 어렵다",  JOptionPane.YES_NO_OPTION);
        //System.out.println("어디서 보낸거지?");
        
// ===================================================================================================== //
        
        ChatWindowPanel.displayComment(message);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private Message toMessage(byte[] recvBuffer, Class<Message> class1) {

    Object obj = null;
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(recvBuffer);
      ObjectInputStream ois = new ObjectInputStream(bis);
      obj = ois.readObject();
    } catch (Exception e) {
    }
    return class1.cast(obj);
  }
  
  // 팝업 창 띄우기
 /* public int sendPop() {
	  
	  return JOptionPane.showConfirmDialog(null,  "일대일 채팅을 하시겠습니까?", "일대일 채팅 수락",  JOptionPane.YES_NO_OPTION);
  }*/
  

  // 서버로 메시지를 보내는 역할
  public void send(Message messageInfo) {

    Thread thread = new Thread(()->{
      // 객체를 byte array로 변환
      byte[] bytes = null;
      ByteArrayOutputStream bos = new ByteArrayOutputStream(); // 바이트 배열에 데이터를 입출력하는데 사용되는 스트림.
                                                               // 데이터를 임시로 바이트 배열에 담아서 변환 등 작업 사용
      try {
        ObjectOutputStream oos = new ObjectOutputStream(bos); // 객체를 직렬화.
        oos.writeObject(messageInfo); // 객체를 직렬화하기 위해 메소드 사용
        oos.flush(); // 버퍼에 잔류하는 모든 바이트 출력
        oos.close();
        bos.close();
        bytes = bos.toByteArray(); // byteArray로 변환
      } catch (IOException e) {
      }
      // message객체를 byte로 변환 후 소켓을 통해 보냄
      try {
        byte[] data = bytes;
        OutputStream outputStream = socket.getOutputStream(); // 출력 스트림 얻기.
        outputStream.write(data);
        outputStream.flush();
        System.out.println("서버로 보내기 완료!");
      } catch (IOException e) {
        System.out.println("서버로 통신 안됨");
        e.printStackTrace();
      }
    });
    thread.start();
  }
}