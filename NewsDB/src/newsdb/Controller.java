/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsdb;


import frames.FrameController;
import classes.*;

import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author stanislaw
 */
public class Controller {
    private String func[] = {"add element", "search element", "delete element", "sample element"};
//    private General gFrame = new General(this);
//    private Setting sFrame = new Setting(this);
//    private Functions fFrame = new Functions(this);
//    private AddNewsFrame anFrame = new AddNewsFrame(this);
//    private OpenNewsFrame onFrame = new OpenNewsFrame(this);
//    private SelectOpenNewsFrame soFrame = new SelectOpenNewsFrame(this);
//    private ComentsViewFrame cvFrame = new ComentsViewFrame(this);
//    private CategoryFrame cFrame = new CategoryFrame(this);
//    private PartnerNewsFrame pnFrame = new PartnerNewsFrame(this);
//    private OtherInformationFrame oiFrame = new OtherInformationFrame(this);
//    private OprosFrame oFrame = new OprosFrame(this);
    
    public FrameController frameController = new FrameController(this);
    
    private mySQL SQL = new mySQL(frameController.gFrame);
    private String user = "rootor", password="root", DBName, SERVER = "127.0.0.1";
    private String PORT = "3306";
    
//    public void show_general_frame() {
//        gFrame.setVisible(true);
//    }
//       
//    public void show_opros_frame() {
//        oFrame.setVisible(true);
//    }
//    
//    public void hide_opros_frame() {
//        oFrame.setVisible(false);
//    }
//    
//    public void show_other_information_frame() {
//        gFrame.setVisible(false);
//        oiFrame.setVisible(true);
//    }
//    
//    public void hide_other_information_frame() {
//        gFrame.setVisible(true);
//        oiFrame.setVisible(false);
//    }
//    
//    public void show_partner_news_frame() {
//        pnFrame.setVisible(true);
//    }
//    
//    public void hide_cpartner_news_frame() {
//        pnFrame.setVisible(false);
//    }
//    
//    public void show_category_frame() {
//        gFrame.setVisible(false);
//        cFrame.setVisible(true);
//    }
//    
//    public void hide_category_frame() {
//        gFrame.setVisible(true);
//        cFrame.setVisible(false);
//    }
//    
//    public void show_setting_frame() {
//        gFrame.setEnabled(false);
//        sFrame.setVisible(true);
//    }
//    
//    public void hide_setting_frame() {
//        gFrame.setEnabled(true);
//        sFrame.setVisible(false);
//    }
    
//    public ImageIcon createIcon(String path) {
//        URL imgURL = SelectOpenNewsFrame.class.getResource(path);     
//        if (imgURL != null) {
//            return new ImageIcon(imgURL);
//        } else {
//            System.err.println("File not found " + path);
//            return null;
//        }
//    }
    
//    public void show_coments_view_frame() {
//
//        cvFrame.setVisible(true);
//    }
//    
//    public void hide_coments_view_frame() {
//        cvFrame.setVisible(false);
//    }
//    
    public void connect_db() {
        SQL.set_table(frameController.gFrame.jComboBox1.getSelectedItem().toString());
        SQL.set_connect_info(SERVER, PORT, DBName, user, password);
        System.out.println(SQL.Conect());
        write_to_table();
    }
    
    public void load_fanctions() {
        frameController.gFrame.jComboBox2.removeAllItems();
        for (String s : func) {
            frameController.gFrame.jComboBox2.addItem(s);
        }
    }
    
    public void do_functions(int functionsNumb) {
        switch (functionsNumb) {
            case 0: 
                add_element();
                break;
            case 1: 
//                search_elements();
                break;
            case 2: 
                delete_element(frameController.gFrame.jTable1.getSelectedRow());
                break;    
        }
    }
    
//    public void show_add_news_frame() {
//        anFrame.setVisible(true);
//        gFrame.setVisible(false);
//    }
//    
//    public void hide_add_news_frame() {
//        anFrame.setVisible(false);
//        gFrame.setVisible(true);
//    }
//    
//    public void show_open_news_frame() {
//        onFrame.setVisible(true);
//        gFrame.setVisible(false);
//    }
//    
//    public void hide_open_news_frame() {
//        onFrame.setVisible(false);
//        gFrame.setVisible(true);
//    }
    
