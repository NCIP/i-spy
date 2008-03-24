package gov.columbia.c2b2.ispy.fileLoad;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class RemoteHelper {
    static Channel channel;
    static InputStream stdIn;
    String cmdoutput = "";
    static PrintStream stdOut ;
    static com.jcraft.jsch.Session sessionS;

    public static String readUntil() {

        byte[] tmp = new byte[1024];

    String output = "";


        try {

                System.out.println("readUntil() called.");

                while(true) {

                 while(stdIn.available()>0) {

                        int i = stdIn.read(tmp, 0, 1024);

                        System.out.print(">"+new String(tmp, 0, i));

                        output += new String(tmp,0,i).toString();

                 //       break;
                 }
                 if(channel.isClosed()) {

                         System.out.println("exit-status: " + channel.getExitStatus());

                         break;

                 }
            }
        try{Thread.sleep(1000);}catch(Exception ee){ee.printStackTrace();}
        }
        catch(IOException ie) {
                System.out.println("IOEXCEPTION:"+ie.toString());
        }
        catch(NullPointerException ne) {
                System.out.println("NULL Exception:"+ne.toString());
        }
        catch(Exception e) {
                System.out.println("EXCEPTION:"+e.toString());
        }
 //       System.out.println("Output:" + output);
        channel.disconnect();
    return output;
    }
    public  static void connectMgc(){
    	String sysUser= System.getProperty("gov.c2b2.columbia.ispyportal.sysuser");
    	String sysIP= System.getProperty("gov.c2b2.columbia.ispyportal.sysip");
        try {

                UserInfo ui = new MyUserInfo();
                JSch jsch = new JSch();

                sessionS = jsch.getSession(sysUser, sysIP, 22);
                sessionS.setUserInfo(ui);
                sessionS.connect();
                System.out.println("Session Connected.");

                channel = sessionS.openChannel("exec");


        }
        catch(Exception e) {
                System.out.println("Exception in connectMgc:"+e.toString());
        }

    }
    public static String sendCommand(String cmd) {

        String cmdoutput = "";

        try {

                System.out.println("sendCommand() called.");

                ((ChannelExec)channel).setCommand(cmd);

                channel.setInputStream(System.in);

                channel.setOutputStream(System.out);
                ((ChannelExec)channel).setErrStream(System.err);

                System.out.println("Channel Connected.");

                stdIn = channel.getInputStream();

                stdOut = new PrintStream (channel.getOutputStream());

                channel.connect();

                cmdoutput=readUntil();

                channel.disconnect();
                sessionS.disconnect();


        } catch(Exception e) {

                System.out.println("Exception in sendCommand()"+e.toString());

        }

        return cmdoutput;

    }

    public static void disconnect() {
        channel.disconnect();
        sessionS.disconnect();
        System.out.println("Disconnected");
    }
 
    

}
