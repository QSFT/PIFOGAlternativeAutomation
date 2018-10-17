/**
 *
 */
package test;
import java.util.List;
import java.util.Map;

import com.ibm.staf.STAFException;
import com.ibm.staf.STAFHandle;
import com.ibm.staf.STAFMarshallingContext;
import com.ibm.staf.STAFUtil;
/**
 * @author schen3
 *
 */
public class Test {

	public void sikuliTest() {

	}

	public static String Test() {
		return "test";
	}

	public static void zmain(String [] argv)
    {
        // Register with STAF

        try
        {
            handle = new STAFHandle("MyApp/Test");
        }
        catch (STAFException e)
        {
            System.out.println(
                "Error registering with STAF, RC: " + e.rc);
            System.exit(1);
        }

        // Submit a request to the PROCESS service to run a command that
        // lists the contents of the STAF root directory and waits for
        // the request to complete

        String machine = "local";
        String service = "PROCESS";
        String command = "dir {c:/}";
        String request = "START SHELL COMMAND " + STAFUtil.wrapData(command) +
                         " RETURNSTDOUT STDERRTOSTDOUT WAIT";

        System.out.println(
            "STAF " + machine + " " + service + " " + request);

        try
        {
            String result = handle.submit(machine, service, request);

            // Unmarshall the result buffer whose root object is a map
            // containing process completion information.  The keys for this
            // map include 'rc' and 'fileList'.  The value for 'fileList'
            // is a list of the returned files.  Each entry in the list
            // consists of a map that contains keys 'rc' and 'data'.
            // In our PROCESS START request, we returned one file, STDOUT,
            // (and returned STDERR to the STDOUT file).

            STAFMarshallingContext mc =
                STAFMarshallingContext.unmarshall(result);

            Map processCompletionMap = (Map)mc.getRootObject();

            // Verify that the process rc is 0

            String processRC = (String)processCompletionMap.get("rc");

            if (!processRC.equals("0"))
            {
                System.out.println(
                    "ERROR:  Process RC is " + processRC + " instead of 0.");
                System.exit(1);
            }

            // Verify that the rc is 0 for returning data to the STDOUT file

            List returnedFileList = (List)processCompletionMap.get(
                "fileList");
            Map stdoutMap = (Map)returnedFileList.get(0);
            String stdoutRC = (String)stdoutMap.get("rc");

            if (!stdoutRC.equals("0"))
            {
                System.out.println(
                    "ERROR retrieving process Stdout data. RC=" + stdoutRC);
                System.exit(1);
            }

            // Get the data from the STDOUT file created by the process
            // and print it

            String stdoutData = (String)stdoutMap.get("data");

            System.out.println("\nProcess Stdout File Contains:\n");
            System.out.println(stdoutData);
        }
        catch (STAFException e)
        {
            System.out.println(
                "Error submitting request STAF " + machine + " " +
                service + " " + request +
                "\nRC: " + e.rc + "  Result: " + e.getMessage());
        }
    }

    private static STAFHandle handle;

}