    public void delete_element(int selected) {
        int X=(int) Integer.valueOf((String)frameController.gFrame.jTable1.getValueAt(selected, 0));
        SQL.del(X, frameController.gFrame.jTable1.getColumnName(0));
        connect_db();
    }
    
    public void search_news(String search){
        DefaultListModel listModelNumber = new DefaultListModel();
        listModelNumber.removeAllElements();
        frameController.onFrame.jList1.setModel(listModelNumber);
        Map<Integer, String> newses = new HashMap<Integer, String>();
        ArrayList<News> news = load_news_from_db();
        
        ArrayList<News> newsSorted = new ArrayList<News>();
        search = search.replaceAll("\\W", " ");
        String searchAr[] = search.split(" ");
        
        ArrayList<String> searchSorted = new ArrayList<String>();
        searchSorted.addAll(Arrays.asList(searchAr));
        
        for (int l = 0; l < news.size(); l++) {
            for (String searchSorted1 : searchSorted) {
                String index = searchSorted1;
                if (searchSorted1.contains(news.get(l).autor) || searchSorted1.contains(news.get(l).date) || searchSorted1.contains(news.get(l).time) || searchSorted1.contains(news.get(l).photo) || searchSorted1.equals(news.get(l).index) || searchSorted1.contains(news.get(l).photoTitle)) {
                    newsSorted.add(news.get(l));
                } else {
                    String[] textes;
                    textes = news.get(l).title.split(" ");
                    for (String texte : textes) {
                        if (searchSorted1.equals(texte)) {
                            newsSorted.add(news.get(l));
                            break;
                        }
                    }
                    
                    textes = news.get(l).txt.split(" ");
                    for (String texte : textes) {
                        if (searchSorted1.equals(texte)) {
                            newsSorted.add(news.get(l));
                            break;
                        }
                    }
                    
                    textes = news.get(l).tegs.split(",");
                    for (String texte : textes) {
                        if (searchSorted1.equals(texte)) {
                            newsSorted.add(news.get(l));
                            break;
                        }
                    }
                    
                    textes = news.get(l).photoTitle.split(" ");
                    for (String texte : textes) {
                        if (searchSorted1.equals(texte)) {
                            newsSorted.add(news.get(l));
                            break;
                        }
                    }
                }
            }
        }
        
        for (int i = 0; i < newsSorted.size(); i++) {
            listModelNumber.addElement("index: " + newsSorted.get(i).index + ", " + "Title: '" + newsSorted.get(i).title + "' by '" + newsSorted.get(i).autor + "' at '" + newsSorted.get(i).date + "' in '" + newsSorted.get(i).time + "' tegs: '" + newsSorted.get(i).tegs + "'");
        }
    }
    
    public void consecrate_id(ArrayList<News> news) {
        
    }
    
    public void add_news_auto(ArrayList<News> news, String index) {
        SQL.delAll();
        
        for (int i = 0; i < news.size(); i++) {
            if(index.equals(news.get(i).index)) {
                news.get(i).autor = frameController.soFrame.autorTextPane.getText();
                news.get(i).title = frameController.soFrame.titleTextPane.getText();
                news.get(i).photo = frameController.soFrame.photoTextPane.getText();
                news.get(i).title = frameController.soFrame.titleTextPane.getText();
                news.get(i).txt = frameController.soFrame.contentTextPane.getText();
                news.get(i).tegs = frameController.soFrame.tegsTextPane.getText();
                news.get(i).photoTitle = frameController.soFrame.photoTitleTextPane.getText();
            }
        }

        for (int i = 0 ; i < news.size(); i++) {
            add_news(news.get(i).title, news.get(i).date, news.get(i).time, news.get(i).txt, news.get(i).autor, news.get(i).tegs, news.get(i).photo, news.get(i).photoTitle);
        }
    }
    
//    public void show_select_news_frame() {
//        soFrame.setVisible(true);
//    }
    
