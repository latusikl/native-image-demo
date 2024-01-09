package pl.latusikl.demo.app.library.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_borrow")
@Data
@NoArgsConstructor
public class ItemBorrow implements BaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "member_id",updatable = false, insertable = false)
	private Member member;

	@ManyToOne
	@JoinColumn(name = "item_id", updatable = false, insertable = false)
	private Item item;

	@Column(name = "item_id")
	private UUID itemId;

	@Column(name = "member_id")
	private UUID memberId;

	@Column
	private LocalDate startDate;

	@Column
	private LocalDate endDate;
}
