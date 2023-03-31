package rd.dru.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;

public class MaterialGroup {
	private Set<String> group = new HashSet<>();
	
	public MaterialGroup(Material material) {
		group.add(material.name());
	}
	
	public MaterialGroup(Collection<Material> materials) {
		group.addAll(materials.stream().map(m->m.name()).collect(Collectors.toSet()));
	}
	
	public MaterialGroup(Material... material) {
		this(Arrays.asList(material));
	}
	
	public MaterialGroup(String... material) {
		group.addAll(Arrays.asList(material));
	}
	
	public void add(Material material) {
		group.add(material.name());
	}
	
	public void remove(Material material) {
		group.remove(material.name());
	}
	
	public Set<Material> getMaterials() {
		return group.stream().map(s->Material.matchMaterial(s)).collect(Collectors.toSet());
	}
	
	public boolean contains(Material material) {
		return group.contains(material.name());
	}
	
	public boolean containsAll(Collection<Material> material) {
		return group.containsAll(material.stream().map(s->s.name()).collect(Collectors.toSet()));
	}
	
	
	
	
	
}
