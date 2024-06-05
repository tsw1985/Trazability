package com.gabriel.trazability.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;

@Entity
public class IngredientDetailUsedInLote {
	
	private Long id;
	private IngredientDetail ingredientDetail;
	private LoteCounter loteCounter;
	
	@Id
	@GeneratedValue	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Index(name = "ingredientDetailUsedIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idIngredientDetail", nullable = false, unique = false)
	public IngredientDetail getIngredientDetail() {
		return ingredientDetail;
	}
	public void setIngredientDetail(IngredientDetail ingredientDetail) {
		this.ingredientDetail = ingredientDetail;
	}

	
	/*public LoteCounter getLote() {
		return lote;
	}
	public void setLote(LoteCounter lote) {
		this.lote = lote;
	}*/
	
	@Index(name = "loteCounterIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idLoteCounter", nullable = false, unique = false)
	public LoteCounter getLoteCounter() {
		return loteCounter;
	}
	public void setLoteCounter(LoteCounter loteCounter) {
		this.loteCounter = loteCounter;
	}

	
	
	
}
