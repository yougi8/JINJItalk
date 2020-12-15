package client.frame;
import javax.swing.*;

import org.json.simple.parser.ParseException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

import controller.Controller;
import server.datacommunication.Message;
import util.ColorSet;
import util.UseImageFile;
import util.UserProfileButton;

@SuppressWarnings("serial")
public class FriendListPanel extends JPanel {

  private ArrayList<String> friends; // 친구들 이름 저장
  private ArrayList<String> todays; // 친구들 오늘의 한마디 저장
  private ArrayList<String> ids; // 친구들 아이디 저장
  private ArrayList<String> nicknames; // 친구들 닉네임 저장
  private ArrayList<String> emails; // 친구들 이메일 저장
  private ArrayList<String> births; // 친구들 생일정보 저장
  
  private ArrayList<ImageIcon> friendIcons = new ArrayList<ImageIcon>(); // 친구들 프로필 이미지 저장

  public static ArrayList<JButton> friendButtons = new ArrayList<JButton>(); // 친구들 정보 버튼 저장
  
  private final int FRIEND_PROFILE_IMG_MAX = 8;

  private final int FRIEND_PROFILE_IMG_MIN = 1;

  public FriendListPanel() {

    setBackground(ColorSet.talkBackgroundColor);
    Controller controller = Controller.getInstance();
   
    /* 친구 정보 확인 위한 list */
    friends = controller.friendList();
    todays = controller.todayList();
    ids = controller.idList();
    nicknames = controller.nicknameList();
    emails = controller.emailList();
    births = controller.birthList();
    
    
    int friendNum = friends.size();
    setLayout(new GridLayout(friendNum, 1));
    for (int index = 0; index < friendNum; index++) {
      Random rand = new Random();
      int randomNum =
          rand.nextInt((FRIEND_PROFILE_IMG_MAX - FRIEND_PROFILE_IMG_MIN) + FRIEND_PROFILE_IMG_MIN)
              + 1;
      Image img = UseImageFile.getImage("C:\\Users\\지연\\team_photo//profile" + randomNum + ".png");
      ImageIcon imageIcon = new ImageIcon(img);
      UserProfileButton userprofileButton = new UserProfileButton(imageIcon);
      userprofileButton.setText(friends.get(index));
      add(userprofileButton);
      friendIcons.add(imageIcon);
      friendButtons.add(userprofileButton);
    }
    for (int i = 0; i < friendNum; i++) {
      friendButtons.get(i).putClientProperty("page", i);
      friendButtons.get(i).addActionListener(new ActionListener() {
    	  @Override
          public void actionPerformed(ActionEvent e) {
    		
    	     Controller controller = Controller.getInstance();
    		 todays = controller.todayList();
    		 
    		 int idx = (Integer) ((JButton) e.getSource()).getClientProperty("page");

    		 //---------------- 화면 (창) ----------------//
    		 
    		  JFrame frame=new JFrame("정보");
    		  Container c = frame.getContentPane();
    		  
    		  c.setBackground(new Color(255,255,204));
    		  JLabel label1 = new JLabel("오늘의 한마디 : " + todays.get(idx)); JLabel label2 = new JLabel("이름 : " + friends.get(idx));
    		  JLabel label3 = new JLabel("ID : " + ids.get(idx)); JLabel label4 = new JLabel("별칭 : " + nicknames.get(idx));
    		  JLabel label5 = new JLabel("이메일 : " + emails.get(idx)); JLabel label6 = new JLabel("생년월일 : " + births.get(idx));
    		  
    		  JButton btn = new JButton (" 일대일 채팅하기 ");
    		  
    		  label1.setFont(new Font("맑은 고딕", Font.BOLD,14)); label2.setFont(new Font("맑은 고딕", Font.BOLD,14)); 
    		  label3.setFont(new Font("맑은 고딕", Font.BOLD,14)); label4.setFont(new Font("맑은 고딕", Font.BOLD,14)); 
    		  label5.setFont(new Font("맑은 고딕", Font.BOLD,14)); label6.setFont(new Font("맑은 고딕", Font.BOLD,14)); 
    		  btn.setFont(new Font("맑은 고딕", Font.BOLD,13));
    		  
    		  btn.setBackground(new Color(255,255,153));
    		  
    		  label1.setHorizontalAlignment(SwingConstants.LEFT); label2.setHorizontalAlignment(SwingConstants.LEFT);
    		  label3.setHorizontalAlignment(SwingConstants.LEFT); label4.setHorizontalAlignment(SwingConstants.LEFT);
    		  label5.setHorizontalAlignment(SwingConstants.LEFT); label6.setHorizontalAlignment(SwingConstants.LEFT);
    		  
    		  c.setLayout(new GridLayout(7,1));
    		  c.add(label1); c.add(label2); c.add(label3); c.add(label4); 
    		  c.add(label5); c.add(label6); c.add(btn);
    		  
    		  frame.setLocation(500, 400);
    		  frame.setPreferredSize(new Dimension(400, 340));
    		  frame.pack();
    		  frame.setVisible(true);
    		  
    		  //---------------- 화면 (끝) ----------------//
    		
    	// ===================== 일대일채팅 버튼 ===================== //
    		  btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					// ================== 채팅 yes / no 팝업 ============================ //
					
			      int result = JOptionPane.showConfirmDialog(null,  "일대일 채팅을 하시겠습니까?", "일대일 채팅 요청",  JOptionPane.YES_NO_OPTION);
			      
			        // ======================= yes no 팝업 끝 =================================== //	
					
					/* no 눌렀을 때 : 채팅 안 함 */
					if (result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {

				            // 작동x
				          } 
					 
					 /* yes 눌렀을 때 : 채팅 정상 작동 */
					 else {
				            friendButtons.get(idx).setText(friendButtons.get(idx).getText() + "       대화 중..");
				            String messageType = "text";
				            Message message = new Message(controller.username, controller.username + "님이 입장하였습니다.",
				                LocalTime.now(), messageType, friends.get(idx));
				            ChatWindowPanel c = new ChatWindowPanel(friendIcons.get(idx), friends.get(idx));
				            new ChatWindowFrame(c, friends.get(idx));
				            IndexPanel.chatPanelName.add(c);
				            
				            Controller controller = Controller.getInstance();
				            controller.clientSocket.send(message);

				          }
				}
    		  });
          // ========================== 일대일채팅 버튼 끝 ================================ //
    		  
    	  } 
      });
    }
  }
}