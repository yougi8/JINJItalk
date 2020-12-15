package client.frame; 

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import server.userdb.UserDAO;
import controller.Controller;
import enums.AlignEnum;
import enums.CommonWord;
import server.datacommunication.Message;
import util.ColorSet;
import util.FileChooser;
import util.UseImageFile;

@SuppressWarnings("serial")
public class ChatWindowPanel extends JPanel {
	public static int flag = 1;
  private ArrayList<String> todays; // 친구들 오늘의 한마디 저장
  
  private String todayLetter;
  
  private String unickname;
  
  private String pw;
  
  private String panelName;

  private JTextArea textArea;

  private JButton sendButton;

  private JButton imgFileButton;
  
  private JButton setButton; // 개인 설정 버튼

  private JTextPane jtp;

  private StyledDocument document;

  private Image img = UseImageFile.getImage("C:\\Users\\지연\\team_photo\\folder.png");

  Controller controller;
  UserDAO change = new UserDAO();
  private static String userName;
  
  public ChatWindowPanel(ImageIcon imageIcon, String friendName) {

    controller = Controller.getInstance();
    userName = controller.username;
    panelName = friendName;
    
    setBackground(ColorSet.talkBackgroundColor);
    setLayout(null);
    showFriendInfo(imageIcon, friendName);
    writeMessageArea();
    showContentArea();
    
    imgFileButton = showImgFileButton();
    add(imgFileButton);
  
    imgFileButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        File file = FileChooser.showFile();
        textArea.setText(file.toString());
      }
    });
    
    
    sendButton = showSendButton();
    add(sendButton);
    
 // ------------------- set 버튼 추가 -------------------------- //
    setButton = showSetButton();
    add(setButton);
    
