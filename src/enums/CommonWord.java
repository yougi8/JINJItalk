package enums;


public enum CommonWord {

  SIGN_UP_MEMBERSHIP("회원가입", 0),
  LOGIN("로그인", 1),
  NAME("이름", 2),
  ID("이메일",3),
  NICKNAME("별칭",4),
  EMAIL("아이디", 5),
  BIRTH("생년원일",6),
  TODAY("오늘의 한마디 ( 변경 가능 )",7),
  PASSWORD("비밀번호", 8),
  MYPROFILE("내 프로필", 9),
  FRIEND("친구", 10),
  GOBACK("뒤로가기",11);


  private final String text;

  private final int num;


  CommonWord(String text, int num) {

    this.text = text;
    this.num = num;
  }

  public String getText() {

    return text;
  }

  public int getNum() {

    return num;
  }


}