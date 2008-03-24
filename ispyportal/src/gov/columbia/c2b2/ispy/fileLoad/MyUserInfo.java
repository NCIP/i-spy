package gov.columbia.c2b2.ispy.fileLoad;

import com.jcraft.jsch.UserInfo;
public class MyUserInfo implements UserInfo{
private	String sysPas = System.getProperty("gov.c2b2.columbia.ispyportal.syspass");
    public String getPassword() {
        return (this.sysPas);
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
