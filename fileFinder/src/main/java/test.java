import java.util.Random;
import java.util.StringTokenizer;

public class test {
	public static void main(String[] args) {
		String query = "name=aa&addr=deagu&age=21";
		StringTokenizer st = new StringTokenizer(query, "&");
		
		System.out.println(st);
		System.out.println(st.nextToken());
		System.out.println(st.nextToken());
		System.out.println(st.nextToken());
		System.out.println(st.nextToken());
	}
}
