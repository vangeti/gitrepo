package cgt.dop.alm;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dashBoardFactory")
public class DashBoardFactory{

	private static final String CIO_DB_DATA = "kpi-data";

	private ResourceBundle resourceBundle = ResourceBundle.getBundle(CIO_DB_DATA, Locale.getDefault());

	@Autowired
	private DashBoardKPI dashBoardKPI;

	/*
	 * @Autowired private ExcelDashBoardKPI dashBoardKPI;
	 */

	
	public DashBoardKPI getObject() {
		
		String dataFrom = (String) resourceBundle.getObject("dataFrom");
		switch (dataFrom.toUpperCase()) {
		case "QC":			
			return dashBoardKPI;
		/*case "EXCEL":			
			return dashBoardKPI;*/
		default:
			return dashBoardKPI;
		}
	}


}
