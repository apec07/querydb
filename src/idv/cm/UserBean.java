package idv.cm;

import java.util.ArrayList;
import java.util.List;

public class UserBean {
	
	private String userName;
	private String userPhone;
	private String userNote;
	
	private UserBean(UserBean.Builder build) {
		this.userName = build.userName;
		this.userPhone = build.userPhone;
		this.userNote = build.userNote;
	}
	
	public static class Builder{
		private String userName;
		private String userPhone;
		private String userNote;
		
		public UserBean.Builder userName(String val){
			this.userName = val;
			return this;
		}
		public UserBean.Builder userPhone(String val){
			this.userPhone = val;
			return this;
		}
		public UserBean.Builder userNote(String val){
			this.userNote = val;
			return this;
		}
		public UserBean build() {
			return new UserBean(this);
		}
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserNote() {
		return userNote;
	}

	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}
	
	
	
	@Override
	public String toString() {
		return "User [userName=" + userName + ", userPhone=" + userPhone + ", userNote=" + userNote + "]";
	}

	public static List<UserBean> createUserList() {
		List<UserBean> list = new ArrayList<>();
		list.add(new UserBean.Builder()
				.userName("Amy")
				.userPhone("123456")
				.userNote("Note1")
				.build()
				);
		list.add(new UserBean.Builder()
				.userName("Jackie")
				.userPhone("234566")
				.userNote("note2")
				.build()
				);
		list.add(new UserBean.Builder()
				.userName("Lisa")
				.userPhone("344567")
				.userNote("note3")
				.build()
				);
		return list;
	}
	
		
}
