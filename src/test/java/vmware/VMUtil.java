/**
 *
 */
package vmware;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import com.vmware.vim25.FileFault;
import com.vmware.vim25.InsufficientResourcesFault;
import com.vmware.vim25.InvalidState;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.TaskInProgress;
import com.vmware.vim25.VirtualMachineSnapshotTree;
import com.vmware.vim25.VmConfigFault;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;


/**
 * @author schen3
 *
 */
public class VMUtil {

	public static VirtualMachine connectVM(VMProfile vm) {
		System.out.println("Connecting to vm "+vm.getName());
		try {
			URL vcURL = new URL(vm.getvCenter().getUrl());
			ServiceInstance service = new ServiceInstance(vcURL, vm.getvCenter().getUsername(), vm.getvCenter().getPassword(), true);
			ManagedEntity rootFolder = service.getRootFolder();
			ManagedEntity vmFolder;
			InventoryNavigator in;
			if(vm.getFolder()==null||vm.getFolder().length()==0) {
				in = new InventoryNavigator(rootFolder);
				vmFolder = in.searchManagedEntity("Folder", vm.getFolder());
			}
			else {
				vmFolder = rootFolder;
			}
			in = new InventoryNavigator(vmFolder);
			VirtualMachine vmachine = (VirtualMachine) in.searchManagedEntity("VirtualMachine", vm.getName());
			System.out.println("Connected to vm "+vm.getName());
			return vmachine;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String getVMIp(VirtualMachine vm, int timeout) throws Exception {
		String ip = "";
		int waitTime = 600;
		if(timeout>0) {
			waitTime = timeout;
		}
		else {
			timeout = waitTime;
		}
		String ipPattern = "^\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b$";
		System.out.println("Getting IP of VM "+vm.getName());
		while(timeout>0) {
			ip = vm.getGuest().getIpAddress();
			if(ip!=null&&ip.matches(ipPattern)) {
				System.out.println("IP of VM "+vm.getName()+" : "+ip);
				return ip;
			}
			Thread.sleep(1000);
			timeout--;
		}
		throw new Exception("Failed to get IP of "+vm.getName()+" in "+waitTime+" seconds");
	}

	public static void removeSnapshot(VirtualMachine vm, String snapshotName) {
		System.out.println("Removing Snapshot "+snapshotName+" of vm "+vm.getName());
		VirtualMachineSnapshot snapshot = getVMSnapshot(vm, snapshotName);
		if(snapshot!=null) {
			try {
				Task snapshotTask = snapshot.removeSnapshot_Task(false);
				if(snapshotTask.waitForTask()=="success") {
					System.out.println("Removed Snapshot "+snapshotName+" of vm "+vm.getName());
				}
				else {
					System.out.println("Failed to remove Snapshot "+snapshotName+" of vm "+vm.getName());
				}
			} catch (VmConfigFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TaskInProgress e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidState e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InsufficientResourcesFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void revertToSnapshot(VirtualMachine vm, String snapshotName) {
		System.out.println("Reverting vm "+vm.getName()+" to snapshot "+snapshotName);
		VirtualMachineSnapshot snapshot = getVMSnapshot(vm, snapshotName);
		if(snapshot!=null) {
			try {
				Task snapshotTask = snapshot.revertToSnapshot_Task(null);
				if(snapshotTask.waitForTask()=="success") {
					System.out.println("Reverted vm "+vm.getName()+" to snapshot "+snapshotName);
				}
				else {
					System.out.println("Failed to vm "+vm.getName()+" to snapshot "+snapshotName);
				}
			} catch (VmConfigFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TaskInProgress e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidState e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InsufficientResourcesFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void poweroffVM(VirtualMachine vm) {
		System.out.println("Shuting down vm "+vm.getName());
		try {
			Task task = vm.powerOffVM_Task();
			if(task.waitForTask()=="success") {
				System.out.println("Shut down vm "+vm.getName()+" complete");
			}

		} catch (TaskInProgress e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidState e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void poweronVM(VirtualMachine vm) {
		System.out.println("Starting vm "+vm.getName());
		try {
			Task task = vm.powerOnVM_Task(null);
			if(task.waitForTask()=="success") {
				System.out.println("Started vm "+vm.getName());
			}

		} catch (TaskInProgress e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidState e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static VirtualMachineSnapshot getVMSnapshot(VirtualMachine vm, String snapshotName) {
		VirtualMachineSnapshotTree[] snapshotTree = vm.getSnapshot().getRootSnapshotList();
		if(snapshotTree!=null&&snapshotTree.length>0) {
			ManagedObjectReference mor = findSnapshotInTree(snapshotTree, snapshotName);
			if(mor!=null) {
				VirtualMachineSnapshot snapshot = new VirtualMachineSnapshot(vm.getServerConnection(), mor);
				return snapshot;
			}
			else {
				System.out.println("Couldn't fin snapshot "+snapshotName+" for VM "+vm.getName());
				return null;
			}
		}
		else {
			System.out.println("Couldn't fin snapshot "+snapshotName+" for VM "+vm.getName());
			return null;
		}

	}

	private static ManagedObjectReference findSnapshotInTree(VirtualMachineSnapshotTree[] snapshotTree, String snapshotName) {
		for(VirtualMachineSnapshotTree node : snapshotTree) {
			if(node.getName().equals(snapshotName)) {
				return node.getSnapshot();
			}
			else {
				VirtualMachineSnapshotTree[] snapshotTree1 = node.getChildSnapshotList();
				if(snapshotTree1!=null&&snapshotTree1.length>0) {
					return findSnapshotInTree(snapshotTree1, snapshotName);
				}
			}
		}
		return null;
	}

}
