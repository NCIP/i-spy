package gov.columbia.c2b2.ispy.fileLoad;

import com.jcraft.jsch.UserInfo;
public class MyUserInfo implements UserInfo{
    public String getPassword() {
        return "oracle9i";
      }

      public String getPassphrase() {
        return "";
      }

      public boolean promptPassword(String arg0) {
        return true;
      }

      public boolean promptPassphrase(String arg0) {
        return true;
      }

      public boolean promptYesNo(String arg0) {
        return true;
      }

      public void showMessage(String arg0) {
      }

}
