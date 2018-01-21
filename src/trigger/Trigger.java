/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trigger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class Trigger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.err.println("=== start " + new java.util.Date().toLocaleString() + "=====" + Calendar.getInstance().get(Calendar.MILLISECOND));
//        create_trigger("upc_grossisterie17");
       
        System.err.println("=== fin " + new java.util.Date().toLocaleString() + "=====" + Calendar.getInstance().get(Calendar.MILLISECOND));

    }

    public static void create_trigger(String BD) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + BD + "?&characterEncoding=UTF-8", "root", "");

            Statement st_select = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            

//            st_select .executeUpdate("update " + BD + ".  `entete_mvts` set ttc =0 , ttc_fix=0 , tva =0  WHERE  ttc is NULL") ; 
//            st_select.executeUpdate("DROP TRIGGER IF EXISTS " + BD + ".`update_produit_in_delete_detail`");
//
//            st_select.executeUpdate("CREATE TRIGGER " + BD + ". `update_produit_in_delete_detail` after DELETE ON  " + BD + ".`detail_mvts` "
//                    + "FOR EACH ROW "
//                    + "begin \n"
//                    + "declare existe boolean default 1 ;\n"
//                    + "declare cmp_new double default  0 ; \n"
//                    + "declare cmp_ug_new  double default 0 ; \n"
//                    + " \n"
//                    + "IF old.type_mvt =  'E' OR old.type_mvt =  'S' THEN \n"
//                    + "begin\n"
//                    + "SELECT cmp , cmp_ug into cmp_new , cmp_ug_new \n"
//                    + "FROM " + BD + ".detail_mvts \n"
//                    + "where `Produit_Code`=old.produit_code and lot = old.lot\n"
//                    + "ORDER BY date DESC, num_entree DESC \n"
//                    + "LIMIT 1 ; \n"
//                    + "\n"
//                    + "if  (cmp_new = '0'and cmp_ug_new = '0' ) then \n"
//                    + " SELECT prix ,  prix_ug into cmp_new , cmp_ug_new \n"
//                    + " FROM " + BD + ".inventaire_debut \n"
//                    + " WHERE produit_code = old.produit_code and lot = old.lot ; \n"
//                    + "end if ;\n"
//                    + "\n"
//                    + "UPDATE " + BD + ". produit \n"
//                    + "SET stock = stock + \n"
//                    + "(if(old.type_mvt = 'E' ,-old.quantite, \n"
//                    + "if(old.type_mvt = 'S' ,+old.quantite, 0)) ) \n"
//                    + ", stock_globale = stock_globale+(if(old.type_mvt = 'E' ,-old.quantite, \n"
//                    + "if(old.type_mvt = 'S' ,+old.quantite, 0)) )\n"
//                    + ", cmp= cmp_new  \n"
//                    + " WHERE code = old.produit_code ; \n"
//                    + " \n"
//                    + " update " + BD + ".lots set cmp =cmp_new , cmp_ug = cmp_ug_new ,\n"
//                    + " stock = stock + (if(old.type_mvt = 'E' ,-old.quantite,\n"
//                    + " if(old.type_mvt = 'S' ,+old.quantite, 0)) ) ,\n"
//                    + " stock_globale = stock_globale+(if(old.type_mvt = 'E' ,-old.quantite,\n"
//                    + "if(old.type_mvt = 'S' ,+old.quantite, 0)) )\n"
//                    + "where produit_code= old.produit_code \n"
//                    + "and lot = old.lot ;\n"
//                    + "end  ; \n"
//                    + "end if ; \n"
//                    + "\n"
//                    + " IF  old.type_mvt =  'V' and  old .type_bon ='BCM'  and right( old.ref  ,3)='BRS' then \n"
//                    + "if( (select qte from " + BD + ".`reservation`\n"
//                    + "where `Code` = old .Entete_mvts_Code and  `code_produit`=old .produit_code and  \n"
//                    + " `lot` = old .lot  ) - old.quantite  != 0 ) then \n"
//                    + "update  " + BD + ".`reservation`  \n"
//                    + "set qte =  qte - old.quantite  \n"
//                    + "where `Code` = old .Entete_mvts_Code and  `code_produit`=old .produit_code and  \n"
//                    + " `lot` = old .lot ; \n"
//                    + " else\n"
//                    + " \n"
//                    + " delete from  " + BD + ".`reservation`   \n"
//                    + "where `Code` = old .Entete_mvts_Code and  `code_produit`=old .produit_code and  \n"
//                    + " `lot` = old .lot ;\n"
//                    + " end if ;  \n"
//                    + " select (select code  from  " + BD + " .entete_mvts  WHERE `code` = old.Entete_mvts_Code \n"
//                    + "and `Type_mvt`=old.type_mvt \n"
//                    + "and `Type_bon`=old .type_bon) is not null into existe ; \n"
//                    + "\n"
//                    + "if existe =1 then \n"
//                    + "begin\n"
//                    + "\n"
//                    + " update  " + BD + ".entete_mvts \n"
//                    + "set total = round(total - (abs(old.quantite)* old.prix)+0.001 , 2) , \n"
//                    + "\n"
//                    + "tva1= ifnull(tva1,0) - round ( if(old.`T_TVA`='0' ,(if((old.`T_TVA`='17' or old.`T_TVA`='19'),((((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) - ((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100))) -((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix))-(((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100)) * (if(total=0, 0, ifnull(montant_remise,0)/total)) ))- (((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) - ((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100))) - ((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix))-(((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100)) * (if(total=0, 0, ifnull(montant_remise,0)/total)) )) *ifnull(TX_Remise, 0)/100))*old.`T_TVA`/100),0)) \n"
//                    + ",0) +0.001 , 2 ),\n"
//                    + "tva2=ifnull(tva2,0) - round( if(old.`T_TVA`='7' or old.`T_TVA`='9' ,(if((old.`T_TVA`='7' or old.`T_TVA`='9'),((((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) - ((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100))) -((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix))-(((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100)) * (if(total=0, 0, ifnull(montant_remise,0)/total)) ))- (((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) - ((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100))) - ((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix))-(((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100)) * (if(total=0, 0, ifnull(montant_remise,0)/total)) )) *ifnull(TX_Remise, 0)/100))*old.`T_TVA`/100),0) )\n"
//                    + ",0) +0.001, 2) , \n"
//                    + "tva3= ifnull(tva3,0) - round( if(old.`T_TVA`='17' or old.`T_TVA`='19' , (if((old.`T_TVA`='17' or old.`T_TVA`='19'),((((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) - ((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100))) -((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix))-(((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100)) * (if(total=0, 0, ifnull(montant_remise,0)/total)) ))- (((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) - ((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100))) - ((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix))-(((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100)) * (if(total=0, 0, ifnull(montant_remise,0)/total)) )) *ifnull(TX_Remise, 0)/100))*old.`T_TVA`/100),0)) \n"
//                    + ",0) +0.001, 2 ) ,\n"
//                    + "remise = round(ifnull(remise,0) - ifnull(round((old .valeur -ifnull(((old.valeur -(abs(old.ug)* old.prix)) - (((old.valeur -(abs(old.ug)* old.prix)) * old.`RABAIS`/100))),0) )+0.001 ,2 )+0.001 , 2),0),\n"
//                    + "tva = ifnull(tva,0) - round( (((((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) - ((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100))) -((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix))-(((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100)) * (if(total=0, 0, ifnull(montant_remise,0)/total)) ))- (((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) - ((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100))) - ((((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix))-(((abs(old.quantite)* old.prix) -(abs(old.ug)* old.prix)) * old .`RABAIS`/100)) * (if(total=0, 0, ifnull(montant_remise,0)/total)) )) *ifnull(TX_Remise, 0)/100))*old.`T_TVA`/100)) \n"
//                    + "+0.001  , 2),\n"
//                    + "`TTC_FIX`=round(total+tva-remise+0.001,2) ,\n"
//                    + "`TTC`= round(total+tva-remise +0.001,2)  \n"
//                    + "\n"
//                    + "WHERE `code` = old.Entete_mvts_Code \n"
//                    + "and `Type_mvt`=old.type_mvt \n"
//                    + "and `Type_bon`=old .type_bon   ;\n"
//                    + " \n"
//                    + "end ;\n"
//                    + "\n"
//                    + "end if ; \n"
//                    + "\n"
//                    + "\n"
//                    + "\n"
//                    + " END IF  ; \n"
//                    + "\n"
//                    + "end");
            
            
            
     
         con.close();
            JOptionPane.showMessageDialog(null, "Fin ", "Information", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            JOptionPane.showMessageDialog(null, " " + errors.toString(), "erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();

        }
    }

}
