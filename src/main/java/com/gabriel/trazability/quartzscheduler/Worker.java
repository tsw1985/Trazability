package com.gabriel.trazability.quartzscheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverManager;
//import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Worker {

	private Connection conn ;
	private Statement stmt;
	private ResultSet rs;
	private Double secondCounter = (double) 0;
	private Double finishCapacity = (double)0;

	Double totalLiters = (double)0;
	String actualFactoryTank = "";
	Long actualLastLote = 0L;
	Long actualPasteurizator = 0L;
 
	Long loteActiveInPasteurizator = 0L;
	Long actualLitersConsumedInPasteurizator = 0L;
	
//	Long actualIdLoteTable = 0L;
	
	public void work(){

			 try 
		     {
				 	Map<String,String>mapeTemperature = new HashMap<String,String>();
				 	Map<String,String>mapeIsPasteurizing = new HashMap<String,String>();
				 	Map<String,String>mapeIsRunning = new HashMap<String,String>();
				 	
		            Class.forName("com.mysql.jdbc.Driver");
		            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/trazabilidad?zeroDateTimeBehavior=convertToNull", "root", "shadow");
		            //conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/trazabilidad", "queseria", "123456");
		            stmt = (Statement) conn.createStatement();
		            
		            if (conn == null){
		            	System.out.println("NO HAY CONEXIÓN EN BASE DE DATOS");
		            }
		            
		            if(stmt == null){
		            	System.out.println("NO HAY STATEMENT POSIBLE ");
		            }
		            
		            String loteActiveInPasteurizatorSentence = "select actualIdLoteCounterForWork from Pasteurizator where id='1'";
		            rs = stmt.executeQuery(loteActiveInPasteurizatorSentence);
		            while(rs.next()){
		            	loteActiveInPasteurizator = rs.getLong("actualIdLoteCounterForWork");
		            }
		            
		            String actualLitersConsumedInLoteCounter = "select LoteCounter.litersConsumed from LoteCounter where LoteCounter.id = (select actualIdLoteCounterForWork from Pasteurizator where id='1')";   
		            rs = stmt.executeQuery(actualLitersConsumedInLoteCounter);
		            while(rs.next()){
		            	actualLitersConsumedInPasteurizator = rs.getLong("litersConsumed");
		            }
		            
		            
		            String factoryTankSelected = "select FactoryTank.actualCapacity from FactoryTank where FactoryTank.id = (select Pasteurizator.actualIdFactoryTankForWork from Pasteurizator where Pasteurizator.id ='1')";
	                rs = stmt.executeQuery(factoryTankSelected);

	                String factoryTankActiveIs = "";
	                Double actualCapacity = (double)0;
	                

	                
		            while(rs.next()){
		              actualCapacity = rs.getDouble("actualCapacity");
		            }
		            
		            
	                String selectIp = "select ip from Pasteurizator where id = '1'";
	                String ip = "";
	                rs = stmt.executeQuery(selectIp);
	                while(rs.next()){
	                	ip = rs.getString("ip");
	                }
		            
		            
		            mapeTemperature   = getResponsePasteurizator(ip,"T=1");
		            mapeIsPasteurizing   = getResponsePasteurizator(ip,"P=1");
		            mapeIsRunning = getResponsePasteurizator(ip,"MSTATE=1");
		            
		            if(mapeIsRunning.get("result").trim().equals("NORUNNING"))
		            {
		            	finishCapacity = (double)0;
		            	secondCounter = (double)0;
		            	totalLiters = (double)0;
		            }
		            	
		            String sentenciaPasteurizando = "update Pasteurizator set pasteurizing='" + mapeIsPasteurizing.get("result") + "' where id=1;";
	                stmt.execute(sentenciaPasteurizando);

		            if( mapeIsPasteurizing.get("result").trim().equals("PASTEURIZANDO") && 
		            	mapeIsRunning.get("result").trim().equals("RUNNING") 
		              )
		            {
		            
		            	secondCounter = secondCounter + 3;
		            	
		            	totalLiters = (secondCounter * 1.11);
		            	finishCapacity = actualCapacity - totalLiters;
		            	
		            	//if(finishCapacity > 0){
		            		actualCapacity = finishCapacity;
		            		//String actualizationCapacitySentence = "update FactoryTank set actualCapacity='" + finishCapacity + "' where id='" + factoryTankActiveIs + "';";
		            		String actualizationCapacitySentence = "update FactoryTank set FactoryTank.actualCapacity='" + finishCapacity + "' where FactoryTank.id=(select Pasteurizator.actualIdFactoryTankForWork from Pasteurizator where Pasteurizator.id='1');";
		            		stmt.execute(actualizationCapacitySentence);
		            	//}
		                
		               // Save liters consumed
		               
		               Long finishCapacityLitersConsumedInPasteurizator = (long) (actualLitersConsumedInPasteurizator + totalLiters);
		               actualLitersConsumedInPasteurizator = finishCapacityLitersConsumedInPasteurizator;

		               //String consumedLitersInLoteSentence = "update LoteCounter set litersConsumed='"+finishCapacityLitersConsumedInPasteurizator+"' where id='" + loteActiveInPasteurizator + "'";
		               String consumedLitersInLoteSentence = "update LoteCounter set LoteCounter.litersConsumed='"+finishCapacityLitersConsumedInPasteurizator+"' where LoteCounter.id=(select Pasteurizator.actualIdLoteCounterForWork from Pasteurizator where Pasteurizator.id =1)";
		               //(select Pasteurizator.actualIdFactoryTankForWork from Pasteurizator where Pasteurizator.id =1)
		               stmt.execute(consumedLitersInLoteSentence);
		               
		               
		               stmt.execute("Insert into Pasteurization values(0,now(),'"+mapeTemperature.get("result")+"','"+loteActiveInPasteurizator+"')");
		               
		               
		               secondCounter = (double)0;
		               
		            }
		            	
		            
		            
		            
		            
	            	String sentenciaTemperatura = "update Pasteurizator set actualTemperature='" + mapeTemperature.get("result") + "' where id='1';";
		            stmt.execute(sentenciaTemperatura);
		            
	                stmt.close();
	                conn.close();
		        }
		        catch(SQLException ex){
		        	System.out.println("SQL EX " + ex.toString());
		        	//conn.close();
		        }
		        catch(ClassNotFoundException ex){
		        	System.out.println("CLASS NOT " + ex.toString());
		        	//conn.close();
		        }
			    catch(Exception e){
			    	System.out.println("EXCEPCION GENERAL " + e.toString());
			    	//conn.close();
			    }
			    
		        
	 }

	private Map<String,String> getResponsePasteurizator(String ipValue , String parameter)
	{
		
		Map<String,String>mape = new HashMap<String,String>();
	      
		try{
			
	    	 URL url;
		     HttpURLConnection conn;
		     BufferedReader rd;
		     String line;
		     String result = "";

	    	 //url = new URL("http://192.168.1.45:80?"+parameter);
		     url = new URL("http://" + ipValue +"?" +parameter);
	         conn = (HttpURLConnection) url.openConnection();
	         
	         
	         conn.setRequestMethod("GET");
	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();
	         
	         mape.put("result", result);
	         mape.put("timeResponse", String.valueOf(conn.getConnectTimeout()));
	         
	         return mape;
	      } 
	      catch(Exception e)
	      {
	    	  mape.put("exception",e.toString());
	    	  return mape;
	      }
	}
}