// ============================= set 버튼 작동 : 오늘의 한마디, 별명, 비밀번호 변경 & 회원탈퇴 ================================= //
    setButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub			
			
		  JFrame frame=new JFrame(userName+"(님) 개인 설정");
  		  Container c = frame.getContentPane();
  		  
  		  JLabel label1 = new JLabel("오늘의 한마디 ");
  		  JLabel label2 = new JLabel("별명");
  		  JLabel label3 = new JLabel("비밀번호");JLabel label4 = new JLabel("회원을 "); JLabel label5 = new JLabel("탈퇴하려면 버튼을 눌러주세요.  ㅡㅡ> ");
  		  label1.setHorizontalAlignment(SwingConstants.CENTER); label2.setHorizontalAlignment(SwingConstants.CENTER);
  		  label3.setHorizontalAlignment(SwingConstants.CENTER); label4.setHorizontalAlignment(SwingConstants.RIGHT);
  		  label5.setHorizontalAlignment(SwingConstants.LEFT);
  		  
  		  JTextField text1=new JTextField(); JTextField text2 = new JTextField();
  		  JTextField text3 = new JTextField();
  		  text1.setHorizontalAlignment(SwingConstants.LEFT); text2.setHorizontalAlignment(SwingConstants.LEFT);
		  text3.setHorizontalAlignment(SwingConstants.LEFT);
		  
  		  JButton btn1 = new JButton("변경"); JButton btn2 = new JButton("변경"); 
  		  JButton btn3 = new JButton("변경"); JButton btn4 = new JButton("회원 탈퇴");
  		  btn1.setBackground(ColorSet.messageSendButtonColor); btn2.setBackground(ColorSet.messageSendButtonColor); 
  		  btn3.setBackground(ColorSet.messageSendButtonColor); btn4.setBackground(ColorSet.messageSendButtonColor); 
  		  btn1.setFont(new Font("맑은 고딕", Font.BOLD,12)); btn2.setFont(new Font("맑은 고딕", Font.BOLD,12));
  		  btn3.setFont(new Font("맑은 고딕", Font.BOLD,12)); btn4.setFont(new Font("맑은 고딕", Font.BOLD,12));
		  
  		  c.setLayout(new GridLayout(4,3));
  		  c.add(label1); c.add(text1); c.add(btn1); c.add(label2); c.add(text2); c.add(btn2);
  		  c.add(label3); c.add(text3); c.add(btn3); c.add(label4); c.add(label5); c.add(btn4);
  		  
  		  frame.setLocation(250, 600);
  		  frame.setPreferredSize(new Dimension(700, 200));
  		  frame.pack();
  		  frame.setVisible(true);
  		  
  		  // ========================= 오늘의 한마디 바꾸는 버튼 작동 ============================ //
  		  btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				todayLetter = text1.getText();			
				
				change.searchToday(userName,todayLetter);
				JOptionPane.showMessageDialog(null, "오늘의 한마디 변경 완료!");
				
			}

			private void dispose() {
				// TODO Auto-generated method stub
				
			}
  			
  		  });
  		  
  		// ========================= 별명 바꾸는 버튼 작동 ============================ //  
  		  btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				unickname = text2.getText();			
				
				change.searchNickname(userName,unickname);
				
				JOptionPane.showMessageDialog(null, "별명 변경 완료!");
				
			}
  			
  		  });
  		  
  		// ========================= 비밀번호 바꾸는 버튼 작동 ============================ //  
  		  btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pw = text3.getText();			
				
				change.changePW(userName,pw);
				JOptionPane.showMessageDialog(null, "비밀번호 변경 완료!");
			}
  			
  		  });
  		// ========================= 회원탈퇴 버튼 작동 ============================ //  
  		  btn4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				System.out.println(userName);
				change.out(userName);
				JOptionPane.showMessageDialog(null, "회원 탈퇴가 완료되었습니다. 감사합니다");
				
				System.exit(0);
			}
  			
  		  });
		}
    });
    
 // ============================= set 버튼 끝 ================================= //
    
    
    // ======================== 메세지 보내는 버튼 작동 =========================== //
    
    sendButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
    	  
    	  
        Controller controller = Controller.getInstance();

        String messageType = null;
        if (textArea.getText().contains(".jpg") || textArea.getText().contains(".png")
            || textArea.getText().contains(".JPG") || textArea.getText().contains(".PNG")) {
          messageType = "file";
        } else {
          messageType = "text";
        }
        Message message = null;
        if (messageType.equals("file")) {
          message = new Message(controller.username, textArea.getText(), LocalTime.now(),
              messageType, friendName);
        } else {
          message = new Message(controller.username, textArea.getText(), LocalTime.now(),
              messageType, friendName);
        }

        controller.clientSocket.send(message);
        textArea.setText("");
      }
    });
  }

  public void paint(Graphics g) {

    super.paint(g);
    Graphics2D g2 = (Graphics2D) g;
    Line2D lin = new Line2D.Float(0, 81, 400, 81);
    g2.draw(lin);
    Graphics2D g3 = (Graphics2D) g;
    Line2D lin2 = new Line2D.Float(0, 458, 400, 458);
    g3.draw(lin2);
  }

  private void showFriendInfo(ImageIcon imageIcon, String friendName) {
	    
    JLabel friendInfolabel = new JLabel(imageIcon);
    friendInfolabel.setOpaque(true);
    friendInfolabel.setText(friendName);
    friendInfolabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
    friendInfolabel.setBounds(0, 0, 400, 80);
    friendInfolabel.setBackground(Color.WHITE);
    add(friendInfolabel);
    
  }


private JButton showImgFileButton() {

    JButton imgFileButton = new JButton(new ImageIcon(img));
    imgFileButton.setBackground(ColorSet.talkBackgroundColor);
    Border emptyBorder2 = BorderFactory.createEmptyBorder();
    imgFileButton.setBorder(emptyBorder2);
    imgFileButton.setFocusPainted(false);
    imgFileButton.setBounds(0, 460, 60, 40);
    return imgFileButton;
  }

  private JButton showSendButton() {

    JButton sendButton = new JButton("전송");
    sendButton.setBackground(ColorSet.messageSendButtonColor);
    sendButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));
    sendButton.setFocusPainted(false);
    sendButton.setBounds(320, 500, 68, 65);
    return sendButton;
  }

