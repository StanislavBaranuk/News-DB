/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsdb;


import frames.FrameController;

import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author stanislaw
 */
public class Controller {
    private String func[] = {"add element", "search element", "delete element", "sample element"};   
    public FrameController frameController = new FrameController(this);
    private mySQL SQL = new mySQL(frameController.gFrame);
    private String user = "rootor", password="root", DBName, SERVER = "127.0.0.1";
    private String PORT = "3306";
    
    public void connect_db() {
        SQL.set_table(frameController.gFrame.jComboBox1.getSelectedItem().toString());
        SQL.set_connect_info(SERVER, PORT, DBName, user, password);
        System.out.println(SQL.Conect());
        write_to_table();
    }
    
    public void update_conect(String table) {
        SQL.set_table(table);
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
    
    public ArrayList<classes.Coments> load_coments_from_db() {
        update_conect("coments");
        ArrayList comentsString = SQL.GetData();
        
        int col = (int) comentsString.get(0);
        
        for (int i =0; i < col+1; i++) {
            comentsString.remove(0);
        }
        
        ArrayList<classes.Coments> coments = new ArrayList<classes.Coments>();
        
        for (int l = 0; l < comentsString.size(); l++) {
            classes.Coments coment = new classes.Coments((String)comentsString.get(l),
                        (String)comentsString.get(l+1),
                        (String)comentsString.get(l+2),
                        (String)comentsString.get(l+3),
                        (String)comentsString.get(l+4),
                        (String)comentsString.get(l+5),
                        (String)comentsString.get(l+6),
                        (String)comentsString.get(l+7),
                        (String)comentsString.get(l+8));
            System.out.println("answer: '" + coment.answer + 
                               "' by '" + coment.autor + 
                               "' country '" + coment.country + 
                               "' in '" + coment.time + 
                               "' data: '" + coment.data +
                                "' id: '" + coment.id + 
                                "' like: '" + coment.like +
                                "' news: '" + coment.news +
                                "' text: '" + coment.text);
            coments.add(coment);
            l+=8;
        }
        return coments;
    }
    
    public void load_coments(int index, String title) {
        DefaultListModel listModelNumber = new DefaultListModel();
        listModelNumber.removeAllElements();
        frameController.cvFrame.jList1.setModel(listModelNumber);
        ArrayList<classes.Coments> coments = load_coments_from_db();
        
        for (int i = 0; i < coments.size(); i++) {
            if (index+1 != Integer.parseInt(coments.get(i).news)) {
            listModelNumber.addElement("index: " + 
                            coments.get(i).id + 
                            ", " + 
                            "text: " + 
                            coments.get(i).text + 
                            " ,by: '" + 
                            coments.get(i).autor + 
                            " ,at: " + 
                            coments.get(i).data + 
                            " ,in " + 
                            coments.get(i).time + 
                            " ,likes: " + 
                            coments.get(i).like + 
                            " ,country: " 
                            + coments.get(i).country);
            }
        }
    }
    
    private void createNodes(DefaultMutableTreeNode top, int index) {
        ArrayList<DefaultMutableTreeNode> categoryArr = new ArrayList<DefaultMutableTreeNode>();
        
//        DefaultMutableTreeNode
//        DefaultMutableTreeNode book = null;
//
//        category = new DefaultMutableTreeNode("Books for Java Programmers");
//        top.add(category);
//
//        //original Tutorial
//        book = new DefaultMutableTreeNode("The Java Tutorial: A Short Course on the Basics");
//        category.add(book);
//
//        //Tutorial Continued
//        book = new DefaultMutableTreeNode("The Java Tutorial Continued: The Rest of the JDK");
//        category.add(book);
//
//        //Swing Tutorial
//        book = new DefaultMutableTreeNode("The Swing Tutorial: A Guide to Constructing GUIs");
//        category.add(book);
//
//        //...add more books for programmers...
//
//        category = new DefaultMutableTreeNode("Books for Java Implementers");
//        top.add(category);
//
//        //VM
//        book = new DefaultMutableTreeNode("The Java Virtual Machine Specification");
//        category.add(book);
//
//        //Language Spec
//        book = new DefaultMutableTreeNode("The Java Language Specification");
//        category.add(book);
        
        ArrayList<classes.Coments> coments = load_coments_from_db();
        DefaultMutableTreeNode category, podCat;
        for (int i = 0; i < coments.size(); i++) {
            if (index+1 != Integer.parseInt(coments.get(i).news)) {
                
                if (!coments.get(i).answer.equals("-1")) {
                    category = new DefaultMutableTreeNode("index: " + 
                            coments.get(i).id + 
                            ", " + 
                            "     text: " + 
                            coments.get(i).text + 
                            "     ,by: '" + 
                            coments.get(i).autor + 
                            "     ,at: " + 
                            coments.get(i).data + 
                            "    ,in " + 
                            coments.get(i).time + 
                            "     ,likes: " + 
                            coments.get(i).like + 
                            "     ,country: " 
                            + coments.get(i).like);
                    categoryArr.add(category);
                } else {
                    category = new DefaultMutableTreeNode("index: " + 
                            coments.get(i).id + 
                            ", " + 
                            "text: " + 
                            coments.get(i).text + 
                            " ,by: '" + 
                            coments.get(i).autor + 
                            " ,at: " + 
                            coments.get(i).data + 
                            " ,in " + 
                            coments.get(i).time + 
                            " ,likes: " + 
                            coments.get(i).like + 
                            " ,country: " 
                            + coments.get(i).like);
                    
                    categoryArr.get(Integer.parseInt(coments.get(i).answer)).add(category);
                    top.add(category);
                }
            }
        }
        
        for (int i = 0; i < categoryArr.size(); i++) {
            top.add(categoryArr.get(i));
        }
            
        
    }
    
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
        ArrayList<classes.News> news = load_news_from_db();
        
        ArrayList<classes.News> newsSorted = new ArrayList<classes.News>();
        search = search.replaceAll("\\W", " ");
        String searchAr[] = search.split(" ");
        
        ArrayList<String> searchSorted = new ArrayList<String>();
        searchSorted.addAll(Arrays.asList(searchAr));
        
        for (int l = 0; l < news.size(); l++) {
            for (String searchSorted1 : searchSorted) {
                String index = searchSorted1;
                if (searchSorted1.contains(news.get(l).autor) || 
                    searchSorted1.contains(news.get(l).date) || 
                    searchSorted1.contains(news.get(l).time) || 
                    searchSorted1.contains(news.get(l).photo) || 
                    searchSorted1.equals(news.get(l).index) || 
                    searchSorted1.contains(news.get(l).photoTitle)) {
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
            listModelNumber.addElement("index: " + newsSorted.get(i).index + ", " +
                                       "Title: '" + newsSorted.get(i).title + "' by '" + 
                                        newsSorted.get(i).autor + "' at '" + 
                                        newsSorted.get(i).date + "' in '" + 
                                        newsSorted.get(i).time + "' tegs: '" + 
                                        newsSorted.get(i).tegs + "'");
        }
    }
    
    public void search_autors(String search){
        DefaultListModel listModelNumber = new DefaultListModel();
        listModelNumber.removeAllElements();
        frameController.onFrame.jList1.setModel(listModelNumber);
        Map<Integer, String> autores = new HashMap<Integer, String>();
        ArrayList<classes.Account> autors = load_autors_from_db();
        
        ArrayList<classes.Account> autorsSorted = new ArrayList<classes.Account>();
        search = search.replaceAll("\\W", " ");
        String searchAr[] = search.split(" ");
        
        ArrayList<String> searchSorted = new ArrayList<String>();
        searchSorted.addAll(Arrays.asList(searchAr));
        //name, surname, avatar, burthday, sex, country, speciality, about, email, password
        for (int l = 0; l < autors.size(); l++) {
            for (String searchSorted1 : searchSorted) {
                String index = searchSorted1;
                if (searchSorted1.contains(autors.get(l).id) || 
                    searchSorted1.contains(autors.get(l).name) || 
                    searchSorted1.contains(autors.get(l).surname) || 
                    searchSorted1.contains(autors.get(l).burthday) || 
                    searchSorted1.contains(autors.get(l).sex) || 
                    searchSorted1.equals(autors.get(l).country) || 
                    searchSorted1.contains(autors.get(l).speciality) ||
                    searchSorted1.contains(autors.get(l).about) ||
                    searchSorted1.contains(autors.get(l).email) ||
                    searchSorted1.contains(autors.get(l).password)) {
                    autorsSorted.add(autors.get(l));
                } else {
                    String[] textes;
                    textes = autors.get(l).name.split(" ");
                    for (String texte : textes) {
                        if (searchSorted1.equals(texte)) {
                            autorsSorted.add(autors.get(l));
                            break;
                        }
                    }
                    
                    textes = autors.get(l).surname.split(" ");
                    for (String texte : textes) {
                        if (searchSorted1.equals(texte)) {
                            autorsSorted.add(autors.get(l));
                            break;
                        }
                    }
                    
                    textes = autors.get(l).sex.split(",");
                    for (String texte : textes) {
                        if (searchSorted1.equals(texte)) {
                            autorsSorted.add(autors.get(l));
                            break;
                        }
                    }
                    
                    textes = autors.get(l).country.split(" ");
                    for (String texte : textes) {
                        if (searchSorted1.equals(texte)) {
                            autorsSorted.add(autors.get(l));
                            break;
                        }
                    }
                    
                    textes = autors.get(l).speciality.split(" ");
                    for (String texte : textes) {
                        if (searchSorted1.equals(texte)) {
                            autorsSorted.add(autors.get(l));
                            break;
                        }
                    }
                    
                    textes = autors.get(l).email.split(" ");
                    for (String texte : textes) {
                        if (searchSorted1.equals(texte)) {
                            autorsSorted.add(autors.get(l));
                            break;
                        }
                    }
                }
            }
        }
        
        for (int i = 0; i < autorsSorted.size(); i++) {
            listModelNumber.addElement("index: " + autorsSorted.get(i).id + ", " +
                                       " name: " + autorsSorted.get(i).name  + 
                                        " surname: " + autorsSorted.get(i).surname  + 
                                        " country: " + autorsSorted.get(i).country + 
                                        " speciality: " + autorsSorted.get(i).speciality + 
                                        " email: " + autorsSorted.get(i).email);
        }
    }
    
    public void consecrate_id(ArrayList<classes.Account> news) {
        
    }
    
    public void add_news_auto(ArrayList<classes.News> news, String index) {
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
            add_news(news.get(i).title, 
                     news.get(i).date, 
                     news.get(i).time, 
                     news.get(i).txt, 
                     news.get(i).autor, 
                     news.get(i).tegs, 
                     news.get(i).photo, 
                     news.get(i).photoTitle);
        }
    }
    
    public void add_autors_auto(ArrayList<classes.Account> accounts, String index) {
        SQL.delAll();
        //id, name, surname, avatar, burthday, sex, country, speciality, about, email, password;
        for (int i = 0; i < accounts.size(); i++) {
            if(index.equals(accounts.get(i).id)) {
                accounts.get(i).name = frameController.soaFrame.nameTextField.getText();
                accounts.get(i).surname = frameController.soaFrame.surnameField.getText();
                accounts.get(i).avatar = null;
                accounts.get(i).burthday = null;
                accounts.get(i).sex = null;
                accounts.get(i).country = null;
                accounts.get(i).speciality = null;
                accounts.get(i).about = null;
                accounts.get(i).email = frameController.soaFrame.emailField.getText();
                accounts.get(i).password = null;
            }
        }

        for (int i = 0 ; i < accounts.size(); i++) {
            add_autors(accounts.get(i).name, 
                       accounts.get(i).surname, 
                       accounts.get(i).avatar, 
                       accounts.get(i).burthday, 
                       accounts.get(i).sex, 
                       accounts.get(i).country, 
                       accounts.get(i).speciality, 
                       accounts.get(i).about, 
                       accounts.get(i).email, 
                       accounts.get(i).password);
        }
    }
    
    public ArrayList<classes.News> load_news_from_db() {
        ArrayList newsString = SQL.GetData();

        int col = (int) newsString.get(0);
        
        for (int i =0; i < col+1; i++) {
            newsString.remove(0);
        }
        
        ArrayList<classes.News> news = new ArrayList<classes.News>();
        
        for (int l = 0; l < newsString.size(); l++) {
            classes.News newsN = new classes.News((String)newsString.get(l),
                        (String)newsString.get(l+1),
                        (String)newsString.get(l+2),
                        (String)newsString.get(l+3),
                        (String)newsString.get(l+4),
                        (String)newsString.get(l+5),
                        (String)newsString.get(l+6),
                        (String)newsString.get(l+7),
                        (String)newsString.get(l+8));
            System.out.println("Title: '" + newsN.title + 
                               "' by '" + newsN.autor + 
                               "' at '" + newsN.date + 
                               "' in '" + newsN.time + 
                               "' tegs: '" + newsN.tegs + "'");
            news.add(newsN);
            l+=8;
        }

        return news;
    }
    
    public void load_frame_info(int selected) {
        ArrayList<classes.News> news = load_news_from_db();
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
    
    public void load_autors_frame_info(int selected) {
        ArrayList<classes.Account> autors = load_autors_from_db();
        String s[] = frameController.onFrame.jList1.getSelectedValue().replaceAll("\\W", " ").split(" ");
        String index = s[2];
        frameController.soaFrame.selectedIndex = index;
        System.err.println("i: " + index);
        //id, name, surname, avatar, burthday, sex, country, speciality, about, email, password
        for (int i = 0; i < autors.size(); i++) {
            if(index.equals(autors.get(i).id)) {
                frameController.soaFrame.nameTextField.setText(autors.get(i).name);
                frameController.soaFrame.surnameField.setText(autors.get(i).surname);
                frameController.soaFrame.emailField.setText(autors.get(i).email);
                frameController.soaFrame.fotoField.setText(autors.get(i).avatar);
                frameController.soaFrame.profTextField.setText(autors.get(i).speciality);
                frameController.soaFrame.aboutTextPane.setText(autors.get(i).about);
//                frameController.soaFrame.dataLabel.setText(autors.get(i).date);
//                frameController.soaFrame.timeLabel.setText(autors.get(i).time);
//                frameController.soaFrame.autorTextPane.setText(autors.get(i).autor);
//                frameController.soaFrame.photoTextPane.setText(autors.get(i).photo);
//                frameController.soaFrame.photoTitleTextPane.setText(autors.get(i).photoTitle);
            }
        }
    }
    
    public void add_news(String title, String date, String time, String txt, 
                         String autor, String tegs, String photo, String photoTitle) {
        update_conect("news");
        ArrayList columnArr = new ArrayList();
        for (int i = 0; i < frameController.gFrame.jTable2.getModel().getColumnCount(); i++) {
            columnArr.add(frameController.gFrame.jTable2.getModel().getColumnName(i));
        }
        
        classes.News news = new classes.News("0", title, date, time, txt, autor, tegs, photo, photoTitle);
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

//        consecrate_id(load_news_from_db());
        connect_db();
    }
    
    public void add_autors(String name, String surname, String avatar, String burthday, String sex, 
                           String country, String speciality, String about, String email, String password) {
        update_conect("autors");
        ArrayList columnArr = new ArrayList();
        for (int i = 0; i < frameController.gFrame.jTable2.getModel().getColumnCount(); i++) {
            columnArr.add(frameController.gFrame.jTable2.getModel().getColumnName(i));
        }
        
        classes.Account account = new classes.Account("0", name, surname, avatar, burthday, sex, 
                                                      country, speciality, about, email, password);
        String value[] = new String[columnArr.size()];
        value[0] = "0";
        value[1] = name;
        value[2] = surname;
        value[3] = avatar;
        value[4] = burthday;
        value[5] = sex;
        value[6] = country;
        value[7] = speciality;
        value[8] = about;
        value[9] = email;
        value[10] = password;
        
        try {
            PreparedStatement X = SQL.insert(columnArr);
            for (int i = 0; i < columnArr.size(); i++) {
                X.setString(i+1, value[i]);
            }
            X.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        consecrate_id(load_autors_from_db());
        connect_db();
    }
    
    public ArrayList<classes.Account> load_autors_from_db() {
        ArrayList autorsString = SQL.GetData();
        int col = (int) autorsString.get(0);
        for (int i =0; i < col+1; i++) {
            autorsString.remove(0);
        }
        ArrayList<classes.Account> acounts = new ArrayList<classes.Account>();
        for (int l = 0; l < autorsString.size(); l++) {
            classes.Account acountsA = new classes.Account((String)autorsString.get(l),
                        (String)autorsString.get(l+1),
                        (String)autorsString.get(l+2),
                        (String)autorsString.get(l+3),
                        (String)autorsString.get(l+4),
                        (String)autorsString.get(l+5),
                        (String)autorsString.get(l+6),
                        (String)autorsString.get(l+7),
                        (String)autorsString.get(l+8),
                        (String)autorsString.get(l+9),
                        (String)autorsString.get(l+10));
            acounts.add(acountsA);
            l+=10;
        }
        return acounts;
    }
    
    public void load_news(){
        update_conect("news");
        DefaultListModel listModelNumber = new DefaultListModel();
        listModelNumber.removeAllElements();
        frameController.onFrame.jList1.setModel(listModelNumber);
        ArrayList<classes.News> news = load_news_from_db();
        
        for (int i = 0; i < news.size(); i++) {
            listModelNumber.addElement("index: " + news.get(i).index + ", " + 
                                       "Title: | " + news.get(i).title + " | by '" + 
                                        news.get(i).autor + " | at | " + 
                                        news.get(i).date + " |  in | " + 
                                        news.get(i).time + " | tegs: | " + 
                                        news.get(i).tegs + " | ");
        }
        
        frameController.soFrame.news = news;
    }
    
    public void load_autors(){
        update_conect("autors");
        DefaultListModel listModelNumber = new DefaultListModel();
        listModelNumber.removeAllElements();
        frameController.oaFrame.jList1.setModel(listModelNumber);
        ArrayList<classes.Account> autors = load_autors_from_db();
        System.out.println("\t\tload_autors_from_db");
        for (int i = 0; i < autors.size(); i++) {
            listModelNumber.addElement("index: " + autors.get(i).id +
                                       " name: " + autors.get(i).name  + 
                                        " surname: " + autors.get(i).surname  + 
                                        " country: " + autors.get(i).country + 
                                        " speciality: " + autors.get(i).speciality + 
                                        " email: " + autors.get(i).email);
            System.out.println("index: " + autors.get(i).id +
                                       " name: " + autors.get(i).name  + 
                                        " surname: " + autors.get(i).surname  + 
                                        " country: " + autors.get(i).country + 
                                        " speciality: " + autors.get(i).speciality + 
                                        " email: " + autors.get(i).email);
        }
        
        frameController.saFrame.autors = autors;
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





