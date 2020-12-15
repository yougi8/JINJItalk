package client.frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.simple.parser.ParseException;

import controller.Controller;
import enums.CommonWord;
import util.UserInfoPanel;

@SuppressWarnings("serial")
public class LoginPanel extends UserInfoPanel {

  private final String LOGIN = "로그인";

  private ArrayList<JTextField> userInfos = new ArrayList<JTextField>(); // 이메일과 비밀번호
   
  public LoginPanel() {

    showFormTitle(CommonWord.LOGIN.getText());
    writeUserInfo();
    showLoginButton();
  }

  public void writeUserInfo() {

    int y_value = 155;
    for (CommonWord commonWord : CommonWord.values()) {
      if (commonWord.getNum() == CommonWord.EMAIL.getNum()
          || commonWord.getNum() == CommonWord.PASSWORD.getNum()) {
        formTitleLabel = new JLabel(commonWord.getText());
        formTitleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        formTitleLabel.setBounds(30, y_value, 200, 50);
        add(formTitleLabel);
        userInfoTextField = new JTextField(10);
        userInfoTextField.setBounds(30, y_value + 45, 325, 30);
        add(userInfoTextField);
        y_value += 100;
        if (commonWord.getNum() == CommonWord.PASSWORD.getNum()) {
          userInfoTextField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

              try {
				try {
					loginUser();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            }
          });
        }
        
       
        userInfos.add(userInfoTextField);
      } else {
        continue;
      }
    }
  }

  private void showLoginButton() {

    JButton loginButton = showFormButton(LOGIN);
    loginButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        try {
			try {
				loginUser();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      }
    });
  }
  
  private void loginUser() throws IOException, ParseException {
    Controller controller = Controller.getInstance();
    controller.findUser(userInfos);
  }
}