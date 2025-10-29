package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;
public class DataProviders {


@DataProvider (name="LoginData")
public String [][] getData() throws IOException
{
//String path="/OpencartV121/testData/Opencart_LoginData.xlsx";//taking xl file from
	//String path = System.getProperty("user.dir") + "\\testData\\Opencart_LoginData.xlsx";
	String path="C:\\Users\\Nitheesh Kumar\\eclipse-workspace\\OpencartV121\\testData\\Opencart_LoginData.xlsx";



ExcelUtility xlutil=new ExcelUtility(path);//creating an object for XLUti

int totalrows=xlutil.getRowCount("Sheet1");
int totalcols=xlutil.getCellCount("Sheet1",1);

String logindata[][]=new String[totalrows][totalcols];//created for two d:

for (int i=1;i<=totalrows;i++) 
{

for (int j=0;j<totalcols;j++) 
{

logindata[i-1][j]= xlutil.getCellData ("Sheet1",i,j); //1,0
}
}

return logindata;//returning
}
}
