package com.gabriel.trazability.command;

//Lote es el LOTE de produccion , no el Lote del ingrediente
public class IngredientUsedInLoteCommand {
	
	private Long lote;
	private Long idIngredientDetail;
	
	public Long getLote() {
		return lote;
	}
	public void setLote(Long lote) {
		this.lote = lote;
	}
	public Long getIdIngredientDetail() {
		return idIngredientDetail;
	}
	public void setIdIngredientDetail(Long idIngredientDetail) {
		this.idIngredientDetail = idIngredientDetail;
	}
	

}
