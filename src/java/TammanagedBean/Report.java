/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TammanagedBean;

import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean(name = "Report")
@SessionScoped
    public class Report {

    private Object response;    
    JasperPrint jasperprint;
    Connection conn;
    
    public void init2() throws JRException, ClassNotFoundException, SQLException{
    Class.forName("com.mysql.jdbc.Driver");    
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TamaraDB","root","123");
    jasperprint= JasperFillManager.fillReport("C:\\users\\fam_expenses_smb214\\web\\report\\report1.jasper", new HashMap(),conn);
    
    }
    
    public void pdf(ActionEvent actionevent) throws JRException, IOException, ClassNotFoundException, SQLException {
    init2();
    
    HttpServletResponse httpServletResponse= (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    httpServletResponse.addHeader("Content-disposition",  "inline; filename=report.pdf");
        try (ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperprint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();
            servletOutputStream.flush();
        }
    }
    }