    public ArrayList<News> load_news_from_db() {
        ArrayList newsString = SQL.GetData();

        int col = (int) newsString.get(0);
        
        for (int i =0; i < col+1; i++) {
            newsString.remove(0);
        }
        
        ArrayList<News> news = new ArrayList<News>();
        
        for (int l = 0; l < newsString.size(); l++) {
            News newsN = new News((String)newsString.get(l),
                        (String)newsString.get(l+1),
                        (String)newsString.get(l+2),
                        (String)newsString.get(l+3),
                        (String)newsString.get(l+4),
                        (String)newsString.get(l+5),
                        (String)newsString.get(l+6),
                        (String)newsString.get(l+7),
                        (String)newsString.get(l+8));
            System.out.println("Title: '" + newsN.title + "' by '" + newsN.autor + "' at '" + newsN.date + "' in '" + newsN.time + "' tegs: '" + newsN.tegs + "'");
            news.add(newsN);
            l+=8;
        }

        return news;
    }
    
    public void load_frame_info(int selected) {
        ArrayList<News> news = load_news_from_db();
        String s[] = frameController.onFrame.jList1.getSelectedValue().replaceAll("\\W", " ").split(" ");
        String index = s[2];
        frameController.soFrame.selectedIndex = index;
        System.err.println("i: " + index);
        for (int i = 0; i < news.size(); i++) {
            if(index.equals(news.get(i).index)) {
                frameController.soFrame.titleTextPane.setText(news.get(i).title);
                frameController.soFrame.contentTextPane.setText(news.get(i).txt);
                frameController.soFrame.tegsTextPane.setText(news.get(i).tegs);
                frameController.soFrame.dataLabel.setText(news.get(i).date);
                frameController.soFrame.timeLabel.setText(news.get(i).time);
                frameController.soFrame.autorTextPane.setText(news.get(i).autor);
                frameController.soFrame.photoTextPane.setText(news.get(i).photo);
                frameController.soFrame.photoTitleTextPane.setText(news.get(i).photoTitle);
            }
        }
    }
    
    public void load_news(){
        DefaultListModel listModelNumber = new DefaultListModel();
        listModelNumber.removeAllElements();
        frameController.onFrame.jList1.setModel(listModelNumber);
        ArrayList<News> news = load_news_from_db();
        
        for (int i = 0; i < news.size(); i++) {
            listModelNumber.addElement("index: " + news.get(i).index + ", " + "Title: | " + news.get(i).title + " | by '" + news.get(i).autor + " | at | " + news.get(i).date + " |  in | " + news.get(i).time + " | tegs: | " + news.get(i).tegs + " | ");
        }
        
        frameController.soFrame.news = news;
    }
    
    public void add_news(String title, String date, String time, String txt, String autor, String tegs, String photo, String photoTitle) {
        ArrayList columnArr = new ArrayList();
        for (int i = 0; i < frameController.gFrame.jTable2.getModel().getColumnCount(); i++) {
            columnArr.add(frameController.gFrame.jTable2.getModel().getColumnName(i));
        }
        
        News news = new News("0", title, date, time, txt, autor, tegs, photo, photoTitle);
        String value[] = new String[columnArr.size()];
        value[0] = "0";
        value[1] = title;
        value[2] = date;
        value[3] = time;
        value[4] = txt;
        value[5] = autor;
        value[6] = tegs;
        value[7] = photo;
        value[8] = photoTitle;
        
        try {
            
            PreparedStatement X = SQL.insert(columnArr);

            for (int i = 0; i < columnArr.size(); i++) {
                X.setString(i+1, value[i]);
            }

            X.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } 

        consecrate_id(load_news_from_db());
        connect_db();
    }
    
