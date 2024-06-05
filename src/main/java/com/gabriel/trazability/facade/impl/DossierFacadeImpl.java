package com.gabriel.trazability.facade.impl;

import java.io.FileOutputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.DossierDAO;
import com.gabriel.trazability.facade.DossierFacade;
import com.gabriel.trazability.model.ChlorinePhSalmuera;
import com.gabriel.trazability.model.FactoryTank;
import com.gabriel.trazability.model.IngredientDetail;
import com.gabriel.trazability.model.Operator;
import com.gabriel.trazability.model.Pasteurization;
import com.gabriel.trazability.model.sql.query.CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription;
import com.gabriel.trazability.model.sql.query.PasteurizatorData;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;

@Service
@Transactional
public class DossierFacadeImpl implements DossierFacade {
	
	@Autowired
	private DossierDAO dossierDAO;

	@Override
	public List<CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription> createDossierFromLote(Long idLote) {
		
		  try {
			  
			  List<CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription> list = dossierDAO.setMilkProviderfromIdLoteCounter(idLote);
			  
		      Document document = new Document(PageSize.LETTER);
		      PdfWriter.getInstance(document, new FileOutputStream("/home/tsw/dossier" + idLote + ".pdf"));
		      document.open();
		      

		      HTMLWorker htmlWorker = new HTMLWorker(document);
		      
		      String head = "<html><head></head><body><a href='http://www.google.es'>google</a><table border=1>" ;
		       
		      String row = "";
		      
		      for(int i = 0 ; i < list.size() ; i++){
		    	  
		    	  row = row + "<tr><td>" + list.get(i).getCorrection()    + "</td>" + 
		    			      "<td>" + list.get(i).getDescription()   + "</td>" +
			    			  "<td>" + list.get(i).getDisplay()       + "</td>" +
			    			  "<td>" + list.get(i).getFilledDate()    + "</td>" +
			    			  "<td>" + list.get(i).getIncidence()     + "</td>" +
			    			  "<td>" + list.get(i).getOperatorName()  + "</td>" +
			    			  "<td>" + list.get(i).getProviderName()  + "</td>" +
			    			  "<td>" + list.get(i).getProviderTank()  + "</td>" +
			    			  "<td>" + list.get(i).getTotalFilled()   + "</td>" +
			    			  "<td>" + list.get(i).getTruckTankName() + "</td></tr>" ;
		      }
		      
		      String endTable = "</table>";
		      String endPage = "</body></html>";
		      
		      htmlWorker.parse(new StringReader(head + row + endTable + endPage));
		      
		      document.close();
		      
		      System.out.println("Done");
		      
		      return list;
		      }
		    catch (Exception e) {
		      e.printStackTrace();
		      return null;
		    }
	}

	@Override
	public ChlorinePhSalmuera getChlorinePhSalmueraFromIdLoteCounter(Long idLoteCounter) {
		return dossierDAO.getChlorinePhSalmueraFromIdLoteCounter(idLoteCounter);
	}

	
	@Override
	public List<PasteurizatorData> getPasteurizatorUsedInThisLote(Long idLoteCounter) {
		return dossierDAO.getPasteurizatorUsedInThisLote(idLoteCounter);
	}
	
	@Override
	public List<Operator> getOperatorInThisLote(Long idOperator) {
		return dossierDAO.getOperatorInThisLote(idOperator);
	}
	
	@Override
	public Long getLitersUsedInThisLote(Long idLoteCounter) {
		return dossierDAO.getLitersUsedInThisLote(idLoteCounter);
	}
	
	@Override
	public List<FactoryTank> getFactoryTankUsedInThisLote(Long idLoteCounter) {
		return dossierDAO.getFactoryTankUsedInThisLote(idLoteCounter);
	}
	
	@Override
	public List<Pasteurization> getPasteurizationDataInThisLote(Long idLoteCounter) {
		return dossierDAO.getPasteurizationDataInThisLote(idLoteCounter);
	}
	
	@Override
	public Time getTimeUsedForDoThisLote(Long idLoteCounter) {
		return dossierDAO.getTimeUsedForDoThisLote(idLoteCounter);
	}
	
	@Override
	public BigInteger getTotalKgInThisLote(Long idLoteCounter) {
		return dossierDAO.getTotalKgInThisLote(idLoteCounter);
	}
	
	@Override
	public List<IngredientDetail> getIngredientsUsedInThisLote(Long idLoteCounter) {
		return dossierDAO.getIngredientsUsedInThisLote(idLoteCounter);
	}

	@Override
	public List<CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription> setMilkProviderfromIdLoteCounter(Long idLoteCounter) {
		return null;
	}

	@Override
	public List<ChlorinePhSalmuera> getChlorinePhSalmueraInThisLote(Long idLoteCounter) {
		return null;
	}


}