package server.userdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTextField;

import client.frame.ChatWindowPanel;

public class UserDAO {

  private String driver = "com.mysql.jdbc.Driver";

  private String jdbcurl = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC&useSSL=false";
  //private String jdbcurl = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
  
  private Connection conn;

  private PreparedStatement pstmt;
  
  ChatWindowPanel chat;

  public String username = null;

  public void connect() {

    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(jdbcurl, "root", "3254");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void disconnect() {

    try {
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean insertDB(User user) {

    connect();
    String sql = "insert into member_table values(?,?,?,?,?,?,?)";
    
    boolean isInsert = false;
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, user.getUname());
      pstmt.setString(2, user.getUemail());
      pstmt.setString(3, user.getPassword());
      pstmt.setString(4, user.getUnickname());
      pstmt.setString(5, user.getBirth());
      pstmt.setString(6, user.getToday());
      pstmt.setString(7, user.getUid());
      pstmt.executeUpdate();
     

      isInsert = true;
 
    } catch (SQLException e) {
      isInsert = false;
    }
    disconnect();
    
    return isInsert;
    
  }
  
  // ================================== 오늘의 한마디 변경 ========================================== //
  public void searchToday(String name, String str) {
	 
	  connect();
	  
	    String sql = "update member_table set today=? where uname=?";
	    String uname = name;
	    
	    String today = null;
	    
	    try {
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(2, uname);
	      pstmt.setString(1, str);
	      int rs = pstmt.executeUpdate();

	      System.out.println(uname+" 의 오늘의 한마디 수정 완료");
	      
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    
	    disconnect();
  }
  
//================================== 별명 변경 ========================================== //
 public void searchNickname(String name, String str) {
	 
	  connect();
	  
	    String sql = "update member_table set unickname=? where uname=?";
	    String uname = name;
	    
	    String today = null;
	    
	    try {
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(2, uname);
	      pstmt.setString(1, str);
	      int rs = pstmt.executeUpdate();

	      System.out.println(uname+" 의 오늘의 별명 수정 완료");
	      
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    
	    disconnect();
 }
 
//================================== 비밀번호 변경 ========================================== //
public void changePW(String name, String str) {
	 
	  connect();
	  
	    String sql = "update member_table set password=? where uname=?";
	    String uname = name;
	    
	    String today = null;
	    
	    try {
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, str);
	      pstmt.setString(2, uname);
	      int rs = pstmt.executeUpdate();

	      System.out.println(uname+" 의 비빌번호 수정 완료");
	      
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    
	    disconnect();
}

//================================== 회원 탈퇴 ========================================== //
public void out(String name) {
	 
	  connect();

	    String sql = "select uemail from member_table where uname=?";
	    String sql2 = "delete from member_table where uname=?";
	    String uname = name;
	    
	    String  id = null;
	    
	    try {
	      // ====== 회원 탈퇴 원하는 사람의 이름으로 아이디 찾아오기 ====== //
	    	pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, name);
		    ResultSet rs = pstmt.executeQuery();
		    while (rs.next()) {
		    	id=  rs.getString("uemail");          
		     }
	      
	      // ====== member_table 테이블에서 회원 삭제해주기 ====== //
	      pstmt = conn.prepareStatement(sql2);
	      pstmt.setString(1, uname);
	      int rs2 = pstmt.executeUpdate();
	     
	      // ====== friendList 테이블에서 친구 관계 끊어주기 ====== //
	      String sql3 = "delete from friendList where userEmail=? or friendEmail=?";
	      pstmt = conn.prepareStatement(sql3);
	      pstmt.setString(1, id);
	      pstmt.setString(2, id);
	      int rs3 = pstmt.executeUpdate();
	      
	      System.out.println(uname+"님 회원 탈퇴 완료");
	      
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    
	    disconnect();
}
 
//=================================== 친구 검색  ============================================ //
 public String[] searchUser(String nickname,String username) {

	    connect();
	    String sql = "select * from member_table where unickname=?";
	    
	    String[] str= new String[7];
	    try {
	      // 친구 검색 당한 사람의 email 가져오기 (unickname의 email)
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, nickname);
	      ResultSet rs = pstmt.executeQuery();
	      while (rs.next()) {
	          str[0] = rs.getString("today");
	          str[1] = rs.getString("uname");
	          str[2] = rs.getString("uemail");
	          str[3] = rs.getString("unickname");
	          str[4] = rs.getString("gmail");
	          str[5] = rs.getString("birth");	          
	        }
	      
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    
	    disconnect();
	    
	    return str;
	    
	  }
 
 	
//=================================== 끝  ============================================ //
 
 // =================================== 친구 신청  ============================================ //
 public void connectUser(String nickname,String username) {

	    connect();
	    String sql = "select uemail from member_table where unickname=?";
	    String sql2 = "select uemail from member_table where uname=?";
	    String sql0 = "select max(id) from friendList";
	    
	    String sendmail="";
	    String receivemail="";	      
	    int max=0;
	    
	    try {
	      pstmt = conn.prepareStatement(sql0);
	      ResultSet rs0 = pstmt.executeQuery();
	      while(rs0.next()) {
	    	  max = rs0.getInt(1);
	      }
	    	
	      // 친구 신청 당한 사람의 email 가져오기 (unickname의 email)
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, nickname);
	      ResultSet rs = pstmt.executeQuery();
	      while (rs.next()) {
	          receivemail = rs.getString("uemail");
	        }
	      
	      // 친구 신청한 사람의 email 가져오기 (username의 email)
	      pstmt = conn.prepareStatement(sql2);
	      pstmt.setString(1, username);
	      ResultSet rs2 = pstmt.executeQuery();
	      while (rs2.next()) {
	          sendmail = rs2.getString("uemail");
	        }
	      
	      // 친구 추가하기
	      String sql3 = "insert into friendList values(?,?,?)";
	      pstmt = conn.prepareStatement(sql3);
	      pstmt.setInt(1, max+1);
	      pstmt.setString(2, receivemail);
	      pstmt.setString(3, sendmail);
	      int rs3 = pstmt.executeUpdate();
	      
	      String sql4 = "insert into friendList values(?,?,?)";
	      pstmt = conn.prepareStatement(sql4);
	      pstmt.setInt(1, max+2);
	      pstmt.setString(2, sendmail);
	      pstmt.setString(3, receivemail);
	      int rs4 = pstmt.executeUpdate();
	      
	      System.out.println("친구신청 완료");
	      
	      

	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    
	    disconnect();
	    
	  }
 
 // =================================== 끝 ============================================ //
  
  public String findUser(ArrayList<JTextField> userInfos) {

    connect();
    String sql = "select uname from member_table where uemail=?";
    String sql2 = "select uname from member_table where password=?";
    String uemail = userInfos.get(0).getText();
    String password = userInfos.get(1).getText();
    
    String temp = null;
    String uname = null;
    String uemailName=null;
    String passwordName=null;
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, uemail);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        uemailName = rs.getString("uname");
      }
      
      pstmt = conn.prepareStatement(sql2);
      pstmt.setString(1, password);
      ResultSet rs2 = pstmt.executeQuery();
      while (rs2.next()) {
        passwordName = rs2.getString("uname");
      }
      
      // id없음
      if(uemailName == null) {
    	  username=null;
    	  temp = "l,l";
      }
      // 로그인 정보 일치 (성공)
      else if(uemailName.equals(passwordName)) {
    	  username=uemailName;
    	  temp = uemailName+",s";
      }
      // 비밀번호 불일치
      else  {
    	  username=null;
    	  temp = uemailName+",p";
      }
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    disconnect();
    
    return temp; 
  }

  public ArrayList<String> friendList() {

    String uemail = findUserEmail();
    connect();
    ArrayList<String> friends = new ArrayList<String>();
    
    String  sql =
            "select m.uname from member_table m, friendList f where m.uemail = f.friendEmail and f.userEmail = ?";
    		try {
    		      pstmt = conn.prepareStatement(sql);
    		      pstmt.setString(1, uemail);
    		      ResultSet rs = pstmt.executeQuery();
    		      while (rs.next()) {
    		        friends.add(rs.getString("uname"));
    		      }
    		    } catch (SQLException e) {
    		    }
    		
    disconnect();
    return friends;
  }
  
  public ArrayList<String> todayList() {

	    String uemail = findUserEmail();
	    connect();
	    ArrayList<String> todays = new ArrayList<String>();
	    String sql =
	        "select m.today from member_table m, friendList f where m.uemail = f.friendEmail and f.userEmail = ?";
	    try {
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, uemail);
	      ResultSet rs = pstmt.executeQuery();
	      while (rs.next()) {
	        todays.add(rs.getString("today"));
	      }
	    } catch (SQLException e) {
	    }
	    disconnect();
	    return todays;
	 }
  public ArrayList<String> idList() {

	    String uemail = findUserEmail();
	    connect();
	    ArrayList<String> ids = new ArrayList<String>();
	    String sql =
	        "select m.uemail from member_table m, friendList f where m.uemail = f.friendEmail and f.userEmail = ?";
	    try {
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, uemail);
	      ResultSet rs = pstmt.executeQuery();
	      while (rs.next()) {
	        ids.add(rs.getString("uemail"));
	      }
	    } catch (SQLException e) {
	    }
	    disconnect();
	    return ids;
	 }
  public ArrayList<String> nicknameList() {

	    String uemail = findUserEmail();
	    connect();
	    ArrayList<String> nicknames = new ArrayList<String>();
	    String sql =
	        "select m.unickname from member_table m, friendList f where m.uemail = f.friendEmail and f.userEmail = ?";
	    try {
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, uemail);
	      ResultSet rs = pstmt.executeQuery();
	      while (rs.next()) {
	        nicknames.add(rs.getString("unickname"));
	      }
	    } catch (SQLException e) {
	    }
	    disconnect();
	    return nicknames;
	 }
  public ArrayList<String> emailList() {

	    String uemail = findUserEmail();
	    connect();
	    ArrayList<String> emails = new ArrayList<String>();
	    String sql =
	        "select m.gmail from member_table m, friendList f where m.uemail = f.friendEmail and f.userEmail = ?";
	    try {
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, uemail);
	      ResultSet rs = pstmt.executeQuery();
	      while (rs.next()) {
	        emails.add(rs.getString("gmail"));
	      }
	    } catch (SQLException e) {
	    }
	    disconnect();
	    return emails;
	 }
  public ArrayList<String> birthList() {

	    String uemail = findUserEmail();
	    connect();
	    ArrayList<String> births = new ArrayList<String>();
	    String sql =
	        "select m.birth from member_table m, friendList f where m.uemail = f.friendEmail and f.userEmail = ?";
	    try {
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, uemail);
	      ResultSet rs = pstmt.executeQuery();
	      while (rs.next()) {
	        births.add(rs.getString("birth"));
	      }
	    } catch (SQLException e) {
	    }
	    disconnect();
	    return births;
	 }
 
  

  private String findUserEmail() {

    connect();
    String sql = "select uemail from member_table where uname=?";
    String uemail = null;
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, username);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        uemail = rs.getString("uemail");
      }
    } catch (SQLException e) {
    }
    disconnect();
    return uemail;
  }
  
}