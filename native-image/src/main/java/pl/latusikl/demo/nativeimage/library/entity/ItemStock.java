package pl.latusikl.demo.nativeimage.library.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_stock")
@Data
@NoArgsConstructor
public class ItemStock implements BaseEntity
{
	@Id
	@Column(name = "item_id", updatable = false)
	private UUID id;

	@Column
	private Integer itemCount;

	@Column
	private Integer availableItemCount;

	@OneToOne
	@MapsId
	@JoinColumn(name = "item_id", insertable = false, updatable = false)
	private Item item;
}
