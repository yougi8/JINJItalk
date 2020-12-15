package server.userdb;


public class User {

  private String uname;
  private String uid;	// db에서는 email임
  private String unickname;
  private String uemail;	// db에서는 gamil임
  private String birth;
  private String today;
  private String password;

  public User(String uname, String uid, String unickname, String uemail, String birth, String today, String password) {

    this.uname = uname;
    this.uemail = uemail;
    this.password = password;
    this.birth=birth;
    this.today=today;
    this.uid=uid;
    this.unickname=unickname;
    
  }
                

  public String getUname() {

    return uname;
  }

  public void setUname(String uname) {

    this.uname = uname;
  }

  public String getUemail() {

    return uemail;
  }

  public void setUemail(String uemail) {

    this.uemail = uemail;
  }

  public String getPassword() {

    return password;
  }

  public void setPassword(String password) {

    this.password = password;
  }
  
  public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUnickname() {
		return unickname;
	}

	public void setUnickname(String unickname) {
		this.unickname = unickname;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

}