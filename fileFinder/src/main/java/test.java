import java.util.ArrayList;
import java.util.List;

public class test {
	public static void main(String[] args) {
		String a = "Korea has learned through the years gone by that the machine that benevolently governs her is of the order of the Hun. It makes laws, it fixes and regulates everything under the sun, almost to a man’s breath, verboten this, and verboten that; it keeps tab on your every motion; it has spies, and police, and gendarmes, and soldiers at its beck and call.";

		List<String> list = new ArrayList<String>();
		String[] arr = a.split("\\.");
		String aa = arr[0];
		System.out.println(aa);
		//list.add(a.split("."));
	}
}
