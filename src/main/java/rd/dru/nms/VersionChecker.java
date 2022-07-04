package rd.dru.nms;

import java.util.Arrays;
import java.util.Comparator;

import org.bukkit.Bukkit;

public class VersionChecker {
	public enum Version {
		v1_8(8, new V1_8Handler()),
		v1_9(9, new V1_9Handler()),
		v1_13(13, new V1_13Handler()),
		v1_16(16, new V1_16Handler()),
		v1_17(17, new V1_17Handler());
		
		private final int id;
		private final NMSHandler nms;
		Version(final int id, NMSHandler nms){
			this.nms = nms;
			this.id = id;
		}

		public int getId() {
			return this.id;
		}

		public static Version getCurrentVersionByID(int id) {
			return Arrays.stream(values()).filter(i->id==i.id).findFirst().orElse(v1_17);
		}
		
		public static Version getSupportVerison(int id) {
			return Arrays.stream(values()).filter(i->id>=i.getId())
					.min(Comparator.comparing(i->id-i.getId())).orElse(v1_8);
		}
		
		
		public boolean isOlderThan(Version other) {
			return getId()>other.getId();
		}

		public NMSHandler getNMS() {
			// TODO Auto-generated method stub
			return nms;
		}
		
	}
	
	public static Version getCurrentVersion() {
		String a = Bukkit.getServer().getClass().getPackage().getName();
		String version = a.substring(a.lastIndexOf('.') + 1,a.lastIndexOf("_"));
		return Version.getSupportVerison(Integer.parseInt(version.substring(version.indexOf("_")+1)));
	}
}