    public void add_element() {
        ArrayList columnArr = new ArrayList();
        for (int i = 0; i < frameController.gFrame.jTable2.getModel().getColumnCount(); i++) {
            columnArr.add(frameController.gFrame.jTable2.getModel().getColumnName(i));
        }

        PreparedStatement X = SQL.insert(columnArr);
        try {
            int l = 1;
            for (int i = 0; i < frameController.gFrame.jTable2.getModel().getColumnCount(); i++) {
                X.setString(l, (String) frameController.gFrame.jTable2.getModel().getValueAt(0, i));
                l++;
            }
            X.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        connect_db();
    }
    
//    public void show_functions_frame() {
//        fFrame.setVisible(true);
//        for (String s : func) {
//            fFrame.jComboBox2.addItem(s);
//        }
//    }
    
    public void write_to_table() {
        ArrayList list = SQL.GetData();
        System.out.println(list);
        String[] columns = new String[(int)list.get(0)];

        list.remove(0);
        
        for (int i = 0; i < columns.length; i++) { 
            columns[i] = (String) list.get(i);
            System.out.println(columns[i]);
        }
        
        
        
        for (int i = 0; i < columns.length; i++) { 
            list.remove(0);
        }
        
        String[][] data = new String[][]{};
        String[][] data2 = new String[1][1];
        
        frameController.gFrame.jTable1.setModel(new DefaultTableModel(data,columns));
        frameController.gFrame.jTable2.setModel(new DefaultTableModel(data2,columns));
        DefaultTableModel model = (DefaultTableModel) frameController.gFrame.jTable1.getModel();
        System.out.println("tupo pizda - " + list.size()/columns.length);
        ArrayList s = new ArrayList();
        for (Object strings : list) {
            s.add(strings);
        }
        for (int i = 0; i < list.size()/columns.length; i++) {
            model.addRow(new String[columns.length]);
            for (int j = 0; j < columns.length; j++) {
                frameController.gFrame.jTable1.setValueAt(s.get(j), i, j);
            }
            for (int k = 0; k < columns.length; k++) { 
                s.remove(0);
            }
        }   
    }
    
    public void check_available_table() {
        
        SQL.set_connect_info(SERVER, PORT, DBName, user, password);
        ArrayList<String> tables = SQL.get_available_table();
        try {
            frameController.gFrame.jComboBox1.removeAllItems();
            tables.forEach((a) -> {
                frameController.gFrame.jComboBox1.addItem(a);
            });
            for (int i = tables.size(); i < (tables.size()*2); i++) {
                frameController.gFrame.jComboBox1.removeItemAt(i);
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
        
        }
    }
    
    public void load_setting_info() {
        frameController.sFrame.jTextField1.setText(SERVER);
        frameController.sFrame.jTextField2.setText(PORT);
        frameController.sFrame.jTextField3.setText(this.user);
            frameController.sFrame.jTextField4.setText(this.password);
    }
    
    public void set_connect_info(String user, String password, String SERVER, String PORT) {
        this.user = user;
        this.password = password;
        this.SERVER = SERVER;
        this.PORT = PORT;
    }
    
    public void set_user(String user){
        this.user = user;
    }
    
    public void set_password(String password){
        this.password = password;
    }
    
    public void set_DBName(String DBName){
        this.DBName = DBName;
    }
    
    public void set_SERVER(String SERVER){
        this.SERVER = SERVER;
    }
    
    public void set_PORT(String PORT){
        this.PORT = PORT;
    }
    
    public String get_user(){
        return this.user;
    }
    
    public String get_password(){
        return this.password;
    }
    
    public String get_DBName(){
        return this.DBName;
    }
    
    public String get_SERVER(){
        return this.SERVER;
    }
    
    public String get_PORT(){
        return this.PORT;
    }
    
}





