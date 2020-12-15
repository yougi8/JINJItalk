package client.frame;

import java.awt.BorderLayout;
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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import client.frame.FriendListPanel;
import server.userdb.UserDAO;
import controller.Controller;
import enums.CommonWord;
import server.datacommunication.Message;
import server.datacommunication.ServerHandler;
import util.CommonPanel;
import util.UseImageFile;
import util.UserProfileButton;
import javax.swing.JPanel;
//
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.JScrollBar;
import java.awt.Scrollbar;
import java.awt.Panel;
import java.awt.Color;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class IndexPanel extends CommonPanel {
  private JLabel jLabel;
  private Image img = UseImageFile.getImage("C:\\Users\\지연\\team_photo\\woman.png");
  
  public static UserProfileButton userProfileButton;
  public static ArrayList<ChatWindowPanel> chatPanelName = new ArrayList<ChatWindowPanel>();
  
  Controller controller;
  
  private String text;
  private JTextField textField;
  
  public IndexPanel() throws IOException, ParseException {
     logoLabel.setBounds(0, 0, 144, 42);
    controller = Controller.getInstance();

    meanMyProfileTitle(CommonWord.MYPROFILE.getText());
    meanMyProfile();
    
    meanFriendProfileTitle(CommonWord.FRIEND.getText());
    showFriendList();
    
  }

  private void meanMyProfileTitle(String text) {

    jLabel = new JLabel(text);
    jLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
    jLabel.setBounds(30, 96, 161, 38);
    add(jLabel);
  }

  private void meanMyProfile() {

    ImageIcon imageIcon = new ImageIcon(img);
    userProfileButton = new UserProfileButton(imageIcon);
    userProfileButton.setText(controller.username);
    userProfileButton.setBounds(30, 149, 325, 64);
    add(userProfileButton);
    
    // 나와의 채팅 버튼 //
    userProfileButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
       
       
        if (userProfileButton.getText().contains("대화 중..")) {
          // 작동x
        } else {
          userProfileButton.setText(userProfileButton.getText() + "       대화 중..");
          String messageType = "text";
          Message message = new Message(controller.username, controller.username + "님이 입장하였습니다.",
              LocalTime.now(), messageType, controller.username);
          ChatWindowPanel c = new ChatWindowPanel(imageIcon, controller.username);
          new ChatWindowFrame(c, controller.username);
          chatPanelName.add(c);
          
          Controller controller = Controller.getInstance();
          controller.clientSocket.send(message);
        }
         
      }
    });
  }

 private void meanFriendProfileTitle(String text) {

    jLabel = new JLabel(text);
    jLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
    jLabel.setBounds(30, 220, 200, 30);
    add(jLabel);
  }

 private void showFriendList() throws IOException, ParseException {

    FriendListPanel jpanel = new FriendListPanel();
    JScrollPane scroller = new JScrollPane(jpanel);
    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroller.setBounds(30, 250, 325, 300);
    add(scroller);
    
  // =========================================== Json parser 생성 ======================================================= //
    String serviceKey = "rlQTEJcgimhJT2Z%2B6KwVwx41Qx%2FUm297Ha7RDcZ72xJYQzPqOjQ4DfzpMr20N2WAf%2BsZRmqOv2REpOFgkQ%2B4vg%3D%3D";//Service Key
    String dataType = "JSON";  // or "XML"
    String baseDate = "20201215";   //
    String baseTime = "0500";   //05시 발표
    
   StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"); /*URL*/
   urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey);/*Service Key*/
   urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
   urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
   urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
   urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*2020년 11월 22일 발표*/
   urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*05시 발표*/
   urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("18", "UTF-8")); /*에보지점 X 좌표값*/
   urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*에보지점의 Y 좌표값*/
    
   URL url = new URL(urlBuilder.toString());
   HttpURLConnection conn = (HttpURLConnection) url.openConnection();
   conn.setRequestMethod("GET");
   conn.setRequestProperty("Content-type", "application/json");
   System.out.println("Response code: " + conn.getResponseCode());
   BufferedReader rd;
   if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
       rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
   } else {
       rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
   }
   StringBuilder sb = new StringBuilder();
   String line;
   while ((line = rd.readLine()) != null) {
       sb.append(line);
   }
   rd.close();
   conn.disconnect();
   System.out.println(sb.toString());
   
   Panel panel = new Panel();
   panel.setBackground(new Color(178, 202, 196));
   panel.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 12));
    panel.setBounds(193, 10, 187, 133);
    add(panel);
   
    JSONParser parser = new JSONParser(); 
    JSONObject obj = (JSONObject) parser.parse(sb.toString()); 
    JSONObject parse_response = (JSONObject) obj.get("response"); 
    JSONObject parse_body = (JSONObject) parse_response.get("body"); 
    JSONObject parse_items = (JSONObject) parse_body.get("items");
    JSONArray parse_item = (JSONArray) parse_items.get("item");
    
    String category;
    JSONObject weather;
    String day=""; String time="";
    
    for(int i = 0 ; i < 1; i++) {
       weather = (JSONObject) parse_item.get(i);
       Object baseDate1 = weather.get("baseDate"); Object baseTime1 = weather.get("baseTime");
       Object fcstDate = weather.get("fcstDate"); Object fcstTime = weather.get("fcstTime");
       Object fcstValue = weather.get("fcstValue"); Object nx = weather.get("nx");
       Object ny = weather.get("ny");

       category = (String)weather.get("category"); 
       
       String cate_info = ""; // ex.강수확률, 습도 등
		String unit = "";	// %, mm 등
		switch (category) {
			case "POP": cate_info = "강수확률"; unit = "%";
				break;
			case "PTY": 
				cate_info = "강수형태";
				/* 강수형태 해석하기 */
				if(fcstValue.equals("0")) fcstValue = "강수 없음";
				else if(fcstValue.equals("1")) fcstValue = "비";
				else if(fcstValue.equals("2")) fcstValue = "비/눈 (진눈개비)";
				else if(fcstValue.equals("3")) fcstValue = "눈";
				else if(fcstValue.equals("4")) fcstValue = "소나기";
				else if(fcstValue.equals("5")) fcstValue = "빗방울";
				else if(fcstValue.equals("6")) fcstValue = "빗방울/눈날림";
				else if(fcstValue.equals("7")) fcstValue = "눈날림";
				else fcstValue = "에러";
				break;
			case "R06": cate_info = "6시간 강수량"; unit = "mm";
				break;
			case "REH": cate_info = "습도"; unit = "%";
				break;
			case "S06": cate_info = "6시간 신적설"; unit = "cm";
				break;
			case "SKY": 
				cate_info = "하늘상태";
				/* 하늘상태 해석하기 */
	            if(fcstValue.equals("1")) fcstValue = "맑음";
	            else if(fcstValue.equals("3")) fcstValue = "구름 많음";
	            else if(fcstValue.equals("4")) fcstValue = "흐림";
	            else fcstValue = "에러";
	            break;
			case "T3H": cate_info = "3시간 기온"; unit = " ℃";
				break;
			case "TMN": cate_info = "아침 최저기온"; unit = " ℃";
				break;
			case "TMX": cate_info = "낮 최고기온";  unit = " ℃";
				break;
			case "UUU": cate_info = "풍속(동서성분)"; unit = "m/s";
				break;
			case "VEC": cate_info = "풍향"; unit = "m/s";
				break;
			case "VVV": cate_info = "풍속(남북성분)"; unit = "m/s";
				break;
			case "WSD": cate_info = "풍속"; unit = "m/s";
				break;
			case "WAV": cate_info = "파고"; unit = "M";
				break;
			default: cate_info = "Fail"; 
				break;
		}
		
       JLabel label0 = new JLabel("<오늘의 날씨 정보>");
       JLabel label1 = new JLabel("예보 날짜 : "+(String)baseDate); JLabel label2 = new JLabel("예보 시간 : " +(String)baseTime);
       JLabel label3 = new JLabel("\t" + cate_info +" : " + fcstValue + unit); 
       
       label1.setFont(new Font("맑은 고딕", Font.BOLD,14)); label2.setFont(new Font("맑은 고딕", Font.BOLD,14)); 
       label3.setFont(new Font("맑은 고딕", Font.BOLD,14)); label0.setFont(new Font("맑은 고딕", Font.BOLD,14));
		  	
       label1.setHorizontalAlignment(SwingConstants.CENTER); label2.setHorizontalAlignment(SwingConstants.CENTER);
       label3.setHorizontalAlignment(SwingConstants.CENTER); label0.setHorizontalAlignment(SwingConstants.CENTER);
       
       
       panel.setLayout(new GridLayout(4,1));
       panel.add(label0); panel.add(label1); panel.add(label2); panel.add(label3);
       

    }
 // ============================================== 날씨정보 파싱 끝 ======================================================== //

