package university;

import com.niit.university.utils.RemoteExecuteCommand;

public class Main {

	public static void main(String[] args) {
		execShellScript();
	}

	public static void execShellCmd() {
		RemoteExecuteCommand rec = RemoteExecuteCommand.getInstance();
		String reString = rec.execute("ls -al /root/");
		System.out.println(reString);
	}
	
	public static void execShellScript() {
		RemoteExecuteCommand rec = RemoteExecuteCommand.getInstance();
		String reString = rec.execute("sh /root/ab_test.sh");
		System.out.println(reString);
	}
}