//----------- 설정 버튼 --------------------//
  private JButton showSetButton() {
		  JButton setButton = new JButton("개인 설정");
		  setButton.setBackground(ColorSet.messageSendButtonColor);
		  setButton.setFont(new Font("맑은 고딕", Font.BOLD,12));
		  setButton.setFocusPainted(false);
		  setButton.setBounds(280,22,120,40);
		 
		  return setButton;
  }
//----------------------------------------- //
  
  private void writeMessageArea() {

    textArea = new JTextArea(20, 20);
    JScrollPane scroller = new JScrollPane(textArea);
    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroller.setBounds(0, 500, 321, 65);
    add(scroller);
  }

  private void showContentArea() {

    StyleContext context = new StyleContext();
    document = new DefaultStyledDocument(context);
    jtp = new JTextPane(document);
    jtp.setBackground(ColorSet.talkBackgroundColor);
    jtp.setEditable(false);
    JScrollPane scroller2 = new JScrollPane(jtp);
    scroller2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroller2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroller2.setBounds(0, 80, 389, 380);
    add(scroller2);
  }

 
 public static void displayComment(Message message) {

	for (ChatWindowPanel chatName : IndexPanel.chatPanelName) {
      // 오른쪽으로 출력.
      if (userName.equals(message.getSendUserName())
          && chatName.panelName.equals(message.getReceiveFriendName())) {
        chatName.textPrint(message.getSendTime().format(DateTimeFormatter.ofPattern("aHH:mm"))
            + "  <" + message.getSendUserName() + ">", AlignEnum.RIGHT);
        if (message.getMessageType().equals("file")) {
          chatName.imgPrint(message.getSendComment());
        } else {
          chatName.textPrint(message.getSendComment(), AlignEnum.RIGHT);
        }
      }
      
      // 왼쪽으로 출력.    
      //!chatName.panelName.equals(message.getReceiveFriendName()) -> 나와의 채팅은 왼쪽 출력되면 안되니까.
      if (chatName.panelName.equals(message.getSendUserName()) && !chatName.panelName.equals(message.getReceiveFriendName())) {
        chatName.textPrint(message.getSendTime().format(DateTimeFormatter.ofPattern("aHH:mm"))
            + "  <" + message.getSendUserName() + ">", AlignEnum.LEFT);
        if (message.getMessageType().equals("file")) {
          chatName.imgPrint(message.getSendComment());
        } else {
          chatName.textPrint(message.getSendComment(),AlignEnum.LEFT);
        }
      }
    }
  }


  private void imgPrint(String sendComment) {

    Image imgFile = UseImageFile.getImage(sendComment);
    Image imgResize = imgFile.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
    StyledDocument doc2 = (StyledDocument) jtp.getDocument();
    Style style2 = doc2.addStyle("StyleName", null);
    StyleConstants.setIcon(style2, new ImageIcon(imgResize));
    try {
      doc2.insertString(doc2.getLength(), "invisible text" + "\n", style2);
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  }

  private void textPrint(String string, AlignEnum alignEnum) {

    try {
      document = jtp.getStyledDocument();
      SimpleAttributeSet sortMethod = new SimpleAttributeSet();
      
      if(alignEnum == AlignEnum.RIGHT) {
        StyleConstants.setAlignment(sortMethod, StyleConstants.ALIGN_RIGHT);   
      }else if (alignEnum == AlignEnum.LEFT) {
        StyleConstants.setAlignment(sortMethod, StyleConstants.ALIGN_LEFT);  
      }
      document.setParagraphAttributes(document.getLength(), document.getLength() + 1, sortMethod, true);
      document.insertString(document.getLength(), string + "\n", sortMethod);
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  } 
}