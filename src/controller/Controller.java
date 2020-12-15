package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.simple.parser.ParseException;

import client.datacommunication.ClientSocket;
import client.frame.ErrorMessagePanel;
import client.frame.IndexPanel;
import client.frame.MainPanel;
import server.userdb.User;
import server.userdb.UserDAO;

public class Controller {

  private static Controller singleton = new Controller();

  public String username = null;

  public ClientSocket clientSocket;

  UserDAO userDAO;


  private Controller() {

    clientSocket = new ClientSocket();

    userDAO = new UserDAO();

  }

  public static Controller getInstance() {

    return singleton;
  }

  public void insertDB(User user) {

    boolean isInsert = userDAO.insertDB(user);

    if (isInsert) {
      MainPanel mainPanel = new MainPanel(MainPanel.frame);
      MainPanel.frame.change(mainPanel);
      JOptionPane.showMessageDialog(mainPanel, "회원가입 성공!!!", "회원가입", JOptionPane.WARNING_MESSAGE);
    } else {
      ErrorMessagePanel errorPanel = new ErrorMessagePanel("회원가입");
      MainPanel.frame.change(errorPanel);
    }

  }

  public void findUser(ArrayList<JTextField> userInfos) throws IOException, ParseException {

   username = userDAO.findUser(userInfos);
   
   System.out.println(username);
   
   String[] check = username.split(",");
   
   username=check[0];
   System.out.println("check[0] : <"+check[0] + "> check[1] : <"+check[1]+">");
   
    if (check[1].equals("s")) {
      IndexPanel indexPanel = new IndexPanel();
      MainPanel.frame.change(indexPanel);
      clientSocket.startClient();
      JOptionPane.showMessageDialog(indexPanel, "로그인 성공!!!", "로그인", JOptionPane.WARNING_MESSAGE);
    } else if (check[1].equals("p")) {
      ErrorMessagePanel err = new ErrorMessagePanel("비밀번호 입력");
      MainPanel.frame.change(err);
    } else {
    	ErrorMessagePanel err = new ErrorMessagePanel("아이디 입력");
        MainPanel.frame.change(err);
    }
  }

  public ArrayList<String> friendList() {

    ArrayList<String> friends = new ArrayList<String>();
    friends = userDAO.friendList();

    return friends;
  }
  
  public ArrayList<String> todayList() {

	    ArrayList<String> todays = new ArrayList<String>();
	    todays = userDAO.todayList();

	    return todays;
	  }
  
  public ArrayList<String> idList() {

	    ArrayList<String> ids = new ArrayList<String>();
	    ids = userDAO.idList();

	    return ids;
	  }
  public ArrayList<String> nicknameList() {

	    ArrayList<String> nicknames = new ArrayList<String>();
	    nicknames = userDAO.nicknameList();

	    return nicknames;
	  }
  public ArrayList<String> emailList() {

	    ArrayList<String> emails = new ArrayList<String>();
	    emails = userDAO.emailList();

	    return emails;
	  }
  public ArrayList<String> birthList() {

	    ArrayList<String> births = new ArrayList<String>();
	    births = userDAO.birthList();

	    return births;
	  }
  

}