//========================== 친구 검색 & 등록 ======================================//
    textField = new JTextField();
	textField.setText("친구 별명을 검색하세요");
	textField.setBounds(84, 220, 215, 30);
	add(textField);
	textField.setColumns(10);

	JButton btn = new JButton("검색");
	btn.setForeground(Color.WHITE);
	btn.setBackground(new Color(83, 129, 127));
	btn.setBounds(298, 220, 82, 30);
	add(btn);

	// =========================  친구 검색 버튼 클릭 ================================= //
	btn.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			UserDAO search = new UserDAO();
			
			text = textField.getText(); // 친구 검색창에 입력된 별명 읽는 변수
			String result[] = search.searchUser(text,controller.username); // searchUser에서 text와 일치하는 값을 찾아 정보를 array로 받아온다
			
			JFrame frame = new JFrame("검색 결과 ( 상세정보 )");
			Container c = frame.getContentPane();
			
			c.setBackground(new Color(255,255,204));
			
			JButton btn2 = new JButton (" 친구 추가하기 "); // 친구추가 버튼
			btn2.setBackground(new Color(255,255,153));
			
			/* 얻어온 친구 정보 출력 */
			JLabel label1 = new JLabel("오늘의 한마디 : " + result[0]); JLabel label2 = new JLabel("이름 : " + result[1]);
			JLabel label3 = new JLabel("ID : " + result[2]); JLabel label4 = new JLabel("별칭 : " + result[3]);
			JLabel label5 = new JLabel("이메일 : " + result[4]); JLabel label6 = new JLabel("생년월일 : " + result[5]);
	  
			label1.setFont(new Font("맑은 고딕", Font.BOLD,14)); label2.setFont(new Font("맑은 고딕", Font.BOLD,14)); 
			label3.setFont(new Font("맑은 고딕", Font.BOLD,14)); label4.setFont(new Font("맑은 고딕", Font.BOLD,14)); 
  		  	label5.setFont(new Font("맑은 고딕", Font.BOLD,14)); label6.setFont(new Font("맑은 고딕", Font.BOLD,14)); 
  		  	btn2.setFont(new Font("맑은 고딕", Font.BOLD,13));
  		  	
			label1.setHorizontalAlignment(SwingConstants.LEFT); label2.setHorizontalAlignment(SwingConstants.LEFT);
			label3.setHorizontalAlignment(SwingConstants.LEFT); label4.setHorizontalAlignment(SwingConstants.LEFT);
			label5.setHorizontalAlignment(SwingConstants.LEFT); label6.setHorizontalAlignment(SwingConstants.LEFT);
	  
			c.setLayout(new GridLayout(7,1));
			c.add(label1); c.add(label2); c.add(label3); c.add(label4); 
			c.add(label5); c.add(label6); c.add(btn2);
	  
			frame.setLocation(500, 400);
			frame.setPreferredSize(new Dimension(400, 340));
			frame.pack();
			frame.setVisible(true);
			
		// =============================== 친구 추가 버튼 클릭 ================================= //
			btn2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					search.connectUser(text,controller.username); // userDAO의 친구추가 함수와 연결
					JOptionPane.showMessageDialog(null, text+"님과 친구가 되었습니다");
				}
			});
		// ================================ 친구 추가 버튼 끝 ================================= //

		}


	});
// =============================== 친구 검색, 추가 끝 ============================================== //
}
  
  public void paint(Graphics g) {

    super.paint(g);
    Graphics2D g2 = (Graphics2D) g;
    Line2D lin = new Line2D.Float(30, 210, 350, 210);
    g2.draw(lin);
  }
}