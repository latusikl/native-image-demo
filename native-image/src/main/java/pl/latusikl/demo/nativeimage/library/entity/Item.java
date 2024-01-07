package pl.latusikl.demo.nativeimage.library.entity;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
public class Item implements BaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String authorName;

	@Column(nullable = false)
	private String authorSurname;

	@Column(nullable = false)
	private Integer publicationYear;

	@OneToMany(mappedBy = "item")
	Set<ItemBorrow> itemBorrows;

	@OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private ItemStock itemStock;
}
